<script setup>
import {ref} from 'vue'
import {onLoad} from '@dcloudio/uni-app'
//引入时间格式化工具函数
import {parseTime} from '@/utils/tool.js'


const options = ref(null) //接受index页面传递的参数

const detail = ref({}) //接收新闻的详情数据

//再onLoad生命周期获取参数，调用新闻详情接口。
onLoad((e)=>{
	console.log(e)
	//将接收到的参数，复制给options
	options.value = e;
	//访问新闻详情接口
	getDetail();
})

// 访问新闻详情的接口
function getDetail(){
	uni.request({
		url:"https://ku.qingnian8.com/dataApi/news/detail.php",
		data: options.value,
		success(res){
			console.log(res)
			// 处理图片bug
			res.data.content = res.data.content.replace(/<img/gi,'<img style="max-width:100%"')
			//对时间进行格式化处理
			res.data.posttime=parseTime(res.data.posttime)
			detail.value = res.data;
			//加载了该新闻，就说明已经浏览量，将其加入到缓存，作为浏览记录
			saveHistory()
			
			//动态设置导航条为：当前新闻的标题
			uni.setNavigationBarTitle({
				title:detail.value.title
			})
		}
	})
}

// 将浏览的新闻详情数据，加入到缓存
function saveHistory(){
	//读取或定义缓存数组
	let historyArr = uni.getStorageSync("historyArr") || []
	//设置要缓存的数据
	let item = {
		title: detail.value.title,
		id: detail.value.id,
		classid: detail.value.classid,
		picurl: detail.value.picurl,
		looktime: parseTime(Date.now())	
	}
	
	//将数据加入缓存前进行判断，防止添加重复的历史记录
	//方案：加入缓存之前，判断新闻id是否已在数组中，获取所在索引
	//处理：可以删除就记录添加新记录，也可以修改旧记录的日期
	let index = historyArr.findIndex(i=>{
		return i.id==detail.value.id
	})
	
	if(index>=0){
		historyArr.splice(index,1)
	}
	
	//将数据追加到数组
	historyArr.unshift(item)
	//截取前10条，也就是最多缓存10条数据
	historyArr = historyArr.slice(0,10);
	
	uni.setStorageSync("historyArr",historyArr)
	console.log(historyArr)
}
</script>

<template>
  <view class="detail">
		<!-- 文章的标题 -->
		<view class="title">
			{{detail.title}}
		</view>
		<!-- 文章的元信息 -->
		<view class="info">
			<view class="author">编辑：{{detail.author}}</view>
			<view class="time">发布日期：{{detail.posttime}}</view>
		</view>
		<!-- 文章的内容 -->
		<view class="content">
			<rich-text :nodes="detail.content"></rich-text>
		</view>
  </view>
</template>   

<style lang="scss" scoped>
.detail{
	paddign:30rpx;
	// 标题样式
	.title{
		font-size: 46rpx;
		color: #333;
	}
	// 元信息样式
	.info{
		background:#f6f6f6;
		padding:20rpx;
		font-size:25rpx;
		color:#666;
		display: flex;
		justify-content:space-between;
		margin:40rpx 0;
	}
	//文章内容
	.content{
		padding-bottom:50rpx;
	}
}
</style>

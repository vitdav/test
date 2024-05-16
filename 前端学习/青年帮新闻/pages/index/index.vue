<script setup>
import {ref} from 'vue'
import {onLoad,onReachBottom} from '@dcloudio/uni-app'
//设置导航条选项被选中时的下标
const navIndex = ref(0)
//导航条选项的点击事件
//点击时设置该选项高亮显示，并切换新闻列表的分类
function clickNav(index, id){
	navIndex.value = index;
	//切换分类时，需要先将页码和新闻列表数据进行初始化
	currentPage.value = 1;
	newsArr.value = [];
	getNewsData(id);
}

function goDetail(){
	uni.navigateTo({
		url:"/pages/detail/detail"
	})
}

//获取页面数据
const navArr = ref([]) //导航条
const newsArr = ref([]) //新闻列表
onLoad(()=>{
	//获取导航栏数据
	getNavData()
	//获取新闻列表数据
	getNewsData()
})

// 获取菜单栏数据
function getNavData(){
	uni.request({
		url:"https://ku.qingnian8.com/dataApi/news/navlist.php",
		success(res){
			navArr.value = res.data
			console.log(res)
		}
	})
}

// 获取新闻列表数据
const currentPage = ref(1) //当前加载的新闻列表页面
function getNewsData(id=50){
	uni.request({
		url:"https://ku.qingnian8.com/dataApi/news/newslist.php",
		data:{
			num:8, //显示的新闻数量，默认是8
			cid:id,//新闻所属的分类id
			page:currentPage.value,
		},
		success(res){
			console.log(res)
			// newsArr.value = res.data
			//为了进行触底加载，需要将上一页的数据和新数据进行拼接
			newsArr.value = [...newsArr.value,...res.data]
		}
	})
}

// 设置触底加载
onReachBottom(()=>{
	console.log("到底部了")
	//使当前页码+1
	currentPage.value++
	//使用新的页面，再次获取新闻列表数据
	getNewsData()
})
</script>
<template>
	<view class="home">
		<!-- 横向的导航条菜单 -->
		<scroll-view scroll-x class="navscroll">
			<view class="item" 
				v-for="(item,index) in navArr" :key="item.id"
				:class="index==navIndex?'active':''"
				@click="clickNav(index,item.id)"
			>{{item.classname}}</view>
		</scroll-view>
		
		<!-- 内容主题：新闻列表 -->
		<view class="content">
			<!-- 通过newsbox组件循环显示新闻项 -->
			<view class="row" v-for="item in newsArr" :key="item.id">
				<newsbox 
				:item="item"
				@click.native="goDetail"></newsbox>
			</view>
			<!-- 当某分类下没有新闻时，进行空白页处理 -->
			<view class="nodata" v-if="!newsArr.length">
				<image src="../../static/icon/nodata.png" mode=""></image>
			</view>
		</view>
	</view>
</template>

<style lang="scss" scoped>
//设置横向滚动条菜单的CSS
.navscroll{
	height: 100rpx;
	background-color: #f7f8fa;
	//下面四行代码用啦固定滚动条菜单
	position:fixed;
	top:var(--window-top); //不使用0，而使用uniapp提供的变量
	left:0;
	z-index:1000;
	//不要让滚动条换行
	white-space: nowrap;
	//设置每个菜单项的样式
	.item{
		//使菜单项横向排列
		display: inline-block;
		font-size: 40rpx;
		line-height: 100rpx;
		padding: 0 30rpx;
		color: #333;
		//设置菜单项被选中后的高亮样式
		&.active{
			color:#31c27c;
		}
	}
}

//设置新闻列表的样式
.content{
	padding: 30rpx;
	//滚动条设置fixed布局后，会遮挡一部分content的内容，因此需要设置padding-top
	padding-top:100rpx; 
	.row{
		//每个新闻间显示一条虚线分割
		border-bottom: 1px dotted #efefef;
		padding: 20rpx 0;
	}
}
// 空白页处理的样式
.nodata{
	display:flex;
	justify-content: center;
	image{
		width:360rpx;
	}
}
</style>

<template>
   <view class="list">
		<view class="fixbg" :style="{'background-image':'url('+playlist.coverImgUrl+')'}"></view>
		<musichead title="歌单" :icon="true" color="#fff" ></musichead>
		<view class="container" v-show="!isLoading">
			<scroll-view scroll-y="true">
				<!-- 头部 -->
				<view class="list-head">
					<!-- 头部左边的图片 -->
					<view class="list-head-img">
						<image :src="playlist.coverImgUrl" mode=""></image>
						<text class="iconfont icon-bofang1">
							<!-- {{playlist.playCount}} -->
							{{playCount}}
						</text>
					</view>
					<!-- 头部右侧的内容 -->
					<view class="list-head-text">
						<view>{{playlist.name}}</view>
						<view>
							<image :src="playlist.creator.avatarUrl" mode=""></image>
							{{playlist.creator.nickname}}
						</view>
						<view>{{playlist.description}}</view>
					</view>
				</view>
				<!-- 头部下方的分享按钮: 采用uniapp内置的分享组件 -->
				<!-- 该分享组件会自动调取微信的分享按钮，可使用条件编译控制 -->
				<!-- #ifdef MP-WEIXIN -->
				<button class="list-share" open-type="share" style="color:white;">
					<text class="iconfont icon-fenxiang"></text>
					分享给微信好友
				</button>
				<!-- #endif -->
				
				<!-- 歌单列表 -->
				<view class="list-music">
					<!-- 歌单列表的头部：播放全部(共xx首) -->
					<view class="list-music-head">
						<text class="iconfont icon-bofang"></text> <!-- 播放图标 -->
						<text>播放全部</text>
						<text>({{playlist.trackCount}})</text><!-- 这个数据需要服务器获取 -->
					</view>
					
					<!-- 歌单列表body：循环显示所有歌曲 -->
					<view class="list-music-item" 
						v-for="(item,index) in playlist.tracks" :key="index"
						@tap="handleToDetail(item.id)"
					>
						<view class="list-music-top">{{index+1}}</view>
						<view class="list-music-song">
							<view>{{item.name}}</view>
							<view>
								<!-- 通过权限判断来决定是否显示独家和SQ -->
								<image 
									v-if="privileges[index].flag>100"
									src="../../static/img/dujia.png">
								</image>
								<image 
									v-if="privileges[index].maxbr == 999000"
									src="../../static/img/sq.png">
								</image>
								{{item.ar[0].name}} - {{item.name}}
							</view>
						</view>
						<text class="iconfont icon-bofang2"></text>
					</view>
				</view>
			</scroll-view>
		</view>
   </view>
</template>


<script setup>
import {ref, computed} from 'vue'
import {onLoad} from '@dcloudio/uni-app'
import { list } from  '../../common/api.js'
import musichead from "../../componnets/musichead/musichead.vue"
import '@/common/css/iconfont.css' 
// 引入store 
import { useListIds } from '@/stores/list_ids.js'


let playlist = ref({
	//对于取嵌套的对象内的值，就要在这里列出来，防止报错
	creator:{ //歌单创建者
		// avatarUrl:'', //创建者头像
		// nickname: '', //创建者名字
	},

})

//歌曲的权限：是否是SQ、独家
//该值为数组，index与tracks的index一一对应，表明了歌曲的权限。
let privileges = ref([])

let playCount = computed(function(){
	if(playlist.value.playCount>=100000000){
		return Math.round(playlist.value.playCount / 100000000) + '亿'
	}else if(10000<playlist.value.playCount){
		return Math.round(playlist.value.playCount / 10000) + '万'
	}else{
		return playlist.value.playCount
	}
})

let isLoading = ref(true);

onLoad((options)=>{
	//进一步优化：在loading时显示一个loading插件，onLoad周期开始时就显示，
	uni.showLoading({
		title:'加载中...'
	})
	
	console.log(options);//options.listId就是当前歌单的id
	// 获取list接口的数据
	list(options.listId).then((res)=>{
		console.log(res)
		// playlist = res.data.playlist; //将歌单详情数据赋值给playlist	
		playlist.value = res.data.playlist;
		privileges.value = res.data.privileges;
		// 将歌单id列表储存进store 
		let ids = useListIds();
		ids.topListIds = res.data.playlist.trackIds
	
	
		isLoading.value = false;
		//获取数据后就隐藏loading 组件
		uni.hideLoading();
	}).catch((err)=>{
		console.log("歌单详情获取失败：", err);
	})
})

function handleToDetail(songId){
	uni.navigateTo({
		url: '/pages/detail/detail?songId='+songId,
	});
}

</script>


<style lang="scss">
// 头部样式
.list-head{
	display: flex; //头部分为左右两部分，因此是flex布局
	margin: 30rpx;
	
	.list-head-img{
		width: 264rpx;
		height: 264rpx;
		border-radius: 30rpx;
		overflow: hidden; //因此圆角表框遮住的部分
		position: relative; //子绝父相，方便子元素精准定位
		margin: 42rpx;
		
		image{
			width: 100%;
			height: 100%;
		}
		
		text{ //该文本应该定位到右上角
			position: absolute;
			right: 8rpx;
			top: 8rpx;
			color: white;
			font-size: 26rpx;
		}
	}
	
	.list-head-text{
		flex: 1;
		color: "#f0f2f7";
		
		view:nth-child(1){
			color: white;
			font-size: 34rpx;
		}
		view:nth-child(2){
			display: flex;
			margin: 20rpx 0;
			font-size: 24rpx;
			align-items: center;
			image{
				width: 54rpx;
				height: 54rpx;
				border-radius: 50%;
				margin-right: 14rpx;
			}
		}
		
		view:nth-child(3){
			line-height: 32rpx;
			font-size: 22rpx;
			line-height: 34rpx;
		}
	}
}
.list-share{
	width: 330rpx;
	height: 74rpx;
	margin: 0 auto;
	background-color: rgba(0,0,0,0.4);
	border-radius: 37rpx;
	color: "#ffffff";
	text-align: center;
	line-height: 74rpx;
	font-size: 28rpx;
	text{
		margin-right: 16rpx; //图标和文字的间距。
	}
}


// 歌单样式
.list-music{
	background: white;
	border-radius: 50rpx;
	margin-top: 40rpx;
	overflow: hidden; //不加这个会发生margin传递现象，导致子元素的margin不生效
	
	// 歌单头部
	.list-music-head{
		height: 50rpx;
		margin: 30rpx 0 70rpx 22rpx;
		
		text:nth-child(1){
			height: 50rpx;
			font-size: 50rpx;
		}
		text:nth-child(2){
			font-size: 30rpx;
			margin: 0 10rpx 0 26rpx; //调整中间元素的margin，三个元素直接都分开了
		}
		text:nth-child(3){
			font-size: 26rpx;
			color: #b2b2b2;
		}
	}
	
	// 歌单body：歌曲列表
	.list-music-item{
		display: flex;
		margin: 0 32rpx 66rpx 46rpx; 
		align-items: center; //flex布局的上下居中
		color: #959595;
		// 歌曲列表的左边，也就是歌曲的序号
		.list-music-top{
			width: 58rpx;
			font-size: 30rpx;
			line-height: 30rpx;
		}
		
		// 每个循环显示的具体歌曲
		.list-music-song{
			flex:1;
			view:nth-child(1){
				font-size: 28rpx;
				color: black;
				
				// 歌名过长就成...
				width: 70vw;
				white-space: nowrap;
				overflow: hidden;
				text-overflow: ellipsis; //显示为...
			}
			
			view:nth-child(2){
				// display: flex; //没必要设置为弹性，图片和文字本身就是左右排列的
			
				font-size:20rpx;
				align-items: center;
				
				// 歌手+歌名过长进行...处理 ：以下配置与flex布局会有冲突
				width: 70vw;
				white-space: nowrap;
				overflow: hidden;
				text-overflow: ellipsis; //显示为...
				
				
				//两个小图标：独家和SQ
				image{  
					width:32rpx;
					height:20rpx;
					margin-right:10rpx;
					// flex-shrink: 0; //禁止图片拉伸
				}
			}
			
		}
		text{
			font-size: 50rpx;
			color: #c7c7c7;
		}
	}
	
}
</style>

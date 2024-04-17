<template>
	<view class="detail">
		<view class="fixbg" :style="{'background-image':'url('+ songDetailData.al.picUrl+')'}">
		</view>
		<musichead :title="songDetailData.name" :icon="true" color="white"></musichead>
		<view class="container">
			<scroll-view scroll-y="true">
				<!-- 封面区域 -->
				<view class="detail-play" @tap="handleToPlay">
					<image :src="songDetailData.al.picUrl" :class="{'detail-play-run' : isPlayRotate}"></image>
					<text class="iconfont" :class="iconPlay"></text>
					<view></view>
				</view>
				
				<!-- 歌词区域 -->
				<view class="detail-lyric">
					<!-- 歌词内容：歌词内容的容器要比歌词区域大，这样歌词才能滚动 -->
					<view class="detail-lyric-wrap"
						:style="{ transform: 'translateY('+ -( lyricIndex -1) * 82 +'rpx)'}">
						<!-- 设计：歌词区域显示三行歌词，其中一行处于选中状态active-->
						<view class="detail-lyric-item" 
							v-for="(item,index) in songLyricData" :key="index"
							:class="{ active : lyricIndex == index}"
						>
							
							{{item.lyric}}
						</view>
					</view>
				</view>
				
				<!-- 相似歌曲：喜欢这首歌的人也听 -->
				<view class="detail-like">
					<view class="detail-like-head">喜欢这首歌的人也听</view>
					<!-- 歌单列表：相似歌曲的数据需要从API获取，并循环遍历 -->
					<view class="detail-like-item" 
						v-for="(item,index) in songSimiData" :key="index"
						@tap="handleSimiPlay(item.id)"
					>
						<!-- 歌曲左边：歌曲的图片 -->
						<view class="detail-like-img">
							<image :src="item.album.picUrl" mode=""></image>
						</view>
						<!-- 歌曲右边：歌曲的详情 -->
						<view class="detail-like-song">
							<view>{{item.name}}</view>
							<view>
								<!-- 独家和SQ两种图片标记 -->
								<image 
								v-if="item.privilege.flag>100"
								src="../../static/img/dujia.png" mode=""></image>
								<image 
								v-if="item.privilege.maxbr == 999000"
								src="../../static/img/sq.png" mode=""></image>
								{{item.album.artists[0].name}}-{{item.name}}
							</view>
						</view>
						<!-- 播放图标 -->
						<text class="iconfont icon-bofang2"></text>
					</view>
				</view>
			
				<!-- 评论区域 -->
				<view class="detail-comment">
					<!-- 区域title：精彩评论 -->
					<view class="detail-comment-head">精彩评论</view>
					<!-- 评论列表：真实数据需要从API获取，循环渲染 -->
					<view class="detail-comment-item"
						v-for="(item,index) in songCommentData" :key="index"
					>
						<!-- 右侧：用户的头像 -->
						<view class="detail-comment-img">
							<image :src="item.user.avatarUrl" mode=""></image>
						</view>
						<!-- 左侧区域：用户名、评论日期、点赞数、评论内容 -->
						<view class="detail-comment-content">
							<!-- 上方： 用户名和评论日期、点赞次数和图标-->
							<view class="detail-comment-title">
								<!-- 上左侧：用户名和评论日期 -->
								<view class="detail-comment-name">
									<view>{{item.user.nickname}}</view>
									<!-- <view>{{item.time}}</view> -->
									<view>{{commentDate(item.time)}}</view>
								</view>
								<!-- 上右侧：点赞次数和图标-->
								<view class="detail-comment-like">
									<!-- {{item.likedCount}} -->
									{{ likedCount(item.likedCount) }}
									<text class="iconfont icon-dianzan"></text>
								</view>
							</view>
							<!-- 下方：评论内容 -->
							<view class="detail-comment-text">
								{{item.content}}
							</view>
						</view>
					</view>
				</view>
				
			</scroll-view>
		</view>
	</view>
</template>

<script setup>
import {onLoad,onUnload,onHide} from '@dcloudio/uni-app'
import musichead from "../../componnets/musichead/musichead"
import { ref,computed} from 'vue'
import '@/common/css/iconfont.css' 
import { songDetail, songSimi, songComment, songLyric, songUrl } from '../../common/api.js'

// 引入store 
import { useListIds } from '@/stores/list_ids.js'

// 歌曲详细信息
let songDetailData = ref({
	al:{} //防止报错
})

// 相似歌曲列表：共5首
let songSimiData = ref([])

// 热门评论：共15个
let songCommentData = ref([])

// 歌词：对歌词进行格式化后，以数组形式储存每一行歌词
let songLyricData = ref([])

// 激活的歌词：通过该值确定哪行歌词被激活，默认是第一行
let lyricIndex = ref(1)

//对点赞数进行格式化
let likedCount = computed(function(){
	return function(count){
		if(count>=100000000){
			return Math.round(count / 100000000) + '亿'
		}else if(10000<count){
			return Math.round(count / 10000) + '万'
		}else{
			return count
		}
	}
})

//对日期进行格式化
let commentDate = computed(function(){
	return function(timestamp){
		let date = new Date(timestamp);
		return date.getFullYear()+'年' + (date.getMonth()+1) + '月' + date.getDate()+ '日'
	}
})

// 控制播放图标：播放时和暂停时，图标是不同的，默认显示暂停图标
let iconPlay = ref("icon-zanting")
// 控制歌曲封面是否旋转，播放时选择，暂停时不旋转
let isPlayRotate = ref(true)

// 全局创建播放器对象，方便后续访问
let innerAudioContext = {}

// 点击事件：控制播放与暂停
function handleToPlay(){
	// 判断当前是否是暂停状态
	if(innerAudioContext.paused){
		innerAudioContext.play();
	}else{
		innerAudioContext.pause();
	}
}
// 播放相似列表的歌曲
function handleSimiPlay(songId){
	// 原地跳转会有bug，因为没有触发onUnload事件，因此这里要手动停止播放
	innerAudioContext.stop();
	cancelLyricIndex();
	
	uni.navigateTo({
		url: '/pages/detail/detail?songId='+songId,
	});
}


// 创建定时器
let  timer = "";
// 定时器：监听播放的时间，以便于和激活的歌词时间匹配
function listenLyricIndex(){
	// 清除旧的定时器
	clearInterval(timer);
	// songLyricData = songLyricData._rawValue
	let songLyric = songLyricData.value
	
	timer = setInterval(()=>{
		// 遍历歌词数据所在的数组
		for(let i=0;i<songLyric.length;i++){	
			if(innerAudioContext.currentTime > songLyric[songLyric.length-1].time){
				// lyricIndex = songLyric.length-1
				lyricIndex.value = songLyric.length-1
				break;
			}
			
			if(innerAudioContext.currentTime > songLyric[i].time && innerAudioContext.currentTime < songLyric[i+1].time){
				// lyricIndex = i;
				lyricIndex.value = i;
			}				
		}
		console.log(lyricIndex.value);
	},500)
}
// 取消定时器的方法：停止播放时，暂停计时器
function cancelLyricIndex(){
	clearInterval(timer);
}

//详情页的数据涉及到了5个API，这里采用Promise.all，当所有数据都获取后再进行处理
function getMusic(songId){
	Promise.all([
		songDetail(songId), 
		songSimi(songId),
		songComment(songId),
		songLyric(songId),
		songUrl(songId)
	]).then((res)=>{
		console.log(res);
		//res[0]:表示promise数组中第一个Promise的返回值，以此类推
		if(res[0].data.code == '200'){
			// 一定要注意，ref赋值要加上value
			songDetailData.value = res[0].data.songs[0]
			// console.log(res[0].data.songs[0])
			// console.log(songDetailData);
		}
		//res[1]:表示promise数组中第二个Promise的返回值，以此类推
		if(res[1].data.code == '200'){
			// 相似歌曲是一个数组，里面有5首歌曲
			songSimiData = res[1].data.songs
			// console.log(songSimiData);
		}
		//res[2]:表示promise数组中第三个Promise的返回值，以此类推
		if(res[2].data.code == '200'){
			songCommentData = res[2].data.hotComments;
			// console.log("热门评论",songCommentData)
		}
		//res[3]:表示promise数组中第使个Promise的返回值，以此类推
		if(res[3].data.code == '200'){
			let lyric = res[3].data.lrc.lyric
			// console.log(lyric);
			//使用正则：将歌词拆分成一行一行的。
			//每行歌曲的开头都是中括号里面有时间，如：[00:24.40]
			//这里对正则进行了分组，因为要将每行的时间和歌词内容分开
			let regex = /\[([^\]]+)\]([^\[]+)/g;
			
			var result = []
			
			//使用replace函数的 回调参数，处理模式匹配中的数据，
			//$0=整体数据；$1=每行歌词的时间；$2=每行歌词的歌词内容
			lyric.replace(regex,function($0,$1,$2){
				//将每行歌词的 `歌词时间`和`歌词内容`以json的格式压入result数组
				result.push({
					// "time":$1,
					"time": formatTimetoSec($1),
					"lyric":$2
				})
			})
			songLyricData.value = result;
			// console.log(result);
			
		}
		if(res[4].data.code == '200'){
			// bgAudioManager = uni.getBackgroundAudioManager();
			// bgAudioManager.title = songDetail.name;
			// bgAudioManager.src = res[4].data.data[0].url
			innerAudioContext = uni.createInnerAudioContext();
			innerAudioContext.autoplay = true;
			innerAudioContext.src = res[4].data.data[0].url
			listenLyricIndex()
			innerAudioContext.onPlay(() => {
				// 监听播放状态的回调
				console.log('开始播放');
				listenLyricIndex()
				// 设置播放时的“播放|暂停”图标
				iconPlay.value = "icon-zanting"
				isPlayRotate.value = true
			});
			innerAudioContext.onPause(()=>{
				// 监听暂停状态的回调
				console.log('暂停播放')
				// 修改播放状态：控制播放封面是否旋转
				iconPlay.value = "icon-bofang"
				isPlayRotate.value = false
				//暂停计时器
				cancelLyricIndex();
			})
			
			// 监听自然播放结束事件，好继续播放下一首
			innerAudioContext.onEnded((res)=>{
				// 重新调用获取歌曲信息的方法，此时从store获取下一首歌曲的id
				getMusic(ids.nextId);
			})
			
			innerAudioContext.onError((res) => {
			  console.log(res.errMsg);
			  console.log(res.errCode);
			});
		}
		
	})
}
// 创建函数格式化歌词数组里的时间信息
// 目标：将时间信息转为秒数，方便以后与歌曲播放对接
function formatTimetoSec(v){
	//将时间信息分割为分钟和秒，用冒号进行分割
	let arr = v.split(':');
	return (Number(arr[0]*60) + Number(arr[1])).toFixed(1);
}

let ids = useListIds()
onLoad((options)=>{
	console.log(options.songId);
	//在这里调用获取API数据的方法，并传递参数
	getMusic(options.songId)
	// 将当前歌曲的id的下一首id，存入store ，因此要遍历对比id列表进行储存
	let ids = useListIds()
	for(let i=0;i<ids.topListIds.length;i++){
		if(ids.topListIds[i].id == options.songId){
			ids.nextId = ids.topListIds[i+1].id;
		}
	}
	console.log("nextId:",ids.nextId);
	
})

// 离开和隐藏该页面时：暂停计时器，停止播放
onUnload(()=>{
	console.log("页面：Unload")
	cancelLyricIndex();
	innerAudioContext.stop();
})

// 隐藏指的是：页面被遮挡，包括，浏览器切换tab，或被其他软件遮挡
onHide(()=>{
	console.log("页面：Hide")
	cancelLyricIndex();
	innerAudioContext.stop();
})
</script>

<style lang="scss">
	// 1.播放封面
	.detail-play{
		width: 580rpx;
		height: 580rpx;
		background: url(~@/static/img/disc.png); //播放封面的固定磁带边背景图
		background-size: cover;
		margin: 214rpx auto 0 auto;
		position: relative;
		
		// 歌曲封面图片（要放到磁带边内，使磁带边成为背景图的边框）
		image{
			width: 370rpx;
			height: 370rpx;
			border-radius: 50%;
			
			// 使图片居中
			position: absolute;
			left: 0;
			top: 0;
			right: 0;
			bottom: 0;
			margin: auto;
			
			// 动画move 的默认状态
			animation: 10s linear move infinite;
			animation-play-state: paused;
			
		}
		// 通过该控制器改变动画的默认状态
		.detail-play-run{
			// 开启动画
			animation-play-state: running;
		}
		// 配置动画
		@keyframes move{
			from{
				transform: rotate(0deg);
			}
			to{
				transform: rotate(360deg);
			}
		}
		
		// 播放与暂停按钮
		text{
			width: 100rpx; //先调整容器大小，才能定为
			height: 100rpx;
			
			font-size: 100rpx;
			color: white;
			// 使图标居中
			position: absolute;
			left: 0;
			top: 0;
			right: 0;
			bottom: 0;
			margin: auto;
			
		}
		// 磁针图片，播放与暂停时会移动
		view{
			width: 230rpx;
			height: 360rpx;
			background: url(~@/static/img/needle.png);
			background-size: cover;
			
			
			position: absolute;
			left: 100rpx;
			top: -200rpx;
			margin: auto;
		}
	}
	
	// 2.歌词区域
	.detail-lyric{
		font-size: 32rpx;
		line-height: 82rpx;
		height: 246rpx;
		text-align: center;
		overflow: hidden; //因此过多的歌词，为了滚动效果
		
		color: #6f6e73;
		
		.detail-lyric-wrap{
			// 使歌词滚动
			transition: 0.5s;
			
			.detail-lyric-item{
			}
			.detail-lyric-item.active{
				color: white;
			}
		}
	}

	// 3.相似歌曲
	.detail-like{
		// 头部文字：喜欢听这首歌的人也听
		margin: 0 30rpx;
		.detail-like-head{
			font-size: 36rpx;
			color: white;
			margin: 50rpx 0;
		}
		// 相似歌曲的列表
		.detail-like-item{
			display: flex;
			align-items: center;
			margin-bottom: 28rpx;
			
			
			// 歌曲左侧的封面缩略图
			.detail-like-img{
				width: 82rpx;
				height: 82rpx;
				border-radius: 20rpx;
				overflow: hidden;
				margin-right: 20rpx;
				
				image{
					width: 100%;
					height: 100%;
				}
			}
			// 歌曲右侧的详细信息
			.detail-like-song{
				flex: 1;
				color: #c6c2bf;
				view:nth-child(1){
					font-size: 28rpx; 
					color: white;
					margin-bottom: 12rpx;
				}
				view:nth-child(2){
					font-size: 22rpx;
				}
				// 歌曲的独家和SQ两个图片
				image{
					width: 26rpx;
					height: 20rpx;
					margin-right: 10rpx;
				}
			}
			
		}
		
		// 歌曲的播放按钮
		text{
			font-size: 50rpx;
			color: #c6c2bf;
		}
	}

	// 4.评论内容
	.detail-comment{
		margin: 0 30rpx;
		
		// title：精彩评论
		.detail-comment-head{
			font-size: 36rpx;
			color: white;
			margin: 50rpx 0;
		}
		
		// 评论列表
		.detail-comment-item{
			margin-bottom: 28rpx;
			display: flex;
			
			// 左侧：用户头像
			.detail-comment-img{
				width: 64rpx;
				height: 64rpx;
				border-radius: 50%;
				overflow: hidden;
				margin-right: 18rpx;
				
				image{
					width: 100%;
					height: 100%;
				}
			}
			
			// 右侧：评论信息和评论内容
			.detail-comment-content{
				flex: 1;
				color: #cbcacf;
				
				// 用户名和点赞数
				.detail-comment-title{
					display: flex;
					justify-content: space-between;
					// 用户名：包含用户名和评论日期
					.detail-comment-name{
						// 用户名
						view:nth-child(1){
							font-size: 26rpx;
						}
						// 评论日期
						view:nth-child(2){
							font-size: 20rpx;
						}
					}
					// 点赞数
					.detail-comment-like{
						font-size: 28rpx;
					}
				}
				
				// 具体的评论内容
				.detail-comment-text{
					font-size: 28rpx;
					line-height: 40rpx;
					color: white;
					margin-top: 20rpx;
					border-bottom: 1px #e0e0e0 solid;
					padding-bottom: 40rpx;
				}
			}
		}
		
		
	}
</style>

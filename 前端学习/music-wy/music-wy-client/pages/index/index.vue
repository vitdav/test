<template>
	<view class="content">
		<musichead title="网易云音乐" :icon="false"></musichead>
		<!-- musichead是固定的头部，下面是可滚动的内容区域 -->
		<view class="container">
			<scroll-view scroll-y="true">
				<!-- 首页的搜索：一个搜索图标+一个输入框-->
				<view class="index-search">
					<text class="iconfont icon-sousuo "></text>
					<input type="text" placeholder="搜索歌曲">
				</view>
				
				<!-- 首页音乐分类 -->
				<view class="index-list">
					<view class="index-list-item" v-for="(item,index) in state.topList" 
					@tap="handleToList(item.id)"
					:key="index">
						<view class="index-list-img">
							<image :src="item.coverImgUrl" mode=""></image>
							<text>{{item.updateFrequency}}</text>
						</view>
						<view class="index-list-text">
							<view v-for="(item,index) in item.tracks" :key="index">
							{{index + 1}}.{{item.first}} - {{item.second}}
							</view>
						</view>
					</view>
				</view>
			</scroll-view>
		</view>
	</view>
</template>

<script setup>
import { reactive } from 'vue'
import '@/common/css/iconfont.css' 
import musichead from "../../componnets/musichead/musichead.vue"
// 引入接口
import { topList } from '../../common/api.js'

const state = reactive({
	topList:[]
})


//调用API获取数据
topList().then((res)=>{
	console.log(res)
	if(res.length){
		state.topList = res;
	}
})

//跳转到列表页
function handleToList(id){
	console.log(id)
	uni.navigateTo({
		url: '/pages/list/list?listId='+id,
	});
}

</script>


<style lang="scss">
	//搜索框的布局 
	.index-search {
		display: flex; /* 容器设置为flex布局 */
		align-items: center; /*容器内的元素上下居中*/
		height: 70rpx;
		margin: 70rpx 30rpx 30rpx 30rpx;
		background-color: #f7f7f7;
		border-radius: 50rpx;
		
		text{
			font-size: 26rpx;
			margin-right: 26rpx;
			margin-left: 28rpx;
		}
		
		input{
			font-size: 28rpx;
			flex: 1;
		}
	}

	//首页音乐分类的布局
    .index-list {
        margin: 0 30rpx;

        .index-list-item{
            display: flex;
            margin-bottom: 34rpx;
        }

        .index-list-img{
            width: 212rpx;
            height: 212rpx;
            position: relative;
			border-radius: 30rpx; 
			overflow: hidden; //隐藏溢出的图片边角，真正做到圆角边框
			margin-right: 22rpx;
            image{
                width: 100%;
                height: 100%;
            }

            text{
                position: absolute;
                left: 12rpx;
                bottom: 16rpx;
                color: white;
                font-size: 0.4em;
            }
        }

        .index-list-text{
			font-size: 0.8em;
			line-height: 66rpx;
        }
    }
</style>

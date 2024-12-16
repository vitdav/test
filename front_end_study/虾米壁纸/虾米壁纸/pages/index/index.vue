<script setup>
	
</script>

<template>
  <view class="homeLayout">
		<!-- 1.轮播图海报 -->
		<view class="banner">
			<swiper circular
				indicator-dots indicator-color="rgba(255,255,255,0.5)"
				indicator-active-color="#fff" autoplay
			>
				<swiper-item>
					<image src="../../common/image/banner1.jpg" mode="aspectFill"></image>
				</swiper-item>
				<swiper-item>
					<image src="../../common/image/banner2.jpg" mode="aspectFill"></image>
				</swiper-item>
				<swiper-item>
					<image src="../../common/image/banner3.jpg" mode="aspectFill"></image>
				</swiper-item>
			</swiper>
		</view>
		
		<!-- 2.公告区域 -->
		<view class="notice">
			<!-- 2.1 左边公告图标+公告文本 -->
			<view class="left">
				<uni-icons type="sound-filled" size="20" color="#28b389"></uni-icons>
				<text class="text">公告</text>
			</view>
			<!-- 2.2 中间公告标题：使用纵向的swiper组件 -->
			<view class="center">
				<swiper vertical autoplay	interval="1500"
					duration="300" circular
				>
					<swiper-item v-for="item,index in 4" >公告标题{{index+1}}</swiper-item>
				</swiper>
			</view>
			<!-- 2.3 右边公告箭头 -->
			<view class="right">
				<uni-icons type="right" size="16" color="#333"></uni-icons>
			</view>
		</view>
  </view>
</template>   

<style lang="scss" scoped>
.homeLayout{
	//1. 轮播图海报
	.banner{
		width: 750rpx;
		padding: 30rpx 0;
		// 轮播组件区域
		swiper{
			width: 750rpx;
			height: 340rpx;
			&-item{
				//轮播元素和元素内的图片都占满整个区域
				width: 100%;
				height: 100%;
				padding: 0 30rpx; //使每张轮播图都有间隔
				image{
					width: 100%;
					height: 100%;
					border-radius: 10rpx;
				}
			}
		}
	}
}
</style>

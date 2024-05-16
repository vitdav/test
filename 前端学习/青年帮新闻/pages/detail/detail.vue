<script setup>
	
</script>

<template>
  <view class="detail">
		<!-- 文章的标题 -->
		<view class="title">
			文章标题文章标题文章标题文章标题文章标题文章标题文章标题文章标题文章标题文章标题文章标题文章标
		</view>
		<!-- 文章的元信息 -->
		<view class="info">
			<view class="author">编辑：张三</view>
			<view class="time">2022-02-22 22:22:22</view>
		</view>
		<!-- 文章的内容 -->
		<view class="content">
			文章内容文章内容文章内容文章内容文章内容文章内容文章内容文章内容文章内容文章内容文章内容文章内容文章内容文章内容文章内容文章内容文章内容文章内容文章内容
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

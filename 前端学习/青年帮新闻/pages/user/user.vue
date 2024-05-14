<script setup>
	
</script>
<template>
	<view class="user">
		<!-- 上方的浏览记录图标 -->
		<view class="top">
			<image src="../../static/icon/history.png" mode=""></image>
			<view class="text">浏览历史</view>
		</view>
		<!-- 下方的浏览记录列表：循环显示newsbox组件 -->
		<view class="content">
			<view class="row">
				<newsbox></newsbox>
			</view>
		</view>
	</view>
</template>

<style lang="scss" scoped>
//1. 定义上方图标的样式
.top{
	padding: 50rpx 0;
	background-color: #f8f8f8;
	color: #666;
	//设置flex布局，进行居中处理
	display: flex;
	flex-direction:column;
	justify-content: center;
	align-items: center;
	//设置图片样式（宽高）
	image{
		width: 150rpx;
		height: 150rpx;
	}
	//设置图片下的文字样式
	.text{
		font-size: 38rpx;
		padding-top: 20rpx; //距离图片的距离
	}
}

//2. 定义下方的浏览记录样式
.content{
	padding: 30rpx;
	//设置每项浏览记录的历史
	.row{
		border-bottom: 1px dotted #efefef;
		padding: 20rpx 0;
	}
}

//3. 设置没有历史记录的显示样式
</style>

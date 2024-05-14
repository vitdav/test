<script setup>
	
</script>
<template>
	<view class="home">
		<!-- 横向的滚动条菜单 -->
		<scroll-view scroll-x class="navscroll">
			<view class="item">分类</view>
			<view class="item">分类</view>
			<view class="item">分类</view>
			<view class="item">分类</view>
			<view class="item">分类</view>
			<view class="item">分类</view>
			<view class="item">分类</view>
			<view class="item">分类</view>
			<view class="item">分类</view>
			<view class="item">分类</view>
		</scroll-view>
		
		<!-- 内容主题：新闻列表 -->
		<view class="content">
			<!-- 通过newsbox组件循环显示新闻项 -->
			<view class="row" v-for="item in 10">
				<newsbox></newsbox>
			</view>
		</view>
	</view>
</template>

<style lang="scss" scoped>
//设置横向滚动条菜单的CSS
.navscroll{
	height: 100rpx;
	background-color: #f7f8fa;
	//不要让滚动条换行
	white-space: nowrap;
	//设置每个菜单项的样式
	.item{
		//使菜单项横向排列
		display: inline-block;
		font-size: 40rpx;
		line-height: 100rpx;
		padding: 0 30rpx;
	}
}

//设置新闻列表的样式
.content{
	padding: 30rpx;
	.row{
		//每个新闻间显示一条虚线分割
		border-bottom: 1px dotted #efefef;
		padding: 20rpx 0;
	}
}
</style>

<script setup>
import {ref} from 'vue'
//设置导航条选项被选中时的下标
const navIndex = ref(0)
//导航条选项的点击事件：点击时设置该选项高亮显示
function clickNav(index){
	navIndex.value = index;
}
</script>
<template>
	<view class="home">
		<!-- 横向的滚动条菜单 -->
		<scroll-view scroll-x class="navscroll">
			<view class="item" 
				v-for="(item,index) in 10"
				:class="index==navIndex?'active':''"
				
				@click="clickNav(index)"
			>分类</view>
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
	.row{
		//每个新闻间显示一条虚线分割
		border-bottom: 1px dotted #efefef;
		padding: 20rpx 0;
		//滚动条设置fixed布局后，会遮挡一部分content的内容，因此需要设置padding-top
		padding-top:100rpx; 
	}
}
</style>

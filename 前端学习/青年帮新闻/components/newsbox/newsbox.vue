<script setup>
// 设置组件的值：注意组件的值是在页面中通过props传递的
const props = defineProps({
	item:{
		type: Object,
		// 设置一组默认值
		default(){
			return {
				title:"组件内默认的标题",
				author:"张三",
				hits:668,
				picurl:"../../static/icon/nopic.jpg",
				//浏览时间，该时间是给个人中心的浏览记录使用的
				//默认不显示浏览时间，而是显示作者信息，当页面向组件传递了改参数，就显示时间。
				// looktime: "2022-2-2　22:22:22"
			}
		}
	}
})

</script>

<template>
	<view class="newsbox">
		<!-- 左边的图片 -->
		<view class="pic">
			<image :src="item.picurl" mode="aspectFill"></image>
		</view>
		
		<!-- 右边的文本 -->
		<view class="text">
			<!-- 右上的标题 -->
			<view class="title">{{item.title}}</view>
			<!-- 右下的作者信息和浏览量 -->
			<view class="info" v-if="!item.looktime">
				<text>{{item.author}}</text>
				<text>{{item.hits}}浏览</text>
			</view>
			<view class="info" v-else>
				<text>上次浏览:{{item.looktime}}</text>
			</view>
		</view>
	</view>
</template>

<style lang="scss">
.newsbox{
	//设置flex布局，使图片和右边的文本显示在一行内
	display: flex; 
	//设置图片容器的样式
	.pic{
		width: 230rpx;
		height: 160rpx;
		//设置image组件的样式
		image{
			width: 100%;
			height: 100%;
		}
	}
	//设置右边文本的样式
	.text{
		//由于是flex布局，可以通过flex：1自动使用剩余的宽度
		flex: 1;
		padding-left:20rpx;
		//下面三行代码，使title和info和上下分离
		display: flex;
		flex-direction: column;
		justify-content: space-between;
		//设置右上的标题
		.title{
			font-size: 36rpx;
			color: #333;
			//设置当标题过长时，显示...隐藏。
			//注意：要允许显示两行，超过两行再显示...，这就需要复杂的设置了
			text-overflow: -o-ellipsis-lastline;
			overflow: hidden;				//溢出内容隐藏
			text-overflow: ellipsis;		//文本溢出部分用省略号表示
			display: -webkit-box;			//特别显示模式
			-webkit-line-clamp: 2;			//行数
			line-clamp: 2;					
			-webkit-box-orient: vertical;	//盒子中内容竖直排列	
		}
		//设置右下的作者和浏览量
		.info{
			font-size: 26rpx;
			color: #999;
			text{
				padding-right: 30rpx;
			}
		}
	}
}
</style>
<template>
   <view class="music-head" :style="{color: color}">
	   <view class="music-head-icon" v-if="icon" >
		   <!-- 导航栏上的左箭头和小房子图标 -->
		   <text class="iconfont icon-zuojiantou" @tap="handleToBack"></text> |
		   <text class="iconfont icon-shouye" @tap="handleToHome"></text>
	   </view>
	   {{title}}
   </view>
</template>


<script setup>
import {ref} from 'vue'
defineProps({
	"title": String,
	"icon": Boolean,
	"color": String,
})

function handleToBack(){
	console.log("返回上一个页面")
	uni.navigateBack();
}

function handleToHome(){
	console.log("返回首页")
	uni.navigateTo({
		url:'/pages/index/index'
	})
}

</script>


<style scss>
/* 这里舍去了测量过程，一般采用通用的尺寸和750rpx的宽进行设计 */
/* 不过在小程序端，为了保证所有设备的导航栏显示尺寸不变，可以采用px */
.music-head{
	width: 100%;
	height: 75px;
	font-size: 1em;
	line-height: 80px;
	text-align: center;
	color: #000000;
	position: relative;
	
	.music-head-icon{
		position: absolute;
		left: 8px;
		top: 23px;
		width: 90px;
		height: 30px;
		background-color: rgba(0,0,0,0.4);
		color:#FFFFFF;
		/* 这里要重新指定行高，不然会继承父辈的行高 */
		line-height: 31px; 
		border-radius: 15px;
		
		/* 采用flex布局中的space-evenly，使两个图标左右分离 */
		display: flex;
		justify-content: space-evenly;
	}
}


</style>

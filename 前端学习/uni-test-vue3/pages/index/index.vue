<template>
<view>
	<h1>首页</h1>
	
</view>
</template>


<script setup>
import {onLoad} from '@dcloudio/uni-app'
onLoad(()=>{
	console.log("hello")
	uni.getLocation({
		type:'wgs84',
		success(res){
			console.log("获取位置信息")
			console.log(res)
		}
	})
})
</script>
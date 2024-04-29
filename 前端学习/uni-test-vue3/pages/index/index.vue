<template>
<view>

</view>
</template>

<script setup>
import {onReady,onShow} from '@dcloudio/uni-app'

onReady(()=>{
	uni.setNavigationBarTitle({
		title: '动态标题'
	})
	
	uni.setNavigationBarColor({
		frontColor: '#ffffff',
		backgroundColor:'#4cd964',
		animation:{
			duration:200,
			timingFunc:'easeIn'
		}
	})
	
	uni.showNavigationBarLoading()
	
	setTimeout(()=>{
		uni.hideNavigationBarLoading()
	},1000)
})

onShow(()=>{
	uni.hideHomeButton()
})
</script>
<template>
	<view>
		<h1>登陆页面</h1>
	</view>
</template>

<script setup>
import {onLoad} from '@dcloudio/uni-app'

onLoad(()=>{
	console.log("进入登陆页面")
	uni.$emit('login',{
		msg:"登陆数据"
	})
})
</script>

<style lang="scss">

</style>

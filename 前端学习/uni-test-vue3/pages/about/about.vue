<template>
	<view>
		<h1>我的</h1>
		<navigator url="/pages/login/login">点击登陆</navigator>
	</view>
</template>

<script setup>
import { onLoad,onUnload } from "@dcloudio/uni-app"
import {ref} from 'vue'

const userinfo = ref({})
onLoad(()=>{
	console.log("about：开始监听login事件")
	//监听login事件,事件触发后执行回调函数
	uni.$on('login',(userinfo)=>{
		console.log("about：login事件已触发")
		userinfo.value = userinfo
		console.log("about：用户"+userinfo.value.userName+"已登陆")
	})
})

onUnload(()=>{
	//移除监听事件
	uni.$off('login')
	console.log("about：login事件已移除")
})


</script>

<style>

</style>

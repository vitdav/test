<template>
	<view>
		<h1>登陆页面</h1>
	</view>
</template>

<script setup>
import { onLoad } from "@dcloudio/uni-app"
import { ref } from 'vue'

onLoad(()=>{
	console.log("login：在登陆页面触发login事件")
	//触发login事件，并传递参数
	uni.$emit('login',{
		userName:'Jinno',
		id:'1',
		vip:true
	})
})
</script>

<style>

</style>

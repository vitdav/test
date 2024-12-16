<template>
	<view>
		<h1>我的</h1>
		<!-- <button type="error" @click="goHome">回到初始页</button> -->
	</view>
	<!--  -->
</template>

<script setup>
//获取页面栈数组
const pages = getCurrentPages()
console.log(pages)

const num = pages.length
console.log("页面栈里页面的数量"+num)

//获取页面栈数组中第一个页面对象（首页）
const home = pages[0]
console.log(home)

const route = home.route 
console.log(route) ///pages/index/index

const vm = home.$vm
console.log(vm)
</script>

<style>

</style>

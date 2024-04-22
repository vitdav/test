<template>
	<view>
		<h1>我的</h1>
		<navigator url="/pages/login/login">点击登陆</navigator>
	</view>
</template>

<script setup>
const obj = uni.getLaunchOptionsSync();
console.log(obj)

const obj2 = uni.getEnterOptionsSync();
console.log(obj)
</script>

<style>

</style>

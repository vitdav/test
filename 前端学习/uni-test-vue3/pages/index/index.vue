<template>
<view>
	<button @click="changeTarbar">修改首页的Tarbar</button>
</view>
</template>

<script setup>
function changeTarbar(){
	uni.setTabBarItem({
		index:0,
		text:'Now',
		selectedIconPath:'/static/logo.png'
	})
}
</script>
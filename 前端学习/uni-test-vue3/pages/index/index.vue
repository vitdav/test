<template>
<view>
	<button @click="changeBg">设置背景色</button>

</view>
</template>

<script setup>

	uni.setBackgroundTextStyle({
	  textStyle: 'dark' // 下拉背景字体、loading 图的样式为dark
	})
</script>
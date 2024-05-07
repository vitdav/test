<template>
<view>
	<button @click="scan">扫码</button>
</view>
</template>

<script setup>
function scan(){
	uni.scanCode({
		success(res){
			console.log(res)
		}
	})
}
</script>
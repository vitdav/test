<template>
<view>
	<button @click="vibrate">手机震动</button>
</view>
</template>

<script setup>
function vibrate(){
	uni.vibrateLong({
		success(){
			console.log("手机发生了震动")
		},
		fail(err){
			console.error(err)
		}
	})
}

</script>
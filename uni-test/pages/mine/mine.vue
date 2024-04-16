<template>
	<h1>我的</h1>
</template>

<script setup>
import {ref, onLoad,onUnload} from '@dcloudio/uni-app'
const userInfo = ref('')

onLoad(()=>{
	console.log("进入我的页面")
	//监听事件
	uni.$on('login',(info)=>{
		userInfo.value = info;
	})
})

onUnload(()=>{
	$uni.$off('login')
})
</script>
</script>

<style lang="scss">

</style>

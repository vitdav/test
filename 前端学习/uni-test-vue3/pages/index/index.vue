<template>
<view>
  <button @click="openDebug">打开调试</button>
	<button @click="closeDebug">关闭调试</button>
</view>
</template>

<script setup>
import {ref} from 'vue'
function openDebug(){
	//打开调试
	uni.setEnableDebug({
		enableDebug:true
	})
}

function closeDebug(){
	uni.setEnableDebug({
		enableDebug:false
	})
}
</script>
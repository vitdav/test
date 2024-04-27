<!--  -->
<template>
<view>
  <button @click="playVideo">播放背景音</button>
	<button @click="stopVideo">停止背景音</button>
	<button @click="pauseVideo">暂停背景音</button>
	<button @click="mulPlay">二倍速播放</button>
	
	<!-- 创建一个audio组件，控制它播放录音 -->
	<!-- <audio :src="voicePath" name="当前录音"></audio> -->
</view>
</template>

<script setup>
import {ref} from 'vue'
//当前录音储存的临时地址
const voicePath = ref('')



</script>
<template>
<view>
	<video :src="src" controls id="myVideo"
		enable-danmu danmu-btn >
	</video>
  <button @click="playVideo">播放视频</button>
	<button @click="pauseVideo">暂停视频</button>
	<view>
		<input type="text" placeholder="输入弹幕" @blur="inputBlur">
	</view>
	<button @click="sendDanmu">发送弹幕</button>
</view>
</template>

<script setup>
import {ref} from 'vue'
const src = ref('https://media.w3.org/2010/05/sintel/trailer.mp4')
const danmuValue = ref('')

const videoContext = uni.createVideoContext('myVideo')
function playVideo(){
	console.log("播放视频")
	videoContext.play()
}

function pauseVideo(){
	console.log('暂停视频')
	videoContext.pause()
}

function inputBlur(e){
	danmuValue.value = e.target.value
}

function sendDanmu(){
	console.log('发送弹幕')
	videoContext.sendDanmu({
		text: danmuValue.value,
	})
}

</script>
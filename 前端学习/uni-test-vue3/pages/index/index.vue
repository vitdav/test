<template>
<view>
	<map style="width: 750rpx; height: 900rpx;" 
		:latitude="latitude" 
		:longitude="longitude" 
		>
	</map>
	
	<h2>除了地图，还能显示其他额外控件</h2>
	<button type="primary">这是一个按钮</button>
</view>
</template>


<script setup>
import {ref} from 'vue'
const latitude = ref(39.999)
const longitude = ref( 116.39742)
</script>

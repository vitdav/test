<!-- 基本使用-->
<template>
<view>
  <map style="width: 750rpx; height: 900rpx;" 
    :latitude="latitude" 
    :longitude="longitude" 
		:markers="covers"
    >
  </map>
</view>
</template>

<script setup>
import {ref} from 'vue'
const latitude = ref(39.999) //定义显示的中心维度
const longitude = ref(116.39742)//定义显示的中心精度
//定义markers（标记点）
const covers = ref([
	{
		id:1,
		latitude: 39.999,
		longitude: 116.39742,
		height:20,
		width:20,
		iconPath: '/static/location/pile.png'
	}, {
		id:2,
		latitude: 39.999,
		longitude: 116.395,
		height:20,
		width:20,
		iconPath: '/static/location/pile.png'
	},
])
</script>

<template>
	<view>
		<picker mode="date" start="1990-01-01" end="2024-04-18"
			:value="current"  @change="changePicker"
		>
			<view>{{current}}</view>
		</picker>
	</view>
</template>

<style lang="scss">
.nav-active{
	color:blue;
}
</style>

<script setup>
import {ref} from 'vue'
const current = ref("2024-04-18")

function changePicker(e){
	console.log(e)
	//内容改变后的值，是一个数组，每一项对应每一列的值
	console.log(e.detail.value)
	current.value = e.detail.value
}
</script>
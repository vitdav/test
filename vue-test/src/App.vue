<template>
	<h1>{{num}}</h1>
	<br>
	<button @click="num++">+</button>
</template>

<script setup>
import { onBeforeUnmount } from 'vue';
import {ref,onMounted,onBeforeUpdate,onUpdated} from 'vue'

const num = ref(100)

//1. 创建阶段
//创建后钩子 setup，默认代码区域已经是setup了
console.log("------setup------")
console.log(num.value)
console.log("可以直接再setup里向后台发送API")
//访问DOM会报错：Cannot read properties of null (reading 'innerHTML')
//console.log(document.querySelector("h1").innerHTML)


//2. 挂载阶段
//挂载后的钩子 onMounted，用来操作DOM
onMounted(()=>{
	console.log("----onMounted----")
	//此时可以访问DOM了
	console.log(document.querySelector("h1").innerHTML)
})

//3. 更新阶段
onBeforeUpdate(()=>{
	console.log("----onBeforeUpdate")
	console.log("DOM更新前，此时数据已经更新了，DOM还没更新")
	//DOM还没更新，拿到的标签中间的值依然是100
	console.log(document.querySelector("h1").innerHTML)
})
onUpdated(()=>{
	console.log("-----onUpdated-----")
	console.log("DOM更新后")
	//DOM已经更新，拿到的标签中间的值变成101
	console.log(document.querySelector("h1").innerHTML)
})

//4. 销毁/卸载 阶段
//一般通过关闭浏览器触发卸载
onBeforeUnmount(()=>{
	console.log("通常用来清除一些Vue意外的资源占用，如定时器")
})


</script>

<style scoped >
/*  */
</style>

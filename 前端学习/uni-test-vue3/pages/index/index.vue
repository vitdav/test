<template>
<view>
  <button @click="saveImg">拍照保存</button>
</view>
</template>

<script setup>

function saveImg(){
	uni.chooseImage({
		count:1,
		//访问相机
		sourceType:['camera'],
		success(res){
			uni.saveImageToPhotosAlbum({
				filePath:res.tempFilePaths[0],
				success(res){
					console.log(res)
					console.log("图片保存成功")
				}
			})
		}
	})
}
</script>
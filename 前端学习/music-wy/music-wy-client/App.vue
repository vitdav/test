<script>
	export default {
		onLaunch: function() {
			console.log('App Launch--Lauch生命周期')
		},
		onShow: function() {
			console.log('App Show--show生命周期')
		},
		onHide: function() {
			console.log('App Hide--show生命周期')
		}
	}
</script>

<style lang="scss">
	/*每个页面公共css */
	
//页面整体容器
.container {
	width: 100%;
	height: calc(100vh - 70px);
	overflow: hidden;
	
	scroll-view {
		height: 100%;
	}
	
}

//背景图容器
.fixbg{
	width: 100%;
	height: 100vh;
	position: fixed;
	left: 0;
	top: 0;
	// background-image: url("static/img/wangyiyunyinyue.png");
	background-size: cover;
	background-position: center 0;
	filter: blur(10px); //设置模糊，产生毛玻璃的效果
	transform: scale(1.2); //放大背景图，消除背景图白边
}


</style>

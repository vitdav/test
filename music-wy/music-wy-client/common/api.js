/* 
 项目的API
 */

import { baseUrl } from './config.js'


// 1. 获取音乐榜单
export function topList(){
	return new Promise(function(reslove,reject){
		uni.request({
			url: `${baseUrl}/toplist/detail`,
			method: 'GET',
			data: {},
			success: res => {
				// console.log("toplist-请求成功：",res)
				let result =  res.data.list;
				result.length = 4; //只要前四个数据
				reslove(result);
			},
			fail: () => {},
			complete: () => {}
		});
	})
}

//2. 获取指定音乐榜单的歌单详情
export function list(listId){
	return uni.request({
		url: `${baseUrl}/playlist/detail?id=${listId}`,
		method: 'GET',
	});
}


//3. 获取歌曲信息信息：/song/detail?ids=xxxx
	//参数为歌曲id，必须，可以是多个
export function songDetail(songId){
	return uni.request({
		url: `${baseUrl}/song/detail?ids=${songId}`,
		method: 'GET',
	});
}


//4. 相似歌曲列表：/simi/song?id=xxx
	//参数是歌曲 id，必须
export function songSimi(songId){
	return uni.request({
		url: `${baseUrl}/simi/song?id=${songId}`,
		method: 'GET',
	});
}

//5. 用户评论：/comment/music?id=xxx
	//参数是歌曲id，必须，还有一个limit参数，可惜，默认是20条
export function songComment(songId){
	return uni.request({
		url: `${baseUrl}/comment/music?id=${songId}`,
		method: 'GET',
	});
}

//6. 歌词:/lyric/id=xxx
	//参数是歌曲id，必须
export function songLyric(songId){
	return uni.request({
		url: `${baseUrl}/lyric?id=${songId}`,
		method: 'GET',
	});
}


//7. 获取音乐url：/song/url?id=xxxx
	//参数是歌曲id，必须
export function songUrl(songId){
	return uni.request({
		url: `${baseUrl}/song/url?id=${songId}`,
		method: 'GET',
	});
}

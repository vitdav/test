<template>
	<div id="app" class="score-case">
		<!-- 1. 通过一个表格显示成绩 -->
		<div class="table">
			<table>
				<!-- 表格head -->
				<thead>
					<tr>
						<th>编号</th>
						<th>科目</th>
						<th>成绩</th>
						<th>操作</th>
					</tr>
				</thead>
				<!-- 表格body，要判断是否又内容 -->
				<tbody v-if="list.length>0">
					<tr v-for="(item,index) in list" :key="item.id">
						<td>{{index+1}}</td>
						<td>{{item.subject}}</td>
						<!-- 判断：对不及格的成绩标红 -->
						<td :class="{red: item.score < 60}">{{item.score}}</td>
						<td><a @click.prevent="del(item.id)" href="#">删除</a></td>
					</tr>
				</tbody>
				<!-- 当没有内容时，表格的body显示暂无数据 -->
				<tbody v-else>
					<tr>
						<td colspan="5"><span class="none">暂无数据</span></td>
					</tr>
				</tbody>
				<!-- 表格的foot -->
				<tfoot>
					<tr>
						<td colspan="5">
							<span>总分：{{totalScore}}</span>
							<span style="margin-left: 50px">平均分：{{averageScore}}</span>
						</td>
					</tr>
				</tfoot>
			</table>
		</div>
		<!-- 2. 通过一个表单用来添加成绩 -->
		<div class="form">
			<!-- 科目输入框 -->
			<div class="form-item">
				<div class="label">科目：</div>
				<div class="input">
					<input type="text" placeholder="请输入科目" v-model.trim="subject"/>
				</div>
			</div>
			<!-- 分数输入框 -->
			<div class="form-item">
				<div class="label">分数：</div>
					<div class="input">
						<input type="text" placeholder="请输入分数" v-model.number="score"
						/>
					</div>
			</div>
			<!-- 添加按钮 -->
			<div class="form-item">
				<div class="label"></div>
				<div class="input">
					<button @click="add" class="submit" >添加</button>
				</div>
			</div>
		</div>
	</div>
</template>

<script setup>
import {ref, computed} from 'vue'

//1. 定义成绩数组
const list = ref([
	{ id: 1, subject: '语文', score: 62 },
	{ id: 7, subject: '数学', score: 89 },
	{ id: 12, subject: '英语', score: 70 },
])

//2. 定义科目和成绩变量用于表单添加成绩
const subject = ref('')
const score = ref('')

//3. 定义计算属性，用来计算总成绩和平均成绩
const totalScore = computed(()=>{
	return list.value.reduce((sum,item)=>sum+item.score,0)
})
const averageScore = computed(()=>{
	if(list.value.length === 0){
		return 0
	}
	console.log(list.value.length)
	return (totalScore.value/list.value.length).toFixed(2)
})

//4. 方法：删除成绩
function del(id){
	list.value = list.value.filter(item=>item.id!==id)
}

//5. 方法：添加成绩
function add(){
	//进行格式的简单判断
	if (!subject) {
		alert('请输入科目')
		return
	}
	console.log(typeof score.value)
	if (typeof score.value !== 'number') {
		alert('请输入正确的成绩')
		return
	}
	
	//将成绩加入输入
	list.value.unshift({
		id: +new Date(),
		subject: subject.value,
		score: score.value
	})
	//将输入框置空
	subject.value = ''
	score.value = ''
}
</script>

<style scoped >
.score-case {
  width: 1000px;
  margin: 50px auto;
  display: flex;
}
.score-case .table {
  flex: 4;
}
.score-case .table table {
  width: 100%;
  border-spacing: 0;
  border-top: 1px solid #ccc;
  border-left: 1px solid #ccc;
}
.score-case .table table th {
  background: #f5f5f5;
}
.score-case .table table tr:hover td {
  background: #f5f5f5;
}
.score-case .table table td,
.score-case .table table th {
  border-bottom: 1px solid #ccc;
  border-right: 1px solid #ccc;
  text-align: center;
  padding: 10px;
}
.score-case .table table td.red,
.score-case .table table th.red {
  color: red;
}
.score-case .table .none {
  height: 100px;
  line-height: 100px;
  color: #999;
}
.score-case .form {
  flex: 1;
  padding: 20px;
}
.score-case .form .form-item {
  display: flex;
  margin-bottom: 20px;
  align-items: center;
}
.score-case .form .form-item .label {
  width: 60px;
  text-align: right;
  font-size: 14px;
}
.score-case .form .form-item .input {
  flex: 1;
}
.score-case .form .form-item input,
.score-case .form .form-item select {
  appearance: none;
  outline: none;
  border: 1px solid #ccc;
  width: 200px;
  height: 40px;
  box-sizing: border-box;
  padding: 10px;
  color: #666;
}
.score-case .form .form-item input::placeholder {
  color: #666;
}
.score-case .form .form-item .cancel,
.score-case .form .form-item .submit {
  appearance: none;
  outline: none;
  border: 1px solid #ccc;
  border-radius: 4px;
  padding: 4px 10px;
  margin-right: 10px;
  font-size: 12px;
  background: #ccc;
}
.score-case .form .form-item .submit {
  border-color: #069;
  background: #069;
  color: #fff;
}
</style>

import { defineStore } from 'pinia'
import { ref,computed } from 'vue'

export const useListIds = defineStore('listIds',()=>{
	let topListIds = ref([])
	let nextId = ref('')
	
	
	
	return {topListIds,nextId}
})
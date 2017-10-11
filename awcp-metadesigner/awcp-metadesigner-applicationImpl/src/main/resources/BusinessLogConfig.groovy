
class BusinessLogConfig {

    def context

    def MetaModelApplicationImpl_findAll() {
		
		[log:"获取单个数据值",category:"查询"]
        
    }

    def ProjectApplicationImpl_findSomeProjects() {

        "ProjectApplicationImpl_findSomeProjects ${context.xx}"
    }

}

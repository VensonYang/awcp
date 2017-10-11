package cn.org.awcp.unit.service;

public interface WorkflowSyncService {
	
	/**
	 * 增加/修改人员、人员、公司、小组和岗位
	 * @param refId:组织权限中的groupId/userId/岗位Id
	 * @param name：用户名、组织名称或岗位名称；
	 * @param groupType： 1。人员；2.公司；3.部门；4.小组；5.岗位；
	 */
	void saveGroup(Long refId, String name, Long groupType);
	
	/**
	 * 删除人员、组织、部门、小组或岗位；
	 * @param refId：组织权限中的groupId/userId/岗位Id
	 * @param groupType： 1。人员；2.公司；3.部门；4.小组；5.岗位；
	 */
	void removeGroup(Long refId, Long groupType);
	
	/**
	 * 创建组织与组织，组织与人员，组织与岗位，岗位与人员的关系；
	 * @param parentRefId：父结点的组织权限中的Id
	 * @param parentGroupType：父结点的组织权限中的类型
	 * @param childRefId：子结点的组织权限中的Id
	 * @param childGroupType：子结点的组织权限中的类型
	 * @param relationType：关系类型，默认为1，行政关系；
	 * @param isManager：是否管理岗位；
	 */
	void createRelation(Long parentRefId, Long parentGroupType,
			Long childRefId, Long childGroupType, Long relationType, Boolean isManager);
	
	/**
	  * 删除组织与组织，组织与人员，组织与岗位，岗位与人员的关系；
	 * @param parentRefId：父结点的组织权限中的Id
	 * @param parentGroupType：父结点的组织权限中的类型
	 * @param childRefId：子结点的组织权限中的Id
	 * @param childGroupType：子结点的组织权限中的类型
	 * @param relationType：关系类型，默认为1，行政关系；
	 */
	void removeRelation(Long parentRefId, Long parentGroupType,
			Long childRefId, Long childGroupType, Long relationType);

}

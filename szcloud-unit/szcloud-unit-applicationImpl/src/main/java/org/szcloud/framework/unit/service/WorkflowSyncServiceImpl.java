package org.szcloud.framework.unit.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WorkflowSyncServiceImpl implements WorkflowSyncService {
	
	/*
	 * 日志对象
	 */
	private static final Logger logger = LoggerFactory.getLogger(WorkflowSyncServiceImpl.class);

	@Override
	public void saveGroup(Long refId, String name, Long groupType) {

	}

	@Override
	public void removeGroup(Long refId, Long groupType) {

	}

	@Override
	public void createRelation(Long parentRefId, Long parentGroupType, Long childRefId, Long childGroupType,
			Long relationType, Boolean isManager) {
		logger.debug(String.format(
				"Create relation with parent refId = %1s," + "parent type = %2s, " + "child refId = %3s, "
						+ "child type = %4s, " + "relation type = %5s" + "relation type = %6s",
				parentRefId, parentGroupType, childRefId, childGroupType, relationType, isManager));
	}

	@Override
	public void removeRelation(Long parentRefId, Long parentGroupType, Long childRefId, Long childGroupType,
			Long relationType) {
		logger.debug(String.format(
				"Remove relation with parent refId = %1s," + "parent type = %2s, " + "child refId = %3s, "
						+ "child type = %4s, " + "relation type = %5s",
				parentRefId, parentGroupType, childRefId, childGroupType, relationType));
	}

}

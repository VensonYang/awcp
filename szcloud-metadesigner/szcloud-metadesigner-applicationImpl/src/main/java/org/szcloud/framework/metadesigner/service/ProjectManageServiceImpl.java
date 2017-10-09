package org.szcloud.framework.metadesigner.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.szcloud.framework.core.domain.QueryChannelService;
import org.szcloud.framework.core.utils.BeanUtils;
import org.szcloud.framework.metadesigner.application.ProjectManageService;
import org.szcloud.framework.metadesigner.core.domain.ProjectManage;
import org.szcloud.framework.metadesigner.vo.ProjectManageVO;

@Service(value="projectManageServiceImpl")
public class ProjectManageServiceImpl implements ProjectManageService{

	@Resource(name="queryChannel")
	private QueryChannelService queryChannel;
	
	public long save(ProjectManageVO vo) {
		try {
			ProjectManage pm = BeanUtils.getNewInstance(vo, ProjectManage.class);
			pm.save();
			vo.setId(pm.getId());
			return pm.getId();
		} catch (Exception e) {
			return 0;
		}
	}

	public boolean remove(ProjectManageVO vo) {
		try {
			ProjectManage pm = BeanUtils.getNewInstance(vo, ProjectManage.class);
			pm.remove();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean update(ProjectManageVO vo) {
		try {
			ProjectManage pm = BeanUtils.getNewInstance(vo, ProjectManage.class);
			pm.save();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public List<ProjectManageVO> findAll() {
		try {
			List<ProjectManage> ls = ProjectManage.findAll(ProjectManage.class);
			List<ProjectManageVO> list = new ArrayList<ProjectManageVO>();
			for(ProjectManage p:ls){
				list.add(BeanUtils.getNewInstance(p, ProjectManageVO.class));
			}
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	public ProjectManageVO get(long id) {
		try {
			ProjectManage pm = ProjectManage.get(ProjectManage.class, id);
			ProjectManageVO pmvo = BeanUtils.getNewInstance(pm, ProjectManageVO.class);
			return pmvo;
		} catch (Exception e) {
			return null;
		}
	}
	
}

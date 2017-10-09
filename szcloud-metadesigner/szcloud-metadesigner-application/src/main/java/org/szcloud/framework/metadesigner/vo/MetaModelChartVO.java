package org.szcloud.framework.metadesigner.vo;

import java.util.List;

import org.szcloud.framework.core.common.exception.MRTException;
import org.szcloud.framework.metadesigner.core.domain.MetaModelChart;

/**
 * 元数据模型图标
 * @author Administrator
 *
 */
public class MetaModelChartVO {

	private Long id;
	
	private String chartContext;
	
	private String chartInfo;
	
	private String chartName;
	
	private long classId;

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getChartContext() {
		return chartContext;
	}

	public void setChartContext(String chartContext) {
		this.chartContext = chartContext;
	}

	public String getChartInfo() {
		return chartInfo;
	}

	public void setChartInfo(String chartInfo) {
		this.chartInfo = chartInfo;
	}

	public String getChartName() {
		return chartName;
	}

	public void setChartName(String chartName) {
		this.chartName = chartName;
	}

	public long getClassId() {
		return classId;
	}

	public void setClassId(long classId) {
		this.classId = classId;
	}
	
	public static List<MetaModelChart> findAll() throws MRTException{
		List<MetaModelChart> treeMenuList = null;
		try {
			treeMenuList = MetaModelChart.findAll(MetaModelChart.class);
		} catch (Exception e) {
			throw new  MRTException(e.getMessage(),e);
		}
		return treeMenuList;
	}
	
}

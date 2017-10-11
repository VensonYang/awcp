package cn.org.awcp.metadesigner.core.domain;

import java.util.List;

import cn.org.awcp.core.common.exception.MRTException;
import cn.org.awcp.core.domain.BaseEntity;

/**
 * 元数据模型图表
 * @author Administrator
 *
 */
public class MetaModelChart extends BaseEntity{

	private static final long serialVersionUID = 1L;

	private String chartContext;
	
	private String chartInfo;
	
	private String chartName;
	
	private long classId;


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
	
	public static List<MetaModelChart>  findAll() throws MRTException{
		List<MetaModelChart> treeMenuList = null;
		try {
			treeMenuList = findAll(MetaModelChart.class);
		} catch (Exception e) {
			throw new  MRTException(e.getMessage(),e);
		}
		return treeMenuList;
	}
}

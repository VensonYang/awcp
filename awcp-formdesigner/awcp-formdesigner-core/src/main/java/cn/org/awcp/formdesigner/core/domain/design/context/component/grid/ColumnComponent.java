package cn.org.awcp.formdesigner.core.domain.design.context.component.grid;

import cn.org.awcp.formdesigner.core.domain.design.context.component.SimpleComponent;

public class ColumnComponent extends SimpleComponent{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7278259968169162954L;

	private String width; //列宽
	private Long columnType; //列类型  1:数据， 2：脚本 3：操作 4：图标
	
	//数据：值类型、数据源
	//脚本：脚本框
	//操作：操作名称、操作类型
	//图标：选择图标
	private int valueType;  //值类型  1：真实值  2： 显示值 (列类型为1数据时才用)
	private String scriptStr; //值脚本  (列类型为2脚本时才用)
	private String operationName; //操作名称		(列类型为3操作时才用)
	private int operationType; //操作类型 (1:删除 2：提交流程 3：模板表单)	
	private int listLength;	
	private String iconLoc;   //图标路径
	private int orderType;  //排序方式 1：不排序     2：升序      3：降序
//	private boolean isProReturn;    //流程回退标识
//	private boolean isSum;			//是否汇总
	private int listAll; 		//是否显示所有	1：显示所有 2：显示部分
	private int isTitle;		//是否显示提示文字 1为显示 为空则不显示
	private int isVisible;		//是否可见 //TODO 待定 1为可见 为空则不可见
	private Integer order;
	
	public Long getColumnType() {
		return columnType;
	}

	public void setColumnType(Long columnType) {
		this.columnType = columnType;
	}

	@Override
	public String getKeyString() {
		return null;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public int getValueType() {
		return valueType;
	}

	public void setValueType(int valueType) {
		this.valueType = valueType;
	}

	public String getScriptStr() {
		return scriptStr;
	}

	public void setScriptStr(String scriptStr) {
		this.scriptStr = scriptStr;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public int getOperationType() {
		return operationType;
	}

	public void setOperationType(int operationType) {
		this.operationType = operationType;
	}

	public int getListLength() {
		return listLength;
	}

	public void setListLength(int listLength) {
		this.listLength = listLength;
	}

	public String getIconLoc() {
		return iconLoc;
	}

	public void setIconLoc(String iconLoc) {
		this.iconLoc = iconLoc;
	}

	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	public int getListAll() {
		return listAll;
	}

	public void setListAll(int listAll) {
		this.listAll = listAll;
	}

	public int getIsTitle() {
		return isTitle;
	}

	public void setIsTitle(int isTitle) {
		this.isTitle = isTitle;
	}

	public int getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(int isVisible) {
		this.isVisible = isVisible;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "ColumnComponent [width=" + width + ", columnType=" + columnType + ", valueType=" + valueType
				+ ", scriptStr=" + scriptStr + ", operationName=" + operationName + ", operationType=" + operationType
				+ ", listLength=" + listLength + ", iconLoc=" + iconLoc + ", orderType=" + orderType + ", listAll="
				+ listAll + ", isTitle=" + isTitle + ", isVisible=" + isVisible + ", order=" + order + "]";
	}

}

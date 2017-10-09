package BP.WF.XML;

import java.util.ArrayList;

import BP.En.Entities;
import BP.Sys.SystemConfig;
import BP.XML.XmlEn;
import BP.XML.XmlEns;

/**
 * 工具栏s
 * 
 */
public class Tools extends XmlEns {
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #region 构造

	public static ArrayList<Tool> convertTools(Object obj) {
		return (ArrayList<Tool>) obj;
	}

	/**
	 * 考核率的数据元素
	 * 
	 */
	public Tools() {
	}
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #endregion

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #region 重写基类属性或方法。
	/**
	 * 得到它的 Entity
	 * 
	 */
	@Override
	public XmlEn getGetNewEntity() {
		return new Tool();
	}

	@Override
	public String getFile() {
		return SystemConfig.getCCFlowAppPath() + "WF/Style/Tools.xml";
	}

	/**
	 * 物理表名
	 * 
	 */
	@Override
	public String getTableName() {
		return "Item";
	}

	@Override
	public Entities getRefEns() {
		return null; // new BP.ZF1.AdminTools();
	}
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #endregion

}
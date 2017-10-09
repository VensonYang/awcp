package BP.Sys.Frm;

import java.io.File;
import java.util.ArrayList;

import BP.En.Entities;
import BP.Sys.SystemConfig;
import BP.XML.XmlEn;
import BP.XML.XmlEns;

/**
 * 分组内容s
 * 
 */
public class FieldGroupXmls extends XmlEns {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #region 构造
	/**
	 * 分组内容s
	 * 
	 */
	public FieldGroupXmls() {
	}

	public static ArrayList<FieldGroupXml> convertFieldGroupXmls(Object obj) {
		return (ArrayList<FieldGroupXml>) obj;
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
		return new FieldGroupXml();
	}

	@Override
	public String getFile() {
		// return SystemConfig.PathOfWebApp + "\\WF\\MapDef\\Style\\XmlDB.xml";
		return SystemConfig.getPathOfData() + File.separator + "XML" + File.separator + "XmlDB.xml";
		// \MapDef\\Style\
	}

	/**
	 * 物理表名
	 * 
	 */
	@Override
	public String getTableName() {
		return "FieldGroup";
	}

	@Override
	public Entities getRefEns() {
		return null; // new BP.ZF1.AdminTools();
	}
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #endregion
}
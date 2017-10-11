package BP.WF.XML;

import java.io.File;

import BP.En.Entities;
import BP.Sys.SystemConfig;
import BP.XML.XmlEn;
import BP.XML.XmlEns;

/**
 * 公文左边谓词s
 * 
 */
public class GovWordLefts extends XmlEns {
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #region 构造
	/**
	 * 考核率的数据元素
	 * 
	 */
	public GovWordLefts() {
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
		return new GovWordLeft();
	}

	/**
	 * XML文件位置.
	 * 
	 */
	@Override
	public String getFile() {
		return SystemConfig.getPathOfWebApp() + File.separator + "WF" + File.separator + "Data" + File.separator + "XML"
				+ File.separator + "XmlDB.xml";
	}

	/**
	 * 物理表名
	 * 
	 */
	@Override
	public String getTableName() {
		return "GovWordLeft";
	}

	@Override
	public Entities getRefEns() {
		return null;
	}
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #endregion
}
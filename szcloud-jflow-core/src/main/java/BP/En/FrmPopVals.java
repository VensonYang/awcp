package BP.En;

import java.io.File;

import BP.Sys.SystemConfig;
import BP.XML.XmlEn;
import BP.XML.XmlMenus;

/**
 * 取值s
 * 
 */
public class FrmPopVals extends XmlMenus {
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #region 构造
	/**
	 * 取值s
	 * 
	 */
	public FrmPopVals() {
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
		return new FrmPopVal();
	}

	@Override
	public String getFile() {
		return SystemConfig.getPathOfDataUser() + File.separator + "Xml" + File.separator + "FrmPopVal.xml";
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
		return null;
	}
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #endregion
}
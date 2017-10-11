package BP.WF.XML;

import java.io.File;

import BP.En.Entities;
import BP.Sys.SystemConfig;
import BP.XML.XmlEn;
import BP.XML.XmlEns;

/**
 * 皮肤s
 * 
 */
public class CondTypeXmls extends XmlEns {
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #region 构造
	/**
	 * 皮肤s
	 * 
	 */
	public CondTypeXmls() {
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
		return new CondTypeXml();
	}

	@Override
	public String getFile() {
		return SystemConfig.getPathOfData() + File.separator + "Xml" + File.separator + "WFAdmin.xml";
	}

	/**
	 * 物理表名
	 * 
	 */
	@Override
	public String getTableName() {
		return "CondType";
	}

	@Override
	public Entities getRefEns() {
		return null;
	}
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #endregion

}
package BP.WF.XML;

import java.io.File;

import BP.En.Entities;
import BP.Sys.SystemConfig;
import BP.XML.XmlEn;
import BP.XML.XmlEns;

/**
 * 语言s
 * 
 */
public class Langs extends XmlEns {
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #region 构造
	/**
	 * 考核率的数据元素
	 * 
	 */
	public Langs() {
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
		return new Lang();
	}

	/**
	 * XML文件位置.
	 * 
	 */
	@Override
	public String getFile() {
		return SystemConfig.getCCFlowAppPath() + "WF" + File.separator + "Style" + File.separator + "Tools.xml";
	}

	/**
	 * 物理表名
	 * 
	 */
	@Override
	public String getTableName() {
		return "Lang";
	}

	@Override
	public Entities getRefEns() {
		return null;
	}
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #endregion

}
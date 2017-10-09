package BP.WF.XML;

import java.io.File;

import BP.En.Entities;
import BP.Sys.SystemConfig;
import BP.XML.XmlEn;
import BP.XML.XmlEns;

/**
 * 流程监控菜单s
 * 
 */
public class WatchDogXmls extends XmlEns {
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #region 构造
	/**
	 * 流程监控菜单s
	 * 
	 */
	public WatchDogXmls() {
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
		return new WatchDogXml();
	}

	@Override
	public String getFile() {
		return SystemConfig.getPathOfWebApp() + File.separator + "WF" + File.separator + "Admin" + File.separator
				+ "Sys" + File.separator + "Sys.xml";
	}

	/**
	 * 表
	 * 
	 */
	@Override
	public String getTableName() {
		return "WatchDog";
	}

	@Override
	public Entities getRefEns() {
		return null;
	}
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #endregion
}
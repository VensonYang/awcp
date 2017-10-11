package BP.WF.Template.AccepterRole;

import java.io.File;

import BP.En.Entities;
import BP.Sys.SystemConfig;
import BP.XML.XmlEn;
import BP.XML.XmlEns;

/**
 * 模式s
 * 
 */
public class ModeSortXmls extends XmlEns {
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #region 构造
	/**
	 * 模式s
	 * 
	 */
	public ModeSortXmls() {
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
		return new ModeSortXml();
	}

	@Override
	public String getFile() {
		return SystemConfig.getPathOfWebApp() + File.separator + "WF" + File.separator + "Admin" + File.separator
				+ "AccepterRole" + File.separator + "AccepterRole.xml";

	}

	/**
	 * 物理表名
	 * 
	 */
	@Override
	public String getTableName() {
		return "ModelSort";
	}

	@Override
	public Entities getRefEns() {
		return null;
	}
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #endregion

}
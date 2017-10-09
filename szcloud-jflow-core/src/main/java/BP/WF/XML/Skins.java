package BP.WF.XML;

import java.io.File;
import java.util.ArrayList;

import BP.En.Entities;
import BP.Sys.SystemConfig;
import BP.XML.XmlEn;
import BP.XML.XmlEns;

/**
 * 皮肤s
 * 
 */
public class Skins extends XmlEns {
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	public static ArrayList<Skin> convertSkins(Object obj) {
		return (ArrayList<Skin>) obj;
	}

	/// #region 构造
	/**
	 * 皮肤s
	 * 
	 */
	public Skins() {
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
		return new Skin();
	}

	@Override
	public String getFile() {
		return SystemConfig.getPathOfWebApp() + File.separator + "WF" + File.separator + "Style" + File.separator
				+ "Tools.xml";
	}

	/**
	 * 物理表名
	 * 
	 */
	@Override
	public String getTableName() {
		return "Skin";
	}

	@Override
	public Entities getRefEns() {
		return null;
	}
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #endregion

}
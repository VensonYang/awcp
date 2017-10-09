package BP.WF.XML;

import java.io.File;
import java.util.ArrayList;

import BP.En.Entities;
import BP.Sys.SystemConfig;
import BP.XML.XmlEn;
import BP.XML.XmlEns;

/**
 * 经典模式左侧菜单s
 * 
 * @author YounHou
 *
 */
public class ClassicMenus extends XmlEns {
	// /#region 构造
	/**
	 * 考核率的数据元素
	 */
	public ClassicMenus() {
	}

	public static ArrayList<ClassicMenu> convertClassicMenus(Object obj) {
		return (ArrayList<ClassicMenu>) obj;
	}

	// /#region 重写基类属性或方法。
	/**
	 * 得到它的 Entity
	 */
	@Override
	public XmlEn getGetNewEntity() {
		return new ClassicMenu();
	}

	@Override
	public String getFile() {
		return SystemConfig.getPathOfWebApp() + File.separator + "DataUser" + File.separator + "XML" + File.separator
				+ "LeftEnum.xml";
	}

	/**
	 * 物理表名
	 */
	@Override
	public String getTableName() {
		return "ClassicMenu";
	}

	@Override
	public Entities getRefEns() {
		return null; // new BP.ZF1.AdminTools();
	}
	// /#endregion
}

package BP.Web;

import java.io.File;

import BP.En.Entities;
import BP.Sys.SystemConfig;
import BP.XML.XmlEn;
import BP.XML.XmlMenus;

public class GroupXmls extends XmlMenus {
	// /#region 构造
	/**
	 * 分组菜单s
	 */
	// ORIGINAL LINE: public GroupXmls()
	public GroupXmls() {
	}

	// /#endregion

	// /#region 重写基类属性或方法。
	/**
	 * 得到它的 Entity
	 */
	@Override
	public XmlEn getGetNewEntity() {
		return new GroupXml();
	}

	@Override
	public String getFile() {
		return SystemConfig.getPathOfXML() + File.separator + "Ens" + File.separator + "Group.xml";
	}

	/**
	 * 物理表名
	 */
	@Override
	public String getTableName() {
		return "Item";
	}

	@Override
	public Entities getRefEns() {
		return null;
	}
	// /#endregion
}

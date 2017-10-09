package BP.WF.XML;

import BP.Port.WebUser;
import BP.WF.Glo;
import BP.XML.XmlEn;
import BP.XML.XmlEns;

public class ClassicMenu extends XmlEn {
	// /#region 属性
	public final String getNo() {
		return this.GetValStringByKey("No");
	}

	public final String getName() {
		if (this.getNo().equals("EmpWorks")) {
			return this.GetValStringByKey(WebUser.getSysLang()) + "("
					+ BP.WF.Dev2Interface.getTodolist_EmpWorks() + ")";
		} else if (this.getNo().equals("Sharing")) {
			return this.GetValStringByKey(WebUser.getSysLang()) + "("
					+ BP.WF.Dev2Interface.getTodolist_Sharing() + ")";
		} else if (this.getNo().equals("CC")) {
			return this.GetValStringByKey(WebUser.getSysLang()) + "("
					+ BP.WF.Dev2Interface.getTodolist_CCWorks() + ")";
		} else {
			return this.GetValStringByKey(WebUser.getSysLang());
		}
	}

	/**
	 * 图片
	 */
	public final String getImg() {
		return Glo.getCCFlowAppPath()+this.GetValStringByKey("Img");
	}

	public final String getTitle() {
		return this.GetValStringByKey("Title");
	}

	public final String getUrl() {
		return Glo.getCCFlowAppPath()+this.GetValStringByKey("Url");
	}

	public final boolean getEnable() {
		return this.GetValBoolByKey("Enable");
	}

	// /#endregion

	// /#region 构造
	/**
	 * 节点扩展信息
	 */
	public ClassicMenu() {
	}

	/**
	 * 获取一个实例
	 */
	@Override
	public XmlEns getGetNewEntities() {
		return new ClassicMenus();
	}
	// /#endregion
}

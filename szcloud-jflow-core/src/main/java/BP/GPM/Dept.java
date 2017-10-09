package BP.GPM;

import BP.DA.DBUrl;
import BP.DA.DBUrlType;
import BP.DA.Depositary;
import BP.En.EnType;
import BP.En.EntityTree;
import BP.En.Map;
import BP.En.UAC;

/**
 * 部门
 */
public class Dept extends EntityTree {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region 属性
	/**
	 * 全名
	 */
	public final String getNameOfPath() {
		return this.GetValStrByKey(DeptAttr.NameOfPath);
	}

	public final void setNameOfPath(String value) {
		this.SetValByKey(DeptAttr.NameOfPath, value);
	}

	/**
	 * 父节点的ID
	 */
	public String getParentNo() {
		return this.GetValStrByKey(DeptAttr.ParentNo);
	}

	public void setParentNo(String value) {
		this.SetValByKey(DeptAttr.ParentNo, value);
	}

	/**
	 * 部门类型
	 */
	public final String getFK_DeptType() {
		return this.GetValStrByKey(DeptAttr.FK_DeptType);
	}

	public final void setFK_DeptType(String value) {
		this.SetValByKey(DeptAttr.FK_DeptType, value);
	}

	/**
	 * 部门类型名称
	 */
	public final String getFK_DeptTypeText() {
		return this.GetValRefTextByKey(DeptAttr.FK_DeptType);
	}

	private Depts _HisSubDepts = null;

	/**
	 * 它的子节点
	 */
	public final Depts getHisSubDepts() {
		if (_HisSubDepts == null) {
			_HisSubDepts = new Depts(this.getNo());
		}
		return _HisSubDepts;
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region 构造函数
	/**
	 * 部门
	 */
	public Dept() {
	}

	/**
	 * 部门
	 * 
	 * @param no
	 *            编号
	 */
	public Dept(String no) {
		super(no);
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region 重写方法
	@Override
	public UAC getHisUAC() {
		UAC uac = new UAC();
		uac.OpenForSysAdmin();
		return uac;
	}

	/**
	 * Map
	 */
	@Override
	public Map getEnMap() {
		if (this.get_enMap() != null) {
			return this.get_enMap();
		}

		Map map = new Map();
		map.setEnDBUrl(new DBUrl(DBUrlType.AppCenterDSN)); // 连接到的那个数据库上. (默认的是:
															// AppCenterDSN )
		map.setPhysicsTable("Port_Dept");
		map.setEnType(EnType.Admin);

		map.setEnDesc("部门"); // 实体的描述.
		map.setDepositaryOfEntity(Depositary.Application); // 实体map的存放位置.
		map.setDepositaryOfMap(Depositary.Application); // Map 的存放位置.

		map.AddTBStringPK(DeptAttr.No, null, "编号", true, true, 1, 50, 20);

		// 比如xx分公司财务部
		map.AddTBString(DeptAttr.Name, null, "名称", true, false, 0, 100, 30);

		// 比如:\\驰骋集团\\南方分公司\\财务部
		map.AddTBString(DeptAttr.NameOfPath, null, "部门路径", false, false, 0,
				300, 30);

		map.AddTBString(DeptAttr.ParentNo, null, "父节点编号", false, false, 0, 100,
				30);
		map.AddTBString(DeptAttr.TreeNo, null, "树编号", false, false, 0, 100, 30);
		map.AddTBString(DeptAttr.Leader, null, "领导", false, false, 0, 100, 30);

		// 比如: 财务部，生产部，人力资源部.
		map.AddTBString(DeptAttr.Tel, null, "联系电话", false, false, 0, 100, 30);

		map.AddTBInt(DeptAttr.Idx, 0, "Idx", false, false);
		map.AddTBInt(DeptAttr.IsDir, 0, "是否是目录", false, false);

		map.AddDDLEntities(DeptAttr.FK_DeptType, null, "部门类型", new DeptTypes(),
				true);

		this.set_enMap(map);
		return this.get_enMap();
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion

	/**
	 * 生成部门全名称.
	 */
	public final void GenerNameOfPath() {
		String name = this.getName();
		Dept dept = new Dept(this.getParentNo());
		while (true) {
			if (dept.getIsRoot()) {
				break;
			}
			name = dept.getName() + "\\\\" + name;
		}
		this.setNameOfPath(name);
		this.DirectUpdate();
	}
}

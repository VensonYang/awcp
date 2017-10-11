package BP.Sys.Frm;

import BP.DA.DBAccess;
import BP.DA.Depositary;
import BP.En.EnType;
import BP.En.EntityOID;
import BP.En.Map;

/** 
 GroupField
 
*/
public class GroupField extends EntityOID
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 属性
	public boolean IsUse = false;
	public final String getEnName()
	{
		return this.GetValStrByKey(GroupFieldAttr.EnName);
	}
	public final void setEnName(String value)
	{
		this.SetValByKey(GroupFieldAttr.EnName, value);
	}
	public final String getLab()
	{
		return this.GetValStrByKey(GroupFieldAttr.Lab);
	}
	public final void setLab(String value)
	{
		this.SetValByKey(GroupFieldAttr.Lab, value);
	}
	public final int getIdx()
	{
		return this.GetValIntByKey(GroupFieldAttr.Idx);
	}
	public final void setIdx(int value)
	{
		this.SetValByKey(GroupFieldAttr.Idx, value);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造方法
	/** 
	 GroupField
	 
	*/
	public GroupField()
	{
	}
	public GroupField(int oid)
	{
		super(oid);
	}
	/** 
	 EnMap
	 
	*/
	@Override
	public Map getEnMap()
	{
		if (this.get_enMap() != null)
		{
			return this.get_enMap();
		}
		Map map = new Map("Sys_GroupField");
		map.setDepositaryOfEntity(Depositary.None);
		map.setDepositaryOfMap(Depositary.Application);
		map.setEnDesc("GroupField");
		map.setEnType(EnType.Sys);

		map.AddTBIntPKOID();
		map.AddTBString(GroupFieldAttr.Lab, null, "Lab", true, false, 0, 500, 20);
		map.AddTBString(GroupFieldAttr.EnName, null, "主表", true, false, 0, 200, 20);
		map.AddTBInt(GroupFieldAttr.Idx, 99, "Idx", true, false);
		map.AddTBString(FrmBtnAttr.GUID, null, "GUID", true, false, 0, 128, 20);

		this.set_enMap(map);
		return this.get_enMap();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	public final void DoDown()
	{
		try {
			this.DoOrderDown(GroupFieldAttr.EnName, this.getEnName(), GroupFieldAttr.Idx);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
	public final void DoUp()
	{
		try {
			this.DoOrderUp(GroupFieldAttr.EnName, this.getEnName(), GroupFieldAttr.Idx);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
	@Override
	protected boolean beforeInsert()
	{
		if (this.IsExit(GroupFieldAttr.EnName, this.getEnName(), GroupFieldAttr.Lab, this.getLab()))
		{
			throw new RuntimeException("@已经在("+this.getEnName()+")里存在("+this.getLab()+")的分组了。");
		}

		try
		{
			String sql = "SELECT MAX(IDX) FROM " + this.getEnMap().getPhysicsTable() + " WHERE EnName='" + this.getEnName() + "'";
			this.setIdx(DBAccess.RunSQLReturnValInt(sql, 0) + 1);
		}
		catch (java.lang.Exception e)
		{
			this.setIdx(1);
		}
		return super.beforeInsert();
	}
}
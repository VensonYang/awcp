package BP.Sys.Frm;

import BP.DA.Depositary;
import BP.En.EnType;
import BP.En.EntityMyPK;
import BP.En.Map;

/** 
 表单元素扩展DB
 
*/
public class FrmEleDB extends EntityMyPK
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 属性
	/** 
	 EleID
	 
	*/
	public final String getEleID()
	{
		return this.GetValStrByKey(FrmEleDBAttr.EleID);
	}
	public final void setEleID(String value)
	{
		this.SetValByKey(FrmEleDBAttr.EleID, value);
	}
	/** 
	 Tag1
	 
	*/
	public final String getTag1()
	{
		return this.GetValStringByKey(FrmEleDBAttr.Tag1);
	}
	public final void setTag1(String value)
	{
		this.SetValByKey(FrmEleDBAttr.Tag1, value);
	}
	/** 
	 Tag2
	 
	*/
	public final String getTag2()
	{
		return this.GetValStringByKey(FrmEleDBAttr.Tag2);
	}
	public final void setTag2(String value)
	{
		this.SetValByKey(FrmEleDBAttr.Tag2, value);
	}
	/** 
	 Tag3
	 
	*/
	public final String getTag3()
	{
		return this.GetValStringByKey(FrmEleDBAttr.Tag3);
	}
	public final void setTag3(String value)
	{
		this.SetValByKey(FrmEleDBAttr.Tag3, value);
	}
	/** 
	 Tag4
	 
	*/
	public final String getTag4()
	{
		return this.GetValStringByKey(FrmEleDBAttr.Tag4);
	}
	public final void setTag4(String value)
	{
		this.SetValByKey(FrmEleDBAttr.Tag4, value);
	}
	/** 
	 FK_MapData
	 
	*/
	public final String getFK_MapData()
	{
		return this.GetValStrByKey(FrmEleDBAttr.FK_MapData);
	}
	public final void setFK_MapData(String value)
	{
		this.SetValByKey(FrmEleDBAttr.FK_MapData, value);
	}
	/** 
	 RefPKVal
	 
	*/
	public final String getRefPKVal()
	{
		return this.GetValStrByKey(FrmEleDBAttr.RefPKVal);
	}
	public final void setRefPKVal(String value)
	{
		this.SetValByKey(FrmEleDBAttr.RefPKVal, value);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造方法
	/** 
	 表单元素扩展DB
	 
	*/
	public FrmEleDB()
	{
	}
	/** 
	 表单元素扩展DB
	 
	 @param mypk
	*/
	public FrmEleDB(String mypk)
	{
		this.setMyPK(mypk);
		this.Retrieve();
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
		Map map = new Map("Sys_FrmEleDB");
		map.setDepositaryOfEntity(Depositary.None);
		map.setDepositaryOfMap(Depositary.Application);
		map.setEnDesc("表单元素扩展DB");
		map.setEnType(EnType.Sys);

		map.AddMyPK();
		map.AddTBString(FrmEleDBAttr.FK_MapData, null, "FK_MapData", true, false, 1, 30, 20);
		map.AddTBString(FrmEleDBAttr.EleID, null, "EleID", true, false, 0, 50, 20);
		map.AddTBString(FrmEleDBAttr.RefPKVal, null, "RefPKVal", true, false, 0, 50, 20);

		map.AddTBString(FrmEleDBAttr.Tag1, null, "Tag1", true, false, 0, 4000, 20);
		map.AddTBString(FrmEleDBAttr.Tag2, null, "Tag2", true, false, 0, 4000, 20);
		map.AddTBString(FrmEleDBAttr.Tag3, null, "Tag3", true, false, 0, 4000, 20);
		map.AddTBString(FrmEleDBAttr.Tag4, null, "Tag4", true, false, 0, 4000, 20);

		this.set_enMap(map);
		return this.get_enMap();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	@Override
	protected boolean beforeUpdateInsertAction()
	{
		//this.MyPK = this.FK_MapData + "_" + this.EleID + "_" + this.RefPKVal;
		this.GenerPKVal();
		return super.beforeUpdateInsertAction();
	}
	public final void GenerPKVal()
	{
		this.setMyPK(this.getFK_MapData() + "_" + this.getEleID() + "_" + this.getRefPKVal());
	}
}
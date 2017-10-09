package BP.Sys.Frm;

import BP.DA.Depositary;
import BP.En.EnType;
import BP.En.EntityMyPK;
import BP.En.Map;

/** 
 单选框
 
*/
public class FrmRB extends EntityMyPK
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 属性
	public final String getLab()
	{
		return this.GetValStringByKey(FrmRBAttr.Lab);
	}
	public final void setLab(String value)
	{
		this.SetValByKey(FrmRBAttr.Lab, value);
	}
	public final String getKeyOfEn()
	{
		return this.GetValStringByKey(FrmRBAttr.KeyOfEn);
	}
	
	public final String getKeyOfEnToLowerCase()
	{
		return getKeyOfEn().toLowerCase();
	}
	public final void setKeyOfEn(String value)
	{
		this.SetValByKey(FrmRBAttr.KeyOfEn, value);
	}
	public final int getIntKey()
	{
		return this.GetValIntByKey(FrmRBAttr.IntKey);
	}
	public final void setIntKey(int value)
	{
		this.SetValByKey(FrmRBAttr.IntKey, value);
	}
	/** 
	  Y
	 
	*/
	public final float getY()
	{
		return this.GetValFloatByKey(FrmRBAttr.Y);
	}
	public final void setY(float value)
	{
		this.SetValByKey(FrmRBAttr.Y, value);
	}
	public final float getX()
	{
		return this.GetValFloatByKey(FrmRBAttr.X);
	}
	public final void setX(float value)
	{
		this.SetValByKey(FrmRBAttr.X, value);
	}
	public final String getFK_MapData()
	{
		return this.GetValStrByKey(FrmRBAttr.FK_MapData);
	}
	public final void setFK_MapData(String value)
	{
		this.SetValByKey(FrmRBAttr.FK_MapData, value);
	}
	public final String getEnumKey()
	{
		return this.GetValStrByKey(FrmRBAttr.EnumKey);
	}
	public final void setEnumKey(String value)
	{
		this.SetValByKey(FrmRBAttr.EnumKey, value);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造方法
	/** 
	 单选框
	 
	*/
	public FrmRB()
	{
	}
	public FrmRB(String mypk)
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
		Map map = new Map("Sys_FrmRB");
		map.setDepositaryOfEntity(Depositary.None);
		map.setDepositaryOfMap(Depositary.Application);
		map.setEnDesc("单选框");
		map.setEnType(EnType.Sys);

		map.AddMyPK();
		map.AddTBString(FrmRBAttr.FK_MapData, null, "FK_MapData", true, false, 0, 30, 20);
		map.AddTBString(FrmRBAttr.KeyOfEn, null, "KeyOfEn", true, false, 0, 30, 20);
		map.AddTBString(FrmRBAttr.EnumKey, null, "EnumKey", true, false, 0, 30, 20);
		map.AddTBString(FrmRBAttr.Lab, null, "Lab", true, false, 0, 90, 20);
		map.AddTBInt(FrmRBAttr.IntKey, 0, "IntKey", true, false);

		map.AddTBFloat(FrmRBAttr.X, 5, "X", true, false);
		map.AddTBFloat(FrmRBAttr.Y, 5, "Y", false, false);
		map.AddTBString(FrmBtnAttr.GUID, null, "GUID", true, false, 0, 128, 20);


		this.set_enMap(map);
		return this.get_enMap();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	@Override
	protected boolean beforeInsert()
	{
		this.setMyPK(this.getFK_MapData() + "_" + this.getKeyOfEn() + "_" + this.getIntKey());
		return super.beforeInsert();
	}

	@Override
	protected boolean beforeUpdateInsertAction()
	{
		this.setMyPK(this.getFK_MapData() + "_" + this.getKeyOfEn() + "_" + this.getIntKey());
		return super.beforeUpdateInsertAction();
	}
}
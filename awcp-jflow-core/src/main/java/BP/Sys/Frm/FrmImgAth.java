package BP.Sys.Frm;

import BP.DA.Depositary;
import BP.En.EnType;
import BP.En.EntityMyPK;
import BP.En.Map;

/** 
 图片附件
 
*/
public class FrmImgAth extends EntityMyPK
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 属性
	/** 
	 控件ID
	 
	*/
	public final String getCtrlID()
	{
		return this.GetValStringByKey(FrmImgAthAttr.CtrlID);
	}
	public final void setCtrlID(String value)
	{
		this.SetValByKey(FrmImgAthAttr.CtrlID, value);
	}
	/** 
	 Y
	 
	*/
	public final float getY()
	{
		return this.GetValFloatByKey(FrmImgAthAttr.Y);
	}
	public final void setY(float value)
	{
		this.SetValByKey(FrmImgAthAttr.Y, value);
	}
	/** 
	 X
	 
	*/
	public final float getX()
	{
		return this.GetValFloatByKey(FrmImgAthAttr.X);
	}
	public final void setX(float value)
	{
		this.SetValByKey(FrmImgAthAttr.X, value);
	}
	/** 
	 H
	 
	*/
	public final float getH()
	{
		return this.GetValFloatByKey(FrmImgAthAttr.H);
	}
	public final void setH(float value)
	{
		this.SetValByKey(FrmImgAthAttr.H, value);
	}
	/** 
	 W
	 
	*/
	public final float getW()
	{
		return this.GetValFloatByKey(FrmImgAthAttr.W);
	}
	public final void setW(float value)
	{
		this.SetValByKey(FrmImgAthAttr.W, value);
	}
	/** 
	 FK_MapData
	 
	*/
	public final String getFK_MapData()
	{
		return this.GetValStrByKey(FrmImgAthAttr.FK_MapData);
	}
	public final void setFK_MapData(String value)
	{
		this.SetValByKey(FrmImgAthAttr.FK_MapData, value);
	}
	/** 
	 是否可编辑
	 
	*/
	public final boolean getIsEdit()
	{
		return this.GetValBooleanByKey(FrmImgAthAttr.IsEdit);
	}
	public final void setIsEdit(boolean value)
	{
		this.SetValByKey(FrmImgAthAttr.IsEdit, value);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造方法
	/** 
	 图片附件
	 
	*/
	public FrmImgAth()
	{
	}
	/** 
	 图片附件
	 
	 @param mypk
	*/
	public FrmImgAth(String mypk)
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
		Map map = new Map("Sys_FrmImgAth");
		map.setDepositaryOfEntity(Depositary.None);
		map.setDepositaryOfMap(Depositary.Application);
		map.setEnDesc("图片附件描述");
		map.setEnType(EnType.Sys);
		map.AddMyPK();

		map.AddTBString(FrmImgAthAttr.FK_MapData, null, "表单ID", true, false, 1, 30, 20);
		map.AddTBString(FrmImgAthAttr.CtrlID, null, "控件ID", true, false, 0, 200, 20);

		  //map.AddTBString(FrmImgAthAttr.CtrlName, null, "控件名称", true, false, 0, 200, 20);

		map.AddTBFloat(FrmImgAthAttr.X, 5, "X", true, false);
		map.AddTBFloat(FrmImgAthAttr.Y, 5, "Y", false, false);

		map.AddTBFloat(FrmImgAthAttr.H, 200, "H", true, false);
		map.AddTBFloat(FrmImgAthAttr.W, 160, "W", false, false);

		map.AddTBInt(FrmImgAthAttr.IsEdit, 1, "是否可编辑", true, true);
		map.AddTBString(FrmBtnAttr.GUID, null, "GUID", true, false, 0, 128, 20);

		this.set_enMap(map);
		return this.get_enMap();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	@Override
	protected boolean beforeUpdateInsertAction()
	{
		this.setMyPK(this.getFK_MapData() + this.getCtrlID());
		return super.beforeUpdateInsertAction();
	}
}
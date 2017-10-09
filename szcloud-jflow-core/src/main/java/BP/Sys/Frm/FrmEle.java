package BP.Sys.Frm;

import BP.DA.Depositary;
import BP.En.EnType;
import BP.En.EntityMyPK;
import BP.En.Map;

/** 
 表单元素扩展
 
*/
public class FrmEle extends EntityMyPK
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region  签名存储位置.
	public final String getHandSigantureSavePath()
	{
		return this.GetValStrByKey(FrmEleAttr.Tag1);
	}
	public final String getHandSiganture_WinOpenH()
	{
		return this.GetValStrByKey(FrmEleAttr.Tag2);
	}
	public final String getHandSiganture_WinOpenW()
	{
		return this.GetValStrByKey(FrmEleAttr.Tag3);
	}
	public final String getHandSiganture_UrlPath()
	{
		return this.GetValStrByKey(FrmEleAttr.Tag4);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion  HandSigantureSavePath

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 类型
	/** 
	 手工签名
	 
	*/
	public static final String HandSiganture = "HandSiganture";
	/** 
	 电子签名
	 
	*/
	public static final String EleSiganture = "EleSiganture";
	/** 
	 网页框架
	 
	*/
	public static final String iFrame = "iFrame";
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion 类型

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 属性
	/** 
	 是否起用
	 
	*/
	public final boolean getIsEnable()
	{
		return this.GetValBooleanByKey(FrmEleAttr.IsEnable);
	}
	public final void setIsEnable(boolean value)
	{
		this.SetValByKey(FrmEleAttr.IsEnable, value);
	}
	/** 
	 EleID
	 
	*/
	public final String getEleID()
	{
		return this.GetValStrByKey(FrmEleAttr.EleID);
	}
	public final void setEleID(String value)
	{
		this.SetValByKey(FrmEleAttr.EleID, value);
	}
	/** 
	 EleName
	 
	*/
	public final String getEleName()
	{
		return this.GetValStringByKey(FrmEleAttr.EleName);
	}
	public final void setEleName(String value)
	{
		this.SetValByKey(FrmEleAttr.EleName, value);
	}
	/** 
	 Tag1
	 
	*/
	public final String getTag1()
	{
		return this.GetValStringByKey(FrmEleAttr.Tag1);
	}
	public final void setTag1(String value)
	{
		this.SetValByKey(FrmEleAttr.Tag1, value);
	}
	/** 
	 Tag2
	 
	*/
	public final String getTag2()
	{
		return this.GetValStringByKey(FrmEleAttr.Tag2);
	}
	public final void setTag2(String value)
	{
		this.SetValByKey(FrmEleAttr.Tag2, value);
	}
	/** 
	 Tag3
	 
	*/
	public final String getTag3()
	{
		return this.GetValStringByKey(FrmEleAttr.Tag3);
	}
	public final void setTag3(String value)
	{
		this.SetValByKey(FrmEleAttr.Tag3, value);
	}
	public final String getTag4()
	{
		return this.GetValStringByKey(FrmEleAttr.Tag4);
	}
	public final void setTag4(String value)
	{
		this.SetValByKey(FrmEleAttr.Tag4, value);
	}

	/** 
	 FK_MapData
	 
	*/
	public final String getFK_MapData()
	{
		return this.GetValStrByKey(FrmEleAttr.FK_MapData);
	}
	public final void setFK_MapData(String value)
	{
		this.SetValByKey(FrmEleAttr.FK_MapData, value);
	}
	/** 
	 EleType
	 
	*/
	public final String getEleType()
	{
		return this.GetValStrByKey(FrmEleAttr.EleType);
	}
	public final void setEleType(String value)
	{
		this.SetValByKey(FrmEleAttr.EleType, value);
	}
	public final float getX()
	{
		return this.GetValFloatByKey(FrmEleAttr.X);
	}
	public final void setX(float value)
	{
		this.SetValByKey(FrmEleAttr.X, value);
	}
	public final float getY()
	{
		return this.GetValFloatByKey(FrmEleAttr.Y);
	}
	public final void setY(float value)
	{
		this.SetValByKey(FrmEleAttr.Y, value);
	}
	public final float getH()
	{
		return this.GetValFloatByKey(FrmEleAttr.H);
	}
	public final void setH(float value)
	{
		this.SetValByKey(FrmEleAttr.H, value);
	}
	public final float getW()
	{
		return this.GetValFloatByKey(FrmEleAttr.W);
	}
	public final void setW(float value)
	{
		this.SetValByKey(FrmEleAttr.W, value);
	}

	public final int getHOfInt()
	{
		return (int)this.getH();
		/*
		 * warning return Integer.parseInt((new Float(this.getH())).ToString("0"));*/
	}
	public final int getWOfInt()
	{
		return (int)this.getW();
		/*
		 * warning return Integer.parseInt((new Float(this.getW())).ToString("0"));*/
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造方法
	/** 
	 表单元素扩展
	 
	*/
	public FrmEle()
	{
	}
	/** 
	 表单元素扩展
	 
	 @param mypk
	*/
	public FrmEle(String mypk)
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
		Map map = new Map("Sys_FrmEle");
		map.setDepositaryOfEntity(Depositary.None);
		map.setDepositaryOfMap(Depositary.Application);
		map.setEnDesc("表单元素扩展");
		map.setEnType(EnType.Sys);

		map.AddMyPK();

		map.AddTBString(FrmEleAttr.FK_MapData, null, "FK_MapData", true, false, 1, 30, 20);
		map.AddTBString(FrmEleAttr.EleType, null, "EleType可扩展的", true, false, 0, 50, 20);
		map.AddTBString(FrmEleAttr.EleID, null, "EleID", true, false, 0, 50, 20);
		map.AddTBString(FrmEleAttr.EleName, null, "名称", true, false, 0, 200, 20);

		map.AddTBFloat(FrmEleAttr.X, 5, "X", true, false);
		map.AddTBFloat(FrmEleAttr.Y, 5, "Y", false, false);

		map.AddTBFloat(FrmEleAttr.H, 20, "H", true, false);
		map.AddTBFloat(FrmEleAttr.W, 20, "W", false, false);

		map.AddTBInt(FrmEleAttr.IsEnable, 1, "IsEnable", false, false);

		map.AddTBString(FrmEleAttr.Tag1, null, "Tag1", true, false, 0, 50, 20);
		map.AddTBString(FrmEleAttr.Tag2, null, "Tag2", true, false, 0, 50, 20);
		map.AddTBString(FrmEleAttr.Tag3, null, "Tag3", true, false, 0, 50, 20);
		map.AddTBString(FrmEleAttr.Tag4, null, "Tag4", true, false, 0, 50, 20);

		map.AddTBString(FrmBtnAttr.GUID, null, "GUID", true, false, 0, 128, 20);

		this.set_enMap(map);
		return this.get_enMap();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	@Override
	protected boolean beforeUpdateInsertAction()
	{
		this.setMyPK(this.getFK_MapData() + "_" + this.getEleType() + "_" + this.getEleID());
		return super.beforeUpdateInsertAction();
	}
}
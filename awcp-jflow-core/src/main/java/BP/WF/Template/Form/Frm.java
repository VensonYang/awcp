package BP.WF.Template.Form;

import BP.DA.*;
import BP.Sys.*;
import BP.Sys.Frm.MapDataAttr;
import BP.En.*;
import BP.WF.Port.*;
import BP.WF.Template.PubLib.FormRunType;

/** 
 表单
 
*/
public class Frm extends EntityNoName
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 基本属性
	public FrmNode HisFrmNode = null;
	public final String getPTable()
	{
		return this.GetValStringByKey(FrmAttr.PTable);
	}
	public final void setPTable(String value)
	{
		this.SetValByKey(FrmAttr.PTable, value);
	}
	public final String getFK_Flow11()
	{
		return this.GetValStringByKey(FrmAttr.FK_Flow);
	}
	public final void setFK_Flow11(String value)
	{
		this.SetValByKey(FrmAttr.FK_Flow, value);
	}
	public final String getURL()
	{
		return this.GetValStringByKey(FrmAttr.URL);
	}
	public final void setURL(String value)
	{
		this.SetValByKey(FrmAttr.URL, value);
	}
	public final FormRunType getHisFormRunType()
	{
		return FormRunType.forValue(this.GetValIntByKey(FrmAttr.FormRunType));
	}
	public final void setHisFormRunType(FormRunType value)
	{
		this.SetValByKey(FrmAttr.FormRunType, value.getValue());
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造方法
	/** 
	 Frm
	 
	*/
	public Frm()
	{
	}
	/** 
	 Frm
	 
	 @param no
	*/
	public Frm(String no)
	{
		super(no);

	}
	/** 
	 重写基类方法
	 
	*/
	@Override
	public Map getEnMap()
	{
		if (this.get_enMap() != null)
		{
			return this.get_enMap();
		}

		Map map = new Map("Sys_MapData");

		map.setEnDesc ( "表单库");
		map.setDepositaryOfEntity ( Depositary.None);
		map.setDepositaryOfMap ( Depositary.Application);
		map.setCodeStruct ( "4");
		map.setIsAutoGenerNo ( false);

		map.AddTBStringPK(FrmAttr.No, null, null, true, true, 1, 4, 4);
		map.AddTBString(FrmAttr.Name, null, null, true, false, 0, 50, 10);
		map.AddTBString(FrmAttr.FK_Flow, null, "流程表单属性:FK_Flow", true, false, 0, 50, 10);
		map.AddDDLSysEnum(FrmAttr.FormRunType, 0, "流程表单属性:运行类型", true, false, FrmAttr.FormRunType);
		map.AddTBString(FrmAttr.PTable, null, "物理表", true, false, 0, 50, 10);
		map.AddTBInt(FrmAttr.DBURL, 0, "DBURL", true, false);

			// 如果是个自定义表单.
		map.AddTBString(FrmAttr.URL, null, "Url", true, false, 0, 50, 10);

			//表单类别.
		map.AddTBString(MapDataAttr.FK_FrmSort, "01", "表单类别", true, false, 0, 500, 20);

		map.AddTBInt(MapDataAttr.FrmW, 900, "表单宽度", true, false);
		map.AddTBInt(MapDataAttr.FrmH, 1200, "表单高度", true, false);

		this.set_enMap ( map);
		return this.get_enMap();
	}
	public final int getFrmW()
	{
		return this.GetValIntByKey(MapDataAttr.FrmW);
	}
	public final int getFrmH()
	{
		return this.GetValIntByKey(MapDataAttr.FrmH);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}
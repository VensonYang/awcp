package BP.Sys.Frm;

import BP.DA.DBAccess;
import BP.DA.Depositary;
import BP.En.EnType;
import BP.En.EntityMyPK;
import BP.En.Map;
import BP.Sys.PubClass;

/** 
 线
 
*/
public class FrmLine extends EntityMyPK
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 属性
	/** 
	 Y
	 
	*/
	public final float getY()
	{
		return this.GetValFloatByKey(FrmLineAttr.Y);
	}
	public final void setY(float value)
	{
		this.SetValByKey(FrmLineAttr.Y, value);
	}
	/** 
	 X
	 
	*/
	public final float getX()
	{
		return this.GetValFloatByKey(FrmLineAttr.X);
	}
	public final void setX(float value)
	{
		this.SetValByKey(FrmLineAttr.X, value);
	}
	public final String getBorderColorHtml()
	{
		try {
			return PubClass.ToHtmlColor(this.getBorderColor());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Black";
		}
	}
	public final String getBorderColor()
	{
		return this.GetValStringByKey("BorderColor");
	}
	public final void setBorderColor(String value)
	{
		this.SetValByKey(FrmLineAttr.BorderColor, value);
	}
	public final float getBorderWidth()
	{
		return this.GetValFloatByKey(FrmLineAttr.BorderWidth);
	}
	public final void setBorderWidth(float value)
	{
		this.SetValByKey(FrmLineAttr.BorderWidth, value);
	}
	/** 
	 Y1
	 
	*/
	public final float getY1()
	{
		return this.GetValFloatByKey(FrmLineAttr.Y1);
	}
	public final void setY1(float value)
	{
		this.SetValByKey(FrmLineAttr.Y1, value);
	}
	/** 
	 X1
	 
	*/
	public final float getX1()
	{
		return this.GetValFloatByKey(FrmLineAttr.X1);
	}
	public final void setX1(float value)
	{
		this.SetValByKey(FrmLineAttr.X1, value);
	}
	public final String getFK_MapData()
	{
		return this.GetValStrByKey(FrmLineAttr.FK_MapData);
	}
	public final void setFK_MapData(String value)
	{
		this.SetValByKey(FrmLineAttr.FK_MapData, value);
	}
	public final float getY2()
	{
		return this.GetValFloatByKey(FrmLineAttr.Y2);
	}
	public final void setY2(float value)
	{
		this.SetValByKey(FrmLineAttr.Y2, value);
	}
	public final float getX2()
	{
		return this.GetValFloatByKey(FrmLineAttr.X2);
	}
	public final void setX2(float value)
	{
		this.SetValByKey(FrmLineAttr.X2, value);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造方法
	/** 
	 线
	 
	*/
	public FrmLine()
	{
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
		Map map = new Map("Sys_FrmLine");
		map.setDepositaryOfEntity(Depositary.None);
		map.setDepositaryOfMap(Depositary.Application);
		map.setEnDesc("线");
		map.setEnType(EnType.Sys);

		map.AddMyPK();
		map.AddTBString(FrmLineAttr.FK_MapData, null, "主表", true, false, 0, 30, 20);

			//xy 没有用了.
		map.AddTBFloat(FrmLineAttr.X, 5, "X", true, false);
		map.AddTBFloat(FrmLineAttr.Y, 5, "Y", false, false);

		map.AddTBFloat(FrmLineAttr.X1, 5, "X1", true, false);
		map.AddTBFloat(FrmLineAttr.Y1, 5, "Y1", false, false);

		map.AddTBFloat(FrmLineAttr.X2, 9, "X2", false, false);
		map.AddTBFloat(FrmLineAttr.Y2, 9, "Y2", false, false);

		map.AddTBFloat(FrmLineAttr.BorderWidth, 1, "宽度", false, false);
		map.AddTBString(FrmLineAttr.BorderColor, "black", "颜色", true, false, 0, 30, 20);

		map.AddTBString(FrmBtnAttr.GUID, null, "GUID", true, false, 0, 128, 20);

		this.set_enMap(map);
		return this.get_enMap();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 是否存在相同的数据?
	 
	 @return 
	*/
	public final boolean IsExitGenerPK()
	{
		String sql = "SELECT COUNT(*) FROM " + this.getEnMap().getPhysicsTable() + " WHERE FK_MapData='" + this.getFK_MapData() + "' AND X=" + this.getX() + " AND Y=" + this.getY() + " and x1=" + this.getX1() + " and x2=" + this.getX2() + " and y1=" + this.getY1() + " and y2=" + this.getY2();
		try {
			if (DBAccess.RunSQLReturnValInt(sql, 0) == 0)
			{
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}
package BP.Sys.Frm;

import BP.DA.Depositary;
import BP.En.EnType;
import BP.En.EntityMyPK;
import BP.En.Map;
import BP.Tools.StringHelper;

/** 
 框架
 
*/
public class MapFrame extends EntityMyPK
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 属性
	/** 
	 是否自适应大小
	 
	*/
	public final boolean getIsAutoSize()
	{
		return this.GetValBooleanByKey(MapFrameAttr.IsAutoSize);
	}
	public final void setIsAutoSize(boolean value)
	{
		this.SetValByKey(MapFrameAttr.IsAutoSize, value);
	}
	/** 
	 编号
	 
	*/
	public final String getNoOfObj()
	{
		return this.GetValStrByKey(MapFrameAttr.NoOfObj);
	}
	public final void setNoOfObj(String value)
	{
		this.SetValByKey(MapFrameAttr.NoOfObj, value);
	}
	/** 
	 名称
	 
	*/
	public final String getName()
	{
		return this.GetValStrByKey(MapFrameAttr.Name);
	}
	public final void setName(String value)
	{
		this.SetValByKey(MapFrameAttr.Name, value);
	}
	/** 
	 连接
	 
	*/
	public final String getURL()
	{
		String s= this.GetValStrByKey(MapFrameAttr.URL);
		if (StringHelper.isNullOrEmpty(s))
		{
			return "http://ccflow.org";
		}
		return s;
	}
	public final void setURL(String value)
	{
		this.SetValByKey(MapFrameAttr.URL, value);
	}
	/** 
	 高度
	 
	*/
	public final String getH()
	{
		return this.GetValStrByKey(MapFrameAttr.H);
	}
	public final void setH(String value)
	{
		this.SetValByKey(MapFrameAttr.H, value);
	}
	/** 
	 宽度
	 
	*/
	public final String getW()
	{
		return this.GetValStrByKey(MapFrameAttr.W);
	}
	public final void setW(String value)
	{
		this.SetValByKey(MapFrameAttr.W, value);
	}
	public boolean IsUse = false;
	public final String getFK_MapData()
	{
		return this.GetValStrByKey(MapFrameAttr.FK_MapData);
	}
	public final void setFK_MapData(String value)
	{
		this.SetValByKey(MapFrameAttr.FK_MapData, value);
	}
	public final int getRowIdx()
	{
		return this.GetValIntByKey(MapFrameAttr.RowIdx);
	}
	public final void setRowIdx(int value)
	{
		this.SetValByKey(MapFrameAttr.RowIdx, value);
	}

	public final int getGroupID()
	{
		return this.GetValIntByKey(MapFrameAttr.GroupID);
	}
	public final void setGroupID(int value)
	{
		this.SetValByKey(MapFrameAttr.GroupID, value);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造方法
	/** 
	 框架
	 
	*/
	public MapFrame()
	{
	}
	/** 
	 框架
	 
	 @param no
	*/
	public MapFrame(String mypk)
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
		Map map = new Map("Sys_MapFrame");
		map.setDepositaryOfEntity(Depositary.None);
		map.setDepositaryOfMap(Depositary.Application);
		map.setEnDesc("框架");
		map.setEnType(EnType.Sys);

		map.AddMyPK();
		map.AddTBString(MapFrameAttr.NoOfObj, null, "编号", true, false, 1, 20, 20);
		map.AddTBString(MapFrameAttr.Name, null, "名称", true, false, 1, 200, 20);

		map.AddTBString(MapFrameAttr.FK_MapData, null, "主表", true, false, 0, 30, 20);
		map.AddTBString(MapFrameAttr.URL, null, "URL", true, false, 0, 3000, 20);
		map.AddTBString(MapFrameAttr.W, null, "W", true, false, 0, 20, 20);
		map.AddTBString(MapFrameAttr.H, null, "H", true, false, 0, 20, 20);

			//map.AddTBInt(MapFrameAttr.H, 500, "高度", false, false);
			//map.AddTBInt(MapFrameAttr.W, 400, "宽度", false, false);

		map.AddBoolean(MapFrameAttr.IsAutoSize, true, "是否自动设置大小", false, false);
		map.AddTBInt(MapFrameAttr.RowIdx, 99, "位置", false, false);
		map.AddTBInt(MapFrameAttr.GroupID, 0, "GroupID", false, false);

		map.AddTBString(FrmBtnAttr.GUID, null, "GUID", true, false, 0, 128, 20);


		this.set_enMap(map);
		return this.get_enMap();
	}

	@Override
	protected boolean beforeUpdateInsertAction()
	{
		this.setMyPK(this.getFK_MapData() + "_" + this.getNoOfObj());
		return super.beforeUpdateInsertAction();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}
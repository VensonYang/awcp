package BP.Sys.Frm;

import BP.DA.DBAccess;
import BP.DA.Depositary;
import BP.En.EnType;
import BP.En.EntityMyPK;
import BP.En.Map;
import BP.Tools.StringHelper;
import BP.WF.Glo;

/** 
 图片
 
*/
public class FrmImg extends EntityMyPK
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 属性
	/** 
	 中文名称
	 
	*/
	public final String getName()
	{
		return this.GetValStringByKey(FrmImgAttr.Name);
	}
	public final void setName(String value)
	{
		this.SetValByKey(FrmImgAttr.Name, value);
	}
	/** 
	 英文名称
	 
	*/
	public final String getEnPK()
	{
		return this.GetValStringByKey(FrmImgAttr.EnPK);
	}
	public final void setEnPK(String value)
	{
		this.SetValByKey(FrmImgAttr.EnPK, value);
	}
	/** 
	 是否可以编辑
	 
	*/
	public final int getIsEdit()
	{
		return this.GetValIntByKey(FrmImgAttr.IsEdit);
	}
	public final void setIsEdit(int value)
	{
		this.SetValByKey(FrmImgAttr.IsEdit, (int)value);
	}
	/** 
	 应用类型
	 
	*/
	public final ImgAppType getHisImgAppType()
	{
		return ImgAppType.forValue(this.GetValIntByKey(FrmImgAttr.ImgAppType));
	}
	public final void setHisImgAppType(ImgAppType value)
	{
		this.SetValByKey(FrmImgAttr.ImgAppType, value.getValue());
	}
	/** 
	 数据来源
	 
	*/
	public final int getSrcType()
	{
		return this.GetValIntByKey(FrmImgAttr.SrcType);
	}
	public final void setSrcType(int value)
	{
		this.SetValByKey(FrmImgAttr.SrcType, value);
	}

	public final String getTag0()
	{
		return this.GetValStringByKey(FrmImgAttr.Tag0);
	}
	public final void setTag0(String value)
	{
		this.SetValByKey(FrmImgAttr.Tag0, value);
	}
	public final String getLinkTarget()
	{
		return this.GetValStringByKey(FrmImgAttr.LinkTarget);
	}
	public final void setLinkTarget(String value)
	{
		this.SetValByKey(FrmImgAttr.LinkTarget, value);
	}
	/** 
	 URL
	 
	*/
	public final String getLinkURL()
	{
		return this.GetValStringByKey(FrmImgAttr.LinkURL);
	}
	public final void setLinkURL(String value)
	{
		this.SetValByKey(FrmImgAttr.LinkURL, value);
	}
	public final String getImgPath()
	{
		String src = this.GetValStringByKey(FrmImgAttr.ImgPath);
		if (StringHelper.isNullOrEmpty(src))
		{
//			String appPath = Glo.getIntallPath();
			src =  "/DataUser/ICON/" + BP.Sys.SystemConfig.getCompanyID() + "/LogBiger.png";
		}
		return src;
	}
	public final void setImgPath(String value)
	{
		this.SetValByKey(FrmImgAttr.ImgPath, value);
	}
	public final String getImgURL()
	{
		String src = this.GetValStringByKey(FrmImgAttr.ImgURL);
		if (StringHelper.isNullOrEmpty(src) || src.contains("component/Img"))
		{
//			String appPath = BP.Sys.Glo.getRequest().getRemoteAddr();
			src = "/DataUser/ICON/" + BP.Sys.SystemConfig.getCompanyID() + "/LogBiger.png";
		}
		return src;
	}
	public final void setImgURL(String value)
	{
		this.SetValByKey(FrmImgAttr.ImgURL, value);
	}
	/** 
	 Y
	 
	*/
	public final float getY()
	{
		return this.GetValFloatByKey(FrmImgAttr.Y);
	}
	public final void setY(float value)
	{
		this.SetValByKey(FrmImgAttr.Y, value);
	}
	/** 
	 X
	 
	*/
	public final float getX()
	{
		return this.GetValFloatByKey(FrmImgAttr.X);
	}
	public final void setX(float value)
	{
		this.SetValByKey(FrmImgAttr.X, value);
	}
	/** 
	 H
	 
	*/
	public final float getH()
	{
		return this.GetValFloatByKey(FrmImgAttr.H);
	}
	public final void setH(float value)
	{
		this.SetValByKey(FrmImgAttr.H, value);
	}
	/** 
	 W
	 
	*/
	public final float getW()
	{
		return this.GetValFloatByKey(FrmImgAttr.W);
	}
	public final void setW(float value)
	{
		this.SetValByKey(FrmImgAttr.W, value);
	}
	/** 
	 FK_MapData
	 
	*/
	public final String getFK_MapData()
	{
		return this.GetValStrByKey(FrmImgAttr.FK_MapData);
	}
	public final void setFK_MapData(String value)
	{
		this.SetValByKey(FrmImgAttr.FK_MapData, value);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造方法
	/** 
	 图片
	 
	*/
	public FrmImg()
	{
	}
	/** 
	 图片
	 
	 @param mypk
	*/
	public FrmImg(String mypk)
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
		Map map = new Map("Sys_FrmImg");
		map.setDepositaryOfEntity(Depositary.None);
		map.setDepositaryOfMap(Depositary.Application);
		map.setEnDesc("图片");
		map.setEnType(EnType.Sys);
		map.AddMyPK();
		map.AddTBString(FrmImgAttr.FK_MapData, null, "FK_MapData", true, false, 1, 30, 20);
		map.AddTBInt(FrmImgAttr.ImgAppType, 0, "应用类型", false, false);

		map.AddTBFloat(FrmImgAttr.X, 5, "X", true, false);
		map.AddTBFloat(FrmImgAttr.Y, 5, "Y", false, false);

		map.AddTBFloat(FrmImgAttr.H, 200, "H", true, false);
		map.AddTBFloat(FrmImgAttr.W, 160, "W", false, false);

		map.AddTBString(FrmImgAttr.ImgURL, null, "ImgURL", true, false, 0, 200, 20);
		map.AddTBString(FrmImgAttr.ImgPath, null, "ImgPath", true, false, 0, 200, 20);

		map.AddTBString(FrmImgAttr.LinkURL, null, "LinkURL", true, false, 0, 200, 20);
		map.AddTBString(FrmImgAttr.LinkTarget, "_blank", "LinkTarget", true, false, 0, 200, 20);

		map.AddTBString(FrmBtnAttr.GUID, null, "GUID", true, false, 0, 128, 20);

			// 如果是seal 就是岗位集合。
		map.AddTBString(FrmImgAttr.Tag0, null, "参数", true, false, 0, 500, 20);
		map.AddTBInt(FrmImgAttr.SrcType, 0, "图片来源0=本地,1=URL", true, false);
		map.AddTBInt(FrmImgAttr.IsEdit, 0, "是否可以编辑", true, false);
		map.AddTBString(FrmImgAttr.Name, null, "中文名称", true, false, 0, 500, 20);
		map.AddTBString(FrmImgAttr.EnPK, null, "英文名称", true, false, 0, 500, 20);
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
		String sql = "SELECT COUNT(*) FROM " + this.getEnMap().getPhysicsTable() + " WHERE FK_MapData='" + this.getFK_MapData() + "' AND X=" + this.getX() + " AND Y=" + this.getY();
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
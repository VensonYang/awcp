package BP.Sys;

import BP.DA.Depositary;
import BP.En.EnType;
import BP.En.EntitiesNoName;
import BP.En.EntityNoName;
import BP.En.Map;
import BP.En.RefMethod;
import BP.En.RefMethodType;
import BP.En.UAC;
import BP.Sys.Frm.MapAttr;
import BP.Sys.Frm.MapAttrAttr;
import BP.Sys.Frm.MapAttrs;

/** 
 用户自定义表
 
*/
public class SFTable extends EntityNoName
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 链接到其他系统获取数据的属性
	/** 
	 数据源
	 
	*/
	public final String getFK_SFDBSrc()
	{
		return this.GetValStringByKey(SFTableAttr.FK_SFDBSrc);
	}
	public final void setFK_SFDBSrc(String value)
	{
		this.SetValByKey(SFTableAttr.FK_SFDBSrc, value);
	}
	/** 
	 物理表名称
	 
	*/
	public final String getSrcTable()
	{
		return this.GetValStringByKey(SFTableAttr.SrcTable);
	}
	public final void setSrcTable(String value)
	{
		this.SetValByKey(SFTableAttr.SrcTable, value);
	}
	/** 
	 值/主键字段名
	 
	*/
	public final String getColumnValue()
	{
		return this.GetValStringByKey(SFTableAttr.ColumnValue);
	}
	public final void setColumnValue(String value)
	{
		this.SetValByKey(SFTableAttr.ColumnValue, value);
	}
	/** 
	 显示字段/显示字段名
	 
	*/
	public final String getColumnText()
	{
		return this.GetValStringByKey(SFTableAttr.ColumnText);
	}
	public final void setColumnText(String value)
	{
		this.SetValByKey(SFTableAttr.ColumnText, value);
	}
	/** 
	 父结点字段名
	 
	*/
	public final String getParentValue()
	{
		return this.GetValStringByKey(SFTableAttr.ParentValue);
	}
	public final void setParentValue(String value)
	{
		this.SetValByKey(SFTableAttr.ParentValue, value);
	}

	/** 
	 查询语句
	 
	*/
	public final String getSelectStatement()
	{
		return this.GetValStringByKey(SFTableAttr.SelectStatement);
	}
	public final void setSelectStatement(String value)
	{
		this.SetValByKey(SFTableAttr.SelectStatement, value);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 属性
	/** 
	 是否是类
	 
	*/
	public final boolean getIsClass()
	{
		if (this.getNo().contains("."))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	/** 
	 是否是树形实体?
	 
	*/
	public final boolean getIsTree()
	{
		return this.GetValBooleanByKey(SFTableAttr.IsTree);
	}
	public final void setIsTree(boolean value)
	{
		this.SetValByKey(SFTableAttr.IsTree, value);
	}

	/** 
	 字典表类型
	 0：NoName类型
	 1：NoNameTree类型
	 2：NoName行政区划类型
	 
	*/
	public final int getSFTableType()
	{
		return this.GetValIntByKey(SFTableAttr.SFTableType);
	}
	public final void setSFTableType(int value)
	{
		this.SetValByKey(SFTableAttr.SFTableType, value);
	}

	/** 
	 值
	 
	*/
	public final String getFK_Val()
	{
		return this.GetValStringByKey(SFTableAttr.FK_Val);
	}
	public final void setFK_Val(String value)
	{
		this.SetValByKey(SFTableAttr.FK_Val, value);
	}
	public final String getTableDesc()
	{
		return this.GetValStringByKey(SFTableAttr.TableDesc);
	}
	public final void setTableDesc(String value)
	{
		this.SetValByKey(SFTableAttr.TableDesc, value);
	}
	public final String getDefVal()
	{
		return this.GetValStringByKey(SFTableAttr.DefVal);
	}
	public final void setDefVal(String value)
	{
		this.SetValByKey(SFTableAttr.DefVal, value);
	}
	public final EntitiesNoName getHisEns()
	{
		if (this.getIsClass())
		{
			EntitiesNoName ens = (EntitiesNoName)BP.En.ClassFactory.GetEns(this.getNo());
			ens.RetrieveAll();
			return ens;
		}

		GENoNames ges = new GENoNames(this.getNo(), this.getName());
		ges.RetrieveAll();
		return ges;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造方法
	@Override
	public UAC getHisUAC()
	{
		UAC uac = new UAC();
		uac.OpenForSysAdmin();
		uac.IsInsert = false;
		return uac;
	}
	/** 
	 用户自定义表
	 
	*/
	public SFTable()
	{
	}
	public SFTable(String mypk)
	{
		this.setNo(mypk);
		try
		{
			this.Retrieve();
		}
		catch (RuntimeException ex)
		{
//C# TO JAVA CONVERTER NOTE: The following 'switch' operated on a string member and was converted to Java 'if-else' logic:
//			switch (this.No)
//ORIGINAL LINE: case "BP.Pub.NYs":
			if (this.getNo().equals("BP.Pub.NYs"))
			{
					this.setName("年月");
				  //  this.HisSFTableType = SFTableType.ClsLab;
					this.setFK_Val("FK_NY");
				 //   this.IsEdit = true;
					this.Insert();
			}
//ORIGINAL LINE: case "BP.Pub.YFs":
			else if (this.getNo().equals("BP.Pub.YFs"))
			{
					this.setName("月");
				  //  this.HisSFTableType = SFTableType.ClsLab;
					this.setFK_Val("FK_YF");
				   // this.IsEdit = true;
					this.Insert();
			}
//ORIGINAL LINE: case "BP.Pub.Days":
			else if (this.getNo().equals("BP.Pub.Days"))
			{
					this.setName("天");
				 //   this.HisSFTableType = SFTableType.ClsLab;
					this.setFK_Val("FK_Day");
					//this.IsEdit = true;
					this.Insert();
			}
//ORIGINAL LINE: case "BP.Pub.NDs":
			else if (this.getNo().equals("BP.Pub.NDs"))
			{
					this.setName("年");
				 //   this.HisSFTableType = SFTableType.ClsLab;
					this.setFK_Val("FK_ND");
				   // this.IsEdit = true;
					this.Insert();
			}
			else
			{
					throw new RuntimeException(ex.getMessage());
			}
		}
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
		Map map = new Map("Sys_SFTable");
		map.setDepositaryOfEntity(Depositary.None);
		map.setDepositaryOfMap(Depositary.Application);
		map.setEnDesc("字典表");
		map.setEnType(EnType.Sys);

		map.AddTBStringPK(SFTableAttr.No, null, "表英文名称", true, false, 1, 20, 20);
		map.AddTBString(SFTableAttr.Name, null, "表中文名称", true, false, 0, 30, 20);
		map.AddTBString(SFTableAttr.FK_Val, null, "默认创建的字段名", true, false, 0, 50, 20);
		map.AddTBString(SFTableAttr.TableDesc, null, "表描述", true, false, 0, 50, 20);
		map.AddTBString(SFTableAttr.DefVal, null, "默认值", true, false, 0, 200, 20);
		map.AddBoolean(SFTableAttr.IsTree, false, "是否是树实体", true, true);
		map.AddDDLSysEnum(SFTableAttr.SFTableType, 0, "字典表类型", true, false, SFTableAttr.SFTableType, "@0=NoName类型@1=NoNameTree类型@2=NoName行政区划类型");

			//数据源.
		map.AddDDLEntities(SFTableAttr.FK_SFDBSrc, "local", "数据源", new BP.Sys.SFDBSrcs(), true);
		map.AddTBString(SFTableAttr.SrcTable, null, "数据源表", true, false, 0, 50, 20);
		map.AddTBString(SFTableAttr.ColumnValue, null, "显示的值(编号列)", true, false, 0, 50, 20);
		map.AddTBString(SFTableAttr.ColumnText, null, "显示的文字(名称列)", true, false, 0, 50, 20);
		map.AddTBString(SFTableAttr.ParentValue, null, "父级值(父级列)", true, false, 0, 50, 20);
		map.AddTBString(SFTableAttr.SelectStatement, null, "查询语句", true, false, 0, 1000, 600, true);

			//查找.
		map.AddSearchAttr(SFTableAttr.FK_SFDBSrc);

		RefMethod rm = new RefMethod();
		rm.Title = "编辑数据";
		rm.ClassMethodName = this.toString() + ".DoEdit";
		rm.refMethodType = RefMethodType.RightFrameOpen;
		rm.IsForEns = false;
		map.AddRefMethod(rm);

		rm = new RefMethod();
		rm.Title = "创建Table向导";
		rm.ClassMethodName = this.toString() + ".DoGuide";
		rm.refMethodType = RefMethodType.RightFrameOpen;
		rm.IsForEns = false;
		map.AddRefMethod(rm);

		rm = new RefMethod();
		rm.Title = "数据源管理";
		rm.ClassMethodName = this.toString() + ".DoMangDBSrc";
		rm.refMethodType = RefMethodType.RightFrameOpen;
		rm.IsForEns = false;
		map.AddRefMethod(rm);

		this.set_enMap(map);
		return this.get_enMap();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
	/** 
	 数据源管理
	 
	 @return 
	*/
	public final String DoMangDBSrc()
	{
		return BP.WF.Glo.getCCFlowAppPath()+"WF/Comm/Search.jsp?EnsName=BP.Sys.SFDBSrcs";
	}
	/** 
	 创建表向导
	 
	 @return 
	*/
	public final String DoGuide()
	{
		return BP.WF.Glo.getCCFlowAppPath()+"WF/Comm/Sys/SFGuide.jsp";
	}
	/** 
	 编辑数据
	 
	 @return 
	*/
	public final String DoEdit()
	{
		if (this.getIsClass())
		{
			return BP.WF.Glo.getCCFlowAppPath()+"WF/Comm/Ens.jsp?EnsName=" + this.getNo();
		}
		else
		{
			return BP.WF.Glo.getCCFlowAppPath()+"WF/MapDef/SFTableEditData.jsp?RefNo=" + this.getNo();
		}
	}
	@Override
	protected boolean beforeDelete()
	{
		MapAttrs attrs = new MapAttrs();
		attrs.Retrieve(MapAttrAttr.UIBindKey, this.getNo());
		if (attrs.size() != 0)
		{
			String err = "";
			for (Object item : attrs)
			{
				err += " @ " + ((MapAttr)item).getMyPK() + " " + ((MapAttr)item).getName();
			}
			throw new RuntimeException("@如下实体字段在引用:"+err);
		}
		return super.beforeDelete();
	}
}
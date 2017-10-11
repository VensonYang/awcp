package BP.Sys.Frm;

import BP.DA.Depositary;
import BP.En.EnType;
import BP.En.EntityMyPK;
import BP.En.Map;
import BP.Port.WebUser;
import BP.Tools.StringHelper;

/** 
 扩展
 
*/
public class MapExt extends EntityMyPK
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 关于 at 参数
	/** 
	 Pop参数.
	 
	*/
	public final int getPopValFormat()
	{
		return this.GetParaInt("PopValFormat");
	}
	public final void setPopValFormat(int value)
	{
		this.SetPara("PopValFormat", value);
	}
	/** 
	 pop 选择方式
	 0,多选,1=单选.
	 
	*/
	public final int getPopValSelectModel()
	{
		return this.GetParaInt("PopValSelectModel");
	}
	public final void setPopValSelectModel(int value)
	{
		this.SetPara("PopValSelectModel", value);
	}

	/** 
	 工作模式
	 0=url, 1=内置.
	 
	*/
	public final int getPopValWorkModel()
	{
		return this.GetParaInt("PopValWorkModel");
	}
	public final void setPopValWorkModel(int value)
	{
		this.SetPara("PopValWorkModel", value);
	}


	/** 
	 pop 呈现方式
	 0,表格,1=目录.
	 
	*/
	public final int getPopValShowModel()
	{
		return this.GetParaInt("PopValShowModel");
	}
	public final void setPopValShowModel(int value)
	{
		this.SetPara("PopValShowModel", value);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 属性
	public final String getExtDesc()
	{
		String dec = "";
//C# TO JAVA CONVERTER NOTE: The following 'switch' operated on a string member and was converted to Java 'if-else' logic:
//		switch (this.ExtType)
//ORIGINAL LINE: case MapExtXmlList.ActiveDDL:
		if (this.getExtType().equals(MapExtXmlList.ActiveDDL))
		{
				dec += "字段" + this.getAttrOfOper();
		}
//ORIGINAL LINE: case MapExtXmlList.TBFullCtrl:
		else if (this.getExtType().equals(MapExtXmlList.TBFullCtrl))
		{
				dec += this.getAttrOfOper();
		}
//ORIGINAL LINE: case MapExtXmlList.DDLFullCtrl:
		else if (this.getExtType().equals(MapExtXmlList.DDLFullCtrl))
		{
				dec += "" + this.getAttrOfOper();
		}
//ORIGINAL LINE: case MapExtXmlList.InputCheck:
		else if (this.getExtType().equals(MapExtXmlList.InputCheck))
		{
				dec += "字段：" + this.getAttrOfOper() + " 检查内容：" + this.getTag1();
		}
//ORIGINAL LINE: case MapExtXmlList.PopVal:
		else if (this.getExtType().equals(MapExtXmlList.PopVal))
		{
				dec += "字段：" + this.getAttrOfOper() + " Url：" + this.getTag();
		}
		else
		{
		}
		return dec;
	}
	/** 
	 是否自适应大小
	 
	*/
	public final boolean getIsAutoSize()
	{
		return this.GetValBooleanByKey(MapExtAttr.IsAutoSize);
	}
	public final void setIsAutoSize(boolean value)
	{
		this.SetValByKey(MapExtAttr.IsAutoSize, value);
	}
	/** 
	 数据源
	 
	*/
	public final String getDBSrc()
	{
		return this.GetValStrByKey(MapExtAttr.DBSrc);
	}
	public final void setDBSrc(String value)
	{
		this.SetValByKey(MapExtAttr.DBSrc, value);
	}
	public final String getAtPara()
	{
		return this.GetValStrByKey(MapExtAttr.AtPara);
	}
	public final void setAtPara(String value)
	{
		this.SetValByKey(MapExtAttr.AtPara, value);
	}

	public final String getExtType()
	{
		return this.GetValStrByKey(MapExtAttr.ExtType);
	}
	public final void setExtType(String value)
	{
		this.SetValByKey(MapExtAttr.ExtType, value);
	}
	public final int getDoWay()
	{
		return this.GetValIntByKey(MapExtAttr.DoWay);
	}
	public final void setDoWay(int value)
	{
		this.SetValByKey(MapExtAttr.DoWay, value);
	}
	/** 
	 操作的attrs
	 
	*/
	public final String getAttrOfOper()
	{
		return this.GetValStrByKey(MapExtAttr.AttrOfOper);
	}
	public final void setAttrOfOper(String value)
	{
		this.SetValByKey(MapExtAttr.AttrOfOper, value);
	}
	
	public final String getAttrOfOperToLowerCase()
	{
		return getAttrOfOper().toLowerCase();
	}
	/** 
	 激活的attrs
	 
	*/
	public final String getAttrsOfActive()
	{
		  //  return this.GetValStrByKey(MapExtAttr.AttrsOfActive).Replace("~", "'");
		return this.GetValStrByKey(MapExtAttr.AttrsOfActive);
	}
	public final String getAttrsOfActiveToLowerCase()
	{
		return getAttrsOfActive().toLowerCase();
	}
	public final void setAttrsOfActive(String value)
	{
		this.SetValByKey(MapExtAttr.AttrsOfActive, value);
	}
	public final String getFK_MapData()
	{
		return this.GetValStrByKey(MapExtAttr.FK_MapData);
	}
	public final void setFK_MapData(String value)
	{
		this.SetValByKey(MapExtAttr.FK_MapData, value);
	}
	/** 
	 Doc
	 
	*/
	public final String getDoc()
	{
		return this.GetValStrByKey("Doc").replace("~","'");
	}
	public final void setDoc(String value)
	{
		this.SetValByKey("Doc", value);
	}
	public final String getTagOfSQL_autoFullTB()
	{
		if (StringHelper.isNullOrEmpty(this.getTag()))
		{
			return this.getDocOfSQLDeal();
		}


		String sql = this.getTag();
		sql = sql.replace("@WebUser.No", WebUser.getNo());
		sql = sql.replace("@WebUser.Name", WebUser.getName());
		sql = sql.replace("@WebUser.FK_Dept", WebUser.getFK_Dept());
		sql = sql.replace("@WebUser.FK_DeptName", WebUser.getFK_DeptName());
		return sql;
	}

	public final String getDocOfSQLDeal()
	{
		String sql = this.getDoc();
		sql = sql.replace("@WebUser.No", WebUser.getNo());
		sql = sql.replace("@WebUser.Name", WebUser.getName());
		sql = sql.replace("@WebUser.FK_Dept", WebUser.getFK_Dept());
		sql = sql.replace("@WebUser.FK_DeptName", WebUser.getFK_DeptName());
		return sql;
	}
	public final String getTag()
	{
		String s= this.GetValStrByKey("Tag").replace("~", "'");

		s = s.replace("\\\\", "\\");
		s = s.replace("\\\\", "\\");

		s = s.replace("CCFlow\\Data\\", "CCFlow\\WF\\Data\\");

		return s;
	}
	public final void setTag(String value)
	{
		this.SetValByKey("Tag", value);
	}
	public final String getTag1()
	{
		return this.GetValStrByKey("Tag1").replace("~", "'");
	}
	public final void setTag1(String value)
	{
		this.SetValByKey("Tag1", value);
	}
	public final String getTag2()
	{
		return this.GetValStrByKey("Tag2").replace("~", "'");
	}
	public final void setTag2(String value)
	{
		this.SetValByKey("Tag2", value);
	}
	public final String getTag3()
	{
		return this.GetValStrByKey("Tag3").replace("~", "'");
	}
	public final void setTag3(String value)
	{
		this.SetValByKey("Tag3", value);
	}
	public final int getH()
	{
		return this.GetValIntByKey(MapExtAttr.H);
	}
	public final void setH(int value)
	{
		this.SetValByKey(MapExtAttr.H, value);
	}
	public final int getW()
	{
		return this.GetValIntByKey(MapExtAttr.W);
	}
	public final void setW(int value)
	{
		this.SetValByKey(MapExtAttr.W, value);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造方法
	/** 
	 扩展
	 
	*/
	public MapExt()
	{
	}
	/** 
	 扩展
	 
	 @param no
	*/
	public MapExt(String mypk)
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

		Map map = new Map("Sys_MapExt");
		map.setDepositaryOfEntity(Depositary.None);
		map.setDepositaryOfMap(Depositary.Application);
		map.setEnDesc("扩展");
		map.setEnType(EnType.Sys);

		map.AddMyPK();

		map.AddTBString(MapExtAttr.FK_MapData, null, "主表", true, false, 0, 30, 20);
		map.AddTBString(MapExtAttr.ExtType, null, "类型", true, false, 0, 30, 20);
		map.AddTBInt(MapExtAttr.DoWay, 0, "执行方式", true, false);

		map.AddTBString(MapExtAttr.AttrOfOper, null, "操作的Attr", true, false, 0, 30, 20);
		map.AddTBString(MapExtAttr.AttrsOfActive, null, "激活的字段", true, false, 0, 900, 20);
		map.AddTBStringDoc();
		map.AddTBString(MapExtAttr.Tag, null, "Tag", true, false, 0, 2000, 20);
		map.AddTBString(MapExtAttr.Tag1, null, "Tag1", true, false, 0, 2000, 20);
		map.AddTBString(MapExtAttr.Tag2, null, "Tag2", true, false, 0, 2000, 20);
		map.AddTBString(MapExtAttr.Tag3, null, "Tag3", true, false, 0, 2000, 20);

		map.AddTBString(MapExtAttr.AtPara, null, "参数", true, false, 0, 2000, 20);
		map.AddTBString(MapExtAttr.DBSrc, null, "数据源", true, false, 0, 20, 20);


		map.AddTBInt(MapExtAttr.H, 500, "高度", false, false);
		map.AddTBInt(MapExtAttr.W, 400, "宽度", false, false);

			// add by stone 2013-12-21 计算的优先级,用于js的计算.
		map.AddTBInt(MapExtAttr.PRI, 0, "PRI", false, false);

		this.set_enMap(map);
		return this.get_enMap();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

}
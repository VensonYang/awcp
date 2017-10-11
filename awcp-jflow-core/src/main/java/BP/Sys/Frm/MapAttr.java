package BP.Sys.Frm;

import java.math.BigDecimal;
import java.math.RoundingMode;

import TL.PingYinUtil;
import BP.DA.*;
import BP.En.*;
import BP.Port.WebUser;
import BP.Sys.*;
import BP.Tools.StringHelper;

/** 
 实体属性
 
*/
public class MapAttr extends EntityMyPK
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 属性
	public final EntitiesNoName getHisEntitiesNoName()
	{
		if (this.getUIBindKey().contains("."))
		{
			EntitiesNoName ens = (EntitiesNoName)ClassFactory.GetEns(this.getUIBindKey());
			ens.RetrieveAll();
			return ens;
		}
		GENoNames myens = new GENoNames(this.getUIBindKey(), this.getName());
		myens.RetrieveAll();
		return myens;
	}
	
    /// <summary>
    /// 判断是否是Num 字串。
    /// </summary>
    /// <param name="str"></param>
    /// <returns></returns>
    public static boolean IsNumStr(String str)
    {
        try
        {
            int d = Integer.parseInt(str);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
	
	
	public final boolean getIsTableAttr()
	{
		return MapAttr.IsNumStr(this.getKeyOfEn().replace("F", ""));
	}
	public final Attr getHisAttr()
	{
		Attr attr = new Attr();
		attr.setKey(this.getKeyOfEn());
		attr.setDesc(this.getName());

		String s = this.getDefValReal();
		if (StringHelper.isNullOrEmpty(s))
		{
			attr.setDefaultValOfReal(null);
		}
		else
		{
				// attr.DefaultVal
			attr.setDefaultValOfReal(this.getDefValReal());
				//this.DefValReal;
		}


		attr.setField(this.getField());
		attr.setMaxLength(this.getMaxLen());
		attr.setMinLength(this.getMinLen());
		attr.setUIBindKey(this.getUIBindKey());
		attr.UIIsLine = this.getUIIsLine();
		attr.setUIHeight(0);
		if (this.getMaxLen() > 3000)
		{
			attr.setUIHeight(10);
		}

		attr.setUIWidth(this.getUIWidth());
		attr.setMyDataType(this.getMyDataType());
		attr.setUIRefKeyValue(this.getUIRefKey());
		attr.setUIRefKeyText(this.getUIRefKeyText());
		attr.setUIVisible(this.getUIVisible());
		if (this.getIsPK())
		{
			attr.setMyFieldType(FieldType.PK);
		}

		switch (this.getLGType())
		{
			case Enum:
				attr.setUIContralType(this.getUIContralType());
				attr.setMyFieldType(FieldType.Enum);
				attr.setUIDDLShowType(DDLShowType.SysEnum);
				attr.setUIIsReadonly(this.getUIIsEnable());
				break;
			case FK:
				attr.setUIContralType(this.getUIContralType());
				attr.setMyFieldType(FieldType.FK);
				attr.setUIDDLShowType(DDLShowType.Ens);
				attr.setUIRefKeyValue("No");
				attr.setUIRefKeyText("Name");
				attr.setUIIsReadonly(this.getUIIsEnable());
				break;
			default:
				attr.setUIContralType(UIContralType.TB);
				if (this.getIsPK())
				{
					attr.setMyFieldType(FieldType.PK);
				}

				attr.setUIIsReadonly(!this.getUIIsEnable());
				switch (this.getMyDataType())
				{
					case DataType.AppBoolean:
						attr.setUIContralType(UIContralType.CheckBok);
						attr.setUIIsReadonly(this.getUIIsEnable());
						break;
					case DataType.AppDate:
						if (this.getTag().equals("1"))
						{
							attr.setDefaultVal(DataType.getCurrentData());
						}
						break;
					case DataType.AppDateTime:
						if (this.getTag().equals("1"))
						{
							attr.setDefaultVal(DataType.getCurrentData());
						}
						break;
					default:
						break;
				}
				break;
		}

			//attr.AutoFullWay = this.HisAutoFull;
			//attr.AutoFullDoc = this.AutoFullDoc;
			//attr.MyFieldType = FieldType
			//attr.UIDDLShowType= BP.Web.Controls.DDLShowType.Self

		return attr;
	}
	/** 
	 是否主键
	 
	*/
	public final boolean getIsPK()
	{
//C# TO JAVA CONVERTER NOTE: The following 'switch' operated on a string member and was converted to Java 'if-else' logic:
//		switch (this.KeyOfEn)
//ORIGINAL LINE: case "OID":
		if (this.getKeyOfEn().equals("OID") || this.getKeyOfEn().equals("No") || this.getKeyOfEn().equals("MyPK"))
		{
				return true;
		}
		else
		{
				return false;
		}
	}
	public final EditType getHisEditType()
	{
		return EditType.forValue(this.GetValIntByKey(MapAttrAttr.EditType));
	}
	public final void setHisEditType(EditType value)
	{
		this.SetValByKey(MapAttrAttr.EditType, value.getValue());
	}
	public final String getFK_MapData()
	{
		return this.GetValStrByKey(MapAttrAttr.FK_MapData);
	}
	public final void setFK_MapData(String value)
	{
		this.SetValByKey(MapAttrAttr.FK_MapData, value);
	}
	/** 
	 AutoFullWay
	 
	*/
	public final AutoFullWay getHisAutoFull_del()
	{
		return AutoFullWay.forValue(this.GetValIntByKey(MapAttrAttr.AutoFullWay));
	}
	public final void setHisAutoFull_del(AutoFullWay value)
	{
		this.SetValByKey(MapAttrAttr.AutoFullWay, value.getValue());
	}
	/** 
	 自动填写
	 
	*/
	public final String getAutoFullDoc_Del()
	{
		String doc = this.GetValStrByKey(MapAttrAttr.AutoFullDoc);
		doc = doc.replace("~", "'");
		return doc;
	}
	public final void setAutoFullDoc_Del(String value)
	{
		this.SetValByKey(MapAttrAttr.AutoFullDoc, value);
	}
	public final String getAutoFullDocRun_Del()
	{
		String doc = this.GetValStrByKey(MapAttrAttr.AutoFullDoc);
		doc = doc.replace("~", "'");
		doc = doc.replace("@WebUser.No", WebUser.getNo());
		doc = doc.replace("@WebUser.Name", WebUser.getName());
		doc = doc.replace("@WebUser.FK_Dept", WebUser.getFK_Dept());
		return doc;
	}
	public final String getKeyOfEn()
	{
		return this.GetValStrByKey(MapAttrAttr.KeyOfEn);
	}
	public final String getKeyOfEnToLowerCase()
	{
		return getKeyOfEn().toLowerCase();
	}
	public final void setKeyOfEn(String value)
	{
		this.SetValByKey(MapAttrAttr.KeyOfEn, value);
	}
	public final FieldTypeS getLGType()
	{
		return FieldTypeS.forValue(this.GetValIntByKey(MapAttrAttr.LGType));
	}
	public final void setLGType(FieldTypeS value)
	{
		this.SetValByKey(MapAttrAttr.LGType, value.getValue());
	}
	public final String getLGTypeT()
	{
		return this.GetValRefTextByKey(MapAttrAttr.LGType);
	}
	/** 
	 描述
	 
	*/
	public final String getName()
	{
		String s = this.GetValStrByKey(MapAttrAttr.Name);
		if (StringHelper.isNullOrEmpty(s))
		{
			return this.getKeyOfEn();
		}
		return s;
	}
	public final void setName(String value)
	{
		this.SetValByKey(MapAttrAttr.Name, value);
	}
	public final boolean getIsNum()
	{
		switch (this.getMyDataType())
		{
			case BP.DA.DataType.AppString:
			case BP.DA.DataType.AppDate:
			case BP.DA.DataType.AppDateTime:
			case BP.DA.DataType.AppBoolean:
				return false;
			default:
				return true;
		}
	}
	public final java.math.BigDecimal getDefValDecimal()
	{
		return new java.math.BigDecimal(this.getDefVal());
		/*
		 * warning return java.math.BigDecimal.Parse(this.getDefVal());*/
	}
	public final String getDefValReal()
	{
		return this.GetValStrByKey(MapAttrAttr.DefVal);
	}
	public final void setDefValReal(String value)
	{
		this.SetValByKey(MapAttrAttr.DefVal, value);
	}
	/** 
	 合并单元格数
	 
	*/
	public final int getColSpan()
	{
		int i= this.GetValIntByKey(MapAttrAttr.ColSpan);
		if (this.getUIIsLine() && i ==1)
		{
			return 3;
		}
		if (i == 0)
		{
			return 1;
		}
		return i;
	}
	public final void setColSpan(int value)
	{
		this.SetValByKey(MapAttrAttr.ColSpan, value);
	}
	/** 
	 默认值
	 
	*/
	public final String getDefVal()
	{
		String s = this.GetValStrByKey(MapAttrAttr.DefVal);
		if (this.getIsNum())
		{
			if (s.equals(""))
			{
				return "0";
			}
		}

		switch (this.getMyDataType())
		{
			case BP.DA.DataType.AppDate:
				if (this.getTag().equals("1") || s.equals("@RDT"))
				{
					return DataType.getCurrentData();
				}
				else
				{
					return "          ";
				}
				/*
				 * warning break;*/
			case BP.DA.DataType.AppDateTime:
				if (this.getTag().equals("1") || s.equals("@RDT"))
				{
					return DataType.getCurrentDataTime();
				}
				else
				{
					return "               ";
				}
					//return "    -  -    :  ";
				/*
				 * warning break;*/
			default:
				break;
		}

		if (!s.contains("@") )
		{
			return s;
		}

//C# TO JAVA CONVERTER NOTE: The following 'switch' operated on a string member and was converted to Java 'if-else' logic:
//		switch (s.ToLower())
//ORIGINAL LINE: case "@webuser.no":
		if (s.toLowerCase().equals("@webuser.no"))
		{
				return WebUser.getNo();
		}
//ORIGINAL LINE: case "@webuser.name":
		else if (s.toLowerCase().equals("@webuser.name"))
		{
				return WebUser.getName();
		}
//ORIGINAL LINE: case "@webuser.fk_dept":
		else if (s.toLowerCase().equals("@webuser.fk_dept"))
		{
				return WebUser.getFK_Dept();
		}
//ORIGINAL LINE: case "@webuser.fk_deptname":
		else if (s.toLowerCase().equals("@webuser.fk_deptname"))
		{
				return WebUser.getFK_DeptName();
		}
//ORIGINAL LINE: case "@webuser.fk_deptnameoffull":
		else if (s.toLowerCase().equals("@webuser.fk_deptnameoffull"))
		{
				return WebUser.getFK_DeptNameOfFull();
		}
//ORIGINAL LINE: case "@fk_ny":
		else if (s.toLowerCase().equals("@fk_ny"))
		{
				return DataType.getCurrentYearMonth();
		}
//ORIGINAL LINE: case "@fk_nd":
		else if (s.toLowerCase().equals("@fk_nd"))
		{
				return DataType.getCurrentYear();
		}
//ORIGINAL LINE: case "@fk_yf":
		else if (s.toLowerCase().equals("@fk_yf"))
		{
				return DataType.getCurrentMonth();
		}
//ORIGINAL LINE: case "@rdt":
		else if (s.toLowerCase().equals("@rdt"))
		{
				if (this.getMyDataType() == DataType.AppDate)
				{
					return DataType.getCurrentData();
				}
				else
				{
					return DataType.getCurrentDataTime();
				}
		}
//ORIGINAL LINE: case "@rd":
		else if (s.toLowerCase().equals("@rd"))
		{
				if (this.getMyDataType() == DataType.AppDate)
				{
					return DataType.getCurrentData();
				}
				else
				{
					return DataType.getCurrentDataTime();
				}
		}
//ORIGINAL LINE: case "@yyyy年mm月dd日":
		else if (s.toLowerCase().equals("@yyyy年mm月dd日"))
		{
				return DataType.getCurrentDataCNOfLong();
		}
//ORIGINAL LINE: case "@yy年mm月dd日":
		else if (s.toLowerCase().equals("@yy年mm月dd日"))
		{
				return DataType.getCurrentDataCNOfShort();
		}
		else
		{
				return s;
				//throw new Exception("没有约定的变量默认值类型" + s);
		}
		/*
		 * warning return this.GetValStrByKey(MapAttrAttr.DefVal);*/
	}
	public final void setDefVal(String value)
	{
		this.SetValByKey(MapAttrAttr.DefVal, value);
	}
	public final boolean getDefValOfBool()
	{
		return this.GetValBooleanByKey(MapAttrAttr.DefVal, false);
	}
	public final void setDefValOfBool(boolean value)
	{
		this.SetValByKey(MapAttrAttr.DefVal, value);
	}
	/** 
	 字段
	 
	*/
	public final String getField()
	{
		return this.getKeyOfEn();
	}
	public final TBType getHisTBType()
	{
		switch (this.getMyDataType())
		{
			case BP.DA.DataType.AppRate:
			case BP.DA.DataType.AppMoney:
				return TBType.Moneny;
			case BP.DA.DataType.AppInt:
			case BP.DA.DataType.AppFloat:
			case BP.DA.DataType.AppDouble:
				return TBType.Num;
			default:
				return TBType.TB;
		}
	}
	public final int getMyDataType()
	{
		return this.GetValIntByKey(MapAttrAttr.MyDataType);
	}
	public final void setMyDataType(int value)
	{
		this.SetValByKey(MapAttrAttr.MyDataType, value);
	}
	public final String getMyDataTypeS()
	{
		switch (this.getMyDataType())
		{
			case DataType.AppString:
				return "String";
			case DataType.AppInt:
				return "Int";
			case DataType.AppFloat:
				return "Float";
			case DataType.AppMoney:
				return "Money";
			case DataType.AppDate:
				return "Date";
			case DataType.AppDateTime:
				return "DateTime";
			case DataType.AppBoolean:
				return "Bool";
			default:
				throw new RuntimeException("没有判断。");
		}
	}
	public final void setMyDataTypeS(String value)
	{

//C# TO JAVA CONVERTER NOTE: The following 'switch' operated on a string member and was converted to Java 'if-else' logic:
//		switch (value)
//ORIGINAL LINE: case "String":
		if (value.equals("String"))
		{
				this.SetValByKey(MapAttrAttr.MyDataType, DataType.AppString);
		}
//ORIGINAL LINE: case "Int":
		else if (value.equals("Int"))
		{
				this.SetValByKey(MapAttrAttr.MyDataType, DataType.AppInt);
		}
//ORIGINAL LINE: case "Float":
		else if (value.equals("Float"))
		{
				this.SetValByKey(MapAttrAttr.MyDataType, DataType.AppFloat);
		}
//ORIGINAL LINE: case "Money":
		else if (value.equals("Money"))
		{
				this.SetValByKey(MapAttrAttr.MyDataType, DataType.AppMoney);
		}
//ORIGINAL LINE: case "Date":
		else if (value.equals("Date"))
		{
				this.SetValByKey(MapAttrAttr.MyDataType, DataType.AppDate);
		}
//ORIGINAL LINE: case "DateTime":
		else if (value.equals("DateTime"))
		{
				this.SetValByKey(MapAttrAttr.MyDataType, DataType.AppDateTime);
		}
//ORIGINAL LINE: case "Bool":
		else if (value.equals("Bool"))
		{
				this.SetValByKey(MapAttrAttr.MyDataType, DataType.AppBoolean);
		}
		else
		{
				throw new RuntimeException("sdsdsd");
		}

	}
	public final String getMyDataTypeStr()
	{
		return DataType.GetDataTypeDese(this.getMyDataType());
	}
	/** 
	 最大长度
	 
	*/
	public final int getMaxLen()
	{
		switch (this.getMyDataType())
		{
			case DataType.AppDate:
				return 100;
			case DataType.AppDateTime:
				return 100;
			default:
				break;
		}

		int i = this.GetValIntByKey(MapAttrAttr.MaxLen);
		if (i > 4000)
		{
			i = 400;
		}
		return i;
	}
	public final void setMaxLen(int value)
	{
		this.SetValByKey(MapAttrAttr.MaxLen, value);
	}
	/** 
	 最小长度
	 
	*/
	public final int getMinLen()
	{
		return this.GetValIntByKey(MapAttrAttr.MinLen);
	}
	public final void setMinLen(int value)
	{
		this.SetValByKey(MapAttrAttr.MinLen, value);
	}
	/** 
	 是否可以为空, 对数值类型的数据有效.
	 
	*/
	public final boolean getIsNull()
	{
		if (this.getMinLen() == 0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	public final int getGroupID()
	{
		return this.GetValIntByKey(MapAttrAttr.GroupID);
	}
	public final void setGroupID(int value)
	{
		this.SetValByKey(MapAttrAttr.GroupID, value);
	}
	public final boolean getIsBigDoc()
	{
		if (this.getMaxLen() > 3000)
		{
			return true;
		}
		return false;
	}
	public final int getUIRows()
	{
		if (this.getUIHeight() < 40)
		{
			return 1;
		}
		
		BigDecimal d = new BigDecimal(
				(new Float(this.getUIHeight())).toString());
		BigDecimal c = new BigDecimal(15);
		return d.divide(c, 0, RoundingMode.HALF_UP).intValue();

		/*
		 * warning java.math.BigDecimal d = java.math.BigDecimal.Parse((new Float(this.getUIHeight())).toString()) / 15;
//C# TO JAVA CONVERTER TODO TASK: Math.Round can only be converted to Java's Math.round if just one argument is used:
		return (int)Math.Round(d, 0);*/
	}
	/** 
	 高度
	 
	*/
	public final int getUIHeightInt()
	{
		return this.GetValIntByKey(MapAttrAttr.UIHeight);
		/*
		 * warning return (int)this.UIHeight;*/
	}
	/** 
	 高度
	 
	*/
	public final float getUIHeight()
	{
		return this.GetValFloatByKey(MapAttrAttr.UIHeight);
	}
	public final void setUIHeight(float value)
	{
		this.SetValByKey(MapAttrAttr.UIHeight, value);
	}
	/** 
	 宽度
	 
	*/
	public final int getUIWidthInt()
	{
		return this.GetValIntByKey(MapAttrAttr.UIWidth);
		/*
		 * warning return (int)this.UIWidth;*/
	}
	/** 
	 宽度
	 
	*/
	public final float getUIWidth()
	{
//		switch (this.getMyDataType())
//		{
//			case DataType.AppString:
//				return this.GetValFloatByKey(MapAttrAttr.UIWidth);
//			case DataType.AppFloat:
//			case DataType.AppInt:
//			case DataType.AppMoney:
//			case DataType.AppRate:
//			case DataType.AppDouble:
//				return 80;
//			case DataType.AppDate:
//				return 75;
//			case DataType.AppDateTime:
//				return 112;
//			default:
//				return 70;
//		}
		return this.GetValFloatByKey(MapAttrAttr.UIWidth);
	}
	public final void setUIWidth(float value)
	{
		this.SetValByKey(MapAttrAttr.UIWidth, value);
	}
	public final int getUIWidthOfLab()
	{
		return 0;

			//Graphics2D g2 = (Graphics2D)g;
			//g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			//                        RenderingHints.VALUE_ANTIALIAS_ON);

			//int textWidth = getFontMetrics(g2.getFont()).bytesWidth(str.getBytes(), 0, str.getBytes().length); 

	}
	/** 
	 是否只读
	 
	*/
	public final boolean getUIVisible()
	{
		return this.GetValBooleanByKey(MapAttrAttr.UIVisible);
	}
	public final void setUIVisible(boolean value)
	{
		this.SetValByKey(MapAttrAttr.UIVisible, value);
	}
	/** 
	 是否可用
	 
	*/
	public final boolean getUIIsEnable()
	{
		return this.GetValBooleanByKey(MapAttrAttr.UIIsEnable);
	}
	public final void setUIIsEnable(boolean value)
	{
		this.SetValByKey(MapAttrAttr.UIIsEnable, value);
	}
	/** 
	 是否单独行显示
	 
	*/
	public final boolean getUIIsLine()
	{
		return this.GetValBooleanByKey(MapAttrAttr.UIIsLine);
	}
	public final void setUIIsLine(boolean value)
	{
		this.SetValByKey(MapAttrAttr.UIIsLine, value);
	}
	/** 
	 是否数字签名
	 
	*/
	public final boolean getIsSigan()
	{
		if (this.getUIIsEnable())
		{
			return false;
		}
		return this.GetValBooleanByKey(MapAttrAttr.IsSigan);
	}
	public final void setIsSigan(boolean value)
	{
		this.SetValByKey(MapAttrAttr.IsSigan, value);
	}
 /** 
	 签名类型
	 
 */
	public final SignType getSignType()
	{
		if (this.getUIIsEnable())
		{
			return SignType.None;
		}
		return SignType.forValue(this.GetValIntByKey(MapAttrAttr.IsSigan));
	}
	public final void setSignType(SignType value)
	{
		this.SetValByKey(MapAttrAttr.IsSigan, value.getValue());
	}
	/** 
	 是否数字签名
	 
	*/
	public final String getPara_SiganField()
	{
		if (this.getUIIsEnable())
		{
			return "";
		}
		return this.GetParaString(MapAttrAttr.SiganField);
	}
	public final void setPara_SiganField(String value)
	{
		this.SetPara(MapAttrAttr.SiganField, value);
	}

	/** 
	 签名类型
	 
	*/
	public final PicType getPicType()
	{
		if (this.getUIIsEnable())
		{
			return PicType.ZiDong;
		}
		return PicType.forValue(this.GetParaInt(MapAttrAttr.PicType));
	}
	public final void setPicType(PicType value)
	{
		this.SetPara(MapAttrAttr.PicType, value.getValue());

	}
	/** 
	 TextBox类型
	 
	*/
	public final int getTBModel()
	{
		String s= this.GetValStrByKey(MapAttrAttr.UIBindKey);
		if (StringHelper.isNullOrEmpty(s) || s.length() != 1)
		{
			return 0;
		}
		else
		{
			return Integer.parseInt(s);
		}
	}

	/** 
	 绑定的值
	 
	*/
	public final String getUIBindKey()
	{
		return this.GetValStrByKey(MapAttrAttr.UIBindKey);
	}
	public final void setUIBindKey(String value)
	{
		this.SetValByKey(MapAttrAttr.UIBindKey, value);
	}
	/** 
	 关联的表的Key
	 
	*/
	public final String getUIRefKey()
	{
		String s = this.GetValStrByKey(MapAttrAttr.UIRefKey);
		if (s.equals("") || s == null)
		{
			s = "No";
		}
		return s;
	}
	public final void setUIRefKey(String value)
	{
		this.SetValByKey(MapAttrAttr.UIRefKey, value);
	}
	/** 
	 关联的表的Lab
	 
	*/
	public final String getUIRefKeyText()
	{
		String s = this.GetValStrByKey(MapAttrAttr.UIRefKeyText);
		if (s.equals("") || s == null)
		{
			s = "Name";
		}
		return s;
	}
	public final void setUIRefKeyText(String value)
	{
		this.SetValByKey(MapAttrAttr.UIRefKeyText, value);
	}
	/** 
	 标识
	 
	*/
	public final String getTag()
	{
		return this.GetValStrByKey(MapAttrAttr.Tag);
	}
	public final void setTag(String value)
	{
		this.SetValByKey(MapAttrAttr.Tag, value);
	}
	/** 
	 控件类型
	 
	*/
	public final UIContralType getUIContralType()
	{
		return UIContralType.forValue(this.GetValIntByKey(MapAttrAttr.UIContralType));
	}
	public final void setUIContralType(UIContralType value)
	{
		this.SetValByKey(MapAttrAttr.UIContralType, value.getValue());
	}
	public final String getF_Desc()
	{
		switch (this.getMyDataType())
		{
			case DataType.AppString:
				if (!this.getUIVisible() )
				{
					return "长度" + this.getMinLen() + "-" + this.getMaxLen() + "不可见";
				}
				else
				{
					return "长度" + this.getMinLen() + "-" + this.getMaxLen();
				}
			case DataType.AppDate:
			case DataType.AppDateTime:
			case DataType.AppInt:
			case DataType.AppFloat:
			case DataType.AppMoney:
				if (!this.getUIVisible() )
				{
					return "不可见";
				}
				else
				{
					return "";
				}
			default:
				return "";
		}
	}
	/** 
	 TabIdx
	 
	*/
	public final int getTabIdx()
	{
		return this.GetValIntByKey(MapAttrAttr.TabIdx);
	}
	public final void setTabIdx(int value)
	{
		this.SetValByKey(MapAttrAttr.TabIdx, value);
	}
	/** 
	 序号
	 
	*/
	public final int getIDX()
	{
		return this.GetValIntByKey(MapAttrAttr.IDX);
	}
	public final void setIDX(int value)
	{
		this.SetValByKey(MapAttrAttr.IDX, value);
	}
	public final float getX()
	{
		return this.GetValFloatByKey(MapAttrAttr.X);
	}
	public final void setX(float value)
	{
		this.SetValByKey(MapAttrAttr.X, value);
	}
	public final float getY()
	{
		return this.GetValFloatByKey(MapAttrAttr.Y);
	}
	public final void setY(float value)
	{
		this.SetValByKey(MapAttrAttr.Y, value);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造方法b
	/** 
	 实体属性
	 
	*/
	public MapAttr()
	{
	}
	public MapAttr(String mypk)
	{
		this.setMyPK(mypk);
		this.Retrieve();
	}
	public MapAttr(String fk_mapdata, String key)
	{
		this.setFK_MapData(fk_mapdata);
		this.setKeyOfEn(key);
		this.Retrieve(MapAttrAttr.FK_MapData, this.getFK_MapData(), MapAttrAttr.KeyOfEn, this.getKeyOfEn());
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

		Map map = new Map("Sys_MapAttr");
		map.setDepositaryOfEntity(Depositary.None);
		map.setDepositaryOfMap(Depositary.Application);
		map.setEnDesc("实体属性");
		map.setEnType(EnType.Sys);

		map.AddMyPK();

		map.AddTBString(MapAttrAttr.FK_MapData, null, "实体标识", true, true, 1, 200, 20);
		map.AddTBString(MapAttrAttr.KeyOfEn, null, "属性", true, true, 1, 200, 20);

		map.AddTBString(MapAttrAttr.Name, null, "描述", true, false, 0, 200, 20);
		map.AddTBString(MapAttrAttr.DefVal, null, "默认值", false, false, 0, 4000, 20);

		 //   map.AddDDLSysEnum(MapAttrAttr.UIContralType, 0, "空件类型", true, false, MapAttrAttr.UIContralType, "@0=文本框@1=下拉框");
		 //   map.AddDDLSysEnum(MapAttrAttr.MyDataType, 0, "数据类型", true, false, MapAttrAttr.MyDataType,
		   //     "@1=文本(String)@2=整型(Int)@3=浮点(Float)@4=布尔@5=Double@6=AppDate@7=AppDateTime@8=AppMoney@9=AppRate");

		map.AddTBInt(MapAttrAttr.UIContralType, 0, "控件", true, false);
		map.AddTBInt(MapAttrAttr.MyDataType, 0, "数据类型", true, false);

		map.AddDDLSysEnum(MapAttrAttr.LGType, 0, "逻辑类型", true, false, MapAttrAttr.LGType, "@0=普通@1=枚举@2=外键");

		map.AddTBFloat(MapAttrAttr.UIWidth, 100, "宽度", true, false);
		map.AddTBFloat(MapAttrAttr.UIHeight, 23, "高度", true, false);

		map.AddTBInt(MapAttrAttr.MinLen, 0, "最小长度", true, false);
		map.AddTBInt(MapAttrAttr.MaxLen, 300, "最大长度", true, false);

		map.AddTBString(MapAttrAttr.UIBindKey, null, "绑定的信息", true, false, 0, 100, 20);
		map.AddTBString(MapAttrAttr.UIRefKey, null, "绑定的Key", true, false, 0, 30, 20);
		map.AddTBString(MapAttrAttr.UIRefKeyText, null, "绑定的Text", true, false, 0, 30, 20);

			//map.AddTBInt(MapAttrAttr.UIVisible, 1, "是否可见", true, true);
			//map.AddTBInt(MapAttrAttr.UIIsEnable, 1, "是否启用", true, true);
			//map.AddTBInt(MapAttrAttr.UIIsLine, 0, "是否单独栏显示", true, true);

		map.AddBoolean(MapAttrAttr.UIVisible, true, "是否可见", true, true);
		map.AddBoolean(MapAttrAttr.UIIsEnable, true, "是否启用", true, true);
		map.AddBoolean(MapAttrAttr.UIIsLine, false, "是否单独栏显示", true, true);


		   // map.AddTBString(MapAttrAttr.AutoFullDoc, null, "自动填写内容", false, false, 0, 500, 20);
		   //// map.AddDDLSysEnum(MapAttrAttr.AutoFullWay, 0, "自动填写方式", true, false, MapAttrAttr.AutoFullWay,
		   //  //   "@0=不设置@1=本表单中数据计算@2=利用SQL自动填充@3=本表单中外键列@4=对从表的列求值");
		   // map.AddTBInt(MapAttrAttr.AutoFullWay, 0, "自动填写方式", true, false);


		map.AddTBInt(MapAttrAttr.IDX, 0, "序号", true, false);
		map.AddTBInt(MapAttrAttr.GroupID, 0, "GroupID", true, false);

			//      map.AddTBInt(MapAttrAttr.TabIdx, 0, "Tab顺序键", true, false);

			// 是否是签字，操作员字段有效。2010-09-23 增加。 @0=无@1=图片签名@2=CA签名.
		map.AddTBInt(MapAttrAttr.IsSigan, 0, "签字？", true, false);

		map.AddTBFloat(MapAttrAttr.X, 5, "X", true, false);
		map.AddTBFloat(MapAttrAttr.Y, 5, "Y", false, false);

		map.AddTBString(FrmBtnAttr.GUID, null, "GUID", true, false, 0, 128, 20);

		map.AddTBString(MapAttrAttr.Tag, null, "标识（存放临时数据）", true, false, 0, 100, 20);
		map.AddTBInt(MapAttrAttr.EditType, 0, "编辑类型", true, false);

			//单元格数量。2013-07-24 增加。
		map.AddTBInt(MapAttrAttr.ColSpan, 1, "单元格数量", true, false);


			//参数属性.
		map.AddTBAtParas(4000);


		this.set_enMap(map);
		return this.get_enMap();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion


	public final void DoDownTabIdx()
	{
		try {
			this.DoOrderDown(MapAttrAttr.FK_MapData, this.getFK_MapData(), MapAttrAttr.IDX);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public final void DoUpTabIdx()
	{
		try {
			this.DoOrderUp(MapAttrAttr.FK_MapData, this.getFK_MapData(), MapAttrAttr.IDX);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public final void DoUp()
	{
		try {
			this.DoOrderUp(MapAttrAttr.GroupID, (new Integer(this.getGroupID())).toString(), MapAttrAttr.UIVisible, "1", MapAttrAttr.IDX);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MapAttr attr = new MapAttr();
		attr.setMyPK(this.getFK_MapData() + "_Title");
		if (attr.RetrieveFromDBSources() == 1)
		{
			attr.setIDX(-1);
			attr.Update();
		}
	}
	public final void DoDown()
	{
		try {
			this.DoOrderDown(MapAttrAttr.GroupID, (new Integer(this.getGroupID())).toString(), MapAttrAttr.UIVisible, "1", MapAttrAttr.IDX);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		MapAttr attr = new MapAttr();
		attr.setMyPK(this.getFK_MapData() + "_Title");
		if (attr.RetrieveFromDBSources() == 1)
		{
			attr.setIDX(-1);
			attr.Update();
		}
	}
	public final void DoDtlDown()
	{
		try
		{
			String sql = "UPDATE Sys_MapAttr SET GroupID=( SELECT OID FROM Sys_GroupField WHERE EnName='" + this.getFK_MapData() + "') WHERE FK_MapData='" + this.getFK_MapData() + "'";
			DBAccess.RunSQL(sql);
		}
		catch (java.lang.Exception e)
		{
		}

		this.DoDown();
	}
	public final void DoDtlUp()
	{
		try
		{
			String sql = "UPDATE Sys_MapAttr SET GroupID=( SELECT OID FROM Sys_GroupField WHERE EnName='" + this.getFK_MapData() + "') WHERE FK_MapData='" + this.getFK_MapData() + "'";
			DBAccess.RunSQL(sql);
		}
		catch (java.lang.Exception e)
		{
		}
		this.DoUp();
	}
	public final void DoJump(MapAttr attrTo)
	{
		if (attrTo.getIDX() <= this.getIDX())
		{
			this.DoJumpUp(attrTo);
		}
		else
		{
			this.DoJumpDown(attrTo);
		}
	}
	private String DoJumpUp(MapAttr attrTo)
	{
		String sql = "UPDATE Sys_MapAttr SET IDX=IDX+1 WHERE IDX <=" + attrTo.getIDX() + " AND FK_MapData='" + this.getFK_MapData() + "' AND GroupID=" + this.getGroupID();
		DBAccess.RunSQL(sql);
		this.setIDX(attrTo.getIDX() - 1);
		this.setGroupID(attrTo.getGroupID());
		this.Update();
		return null;
	}
	private String DoJumpDown(MapAttr attrTo)
	{
		String sql = "UPDATE Sys_MapAttr SET IDX=IDX-1 WHERE IDX <=" + attrTo.getIDX() + " AND FK_MapData='" + this.getFK_MapData() + "' AND GroupID=" + this.getGroupID();
		DBAccess.RunSQL(sql);
		this.setIDX(attrTo.getIDX() + 1);
		this.setGroupID(attrTo.getGroupID());
		this.Update();
		return null;
	}
	@Override
	protected boolean beforeUpdateInsertAction()
	{
		if (this.getLGType() == FieldTypeS.Normal)
		{
			if (this.getUIIsEnable() &&this.getDefVal() !=null && this.getDefVal().contains("@"))
			{
				throw new RuntimeException("@不能在非只读(不可编辑)的字段设置具有@的默认值. 您设置的默认值为:" + this.getDefVal());
			}
		}

		return super.beforeUpdateInsertAction();
	}
	@Override
	protected boolean beforeUpdate()
	{
		switch (this.getMyDataType())
		{
			case DataType.AppDateTime:
				this.setMaxLen(20);
				break;
			case DataType.AppDate:
				this.setMaxLen(10);
				break;
			default:
				break;
		}
		this.setMyPK(this.getFK_MapData() + "_" + this.getKeyOfEn());
		return super.beforeUpdate();
	}
	@Override
	protected boolean beforeInsert()
	{
		if (StringHelper.isNullOrEmpty(this.getName()))
		{
			throw new RuntimeException("@请输入字段名称。");
		}



		if (this.getKeyOfEn() == null || this.getKeyOfEn().trim().equals(""))
		{
			try
			{
				this.setKeyOfEn(PingYinUtil.getFullSpell(this.getName()));
				if (this.getKeyOfEn().length() > 20)
				{
					this.setKeyOfEn(PingYinUtil.getFirstSpell(this.getName()));
				}

				if (this.getKeyOfEn() == null || this.getKeyOfEn().trim().equals(""))
				{
					throw new RuntimeException("@请输入字段描述或者字段名称。");
				}
			}
			catch (RuntimeException ex)
			{
				throw new RuntimeException("@请输入字段描述或字段名称。异常信息:" + ex.getMessage());
			}
		}
		else
		{
			this.setKeyOfEn(PubClass.DealToFieldOrTableNames(this.getKeyOfEn()));
		}

		/*
		 * warning Object tempVar = this.getKeyOfEn().clone();*/
		Object tempVar = this.getKeyOfEn();
		String keyofenC = (String)((tempVar instanceof String) ? tempVar : null);
		keyofenC = keyofenC.toLowerCase();

		if (PubClass.getKeyFields().contains("," + keyofenC + ","))
		{
			throw new RuntimeException("@错误:[" + this.getKeyOfEn() + "]是字段关键字，您不能用它做字段。");
		}

		if (this.IsExit(MapAttrAttr.KeyOfEn, this.getKeyOfEn(), MapAttrAttr.FK_MapData, this.getFK_MapData()))
		{
			throw new RuntimeException("@在["+this.getMyPK()+"]已经存在字段名称[" + this.getName() + "]字段[" + this.getKeyOfEn() + "]");
		}


		this.setIDX(999); // BP.DA.DBAccess.RunSQLReturnValInt("SELECT COUNT(*) FROM Sys_MapAttr WHERE FK_MapData='" + this.FK_MapData + "'") + 1;
		this.setMyPK(this.getFK_MapData() + "_" + this.getKeyOfEn());
		return super.beforeInsert();
	}
	/** 
	 删除之前
	 
	 @return 
	*/
	@Override
	protected boolean beforeDelete()
	{
		String sql = "DELETE FROM Sys_MapExt WHERE (AttrOfOper='" + this.getKeyOfEn() + "' OR AttrsOfActive='" + this.getKeyOfEn() + "' ) AND (FK_MapData='')";
		//删除权限管理字段.
		sql += "@DELETE FROM Sys_FrmSln WHERE KeyOfEn='" + this.getKeyOfEn() + "' AND FK_MapData='"+this.getFK_MapData()+"'";
		try {
			BP.DA.DBAccess.RunSQLs(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.beforeDelete();
	}
}
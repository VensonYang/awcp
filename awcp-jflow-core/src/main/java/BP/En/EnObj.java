package BP.En;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import BP.DA.Cash;
import BP.DA.DBAccess;
import BP.DA.DataType;
import BP.DA.Depositary;
import BP.DA.Paras;
import BP.En.Attr;
import BP.En.Attrs;
import BP.En.FieldType;
import BP.En.Map;
import BP.En.Row;
import BP.Port.WebUser;
import BP.Sys.EnsAppCfg;
import BP.Sys.SysDocFile;
import BP.Sys.SysFileManagers;
import BP.Sys.SystemConfig;
import BP.Tools.StringHelper;

/** 
 Entity 的摘要说明。
 	
*/
public abstract class EnObj implements Serializable
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 访问控制.
	private String _DBVarStr = null;
	public final String getHisDBVarStr()
	{
		if (_DBVarStr != null)
		{
			return _DBVarStr;
		}

		_DBVarStr = this.getEnMap().getEnDBUrl().getDBVarStr();
		return _DBVarStr;
	}
	/** 
	 他的访问控制.
	 
	*/
	protected UAC _HisUAC = null;
	/** 
	 得到 uac 控制.
	 
	 @return 
	*/
	public UAC getHisUAC()
	{
		if (_HisUAC == null)
		{
			_HisUAC = new UAC();
			_HisUAC.OpenForSysAdmin();
		
		}
		return _HisUAC;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 取出外部配置的属性信息
	/** 
	 取出Map 的扩展属性。
	 用于第3方的扩展属性开发。
	 
	 @param key 属性Key
	 @return 设置的属性
	 * @throws Exception 
	*/
	public final String GetMapExtAttrByKey(String key) throws Exception
	{
		Paras ps = new Paras();
		ps.Add("enName", this.toString());
		ps.Add("key", key);

		return (String)DBAccess.RunSQLReturnVal("select attrValue from Sys_ExtMap WHERE className=" + SystemConfig.getAppCenterDBVarStr() + "enName AND attrKey=" + SystemConfig.getAppCenterDBVarStr() + "key", ps);
	}
	
	public String toString()
	{
		return this.getClass().getName();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region CreateInstance
	/** 
	 创建一个实例
	 
	 @return 自身的实例
	*/
	public final Entity CreateInstance()
	{
		/*
		 * warning Object tempVar = this.getClass().Assembly.CreateInstance(this.toString());*/
		Object tempVar = null;
		try {
			tempVar = this.getClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (Entity)((tempVar instanceof Entity) ? tempVar : null);
		//return ClassFactory.GetEn(this.ToString());
	}
	private Entities _GetNewEntities = null;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 方法
	/** 
	 重新设置默信息.
	 
	*/
	public final void ResetDefaultVal()
	{
		Attrs attrs = this.getEnMap().getAttrs();
		for (Attr attr : attrs)
		{
			String tempVar = attr.getDefaultValOfReal();
			String v = (String)((tempVar instanceof String) ? tempVar : null);
			if (v == null)
			{
				continue;
			}

			if (!attr.getDefaultValOfReal().contains("@"))
			{
				continue;
			}

			String myval = this.GetValStrByKey(attr.getKey());

			// 设置默认值.
//C# TO JAVA CONVERTER NOTE: The following 'switch' operated on a string member and was converted to Java 'if-else' logic:
//			switch (v)
//ORIGINAL LINE: case "@WebUser.No":
			if (v.equals("@WebUser.No"))
			{
					if (attr.getUIIsReadonly())
					{
						this.SetValByKey(attr.getKey(), WebUser.getNo());
					}
					else
					{
						if (StringHelper.isNullOrEmpty(myval) || v.equals(myval))
						{
							this.SetValByKey(attr.getKey(), WebUser.getNo());
						}
					}
					continue;
			}
//ORIGINAL LINE: case "@WebUser.Name":
			else if (v.equals("@WebUser.Name"))
			{
					if (attr.getUIIsReadonly())
					{
						this.SetValByKey(attr.getKey(), WebUser.getName());
					}
					else
					{
						if (StringHelper.isNullOrEmpty(myval) || v.equals(myval))
						{
							this.SetValByKey(attr.getKey(), WebUser.getName());
						}
					}
					continue;
			}
//ORIGINAL LINE: case "@WebUser.FK_Dept":
			else if (v.equals("@WebUser.FK_Dept"))
			{
					if (attr.getUIIsReadonly())
					{
						this.SetValByKey(attr.getKey(), WebUser.getFK_Dept());
					}
					else
					{
						if (StringHelper.isNullOrEmpty(myval) || v.equals(myval))
						{
							this.SetValByKey(attr.getKey(), WebUser.getFK_Dept());
						}
					}
					continue;
			}
//ORIGINAL LINE: case "@WebUser.FK_DeptName":
			else if (v.equals("@WebUser.FK_DeptName"))
			{
					if (attr.getUIIsReadonly())
					{
						this.SetValByKey(attr.getKey(), WebUser.getFK_DeptName());
					}
					else
					{
						if (StringHelper.isNullOrEmpty(myval) || v.equals(myval))
						{
							this.SetValByKey(attr.getKey(), WebUser.getFK_DeptName());
						}
					}
					continue;
			}
//ORIGINAL LINE: case "@WebUser.FK_DeptNameOfFull":
			else if (v.equals("@WebUser.FK_DeptNameOfFull"))
			{
					if (attr.getUIIsReadonly())
					{
						this.SetValByKey(attr.getKey(), WebUser.getFK_DeptNameOfFull());
					}
					else
					{
						if (StringHelper.isNullOrEmpty(myval) || v.equals(myval))
						{
							this.SetValByKey(attr.getKey(), WebUser.getFK_DeptNameOfFull());
						}
					}
					continue;
			}
//ORIGINAL LINE: case "@RDT":
			else if (v.equals("@RDT"))
			{
					if (attr.getUIIsReadonly())
					{
						if (attr.getMyDataType() == DataType.AppDate || v.equals(myval))
						{
							this.SetValByKey(attr.getKey(), DataType.getCurrentData());
						}

						if (attr.getMyDataType() == DataType.AppDateTime || v.equals(myval))
						{
							this.SetValByKey(attr.getKey(), DataType.getCurrentDataTime());
						}
					}
					else
					{
						if (StringHelper.isNullOrEmpty(myval) || v.equals(myval))
						{
							if (attr.getMyDataType() == DataType.AppDate)
							{
								this.SetValByKey(attr.getKey(), DataType.getCurrentData());
							}
							else
							{
								this.SetValByKey(attr.getKey(), DataType.getCurrentDataTime());
							}
						}
					}
					continue;
			}
			else
			{
					continue;
			}
		}
	}
	/** 
	 把所有的值都设置成默认值，但是主键除外。
	 
	*/
	public final void ResetDefaultValAllAttr()
	{
		Attrs attrs = this.getEnMap().getAttrs();
		for (Attr attr : attrs)
		{
			if (!attr.getUIIsReadonly() && attr.getDefaultValOfReal() != null)
			{
				continue;
			}

			if (attr.getIsPK())
			{
				continue;
			}

			String tempVar = attr.getDefaultValOfReal();
			String v = (String)((tempVar instanceof String) ? tempVar : null);
			if (v == null)
			{
				this.SetValByKey(attr.getKey(), "");
				continue;
			}

			if (!v.contains("@") || v.equals("0") || v.equals("0.00"))
			{
				this.SetValByKey(attr.getKey(), v);
				continue;
			}

			// 设置默认值.
//C# TO JAVA CONVERTER NOTE: The following 'switch' operated on a string member and was converted to Java 'if-else' logic:
//			switch (v)
//ORIGINAL LINE: case "@WebUser.No":
			if (v.equals("@WebUser.No"))
			{
					this.SetValByKey(attr.getKey(), WebUser.getNo());
					continue;
			}
//ORIGINAL LINE: case "@WebUser.Name":
			else if (v.equals("@WebUser.Name"))
			{
					this.SetValByKey(attr.getKey(), WebUser.getName());
					continue;
			}
//ORIGINAL LINE: case "@WebUser.FK_Dept":
			else if (v.equals("@WebUser.FK_Dept"))
			{
					this.SetValByKey(attr.getKey(), WebUser.getFK_Dept());
					continue;
			}
//ORIGINAL LINE: case "@WebUser.FK_DeptName":
			else if (v.equals("@WebUser.FK_DeptName"))
			{
					this.SetValByKey(attr.getKey(), WebUser.getFK_DeptName());
					continue;
			}
//ORIGINAL LINE: case "@WebUser.FK_DeptNameOfFull":
			else if (v.equals("@WebUser.FK_DeptNameOfFull"))
			{
					this.SetValByKey(attr.getKey(), WebUser.getFK_DeptNameOfFull());
					continue;
			}
//ORIGINAL LINE: case "@RDT":
			else if (v.equals("@RDT"))
			{
					if (attr.getMyDataType() == DataType.AppDate)
					{
						this.SetValByKey(attr.getKey(), DataType.getCurrentData());
					}
					else
					{
						this.SetValByKey(attr.getKey(), DataType.getCurrentDataTime());
					}
					continue;
			}
			else
			{
					continue;
			}
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	/** 
	 实体
	 
	*/
	public EnObj()
	{
	}
	private Map _tmpEnMap = null;
	/** 
	 Map
	 
	*/
	protected final Map get_enMap()
	{
		if (_tmpEnMap != null)
		{
			return _tmpEnMap;
		}

		Map obj = Cash.GetMap(this.toString());
		if (obj == null)
		{
			if (_tmpEnMap == null)
			{
				return null;
			}
			else
			{
				return _tmpEnMap;
			}
		}
		else
		{
			_tmpEnMap = obj;
		}
		return _tmpEnMap;
	}
	protected final void set_enMap(Map value)
	{
		if (value == null)
		{
			_tmpEnMap = null;
			return;
		}

		Map mp = (Map)value;
		if (SystemConfig.getIsDebug())
		{
				///#region 检查map 是否合理。
				//if (mp != null)
				//{
				//    int i = 0;
				//    foreach (Attr attr in this.EnMap.Attrs)
				//    {
				//        if (attr.MyFieldType == FieldType.PK || attr.MyFieldType == FieldType.PKEnum || attr.MyFieldType == FieldType.PKFK)
				//            i++;
				//    }
				//    if (i == 0)
				//        throw new Exception("@没有给【" + this.EnDesc + "】定义主键。");

				//    if (this.IsNoEntity)
				//    {
				//        if (!mp.Attrs.Contains("No"))
				//            throw new Exception("@EntityNo 类map中没有 No 属性。@类" + mp.EnDesc + " , " + this.ToString());

				//        if (i != 1)
				//            throw new Exception("@多个主键在 EntityNo 类中是不允许的。 @类" + mp.EnDesc + " , " + this.ToString());
				//    }
				//    else if (this.IsOIDEntity)
				//    {
				//        if (!mp.Attrs.Contains("OID"))
				//            throw new Exception("@EntityOID 类map中没有 OID 属性。@类" + mp.EnDesc + " , " + this.ToString());
				//        if (i != 1)
				//            throw new Exception("@多个主键在 EntityOID 类中是不允许的。 @类" + mp.EnDesc + " , " + this.ToString());
				//    }
				//    else
				//    {
				//        if (mp.Attrs.Contains("MyPK"))
				//            if (i != 1)
				//                throw new Exception("@多个主键在 EntityMyPK 类中是不允许的。 @类" + mp.EnDesc + " , " + this.ToString());
				//    }
				//}
				///#endregion 检查map 是否合理。
		}

		if (mp == null || mp.getDepositaryOfMap() == Depositary.None)
		{
			_tmpEnMap = mp;
			return;
		}
		Cash.SetMap(this.toString(), mp);
		_tmpEnMap = mp;
	}
	/** 
	 子类需要继承
	 
	*/
	public abstract Map getEnMap();
	
	/**
	 * 动态的获取map
	 */
    public Map getEnMapInTime()
    {
        _tmpEnMap = null;
        Cash.SetMap(this.getClass().getName(), null);
        return this.getEnMap();
    }
    
	/** 
	 实体的 map 信息。	
	 		
	//public abstract void EnMap();		
	*/
	private Row _row = null;
	public final Row getRow()
	{
		if (this._row == null)
		{
			this._row = new Row();
			this._row.LoadAttrs(this.getEnMap().getAttrs());
		}
		return this._row;
	}
	public final void setRow(Row value)
	{
		this._row = value;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 关于属性的操作。

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 设置值方法
	public final void SetValByKeySuperLink(String attrKey, String val)
	{
		this.SetValByKey(attrKey, DataType.DealSuperLink(val));
	}

	/** 
	 设置object类型的值
	 
	 @param attrKey attrKey
	 @param val val
	*/
	public final void SetValByKey(String attrKey, String val)
	{
//C# TO JAVA CONVERTER NOTE: The following 'switch' operated on a string member and was converted to Java 'if-else' logic:
//		switch (val)
//ORIGINAL LINE: case null:
		if (val == null || val.equals("&nbsp;"))
		{
				val = "";
		}
//ORIGINAL LINE: case "RDT":
		else if (val.equals("RDT"))
		{
				if (val.length() > 4)
				{
					this.SetValByKey("FK_NY", val.substring(0, 7));
					this.SetValByKey("FK_ND", val.substring(0, 4));
				}
		}
		else
		{
		}
		this.getRow().SetValByKey(attrKey, val);
	}
	public final void SetValByKey(String attrKey, int val)
	{
		this.getRow().SetValByKey(attrKey.toLowerCase(), val);
	}
	public final void SetValByKey(String attrKey, long val)
	{
		this.getRow().SetValByKey(attrKey, val);
	}
	public final void SetValByKey(String attrKey, float val)
	{
		this.getRow().SetValByKey(attrKey, val);
	}
	public final void SetValByKey(String attrKey, java.math.BigDecimal val)
	{
		this.getRow().SetValByKey(attrKey, val);
	}
	public final void SetValByKey(String attrKey, Object val)
	{
		this.getRow().SetValByKey(attrKey, val);
	}

	public final void SetValByDesc(String attrDesc, Object val)
	{
		if (val == null)
		{
			throw new RuntimeException("@不能设置属性[" + attrDesc + "]null 值。");
		}
		this.getRow().SetValByKey(this.getEnMap().GetAttrByDesc(attrDesc).getKey(), val);
	}

	/** 
	 设置关联类型的值
	 
	 @param attrKey attrKey
	 @param val val
	*/
	public final void SetValRefTextByKey(String attrKey, Object val)
	{
		this.SetValByKey(attrKey + "Text", val);
	}
	/** 
	 设置bool类型的值
	 
	 @param attrKey attrKey
	 @param val val
	*/
	public final void SetValByKey(String attrKey, boolean val)
	{
		if (val)
		{
			this.SetValByKey(attrKey, 1);
		}
		else
		{
			this.SetValByKey(attrKey, 0);
		}
	}
	/** 
	 设置默认值
	 
	*/
	public final void SetDefaultVals()
	{
		for (Attr attr : this.getEnMap().getAttrs())
		{
			this.SetValByKey(attr.getKey(), attr.getDefaultVal());
		}
	}
	/** 
	 设置日期类型的值
	 
	 @param attrKey attrKey
	 @param val val
	*/
	public final void SetDateValByKey(String attrKey, String val)
	{
		try
		{
			this.SetValByKey(attrKey, DataType.StringToDateStr(val));
		}
		catch (RuntimeException ex)
		{
			throw new RuntimeException("@不合法的日期数据格式:key=[" + attrKey + "],value=" + val + " " + ex.getMessage());
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 取值方法
	/** 
	 取得Object
	 
	 @param attrKey
	 @return 
	*/
	public final Object GetValByKey(String attrKey)
	{
		return this.getRow().GetValByKey(attrKey);

		//try
		//{
		//    return this.Row.GetValByKey(attrKey);				
		//}
		//catch(Exception ex)
		//{
		//    throw new Exception(ex.Message+"  "+attrKey+" EnsName="+this.ToString() );
		//}
	}
	/** 
	 GetValDateTime
	 
	 @param attrKey
	 @return 
	*/
	public final java.util.Date GetValDateTime(String attrKey)
	{
		return DataType.ParseSysDateTime2DateTime(this.GetValStringByKey(attrKey));
	}
	/** 
	 在确定  attrKey 存在 map 的情况下才能使用它
	 
	 @param attrKey
	 @return 
	*/
	public final String GetValStrByKey(String key)
	{
		Object value = this.getRow().GetValByKey(key);
		if(null == value || value.equals("")){
			value = this.getRow().GetValByKey(key.toLowerCase());//读小写
			if(null == value || value.equals("")){
				value = this.getRow().GetValByKey(key.toUpperCase());//读大写
				if(null == value || value.equals(""))
					return "";
			}
		}
		
		return value.toString();
	}
	public final String GetValStrByKey(String key, String isNullAs)
	{
		try
		{
			return this.getRow().GetValByKey(key).toString();
		}
		catch (java.lang.Exception e)
		{
			return isNullAs;
		}
	}
	/** 
	 取得String
	 
	 @param attrKey
	 @return 
	*/
	public final String GetValStringByKey(String attrKey)
	{
//C# TO JAVA CONVERTER NOTE: The following 'switch' operated on a string member and was converted to Java 'if-else' logic:
//		switch (attrKey)
//ORIGINAL LINE: case "Doc":
		if (attrKey.equals("Doc"))
		{
				String s = this.getRow().GetValByKey(attrKey).toString();
				if (s.equals(""))
				{
					s = this.GetValDocText();
				}
				return s;
		}
		else
		{
				try
				{
					if (this.getRow() == null)
					{
						throw new RuntimeException("@没有初始化Row.");
					}
					Object value = this.getRow().GetValByKey(attrKey);
					if(null == value){
						return "";
					}
					
					return value.toString();
				}
				catch (RuntimeException ex)
				{
					throw new RuntimeException("@获取值期间出现如下异常：" + ex.getMessage() + "  " + attrKey + " 您没有在类增加这个属性，EnsName=" + this.toString());
				}
		}
	}
	public final String GetValStringByKey(String attrKey, String defVal)
	{
		String val = this.GetValStringByKey(attrKey);
		if (val == null || val.equals(""))
		{
			return defVal;
		}
		else
		{
			return val;
		}
	}
	/** 
	  取出大块文本
	 
	 @return 
	*/
	public final String GetValDocText()
	{
		String s = this.GetValStrByKey("Doc");
		if (s.trim().length() != 0)
		{
			return s;
		}

		//s = SysDocFile.GetValTextV2(this.toString(), this.getPKVal().toString());
		this.SetValByKey("Doc", s);
		return s;
	}
	public final String GetValDocHtml()
	{
		String s = this.GetValHtmlStringByKey("Doc");
		if (s.trim().length() != 0)
		{
			return s;
		}

		s = SysDocFile.GetValHtmlV2(this.toString(), this.getPKVal().toString());
		this.SetValByKey("Doc", s);
		return s;
	}
	/** 
	 取到Html 信息。
	 
	 @param attrKey attr
	 @return html.
	*/
	public final String GetValHtmlStringByKey(String attrKey)
	{
		return DataType.ParseText2Html(this.GetValStringByKey(attrKey));
	}
	public final String GetValHtmlStringByKey(String attrKey, String defval)
	{
		return DataType.ParseText2Html(this.GetValStringByKey(attrKey, defval));
	}
	/** 
	 取得枚举或者外键的标签
	 如果是枚举就获取枚举标签.
	 如果是外键就获取为外键的名称.
	 
	 @param attrKey
	 @return 
	*/
	public final String GetValRefTextByKey(String attrKey)
	{
		String str = "";
		try
		{
			Object tempVar = this.getRow().GetValByKey(attrKey + "Text");
			str = (String)((tempVar instanceof String) ? tempVar : null);
		}
		catch (RuntimeException ex)
		{
			throw new RuntimeException(ex.getMessage() + attrKey);
		}
		return str;
	}
	public long GetValInt64ByKey(String key)
	{
		String str = this.GetValStringByKey(key);
		if(str.equals(""))
			return 0;
		return Long.parseLong(str);
	}
	public final int GetValIntByKey(String key, int IsZeroAs)
	{
		int i = this.GetValIntByKey(key);
		if (i == 0)
		{
			i = IsZeroAs;
		}
		return i;
	}
	public final int GetValIntByKey11(String key)
	{
		return Integer.parseInt(this.GetValStrByKey(key));
	}
	/** 
	 根据key 得到int val
	 
	 @param key
	 @return 
	*/
	public int GetValIntByKey(String key)
	{
		try
		{
			if(this.GetValStrByKey(key).equals("on")){
				return 1;
			}else{
				return Integer.parseInt(this.GetValStrByKey(key));
			}
		}
		catch (RuntimeException ex)
		{
			//if (!SystemConfig.IsDebug)
			//    throw new Exception("@[" + this.EnMap.GetAttrByKey(key).Desc + "]请输入数字，您输入的是[" + this.GetValStrByKey(key) + "]。");
			//else
			//    throw new Exception("@表[" + this.EnDesc + "]在获取属性[" + key + "]值,出现错误，不能将[" + this.GetValStringByKey(key) + "]转换为int类型.错误信息：" + ex.Message + "@请检查是否在存储枚举类型时，您在SetValbyKey中没有转换。正确做法是:this.SetValByKey( Key ,(int)value)  ");

			String v = this.GetValStrByKey(key).toLowerCase();
			if (v.equals("true"))
			{
				this.SetValByKey(key, 1);
				return 1;
			}
			if (v.equals("false"))
			{
				this.SetValByKey(key, 0);
				return 0;
			}

			if (key.equals("OID"))
			{
				this.SetValByKey("OID", 0);
				return 0;
			}

			if (this.GetValStrByKey(key).equals(""))
			{
				Attr attr = this.getEnMap().GetAttrByKey(key);
				if (attr.getIsNull())
				{
					return 567567567;
				}
				else
				{
					if(attr.getDefaultVal().toString().equals(""))
					{
						return 0;
					}else
					{
						return Integer.parseInt(attr.getDefaultVal().toString());
					}
				}
			}else{
				String value = this.GetValStrByKey(key);
				if(value.indexOf(".") != -1){
					value = value.substring(0, value.indexOf("."));
					return Integer.parseInt(value);
				}
			}

			//else
			//{
			//    return int.Parse(this.EnMap.GetAttrByKey(key).DefaultVal.ToString());
			//}

			if (!SystemConfig.getIsDebug())
			{
				throw new RuntimeException("@[" + this.getEnMap().GetAttrByKey(key).getDesc() + "]请输入数字，您输入的是[" + this.GetValStrByKey(key) + "]。");
			}
			else
			{
				throw new RuntimeException("@表[" + this.getEnDesc() + "]在获取属性[" + key + "]值,出现错误，不能将[" + this.GetValStringByKey(key) + "]转换为int类型.错误信息：" + ex.getMessage() + "@请检查是否在存储枚举类型时，您在SetValbyKey中没有转换。正确做法是:this.SetValByKey( Key ,(int)value)  ");
			}
		}
	}
	/** 
	 根据key 得到 bool val
	 
	 @param key
	 @return 
	*/
	public final boolean GetValBooleanByKey(String key)
	{
		String s = this.GetValStrByKey(key);
		if (StringHelper.isNullOrEmpty(s))
		{
			s = this.getEnMap().GetAttrByKey(key).getDefaultVal().toString();
		}
		if (s.equals("")){
			return true;
		}

		if (s.toUpperCase().equals("FALSE"))
		{
			return false;
		}
		if (s.toUpperCase().equals("TRUE"))
		{
			return true;
		}

		if (Integer.parseInt(s) == 0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	public final boolean GetValBooleanByKey(String key, boolean defval)
	{
		try
		{

			if (Integer.parseInt(this.GetValStringByKey(key)) == 0)
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		catch (java.lang.Exception e)
		{
			return defval;
		}
	}
	public final String GetValBoolStrByKey(String key)
	{
		if (Integer.parseInt(this.GetValStringByKey(key)) == 0)
		{
			return "否";
		}
		else
		{
			return "是";
		}
	}
	/** 
	 根据key 得到flaot val
	 
	 @param key
	 @return 
	*/
	public final float GetValFloatByKey(String key, int blNum)
	{
		DecimalFormat decimalFormat = new DecimalFormat("#.00");
		return Float.parseFloat(decimalFormat.format(this.getRow()
				.GetValByKey(key).toString()));
		/*
		 * warning return Float.parseFloat(Float.parseFloat(this.getRow().GetValByKey(key).toString()).ToString("0.00"));*/
	}
	/** 
	 根据key 得到flaot val
	 
	 @param key
	 @return 
	*/
	public final float GetValFloatByKey(String key)
	{
		try
		{
			DecimalFormat decimalFormat = new DecimalFormat("#.00");
			
			return Float.parseFloat(decimalFormat.format(this.getRow().GetValByKey(key)));
			/*
			 * warning return Float.parseFloat(Float.parseFloat(this.getRow().GetValByKey(key).toString()).ToString("0.00"));*/
		}
		catch (java.lang.Exception e)
		{
			if (this.GetValStringByKey(key).equals(""))
			{
				Attr attr = this.getEnMap().GetAttrByKey(key);
				if (attr.getIsNull())
				{
					return 567567567;
				}
				else
				{
					return Float.parseFloat(attr.getDefaultVal().toString());
				}
			}
			return 0;
		}
	}
	public final java.math.BigDecimal GetValMoneyByKey(String key)
	{
		try
		{
			return this.GetValDecimalByKey(key).setScale(2, BigDecimal.ROUND_HALF_UP);
			/*
			 * warning return java.math.BigDecimal.Parse(this.GetValDecimalByKey(key).ToString("0.00"));*/
		}
		catch (java.lang.Exception e)
		{
			if (this.GetValStringByKey(key).equals(""))
			{
				Attr attr = this.getEnMap().GetAttrByKey(key);
				if (attr.getIsNull())
				{
					return new BigDecimal(567567567);
				}
				else
				{
					return new java.math.BigDecimal(attr.getDefaultVal().toString());
				}
			}
			return BigDecimal.ZERO;
		}
	}
	/** 
	 根据key 得到flaot val
	 
	 @param key
	 @return 
	*/
	public final java.math.BigDecimal GetValDecimalByKey(String key)
	{
		BigDecimal bd = new BigDecimal(this.GetValStrByKey(key));
		return bd.setScale(4, BigDecimal.ROUND_HALF_UP);
		/*
		 * warning return java.math.BigDecimal.Round(java.math.BigDecimal.Parse(this.GetValStrByKey(key)), 4);*/
	}
	public final java.math.BigDecimal GetValDecimalByKey(String key, String items)
	{
		if (items.equals("") || items == null)
		{
			return BigDecimal.ZERO;
		}

		if (items.indexOf("@" + key) == -1)
		{
			return BigDecimal.ZERO;
		}

		String str = items.substring(items.indexOf("@" + key));

		/*
		 * warning return java.math.BigDecimal.Round(java.math.BigDecimal.Parse(this.GetValStringByKey(key)), 4);*/
		return GetValDecimalByKey(key);
	}
	public final double GetValDoubleByKey(String key)
	{
		try
		{
			return Double.parseDouble(this.GetValStrByKey(key));
		}
		catch (RuntimeException ex)
		{
			throw new RuntimeException("@表[" + this.getEnDesc() + "]在获取属性[" + key + "]值,出现错误，不能将[" + this.GetValStringByKey(key) + "]转换为double类型.错误信息：" + ex.getMessage());
		}
	}
	public final String GetValAppDateByKey(String key)
	{
		try
		{
			String str = this.GetValStringByKey(key);
			if (str == null || str.equals(""))
			{
				return str;
			}
			return DataType.StringToDateStr(str);
		}
		catch (RuntimeException ex)
		{
			throw new RuntimeException("@实例：[" + this.getEnMap().getEnDesc() + "]  属性[" + key + "]值[" + this.GetValStringByKey(key).toString() + "]日期格式转换出现错误：" + ex.getMessage());
		}
		//return "2003-08-01";
	}

	///#region 获取配置信息
	public final String GetCfgValStr(String key)
	{
		return BP.Sys.EnsAppCfgs.GetValString(this.toString() + "s", key);
	}

	public final int GetCfgValInt(String key)
	{
		return BP.Sys.EnsAppCfgs.GetValInt(this.toString() + "s", key);
	}

	public final boolean GetCfgValBoolen(String key)
	{
		return BP.Sys.EnsAppCfgs.GetValBoolen(this.toString() + "s", key);
	}
	public final void SetCfgVal(String key, Object val)
	{
		BP.Sys.EnsAppCfg cfg = new EnsAppCfg();
		cfg.setMyPK(this.toString() + "s@" + key);
		cfg.setCfgKey(key);
		cfg.setCfgVal(val.toString());
		cfg.setEnsName(this.toString() + "s");
		cfg.Save();
	}

	
	/** 
	 文件管理者
	 
	*/
	public final SysFileManagers getHisSysFileManagers()
	{
		return new SysFileManagers(this.toString(), this.getPKVal().toString());
	}
	public final boolean getIsBlank()
	{
		if (this._row == null)
		{
			return true;
		}

		Attrs attrs = this.getEnMap().getAttrs();
		for (Attr attr : attrs)
		{

			if (attr.getUIIsReadonly() && !attr.getIsFKorEnum())
			{
				continue;
			}

			if (attr.getIsFK() && StringHelper.isNullOrEmpty(attr.getDefaultVal().toString()))
			{
				continue; //如果是外键,并且外键的默认值为null.
			}

			String str = this.GetValStrByKey(attr.getKey());
			if (str.equals("") || attr.getDefaultVal().toString().equals(str) || str == null)
			{
				continue;
			}

			if (attr.getMyDataType() == DataType.AppDate && attr.getDefaultVal() == null)
			{
				if (DataType.getCurrentData().equals(str))
				{
					continue;
				}
				else
				{
					return true;
				}
			}

			if (attr.getDefaultVal().toString().equals(str) && !attr.getIsFK())
			{
				continue;
			}

			if (attr.getIsEnum())
			{
				if (attr.getDefaultVal().toString().equals(str))
				{
					continue;
				}
				else
				{
					return false;
				}
				/*
				 * warning continue; 
				 */
			}

			if (attr.getIsNum())
			{
				/*
				 * warning if (java.math.BigDecimal.Parse(str) != java.math.BigDecimal.Parse(attr.getDefaultVal().toString()))*/
				if ((new BigDecimal(str)).compareTo(new BigDecimal(attr
						.getDefaultVal().toString())) != 0)
				{
					return false;
				}
				else
				{
					continue;
				}
			}

			if (attr.getIsFKorEnum())
			{
					//if (attr.DefaultVal == null || attr.DefaultVal == "")
					//    continue;

				if (!attr.getDefaultVal().toString().equals(str))
				{
					return false;
				}
				else
				{
					continue;
				}
			}

			if (!attr.getDefaultVal().toString().equals(str))
			{
				return false;
			}
		}
		return true;
	}
	/** 
	 获取或者设置
	 是不是空的实体.
	 
	*/
	public final boolean getIsEmpty()
	{
		if (this._row == null)
		{
			return true;
		}
		else
		{
			if (this.getPKVal() == null || this.getPKVal().toString().equals("0") || this.getPKVal().toString().equals(""))
			{
				return true;
			}
			return false;
		}
	}
	public final void setIsEmpty(boolean value)
	{
		this._row = null;
	}
	/** 
	 对这个实体的描述
	 
	*/
	public final String getEnDesc()
	{
		return this.getEnMap().getEnDesc();
	}
	/** 
	 取到主健值。如果它的主健不唯一，就返回第一个值。
	 获取或设置
	 
	*/
	public final Object getPKVal()
	{
		return this.GetValByKey(this.getPK());
	}
	public final void setPKVal(Object value)
	{
		this.SetValByKey(this.getPK(), value);
	}
	/** 
	 如果只有一个主键,就返回PK,如果有多个就返回第一个.PK
	 
	*/
	public final int getPKCount()
	{
//C# TO JAVA CONVERTER NOTE: The following 'switch' operated on a string member and was converted to Java 'if-else' logic:
//		switch (this.PK)
//ORIGINAL LINE: case "OID":
		if (this.getPK().equals("OID") || this.getPK().equals("No") || this.getPK().equals("MyPK"))
		{
				return 1;
		}
		else
		{
		}

		int i = 0;
		for (Attr attr : this.getEnMap().getAttrs())
		{
			if (attr.getMyFieldType() == FieldType.PK || attr.getMyFieldType() == FieldType.PKEnum || attr.getMyFieldType() == FieldType.PKFK)
			{
				i++;
			}
		}
		if (i == 0)
		{
			throw new RuntimeException("@没有给【" + this.getEnDesc() + "，" + this.getEnMap().getPhysicsTable() + "】定义主键。");
		}
		else
		{
			return i;
		}
	}
	/** 
	 是不是OIDEntity
	 
	*/
	public final boolean getIsOIDEntity()
	{
		if (this.getPK().equals("OID"))
		{
			return true;
		}
		return false;
	}
	/** 
	 是不是OIDEntity
	 
	*/
	public final boolean getIsNoEntity()
	{
		if (this.getPK().equals("No"))
		{
			return true;
		}
		return false;
	}
	/** 
	 是否是TreeEntity
	 
	*/
	public final boolean getIsTreeEntity()
	{
		if (this.getPK().equals("ID"))
		{
			return true;
		}
		return false;
	}
	/** 
	 是不是IsMIDEntity
	 
	*/
	public final boolean getIsMIDEntity()
	{
		if (this.getPK().equals("MID"))
		{
			return true;
		}
		return false;
	}
	/** 
	 如果只有一个主键,就返回PK,如果有多个就返回第一个.PK
	 
	*/
	public String getPK()
	{
		String pks = "";
		for (Attr attr : this.getEnMap().getAttrs())
		{
			if (attr.getMyFieldType() == FieldType.PK || attr.getMyFieldType() == FieldType.PKEnum || attr.getMyFieldType() == FieldType.PKFK)
			{
				pks += attr.getKey() + ",";
			}
		}
		if (pks.equals(""))
		{
			throw new RuntimeException("@没有给【" + this.getEnDesc() + "，" + this.getEnMap().getPhysicsTable() + "】定义主键。");
		}
		pks = pks.substring(0, pks.length()-1);
		return pks;
	}
	
	public String getPKField()
	{
		for (Attr attr : this.getEnMap().getAttrs())
		{
			if (attr.getMyFieldType() == FieldType.PK || attr.getMyFieldType() == FieldType.PKEnum || attr.getMyFieldType() == FieldType.PKFK)
			{
				return attr.getField();
			}
		}
		throw new RuntimeException("@没有给【" + this.getEnDesc() + "】定义主键。");
	}
	
	
	/** 
	 如果只有一个主键,就返回PK,如果有多个就返回第一个.PK 
	*/
	public final String[] getPKs()
	{
		String[] strs1 = new String[this.getPKCount()];
		int i = 0;
		for (Attr attr : this.getEnMap().getAttrs())
		{
			if (attr.getMyFieldType() == FieldType.PK || attr.getMyFieldType() == FieldType.PKEnum || attr.getMyFieldType() == FieldType.PKFK)
			{
				strs1[i] = attr.getKey();
				i++;
			}
		}
		return strs1;
	}
	/** 
	 取到主健值。
	 
	*/
	public final java.util.Hashtable getPKVals()
	{
		java.util.Hashtable ht = new java.util.Hashtable();
		String[] strs = this.getPKs();
		for (String str : strs)
		{
			ht.put(str, this.GetValStringByKey(str));
		}
		return ht;
	}

	public final void domens()
	{
	}

}
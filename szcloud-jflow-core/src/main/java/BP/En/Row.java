package BP.En;

import java.util.Hashtable;

import org.omg.CORBA.TypeCode;

import BP.DA.DataColumn;
import BP.DA.DataRow;
import BP.DA.DataTable;
import BP.Tools.StringHelper;

/** 
 Row 的摘要说明。
 用来处理一条记录
 
*/
public class Row extends Hashtable<String, Object>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * warning public Row()
	{
		super(System.StringComparer.Create(System.Globalization.CultureInfo.CurrentCulture, true));
	}*/
	
	/** 
	 初始化数据.
	 
	 @param attrs
	*/
	public final void LoadAttrs(Attrs attrs)
	{
		this.clear();
		for (Attr attr : attrs)
		{
			switch (attr.getMyDataType())
			{
				case BP.DA.DataType.AppInt:
					if (attr.getIsNull())
					{
						this.put(attr.getKey().toLowerCase(), 0);
						/*
						 * warning this.Add(attr.getKey(), DBNull.getValue());*/
					}
					else
					{
						try {
							this.put(attr.getKey().toLowerCase(), Integer.parseInt(attr.getDefaultVal().toString()));
						} catch (Exception e) {
							this.put(attr.getKey().toLowerCase(), 0);
							
						}
						/*
						 * warning this.Add(attr.getKey(), Integer.parseInt(attr.getDefaultVal().toString()));*/
					}
					break;
				case BP.DA.DataType.AppFloat:
					if (attr.getIsNull())
					{
						this.put(attr.getKey().toLowerCase(), 0.0);
						/*
						 * warning this.Add(attr.getKey(), DBNull.getValue());*/
					}
					else
					{
						this.put(attr.getKey().toLowerCase(), Float.parseFloat(attr.getDefaultVal().toString()));
						/*
						 * warning this.Add(attr.getKey(), Float.parseFloat(attr.getDefaultVal().toString()));*/
					}
					break;
				case BP.DA.DataType.AppMoney:
					if (attr.getIsNull())
					{
						this.put(attr.getKey().toLowerCase(), 0.00);
						/*
						 * warning this.Add(attr.getKey(), DBNull.getValue());*/
					}
					else
					{
						this.put(attr.getKey().toLowerCase(), new java.math.BigDecimal(attr.getDefaultVal().toString()));
						/*
						 * warning this.Add(attr.getKey(), java.math.BigDecimal.Parse(attr.getDefaultVal().toString()));*/
					}
					break;
				case BP.DA.DataType.AppDouble:
					if (attr.getIsNull())
					{
						this.put(attr.getKey().toLowerCase(), 0.00);
						/*
						 * warning this.Add(attr.getKey(), DBNull.getValue());*/
					}
					else
					{
						this.put(attr.getKey().toLowerCase(), Double.parseDouble(attr.getDefaultVal().toString()));
						/*
						 * warning this.Add(attr.getKey(), Double.parseDouble(attr.getDefaultVal().toString()));*/
					}
					break;
				default:
					this.put(attr.getKey().toLowerCase(), attr.getDefaultVal());
					/*
					 * warning this.Add(attr.getKey(), attr.getDefaultVal());*/
					break;
			}
		}
	}
	/** 
	 LoadAttrs
	 
	 @param attrs
	*/
	public final void LoadDataTable(DataTable dt, DataRow dr)
	{
		this.clear();
		for (DataColumn dc : dt.Columns)
		{
			if(dr.containsKey(dc.ColumnName))
				this.put(dc.ColumnName, dr.getValue(dc.ColumnName));
			/*
			 * warning this.Add(dc.ColumnName, dr[dc.ColumnName]);*/
		}
	}

	/** 
	 设置一个值by key . 
	 
	 @param key
	 @param val
	*/
	public final void SetValByKey(String key, Object val)
	{
		if (val == null||val.equals(""))
		{
			this.put(key.toLowerCase(), "");
			/*
			 * warning this[key] = val;*/
			return;
		}

		if (val.getClass() == TypeCode.class)
		{
			this.put(key.toLowerCase(), ((Integer)val).intValue());
			/*
			 * warning this[key] = ((Integer)val).intValue();*/
		}
		else
		{
			this.put(key.toLowerCase(), val);
			/*
			 * warning this[key] = val;*/
		}
	}


	public final boolean GetBoolenByKey(String key)
	{
		Object obj = this.get(key.toLowerCase());
		/*
		 * warning Object obj = this[key];*/
		if (obj == null || StringHelper.isNullOrEmpty(obj.toString()) || obj.toString().equals("0"))
		{
			return false;
		}
		return true;
	}
	public final Object GetValByKey(String key)
	{
		return this.get(key.toLowerCase());
           
	}

}
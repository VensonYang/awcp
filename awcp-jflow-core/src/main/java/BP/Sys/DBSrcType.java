package BP.Sys;


/** 
 数据源类型
 
*/
public enum DBSrcType
{
	/** 
	 本机数据库
	 
	*/
	Localhost,
	/** 
	 SQL
	 
	*/
	SQLServer,
	/** 
	 Oracle
	 
	*/
	Oracle,
	/** 
	 MySQL
	 
	*/
	MySQL,
	/** 
	 Infomax
	 
	*/
	Infomax;

	public int getValue()
	{
		return this.ordinal();
	}

	public static DBSrcType forValue(int value)
	{
		return values()[value];
	}
}
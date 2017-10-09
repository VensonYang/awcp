package BP.DA;

public class DataColumn {

	/**
	 * DataColumn所屬的DataTable
	 */
	private DataTable table;
	/**
	 * DataColumn的欄位名稱
	 */
	public String ColumnName; // 欄名，當做DataRow的key

	public Object DataType;
	
	public Object getDataType() {
		return DataType;
	}

	public void setDataType(Object dataType) {
		DataType = dataType;
	}

	/**
	 * DataColumn被建立時，一定要指定欄名
	 * 
	 * @param columnName
	 *            欄名
	 */
	public DataColumn(String columnName){
		this.ColumnName = columnName.toLowerCase();
		//this.ColumnName = columnName;
	}
	
	public DataColumn(String columnName, Object DataType) {
		this.ColumnName = columnName.toLowerCase();
		//this.ColumnName = columnName;
		this.DataType = DataType;
	}
	
	public DataColumn(String columnName,Object DataType,String str){
		this.ColumnName = columnName;
		//this.ColumnName = columnName;
		this.DataType = DataType;
	}
	
	/**
	 * 給DataColumnCollection加入DataColumn時設定所屬的DataTable的方法，同一個package才用到
	 * 
	 * @param table
	 */
	void setTable(DataTable table) {
		this.table = table;
	}

	/**
	 * 取得DataColumn所屬的DataTable，唯讀
	 * 
	 * @return DataTable
	 */
	public DataTable getTable() {
		return this.table;
	}

	/**
	 * DataColumn物件的toString()，會回傳自己的欄名
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		return this.ColumnName;
	}
}


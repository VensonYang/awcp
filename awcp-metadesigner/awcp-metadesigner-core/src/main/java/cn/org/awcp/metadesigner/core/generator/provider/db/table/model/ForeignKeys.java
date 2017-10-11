package cn.org.awcp.metadesigner.core.generator.provider.db.table.model;

import cn.org.awcp.metadesigner.core.generator.util.ListHashtable;

/**
 * @author caoyong
 * 
 */
public class ForeignKeys  implements java.io.Serializable{

	private static final long serialVersionUID = 3809613363362536582L;
	protected Table parentTable;  //宿主表
	protected ListHashtable associatedTables;
	
	/**
	 * Constructor for Foreign Keys
	 */
	public ForeignKeys(Table aTable) {
		super();
		parentTable      = aTable;
		associatedTables = new ListHashtable();
	}
	
	/**
	 * @param tableName
	 * @param columnName
	 * @param seq
	 */
	public void addForeignKey(String tableName, String columnName,  String parentColumn, Integer seq) {
		ForeignKey tbl = null;
		if (associatedTables.containsKey(tableName)) {
			tbl = (ForeignKey) associatedTables.get(tableName);
			tbl.setColumnName(columnName);
			tbl.setParentColumn(parentColumn);
		}
		else {
			tbl = new ForeignKey(parentTable,tableName);
			associatedTables.put(tableName,tbl);
			tbl.setColumnName(columnName);
			tbl.setParentColumn(parentColumn);
		}
		 
		tbl.addColumn(columnName, parentColumn, seq);
	}

	/**
	 * @return Returns the associatedTables.
	 */
	public ListHashtable getAssociatedTables() {
		return associatedTables;
	}
	
	public int getSize() {
		return getAssociatedTables().size();
	}
	
	public boolean getHasImportedKeyColumn(String aColumn) {
		boolean isFound = false;
		int numKeys = getAssociatedTables().size();
		for (int i=0;i<numKeys;i++) {
			ForeignKey aKey = (ForeignKey) getAssociatedTables().getOrderedValue(i);
			if (aKey.getHasImportedKeyColumn(aColumn)) {
				isFound = true;
				break;
			}
		}
		return isFound;
	}
	
	public ForeignKey getAssociatedTable(String name) {
		Object fkey = getAssociatedTables().get(name);
		if (fkey != null) {
			return (ForeignKey) fkey;
		}
		else return null;
	}
	
	/**
	 * @return Returns the parentTable.
	 */
	public Table getParentTable() {
		return parentTable;
	}
	
	public boolean getHasImportedKeyParentColumn(String aColumn) {
		boolean isFound = false;
		int numKeys = getAssociatedTables().size();
		for (int i=0;i<numKeys;i++) {
			ForeignKey aKey = (ForeignKey) getAssociatedTables().getOrderedValue(i);
			if (aKey.getHasImportedKeyParentColumn(aColumn)) {
				isFound = true;
				break;
			}
		}
		return isFound;
	}
	
	public ForeignKey getImportedKeyParentColumn(String aColumn) {
		ForeignKey aKey = null;
		int numKeys = getAssociatedTables().size();
		for (int i=0;i<numKeys;i++) {
			aKey = (ForeignKey) getAssociatedTables().getOrderedValue(i);
			if (aKey.getHasImportedKeyParentColumn(aColumn)) {
				break;
			}
		}
		return aKey;
	}
}

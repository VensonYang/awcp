package BP.Sys;

import java.util.ArrayList;

import BP.En.EntitiesNoName;
import BP.En.Entity;

/** 
用户自定义表s

*/
public class SFTables extends EntitiesNoName
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static ArrayList<SFTable> convertSFTables(Object obj) {
		return (ArrayList<SFTable>) obj;
	}
		///#region 构造
	/** 
	 用户自定义表s
	 
	*/
	public SFTables()
	{
	}
	/** 
	 得到它的 Entity
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new SFTable();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}
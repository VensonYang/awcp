package BP.Sys;

import java.util.ArrayList;

import BP.En.EntitiesNoName;
import BP.En.Entity;
import BP.WF.Template.Flow;

/** 
 数据源s
 
*/
public class SFDBSrcs extends EntitiesNoName
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	/** 
	 数据源s
	 
	*/
	
	public static ArrayList<SFDBSrc> convertSFDBSrcs(Object obj) {
		return (ArrayList<SFDBSrc>) obj;
	}
	
	public SFDBSrcs()
	{
	}
	/** 
	 得到它的 Entity
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new SFDBSrc();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	@Override
	public int RetrieveAll()
	{
		int i = this.RetrieveAllFromDBSource();
		if (i == 0)
		{
			SFDBSrc src = new SFDBSrc();
			src.setNo("local");
			src.setName("应用系统主数据库(默认)");
			src.Insert();
			this.AddEntity(src);
			return 1;
		}
		return i;
	}
}
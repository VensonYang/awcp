package BP.WF.Template.CC;

import java.util.ArrayList;

import BP.En.EntitiesMyPK;
import BP.En.Entity;
import BP.En.QueryObject;

/** 
 抄送
 
*/
public class CCLists extends EntitiesMyPK
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 方法
	public static ArrayList<CCList> convertCCLists(Object obj) {
		return (ArrayList<CCList>) obj;
	}
	
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new CCList();
	}
	/** 
	 抄送
	 
	*/
	public CCLists()
	{
	}


	/** 
	 查询出来所有的抄送信息
	 
	 @param flowNo
	 @param workid
	 @param fid
	*/
	public CCLists(String flowNo, long workid, long fid)
	{
		QueryObject qo = new QueryObject(this);
		qo.AddWhere(CCListAttr.FK_Flow, flowNo);
		qo.addAnd();
		if (fid != 0)
		{
			qo.AddWhereIn(CCListAttr.WorkID, "(" + workid + "," + fid + ")");
		}
		else
		{
			qo.AddWhere(CCListAttr.WorkID, workid);
		}
		qo.DoQuery();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}
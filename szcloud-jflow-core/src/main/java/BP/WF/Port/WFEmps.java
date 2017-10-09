package BP.WF.Port;

import java.util.ArrayList;

import BP.DA.*;
import BP.En.*;
import BP.WF.*;
import BP.Port.*;
import BP.Port.*;
import BP.En.*;
import BP.Web.*;

/** 
 操作员s 
 
*/
public class WFEmps extends EntitiesNoName
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	
	public static ArrayList<WFEmp> convertWFEmps(Object obj) {
		// TODO Auto-generated method stub
		return (ArrayList<WFEmp>)obj;
	}
	/** 
	 操作员s
	 
	*/
	public WFEmps()
	{
	}
	/** 
	 得到它的 Entity
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new WFEmp();
	}

	@Override
	public int RetrieveAll()
	{
		return super.RetrieveAll("FK_Dept","Idx");
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}
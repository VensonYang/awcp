package BP.WF.Template;

import java.util.ArrayList;

import BP.DA.*;
import BP.En.*;
import BP.WF.*;
import BP.Port.*;

/** 
 自定义运行路径
 
*/
public class TransferCustoms extends EntitiesMyPK
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 方法
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new TransferCustom();
	}
	/** 
	 自定义运行路径
	 
	*/
	public TransferCustoms()
	{
	}
	@SuppressWarnings("unchecked")
	public static ArrayList<TransferCustom> convertTransferCustoms(Object obj) {
		return (ArrayList<TransferCustom>) obj;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}
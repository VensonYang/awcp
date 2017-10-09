package BP.WF.Template.Form.Sys;

import java.util.ArrayList;

import BP.En.EntitiesNoName;
import BP.En.Entity;

/** 
 系统表单s
 
*/
public class SysForms extends EntitiesNoName
{
	
	public static ArrayList<SysForm> convertSysForms(Object obj) {
		return (ArrayList<SysForm>) obj;
	}
	
	/** 
	 Frm
	 
	*/
	public SysForms()
	{

	}
	/** 
	 得到它的 Entity 
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new SysForm();
	}
}
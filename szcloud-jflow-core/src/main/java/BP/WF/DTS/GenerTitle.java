package BP.WF.DTS;

import BP.En.Method;
import BP.Port.WebUser;
import BP.WF.Template.FlowSheets;

/** 
 重新生成标题
 
*/
public class GenerTitle extends Method
{
	/** 
	 重新生成标题
	 
	*/
	public GenerTitle()
	{
		this.Title = "重新生成标题（为所有的流程，根据新的规则生成流程标题）";
		this.Help = "您也可以打开流程属性一个个的单独执行。";
	}
	/** 
	 设置执行变量
	 
	 @return 
	*/
	@Override
	public void Init()
	{
		//this.Warning = "您确定要执行吗？";
		//HisAttrs.AddTBString("P1", null, "原密码", true, false, 0, 10, 10);
		//HisAttrs.AddTBString("P2", null, "新密码", true, false, 0, 10, 10);
		//HisAttrs.AddTBString("P3", null, "确认", true, false, 0, 10, 10);
	}
	/** 
	 当前的操纵员是否可以执行这个方法
	 
	*/
	@Override
	public boolean getIsCanDo()
	{
		if (WebUser.getNo().equals("admin"))
		{
			return true;
		}
		return false;
	}
	/** 
	 执行
	 
	 @return 返回执行结果
	*/
	@Override
	public Object Do()
	{
		BP.WF.Template.FlowSheets ens = new BP.WF.Template.FlowSheets();
		for (BP.WF.Template.FlowSheet en :FlowSheets.convertFlowSheets(ens))
		{
			en.DoGenerTitle();
		}
		return "执行成功...";
	}
}
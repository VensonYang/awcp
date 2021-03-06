package BP.WF.DTS;

import BP.DA.*;
import BP.WF.Template.Node;

import BP.Port.*;
import BP.En.*;
import BP.Sys.*;
import BP.Sys.Frm.MapData;
import BP.Sys.Frm.MapDatas;

/** 
 生成模版的垃圾数据 
 
*/
public class GenerDBTemplete extends Method
{
	/** 
	 生成模版的垃圾数据
	 
	*/
	public GenerDBTemplete()
	{
		this.Title = "生成模版的垃圾数据";
		this.Help = "可以手工的查看并清楚他们.";
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
		return true;
	}
	/** 
	 执行
	 
	 @return 返回执行结果
	*/
	@Override
	public Object Do()
	{

		MapDatas mds = new MapDatas();
		mds.RetrieveAll();

		String msg = "";
		Node nd = new Node();
		for (MapData item :MapDatas.convertMapDatas(mds))
		{
			if (!item.getNo().contains("ND"))
			{
				continue;
			}

			String temp = item.getNo().replace("ND", "");
			int nodeID = 0;
			try
			{
				nodeID = Integer.parseInt(temp);
			}
			catch (java.lang.Exception e)
			{
				continue;
			}

			nd.setNodeID(nodeID);
			if (!nd.getIsExits())
			{
				msg += "@" + item.getNo() + "," + item.getName();
				//删除该模版.
				item.Delete();
			}
		}
		if (msg.equals(""))
		{
			msg = "数据良好.";
		}
		else
		{
			msg = "如下节点已经删除，但是节点表单数据没有被删除." + msg;
		}

		return msg;
	}
}
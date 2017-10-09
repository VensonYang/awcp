package BP.WF.Template;

import BP.DA.*;
import BP.En.*;
import BP.Port.*;
import BP.Sys.*;
import BP.Tools.StringHelper;
import BP.WF.OSModel;

/** 
  流程类别
 
*/
public class FlowSort extends EntityTree
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造方法
	/** 
	 流程类别
	 
	*/
	public FlowSort()
	{
	}
	/** 
	 流程类别
	 
	 @param _No
	*/
	public FlowSort(String _No)
	{
		super(_No);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 流程类别Map
	 
	*/
	@Override
	public Map getEnMap()
	{
		if (this.get_enMap() != null)
		{
			return this.get_enMap();
		}

		Map map = new Map("WF_FlowSort");
		map.setEnDesc("流程类别");
		map.setCodeStruct("2");
		map.setIsAllowRepeatName(false);



		map.setDepositaryOfEntity(Depositary.Application);
		map.setDepositaryOfMap(Depositary.Application);

		map.AddTBStringPK(FlowSortAttr.No, null, "编号", true, true, 1, 10, 20);
		map.AddTBString(FlowSortAttr.Name, null, "名称", true, false, 0, 100, 30);
		map.AddTBString(FlowSortAttr.ParentNo, null, "父节点No", false, false, 0, 100, 30);
		map.AddTBString(FlowSortAttr.TreeNo, null, "TreeNo", false, false, 0, 100, 30);

		map.AddTBInt(FlowSortAttr.Idx, 0, "Idx", false, false);
		map.AddTBInt(FlowSortAttr.IsDir, 0, "IsDir", false, false);

		this.set_enMap (map);
		return this.get_enMap();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 重写方法
	public final void WritToGPM()
	{
		return;
	}

	@Override
	protected boolean beforeInsert()
	{
		this.WritToGPM();
		return super.beforeInsert();
	}

	@Override
	protected boolean beforeDelete()
	{
		try
		{
			//删除权限管理
			if (BP.WF.Glo.getOSModel() == OSModel.BPM)
			{
				DBAccess.RunSQL("DELETE FROM GPM_Menu WHERE Flag='FlowSort" + this.getNo() + "' AND FK_App='" + SystemConfig.getSysNo() + "'");
			}
		}
		catch (java.lang.Exception e)
		{
		}
		return super.beforeDelete();
	}

	@Override
	protected boolean beforeUpdate()
	{
		//修改权限管理
		if (BP.WF.Glo.getOSModel() == OSModel.BPM)
		{
			DBAccess.RunSQL("UPDATE  GPM_Menu SET Name='" + this.getName() + "' WHERE Flag='FlowSort" + this.getNo() + "' AND FK_App='" + SystemConfig.getSysNo() + "'");
		}
		return super.beforeUpdate();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion 重写方法
}
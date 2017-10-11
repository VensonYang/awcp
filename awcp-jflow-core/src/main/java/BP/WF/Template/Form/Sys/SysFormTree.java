package BP.WF.Template.Form.Sys;

import BP.DA.*;
import BP.En.*;
import BP.Port.*;
import BP.Sys.Frm.MapData;
import BP.Tools.StringHelper;

/** 
  流程表单树
 
*/
public class SysFormTree extends EntityTree
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造方法
	/** 
	 流程表单树
	 
	*/
	public SysFormTree()
	{
	}
	/** 
	 流程表单树
	 
	 @param _No
	*/
	public SysFormTree(String _No)
	{
		super(_No);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 系统方法.
	/** 
	 流程表单树Map
	 
	*/
	@Override
	public Map getEnMap()
	{
		if (this.get_enMap() != null)
		{
			return this.get_enMap();
		}

		Map map = new Map("Sys_FormTree");
		map.setEnDesc ( "表单树");
		map.setCodeStruct ( "2");

		map.setDepositaryOfEntity( Depositary.Application);
		map.setDepositaryOfMap ( Depositary.Application);

		map.AddTBStringPK(SysFormTreeAttr.No, null, "编号", true, true, 1, 10, 20);
		map.AddTBString(SysFormTreeAttr.Name, null, "名称", true, false, 0, 100, 30);
		map.AddTBString(SysFormTreeAttr.ParentNo, null, "父节点No", false, false, 0, 100, 30);
		map.AddTBString(SysFormTreeAttr.TreeNo, null, "TreeNo", false, false, 0, 100, 30);

		map.AddTBInt(SysFormTreeAttr.IsDir, 0, "是否是目录?", false, false);
		map.AddTBInt(SysFormTreeAttr.Idx, 0, "Idx", false, false);

		this.set_enMap ( map);
		return this.get_enMap();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion 系统方法.

	@Override
	protected boolean beforeDelete()
	{
		if (!StringHelper.isNullOrEmpty(this.getNo()))
		{
			DeleteChild(this.getNo());
		}
		return super.beforeDelete();
	}
	/** 
	 删除子项
	 
	 @param parentNo
	*/
	private void DeleteChild(String parentNo)
	{
		SysFormTrees formTrees = new SysFormTrees();
		formTrees.RetrieveByAttr(SysFormTreeAttr.ParentNo, parentNo);
		for (SysFormTree item : SysFormTrees.convertSysFormTrees( formTrees))
		{
			MapData md = new MapData();
			md.setFK_FormTree ( item.getNo());
			md.Delete();
			DeleteChild(item.getNo());
		}
	}
}
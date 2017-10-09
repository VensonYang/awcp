package BP.WF.DTS;

import BP.*;
import BP.Sys.*;
import BP.Sys.Frm.FrmLines;
import BP.Sys.Frm.GroupField;
import BP.Sys.Frm.GroupFields;
import BP.DA.*;
import BP.En.*;

/** 
 删除空白的字段分组
 
*/
public class DeleteBlankGroupField extends Method
{
	/** 
	 删除空白的字段分组
	 
	*/
	public DeleteBlankGroupField()
	{
		this.Title = "删除空白的字段分组";
		this.Help = "";
		this.Icon = "<img src='/WF/Img/Btn/Delete.gif'  border=0 />";
	}
	@Override
	public void Init()
	{

	}
	@Override
	public boolean getIsCanDo()
	{
		return true;
	}
	@Override
	public Object Do()
	{
		GroupFields gfs = new GroupFields();
		gfs.RetrieveAll();

		int delNum = 0;
		for (GroupField item :GroupFields.convertGroupFields(gfs))
		{
			int num = 0;
			num += DBAccess.RunSQLReturnValInt("SELECT COUNT(*) FROM Sys_MapAttr WHERE GroupID=" + item.getOID() + " and FK_MapData='" + item.getEnName() + "'");
			num += DBAccess.RunSQLReturnValInt("SELECT COUNT(*) FROM Sys_FrmAttachment WHERE GroupID=" + item.getOID() + " and FK_MapData='" + item.getEnName() + "'");
			num += DBAccess.RunSQLReturnValInt("SELECT COUNT(*) FROM Sys_MapDtl WHERE GroupID=" + item.getOID() + " and FK_MapData='" + item.getEnName() + "'");
			num += DBAccess.RunSQLReturnValInt("SELECT COUNT(*) FROM Sys_MapFrame WHERE GroupID=" + item.getOID() + " and FK_MapData='" + item.getEnName() + "'");
			num += DBAccess.RunSQLReturnValInt("SELECT COUNT(*) FROM Sys_MapM2M WHERE GroupID=" + item.getOID()+" and FK_MapData='"+item.getEnName()+"'");
			if (num == 0)
			{
				delNum++;
				item.Delete();
			}
		}
		return "执行成功,删除数量:" + delNum;
	}
}
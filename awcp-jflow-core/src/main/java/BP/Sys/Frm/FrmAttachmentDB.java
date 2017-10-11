package BP.Sys.Frm;

import BP.DA.Depositary;
import BP.En.EnType;
import BP.En.EntityMyPK;
import BP.En.Map;

/** 
 附件数据存储
 
*/
public class FrmAttachmentDB extends EntityMyPK
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 属性
	/** 
	 类别
	 
	*/
	public final String getSort()
	{
		return this.GetValStringByKey(FrmAttachmentDBAttr.Sort);
	}
	public final void setSort(String value)
	{
		this.SetValByKey(FrmAttachmentDBAttr.Sort, value);
	}
	/** 
	 记录日期
	 
	*/
	public final String getRDT()
	{
		return this.GetValStringByKey(FrmAttachmentDBAttr.RDT);
	}
	public final void setRDT(String value)
	{
		this.SetValByKey(FrmAttachmentDBAttr.RDT, value);
	}
	/** 
	 文件
	 
	*/
	public final String getFileFullName()
	{
		return this.GetValStringByKey(FrmAttachmentDBAttr.FileFullName);
	}
	public final void setFileFullName(String value)
	{
		this.SetValByKey(FrmAttachmentDBAttr.FileFullName, value);
	}
	/** 
	 上传GUID
	 
	*/
	public final String getUploadGUID()
	{
		return this.GetValStringByKey(FrmAttachmentDBAttr.UploadGUID);
	}
	public final void setUploadGUID(String value)
	{
		this.SetValByKey(FrmAttachmentDBAttr.UploadGUID, value);
	}
	/** 
	 附件路径
	 
	*/
	public final String getFilePathName()
	{
		return this.getFileFullName().substring(this.getFileFullName().lastIndexOf('\\') + 1);
	}
	/** 
	 附件名称
	 
	*/
	public final String getFileName()
	{
		return this.GetValStringByKey(FrmAttachmentDBAttr.FileName);
	}
	public final void setFileName(String value)
	{
		this.SetValByKey(FrmAttachmentDBAttr.FileName, value);
	}
	/** 
	 附件扩展名
	 
	*/
	public final String getFileExts()
	{
		return this.GetValStringByKey(FrmAttachmentDBAttr.FileExts);
	}
	public final void setFileExts(String value)
	{
		this.SetValByKey(FrmAttachmentDBAttr.FileExts, value.replace(".",""));
	}
	/** 
	 相关附件
	 
	*/
	public final String getFK_FrmAttachment()
	{
		return this.GetValStringByKey(FrmAttachmentDBAttr.FK_FrmAttachment);
	}
	public final void setFK_FrmAttachment(String value)
	{
		this.SetValByKey(FrmAttachmentDBAttr.FK_FrmAttachment, value);
	}
	/** 
	 主键值
	 
	*/
	public final String getRefPKVal()
	{
		return this.GetValStringByKey(FrmAttachmentDBAttr.RefPKVal);
	}
	public final void setRefPKVal(String value)
	{
		this.SetValByKey(FrmAttachmentDBAttr.RefPKVal, value);
	}
	/** 
	 MyNote
	 
	*/
	public final String getMyNote()
	{
		return this.GetValStringByKey(FrmAttachmentDBAttr.MyNote);
	}
	public final void setMyNote(String value)
	{
		this.SetValByKey(FrmAttachmentDBAttr.MyNote, value);
	}
	/** 
	 记录人
	 
	*/
	public final String getRec()
	{
		return this.GetValStringByKey(FrmAttachmentDBAttr.Rec);
	}
	public final void setRec(String value)
	{
		this.SetValByKey(FrmAttachmentDBAttr.Rec, value);
	}
	/** 
	 记录人名称
	 
	*/
	public final String getRecName()
	{
		return this.GetValStringByKey(FrmAttachmentDBAttr.RecName);
	}
	public final void setRecName(String value)
	{
		this.SetValByKey(FrmAttachmentDBAttr.RecName, value);
	}
	/** 
	 附件编号
	 
	*/
	public final String getFK_MapData()
	{
		return this.GetValStringByKey(FrmAttachmentDBAttr.FK_MapData);
	}
	public final void setFK_MapData(String value)
	{
		this.SetValByKey(FrmAttachmentDBAttr.FK_MapData, value);
	}
	/** 
	 文件大小
	 
	*/
	public final float getFileSize()
	{
		return this.GetValFloatByKey(FrmAttachmentDBAttr.FileSize);
	}
	public final void setFileSize(float value)
	{
		this.SetValByKey(FrmAttachmentDBAttr.FileSize, value/1024);
	}
	/** 
	 是否锁定行?
	 
	*/
	public final boolean getIsRowLock()
	{
		return this.GetValBooleanByKey(FrmAttachmentDBAttr.IsRowLock);
	}
	public final void setIsRowLock(boolean value)
	{
		this.SetValByKey(FrmAttachmentDBAttr.IsRowLock, value);
	}
	/** 
	 附件扩展名
	 
	*/
	public final String getNodeID()
	{
		return this.GetValStringByKey(FrmAttachmentDBAttr.NodeID);
	}
	public final void setNodeID(String value)
	{
		this.SetValByKey(FrmAttachmentDBAttr.NodeID, value);
	}
	/** 
	 附件类型
	 
	*/
	public final AttachmentUploadType getHisAttachmentUploadType()
	{

		if (this.getMyPK().contains("_") && this.getMyPK().length() < 32)
		{
			return AttachmentUploadType.Single;
		}
		else
		{
			return AttachmentUploadType.Multi;
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造方法
	/** 
	 附件数据存储
	 
	*/
	public FrmAttachmentDB()
	{
	}
	/** 
	 附件数据存储
	 
	 @param mypk
	*/
	public FrmAttachmentDB(String mypk)
	{
		this.setMyPK(mypk);
		this.Retrieve();
	}
	/** 
	 EnMap
	 
	*/
	@Override
	public Map getEnMap()
	{
		if (this.get_enMap() != null)
		{
			return this.get_enMap();
		}
		Map map = new Map("Sys_FrmAttachmentDB");

		map.setDepositaryOfEntity(Depositary.None);
		map.setDepositaryOfMap(Depositary.Application);
		map.setEnDesc("附件数据存储");
		map.setEnType(EnType.Sys);
		map.AddMyPK();
		map.AddTBString(FrmAttachmentDBAttr.FK_MapData, null, "FK_MapData", true, false, 1, 30, 20);
		map.AddTBString(FrmAttachmentDBAttr.FK_FrmAttachment, null, "附件编号", true, false, 1, 500, 20);
		map.AddTBString(FrmAttachmentDBAttr.RefPKVal, null, "实体主键", true, false, 0, 50, 20);

		map.AddTBString(FrmAttachmentDBAttr.Sort, null, "类别", true, false, 0, 200, 20);
		map.AddTBString(FrmAttachmentDBAttr.FileFullName, null, "FileFullName", true, false, 0, 700, 20);
		map.AddTBString(FrmAttachmentDBAttr.FileName, null, "名称", true, false, 0, 500, 20);
		map.AddTBString(FrmAttachmentDBAttr.FileExts, null, "扩展", true, false, 0, 50, 20);
		map.AddTBFloat(FrmAttachmentDBAttr.FileSize, 0, "文件大小", true, false);

		map.AddTBDateTime(FrmAttachmentDBAttr.RDT, null, "记录日期", true, false);
		map.AddTBString(FrmAttachmentDBAttr.Rec, null, "记录人", true, false, 0, 50, 20);
		map.AddTBString(FrmAttachmentDBAttr.RecName, null, "记录人名字", true, false, 0, 50, 20);
		map.AddTBStringDoc(FrmAttachmentDBAttr.MyNote, null, "备注", true, false);
		map.AddTBString(FrmAttachmentDBAttr.NodeID, null, "节点ID", true, false, 0, 50, 20);

		map.AddTBInt(FrmAttachmentDBAttr.IsRowLock, 0, "是否锁定行", true, false);

			//这个值在上传时候产生.
		map.AddTBString(FrmAttachmentDBAttr.UploadGUID, null, "上传GUID", true, false, 0, 50, 20);

		this.set_enMap(map);
		return this.get_enMap();
	}
	/** 
	 重写
	 
	 @return 
	*/
	@Override
	protected boolean beforeInsert()
	{
		return super.beforeInsert();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}
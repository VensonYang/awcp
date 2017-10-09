package BP.Sys.Frm;

import BP.DA.Depositary;
import BP.En.EnType;
import BP.En.EntityMyPK;
import BP.En.Map;

/** 
 剪切图片附件数据存储
 
*/
public class FrmImgAthDB extends EntityMyPK
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
		return this.GetValStringByKey(FrmImgAthDBAttr.Sort);
	}
	public final void setSort(String value)
	{
		this.SetValByKey(FrmImgAthDBAttr.Sort, value);
	}
	/** 
	 记录日期
	 
	*/
	public final String getRDT()
	{
		return this.GetValStringByKey(FrmImgAthDBAttr.RDT);
	}
	public final void setRDT(String value)
	{
		this.SetValByKey(FrmImgAthDBAttr.RDT, value);
	}
	/** 
	 文件
	 
	*/
	public final String getFileFullName()
	{
		return this.GetValStringByKey(FrmImgAthDBAttr.FileFullName);
	}
	public final void setFileFullName(String value)
	{
		this.SetValByKey(FrmImgAthDBAttr.FileFullName, value);
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
		return this.GetValStringByKey(FrmImgAthDBAttr.FileName);
	}
	public final void setFileName(String value)
	{
		this.SetValByKey(FrmImgAthDBAttr.FileName, value);
	}
	/** 
	 附件扩展名
	 
	*/
	public final String getFileExts()
	{
		return this.GetValStringByKey(FrmImgAthDBAttr.FileExts);
	}
	public final void setFileExts(String value)
	{
		this.SetValByKey(FrmImgAthDBAttr.FileExts, value.replace(".",""));
	}
	/** 
	 相关附件
	 
	*/
	public final String getFK_FrmImgAth()
	{
		return this.GetValStringByKey(FrmImgAthDBAttr.FK_FrmImgAth);
	}
	public final void setFK_FrmImgAth(String value)
	{
		this.SetValByKey(FrmImgAthDBAttr.FK_FrmImgAth, value);
	}
	/** 
	 主键值
	 
	*/
	public final String getRefPKVal()
	{
		return this.GetValStringByKey(FrmImgAthDBAttr.RefPKVal);
	}
	public final void setRefPKVal(String value)
	{
		this.SetValByKey(FrmImgAthDBAttr.RefPKVal, value);
	}
	/** 
	 MyNote
	 
	*/
	public final String getMyNote()
	{
		return this.GetValStringByKey(FrmImgAthDBAttr.MyNote);
	}
	public final void setMyNote(String value)
	{
		this.SetValByKey(FrmImgAthDBAttr.MyNote, value);
	}
	/** 
	 记录人
	 
	*/
	public final String getRec()
	{
		return this.GetValStringByKey(FrmImgAthDBAttr.Rec);
	}
	public final void setRec(String value)
	{
		this.SetValByKey(FrmImgAthDBAttr.Rec, value);
	}
	/** 
	 记录人名称
	 
	*/
	public final String getRecName()
	{
		return this.GetValStringByKey(FrmImgAthDBAttr.RecName);
	}
	public final void setRecName(String value)
	{
		this.SetValByKey(FrmImgAthDBAttr.RecName, value);
	}
	/** 
	 附件编号
	 
	*/
	public final String getFK_MapData()
	{
		return this.GetValStringByKey(FrmImgAthDBAttr.FK_MapData);
	}
	public final void setFK_MapData(String value)
	{
		this.SetValByKey(FrmImgAthDBAttr.FK_MapData, value);
	}
	/** 
	 文件大小
	 
	*/
	public final float getFileSize()
	{
		return this.GetValFloatByKey(FrmImgAthDBAttr.FileSize);
	}
	public final void setFileSize(float value)
	{
		this.SetValByKey(FrmImgAthDBAttr.FileSize, value/1024);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造方法
	/** 
	 剪切图片附件数据存储
	 
	*/
	public FrmImgAthDB()
	{
	}
	/** 
	 剪切图片附件数据存储
	 
	 @param mypk
	*/
	public FrmImgAthDB(String mypk)
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

		Map map = new Map("Sys_FrmImgAthDB");

		map.setDepositaryOfEntity(Depositary.None);
		map.setDepositaryOfMap(Depositary.Application);
		map.setEnDesc("剪切图片附件数据存储");
		map.setEnType(EnType.Sys);
		map.AddMyPK();

			// 以下三个字段组成一个主键. FK_FrmImgAth+"_"+RefPKVal
		map.AddTBString(FrmImgAthDBAttr.FK_MapData, null, "附件ID", true, false, 1, 30, 20);
		map.AddTBString(FrmImgAthDBAttr.FK_FrmImgAth, null, "图片附件编号", true, false, 1, 50, 20);
		map.AddTBString(FrmImgAthDBAttr.RefPKVal, null, "实体主键", true, false, 1, 50, 20);

		map.AddTBString(FrmImgAthDBAttr.FileFullName, null, "文件全路径", true, false, 0, 700, 20);
		map.AddTBString(FrmImgAthDBAttr.FileName, null, "名称", true, false, 0, 500, 20);
		map.AddTBString(FrmImgAthDBAttr.FileExts, null, "扩展名", true, false, 0, 50, 20);
		map.AddTBFloat(FrmImgAthDBAttr.FileSize, 0, "文件大小", true, false);

		map.AddTBDateTime(FrmImgAthDBAttr.RDT, null, "记录日期", true, false);
		map.AddTBString(FrmImgAthDBAttr.Rec, null, "记录人", true, false, 0, 50, 20);
		map.AddTBString(FrmImgAthDBAttr.RecName, null, "记录人名字", true, false, 0, 50, 20);
		map.AddTBStringDoc(FrmImgAthDBAttr.MyNote, null, "备注", true, false);

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
		this.setMyPK(this.getFK_FrmImgAth() + "_" + this.getRefPKVal());
		return super.beforeInsert();
	}
	/** 
	 重写
	 
	 @return 
	*/
	@Override
	protected boolean beforeUpdate()
	{
		this.setMyPK(this.getFK_FrmImgAth() + "_" + this.getRefPKVal());
		return super.beforeUpdate();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}
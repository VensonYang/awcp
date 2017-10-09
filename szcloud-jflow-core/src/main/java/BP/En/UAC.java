package BP.En;

import BP.DA.DBAccess;
import BP.DA.Paras;
import BP.Port.WebUser;
import BP.Sys.SystemConfig;

/** 
 访问控制
 
*/
public class UAC
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 常用方法
	public final void Readonly()
	{
		this.IsUpdate = false;
		this.IsDelete = false;
		this.IsInsert = false;
		this.IsAdjunct = false;
		this.IsView = true;
	}
	/** 
	 全部开放
	 
	*/
	public final void OpenAll()
	{
		this.IsUpdate = true;
		this.IsDelete = true;
		this.IsInsert = true;
		this.IsAdjunct = false;
		this.IsView = true;
	}
	/** 
	 为一个岗位设置全部的权限
	 
	 @param fk_station
	*/
	public final void OpenAllForStation(String fk_station)
	{
		Paras ps = new Paras();
		ps.Add("user", WebUser.getNo());
		ps.Add("st", fk_station);

		if (DBAccess.IsExits("SELECT FK_Emp FROM Port_EmpStation WHERE FK_Emp=" + SystemConfig.getAppCenterDBVarStr() + "user AND FK_Station=" + SystemConfig.getAppCenterDBVarStr() + "st", ps))
		{
			this.OpenAll();
		}
		else
		{
			this.Readonly();
		}
	}
	/** 
	 仅仅对管理员
	 
	*/
	public final UAC OpenForSysAdmin()
	{
		if (SystemConfig.getSysNo().equals("WebSite"))
		{
			this.OpenAll();
			return this;
		}

		if (WebUser.getNo().equals(SystemConfig.getAppSettings().get("Admin").toString()))
		{
			this.OpenAll();
		}
		return this;
	}
	public final UAC OpenForAppAdmin()
	{
		if (WebUser.getNo() != null && WebUser.getNo().contains("admin"))
		{
			this.OpenAll();
		}
		return this;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 控制属性
	/** 
	 是否插入
	 
	*/
	public boolean IsInsert = false;
	/** 
	 是否删除
	 
	*/
	public boolean IsDelete = false;
	/** 
	 是否更新
	 
	*/
	public boolean IsUpdate = false;
	/** 
	 是否查看
	 
	*/
	public boolean IsView = true;
	/** 
	 附件
	 
	*/
	public boolean IsAdjunct = false;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	/** 
	 用户访问
	 
	*/
	public UAC()
	{

	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}
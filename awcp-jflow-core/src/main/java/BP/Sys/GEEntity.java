package BP.Sys;

import BP.En.Entities;
import BP.En.Entity;
import BP.En.Map;

/** 
通用实体

*/
public class GEEntity extends Entity
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造函数
	@Override
	public String getPK()
	{
		return "OID";
	}
	@Override
	public String getPKField()
	{
		return "OID";
	}
	@Override
	public String toString()
	{
		return this.FK_MapData;
	}
	@Override
	public String getClassID()
	{
		return this.FK_MapData;
	}
	/** 
	 主键
	 
	*/
	public String FK_MapData = null;
	/** 
	 通用实体
	 
	*/
	public GEEntity()
	{
	}
	/** 
	 通用实体
	 
	 @param nodeid 节点ID
	*/
	public GEEntity(String fk_mapdata)
	{
		this.FK_MapData = fk_mapdata;
	}
	/** 
	 通用实体
	 
	 @param nodeid 节点ID
	 @param _oid OID
	*/
	public GEEntity(String fk_mapdata, Object pk)
	{
		this.FK_MapData = fk_mapdata;
		this.setPKVal(pk);
		this.Retrieve();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Map
	/** 
	 重写基类方法
	 
	*/
	@Override
	public Map getEnMap()
	{
		if (this.get_enMap() != null)
		{
			return this.get_enMap();
		}

		if (this.FK_MapData == null)
		{
			throw new RuntimeException("没有给" + this.FK_MapData + "值，您不能获取它的Map。");
		}

		this.set_enMap(BP.Sys.Frm.MapData.GenerHisMap(this.FK_MapData));
		return this.get_enMap();
	}
	/** 
	 GEEntitys
	 
	*/
	@Override
	public Entities getGetNewEntities()
	{
		if (this.FK_MapData == null)
		{
			return new GEEntitys();
		}
		return new GEEntitys(this.FK_MapData);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	private java.util.ArrayList _Dtls = null;
	public final java.util.ArrayList getDtls()
	{
		if (_Dtls == null)
		{
			_Dtls = new java.util.ArrayList();
		}
		return _Dtls;
	}
}
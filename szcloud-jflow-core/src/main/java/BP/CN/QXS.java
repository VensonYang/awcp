package BP.CN;

import BP.DA.DBUrl;
import BP.DA.DBUrlType;
import BP.DA.Depositary;
import BP.En.AdjunctType;
import BP.En.EnType;
import BP.En.EntityNoName;
import BP.En.Map;
import BP.En.UAC;

/** 
区县市

*/
public class QXS extends EntityNoName
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#region 基本属性
	public final String getNameS()
	{
		return this.GetValStrByKey(QXSAttr.NameS);
	}
	public final String getFK_PQ()
	{
		return this.GetValStrByKey(QXSAttr.FK_PQ);
	}
	public final String getFK_SF()
	{
		return this.GetValStrByKey(QXSAttr.FK_SF);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#region 构造函数
	@Override
	public UAC getHisUAC()
	{
		UAC uac = new UAC();
		uac.OpenForSysAdmin();
		return uac;
	}
	/** 
	 区县市
	 		
	*/
	public QXS()
	{
	}
	public QXS(String no)
	{
		super(no);
	}
	/** 
	 Map
	 
	*/
	@Override
	public Map getEnMap()
	{
		if (this.get_enMap()!=null)
		{
			return this.get_enMap();
		}
		Map map = new Map();

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 基本属性
		map.setEnDBUrl(new DBUrl(DBUrlType.AppCenterDSN));
		map.setPhysicsTable("CN_QXS");
		map.setAdjunctType(AdjunctType.AllType);
		map.setDepositaryOfMap(Depositary.Application);
		map.setDepositaryOfEntity(Depositary.None);
		map.setIsCheckNoLength(false);
		map.setEnDesc("区县市");
		map.setEnType(EnType.App);
		map.setCodeStruct("4");
		
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 字段
		map.AddTBStringPK(QXSAttr.No, null, "编号", true, false, 0, 50, 50);
		map.AddTBString(QXSAttr.Name, null, "名称", true, false, 0, 50, 200);
		map.AddTBString(QXSAttr.NameS, null, "NameS", true, false, 0, 50, 200);


		map.AddDDLEntities(QXSAttr.FK_SF, null, "省份", new SFs(), true);
		map.AddDDLEntities(QXSAttr.FK_PQ, null, "片区", new PQs(), true);

		map.AddSearchAttr(QXSAttr.FK_SF);
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

		this.set_enMap(map);
		return this.get_enMap();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#endregion

	/** 
	 获取一个字符串中是否包含区县名称，如果包含就返回它的编号，不包含就返回默认的值。
	 
	 @param name 字串
	 @param defVal 默认值
	 @return 区县代码
	*/
	public static String GenerQXSNoByName(String name, String defVal)
	{
		//进行模糊匹配地区。
		QXSs qxss = new QXSs();
		qxss.RetrieveAll();

		for (QXS qxs : QXSs.convertQXSs(qxss))
		{
			if (name.contains(qxs.getNameS()))
			{
				return qxs.getNo();
			}
		}

		SFs sfs = new SFs();
		sfs.RetrieveAll();
		for (SF sf : SFs.convertSFs(sfs))
		{
			if (name.contains(sf.getNames()))
			{
				return sf.getNo();
			}
		}

		return defVal;
	}
}


package BP.Sys;

import java.util.ArrayList;

import BP.DA.*;
import BP.En.*;
import BP.Sys.XML.EnumInfoXml;

/** 
 纳税人集合 
 
*/
public class SysEnums extends Entities
{
	public static ArrayList<SysEnum> convertSysEnums(Object obj) {
		return (ArrayList<SysEnum>) obj;
	}
	/** 
	 此枚举类型的个数
	 
	*/
	public int Num = -1;
	public final String ToDesc()
	{
		String strs = "";
		for (Object se : this)
		{
			strs += ((SysEnum)se).getIntKey() + " " + ((SysEnum)se).getLab() + ";";
		}
		return strs;
	}
	public final String GenerCaseWhenForOracle(String enName, String mTable, String key, String field, String enumKey, int def)
	{
		String sql = (String)Cash.GetObjFormApplication("ESQL" + enName +mTable+ key + "_" + enumKey, null);
		// string sql = "";
		if (sql != null)
		{
			return sql;
		}

		if (this.size() == 0)
		{
			throw new RuntimeException("@枚举值" + enumKey + "已被删除。");
		}

		sql = " CASE NVL(" + mTable + field+","+def+")";
		for (Object se1 : this)
		{
			sql += " WHEN " + ((SysEnum)se1).getIntKey() + " THEN '" + ((SysEnum)se1).getLab() + "'";
		}

		SysEnum se = (SysEnum)this.GetEntityByKey(SysEnumAttr.IntKey, def);
		if (se == null)
		{
			sql += " END " + key + "Text";
		}
		else
		{
			sql += " WHEN NULL THEN '" + se.getLab() + "' END " + key + "TEXT";
		}

		Cash.AddObj("ESQL" + enName + mTable + key + "_" + enumKey, Depositary.Application, sql);
		return sql;
	}

	public final String GenerCaseWhenForOracle(String mTable, String key, String field, String enumKey, int def)
	{
		if (this.size() == 0)
		{
			throw new RuntimeException("@枚举值（" + enumKey + "）已被删除，无法形成期望的SQL。");
		}


		String sql = "";
		sql = " CASE " + mTable + field;
		for (Object se1 : this)
		{
			sql += " WHEN " + ((SysEnum)se1).getIntKey() + " THEN '" + ((SysEnum)se1).getLab() + "'";
		}

		SysEnum se = (SysEnum)this.GetEntityByKey(SysEnumAttr.IntKey, def);
		if (se == null)
		{
			sql += " END " + key + "Text";
		}
		else
		{
			sql += " WHEN NULL THEN '" + se.getLab() + "' END " + key + "TEXT";
		}

		// Cash.AddObj("ESQL" + enName + key + "_" + enumKey, Depositary.Application, sql);
		return sql;
	}
	public final void LoadIt(String enumKey)
	{
		if (!this.Full(enumKey)) {
			try {
				BP.DA.DBAccess.RunSQL("UPDATE Sys_Enum SET Lang='"
						+ BP.Port.WebUser.getSysLang()
						+ "' WHERE LANG IS NULL ");
				BP.DA.DBAccess
						.RunSQL("UPDATE Sys_Enum SET MyPK=EnumKey+'_'+Lang+'_'+cast(IntKey as NVARCHAR )");
			} catch (java.lang.Exception e) {
			}

			try {
				EnumInfoXml xml = new EnumInfoXml(enumKey);
				this.RegIt(enumKey, xml.getVals());
			} catch (RuntimeException ex) {
				throw new RuntimeException("@你没有预制[" + enumKey
						+ "]枚举值。@在修复枚举值出现错误:" + ex.getMessage());
			}
		}
	}
	/** 
	 SysEnums
	 
	 @param EnumKey
	*/
	public SysEnums(String enumKey)
	{
		this.LoadIt(enumKey);
	}
	public SysEnums(String enumKey, String vals)
	{
		if (vals == null || vals.equals(""))
		{
			this.LoadIt(enumKey);
			return;
		}

		if (!this.Full(enumKey) )
		{
			this.RegIt(enumKey, vals);
		}
	}
	public final void RegIt(String EnumKey, String vals)
	{
		try
		{
			String[] strs = vals.split("[@]", -1);
			SysEnums ens = new SysEnums();
			ens.Delete(SysEnumAttr.EnumKey, EnumKey);
			this.clear();

			for (String s : strs)
			{
				if (s.equals("") || s == null)
				{
					continue;
				}

				String[] vk = s.split("[=]", -1);
				SysEnum se = new SysEnum();
				se.setIntKey(Integer.parseInt(vk[0]));
				se.setLab(vk[1]);
				se.setEnumKey(EnumKey);
				se.setLang(BP.Port.WebUser.getSysLang());
				se.Insert();
				this.AddEntity(se);
			}
		}
		catch (RuntimeException ex)
		{
			throw new RuntimeException(ex.getMessage() + " - " + vals);
		}
		//  this.Full(EnumKey);
	}
	public final boolean Full(String enumKey)
	{
		Entities ens = (Entities)Cash.GetObjFormApplication("EnumOf" + enumKey + BP.Port.WebUser.getSysLang(), null);
		if (ens != null)
		{
			this.AddEntities(ens);
			return true;
		}
		
	 

		QueryObject qo = new QueryObject(this);
		qo.AddWhere(SysEnumAttr.EnumKey, enumKey);
		qo.addAnd();
		qo.AddWhere(SysEnumAttr.Lang, BP.Port.WebUser.getSysLang());
		qo.addOrderBy(SysEnumAttr.IntKey);
		if (qo.DoQuery() == 0)
		{
			// 看看xml配置里面是否有?
			return false;
		}

		Cash.AddObj("EnumOf" + enumKey + BP.Port.WebUser.getSysLang(), Depositary.Application, this);
		return true;
	}
	///// <summary>
	///// DBSimpleNoNames
	///// </summary>
	///// <returns></returns>
	//public DBSimpleNoNames ToEntitiesNoName()
	//{
	//    DBSimpleNoNames ens = new DBSimpleNoNames();
	//    foreach (SysEnum en in this)
	//    {
	//        ens.AddByNoName(en.IntKey.ToString(), en.Lab);
	//    }
	//    return ens;
	//}
	/** 
	 
	 
	 @param key
	 @param val
	 @return 
	*/
	public  int Delete(String key, Object val)
	{
		try
		{
			Entity en = this.getGetNewEntity();
			Paras ps = new Paras();

			ps.SQL = SqlBuilder.DeleteSysEnumsSQL(en.getEnMap().getPhysicsTable(),key );
			ps.Add("p", val);
			return en.RunSQL(ps);
		}
		catch (java.lang.Exception e)
		{
			Entity en = this.getGetNewEntity();
			try {
				en.CheckPhysicsTable();
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			Paras ps = new Paras();
			ps.SQL = "DELETE FROM " + en.getEnMap().getPhysicsTable() + " WHERE " + key + "=" + en.getHisDBVarStr() + "p";
			ps.Add("p", val);
			try {
				return en.RunSQL(ps);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return 0;
	}
	/** 
	 SysEnums
	 
	*/
	public SysEnums()
	{
	}
	/** 
	 得到它的 Entity
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new SysEnum();
	}
	/** 
	 通过int 得到Lab
	 
	 @param val val
	 @return string val
	*/
	public final String GetLabByVal(int val)
	{
		for(Object en : this)
		{
			if (((SysEnum)en).getIntKey() == val)
			{
				return ((SysEnum)en).getLab();
			}
		}
		return null;
	}
}
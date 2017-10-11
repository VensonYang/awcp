package BP.WF;

import java.util.ArrayList;

import BP.DA.DataSet;
import BP.En.Entity;
import BP.Sys.GEEntity;
import BP.Sys.Frm.MapData;
import BP.WF.Data.BillFileType;

/** 
 表单引擎api
 
*/
public class CCFormAPI extends Dev2Interface
{
	/** 
	 生成报表
	 
	 @param templeteFilePath 模版路径
	 @param ds 数据源
	 @return 生成单据的路径
	*/
	public static void Frm_GenerBill(String templeteFullFile, String saveToDir, String saveFileName, BillFileType fileType, DataSet ds, String fk_mapData)
	{

		MapData md = new MapData(fk_mapData);
		GEEntity entity = md.GenerGEEntityByDataSet(ds);

		BP.Pub.RTFEngine rtf = new BP.Pub.RTFEngine();
		rtf.getHisEns().clear();
		rtf.getEnsDataDtls().clear();

		rtf.getHisEns().AddEntity(entity);
		ArrayList<Entity> dtls = entity.getDtls();

		for (Object item : dtls)
		{
			rtf.getEnsDataDtls().add(item);
		}

		rtf.MakeDoc(templeteFullFile, saveToDir, saveFileName, null, false);
	}
}
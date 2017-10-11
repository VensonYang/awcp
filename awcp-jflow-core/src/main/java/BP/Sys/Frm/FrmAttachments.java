package BP.Sys.Frm;

import java.util.ArrayList;

import BP.En.EntitiesMyPK;
import BP.En.Entity;

/** 
 附件s
 
*/
public class FrmAttachments extends EntitiesMyPK
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 构造
	/** 
	 附件s
	 
	*/
	public FrmAttachments()
	{
	}
	public static ArrayList<FrmAttachment> convertFrmAttachments(Object obj) {
		return (ArrayList<FrmAttachment>) obj;
	}
	/** 
	 附件s
	 
	 @param fk_mapdata s
	*/
	public FrmAttachments(String fk_mapdata)
	{
		this.Retrieve(FrmAttachmentAttr.FK_MapData, fk_mapdata, FrmAttachmentAttr.FK_Node, 0);
	}
	/** 
	 得到它的 Entity
	 
	*/
	@Override
	public Entity getGetNewEntity()
	{
		return new FrmAttachment();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}
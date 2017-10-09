package BP.Sys;
import BP.Port.*;
import BP.Sys.Frm.MapDataAttr;
import BP.DA.Depositary;
import BP.En.EntityNoName;
import BP.En.Map;
import BP.En.UAC;

/// <summary>
///  ToolbarExcel 控制器  
/// </summary>
public class ToolbarExcel extends EntityNoName
{
    //#region 界面上的访问控制
    /// <summary> 
    /// UI界面上的访问控制 
    /// </summary>
    public UAC getHisUAC()
    {
    	UAC uac = new UAC();
    	uac.IsDelete = false;
    	uac.IsInsert = false;
    	uac.IsView = false;
    	if (WebUser.getNo() == "admin")
    	{
    		uac.IsUpdate = true;
    		uac.IsView = true;
    	}
    	return uac;
    }
    
    //#region 功能按钮.
    /// <summary>
    /// 打开本地标签.
    /// </summary>

    public final String getOfficeOpenLab() {
		return this.GetValStringByKey(ToolbarExcelAttr.OfficeOpenLab);
	}
	public final void setOfficeOpenLab(String officeOpenLab) {
		this.SetValByKey(ToolbarExcelAttr.OfficeOpenLab, officeOpenLab);
	}
  
//    public String  OfficeOpenLab()
//    {
//    	this.SetValByKey(ToolbarExcelAttr.OfficeOpenLab, value);
//           return this.GetValStringByKey(ToolbarExcelAttr.OfficeOpenLab);
//    }
    
    
    /// <summary>
    /// 是否打开本地模版文件.
    /// </summary>
	
	public final boolean getOfficeOpenEnable() {
		return this.GetValBooleanByKey(ToolbarExcelAttr.OfficeOpenEnable);
	}
	public final void setOfficeOpenEnable(boolean officeOpenEnable) {
		this.SetValByKey(ToolbarExcelAttr.OfficeOpenEnable, officeOpenEnable);
	}

//	public boolean OfficeOpenEnable
//    {
//        get
//        {
//            return this.GetValBooleanByKey(ToolbarExcelAttr.OfficeOpenEnable);
//        }
//        set
//        {
//            this.SetValByKey(ToolbarExcelAttr.OfficeOpenEnable, value);
//        }
//    }
    /// <summary>
    /// 打开模板 标签.
    /// </summary>
    public final String getOfficeOpenTemplateLab()
    {
            return this.GetValStringByKey(ToolbarExcelAttr.OfficeOpenTemplateLab);
    }
    public final void setOfficeOpenTemplateLab(String value)
    {
    	this.SetValByKey(ToolbarExcelAttr.OfficeOpenTemplateLab, value);
    }
    /// <summary>
    /// 打开模板.
    /// </summary>
    public final boolean getOfficeOpenTemplateEnable()
    {
    	return this.GetValBooleanByKey(ToolbarExcelAttr.OfficeOpenTemplateEnable);
    }
    public final void setOfficeOpenTemplateEnable(String value)
    {
    	this.SetValByKey(ToolbarExcelAttr.OfficeOpenTemplateEnable, value);
    }
    /// <summary>
    /// 保存 标签.
    /// </summary>
    public final String getOfficeSaveLab()
    {
    	return this.GetValStringByKey(ToolbarExcelAttr.OfficeSaveLab);
    }
    public final void setOfficeSaveLab(String value)
    {
    	this.SetValByKey(ToolbarExcelAttr.OfficeSaveLab, value);
    }
    /// <summary>
    /// 保存.是否启用.
    /// </summary>
    public final boolean getOfficeSaveEnable()
    {
    	return this.GetValBooleanByKey(ToolbarExcelAttr.OfficeSaveEnable);
    }
    public final void setOfficeSaveEnable(boolean value)
    {
    	this.SetValByKey(ToolbarExcelAttr.OfficeSaveEnable, value);
    }
    /// <summary>
    /// 接受修订 标签.
    /// </summary>
    public final String getOfficeAcceptLab()
    {
    	return this.GetValStringByKey(ToolbarExcelAttr.OfficeAcceptLab);
    }
    public final void setOfficeAcceptLab(String value)
    {
    	this.SetValByKey(ToolbarExcelAttr.OfficeAcceptLab, value);
    }
    /// <summary>
    /// 接受修订.
    /// </summary>
    public final boolean getOfficeAcceptEnable()
    {
    	return this.GetValBooleanByKey(ToolbarExcelAttr.OfficeAcceptEnable);
    }
    public final void setOfficeAcceptEnable(boolean value)
    {
    	this.SetValByKey(ToolbarExcelAttr.OfficeAcceptEnable, value);
    }
    /// <summary>
    /// 拒绝修订 标签.
    /// </summary>
    public final String getOfficeRefuseLab()
    {
            return this.GetValStringByKey(ToolbarExcelAttr.OfficeRefuseLab);
        }
    public final void setOfficeRefuseLab(String value)
        {
            this.SetValByKey(ToolbarExcelAttr.OfficeRefuseLab, value);
        }
    /// <summary>
    /// 拒绝修订.
    /// </summary>
    public final boolean getOfficeRefuseEnable()
    {
            return this.GetValBooleanByKey(ToolbarExcelAttr.OfficeRefuseEnable);
        }
    public final void setOfficeRefuseEnable(String value)
        {
            this.SetValByKey(ToolbarExcelAttr.OfficeRefuseEnable, value);
        }
    /// <summary>
    /// 套红按钮 标签.
    /// </summary>
    public final String getOfficeOverLab()
    {
            return this.GetValStringByKey(ToolbarExcelAttr.OfficeOverLab);
        }
    public final void setOfficeOverLab(String value)
        {
            this.SetValByKey(ToolbarExcelAttr.OfficeOverLab, value);
        }
    /// <summary>
    /// 套红按钮.
    /// </summary>
    public final boolean getOfficeOverEnable()
    {
            return this.GetValBooleanByKey(ToolbarExcelAttr.OfficeOverEnable);
        }
    public final void getOfficeOverEnable(String value)
        {
            this.SetValByKey(ToolbarExcelAttr.OfficeOverEnable, value);
        }
    /// <summary>
    /// 查看用户留痕
    /// </summary>
    public final boolean getOfficeMarksEnable()
    {
            return this.GetValBooleanByKey(ToolbarExcelAttr.OfficeMarksEnable);
        }
    public final void setOfficeMarksEnable(String value)
        {
            this.SetValByKey(ToolbarExcelAttr.OfficeMarksEnable, value);
        }
    /// <summary>
    /// 打印按钮-标签
    /// </summary>
    public final String getOfficePrintLab()
    {
            return this.GetValStringByKey(ToolbarExcelAttr.OfficePrintLab);
        }
    public final void setOfficePrintLab(String value)
        {
            this.SetValByKey(ToolbarExcelAttr.OfficePrintLab, value);
        }
    
    /// <summary>
    /// 打印
    /// </summary>
    public final boolean getOfficePrintEnable()
    {
            return this.GetValBooleanByKey(ToolbarExcelAttr.OfficePrintEnable);
        }
    public final void setOfficePrintEnable(String value)
        {
            this.SetValByKey(ToolbarExcelAttr.OfficePrintEnable, value);
        }
    /// <summary>
    /// 签章-标签
    /// </summary>
    public final String getOfficeSealLab()
    {
            return this.GetValStringByKey(ToolbarExcelAttr.OfficeSealLab);
        }
    public final void setOfficeSealLab(String value)
        {
            this.SetValByKey(ToolbarExcelAttr.OfficeSealLab, value);
        }
    /// <summary>
    /// 签章
    /// </summary>
    public final boolean getOfficeSealEnable()
    {
            return this.GetValBooleanByKey(ToolbarExcelAttr.OfficeSealEnable);
        }
    public final void setOfficeSealEnable(String value)
        {
            this.SetValByKey(ToolbarExcelAttr.OfficeSealEnable, value);
        }



    /// <summary>
    /// 插入流程-标签
    /// </summary>
    public final String getOfficeInsertFlowLab()
    {
            return this.GetValStringByKey(ToolbarExcelAttr.OfficeInsertFlowLab);
        }
    public final void setOfficeInsertFlowLab(String value)
        {
            this.SetValByKey(ToolbarExcelAttr.OfficeInsertFlowLab, value);
        }
    /// <summary>
    /// 插入流程
    /// </summary>
    public final boolean getOfficeInsertFlowEnable()
    {
            return this.GetValBooleanByKey(ToolbarExcelAttr.OfficeInsertFlowEnable);
        }
    public final void setOfficeInsertFlowEnable(String value)
        {
            this.SetValByKey(ToolbarExcelAttr.OfficeInsertFlowEnable, value);
        }

    /// <summary>
    /// 是否自动记录节点信息
    /// </summary>
    public final boolean getOfficeNodeInfo()
    {
            return this.GetValBooleanByKey(ToolbarExcelAttr.OfficeNodeInfo);
        }
    public final void setOfficeNodeInfo(String value)
        {
            this.SetValByKey(ToolbarExcelAttr.OfficeNodeInfo, value);
        }
    
    /// <summary>
    /// 是否该节点保存为PDF
    /// </summary>
    public final boolean getOfficeReSavePDF()
    {
            return this.GetValBooleanByKey(ToolbarExcelAttr.OfficeReSavePDF);
        }
    public final void setOfficeReSavePDF(String value)
        {
            this.SetValByKey(ToolbarExcelAttr.OfficeReSavePDF, value);
        }
    /// <summary>
    /// 是否进入留痕模式
    /// </summary>
    public final boolean getOfficeIsMarks()
    {
            return this.GetValBooleanByKey(ToolbarExcelAttr.OfficeIsMarks);
        }
    public final void setOfficeIsMarks(String value)
        {
            this.SetValByKey(ToolbarExcelAttr.OfficeIsMarks, value);
        }
    /// <summary>
    /// 指定文档模板
    /// </summary>
    public final String getOfficeTemplate()
    {
            return this.GetValStringByKey(ToolbarExcelAttr.OfficeTemplate);
        }
    public final void setOfficeTemplate(String value)
        {
            this.SetValByKey(ToolbarExcelAttr.OfficeTemplate, value);
        }
    /// <summary>
    /// 是否使用父流程的文档
    /// </summary>
    public final boolean getOfficeIsParent()
    {
            return this.GetValBooleanByKey(ToolbarExcelAttr.OfficeIsParent);
        }
    public final void setOfficeIsParent(String value)
        {
            this.SetValByKey(ToolbarExcelAttr.OfficeIsParent, value);
        }
    
    /// <summary>
    /// 是否启用标签
    /// </summary>
    public final String getOfficeDownLab()
    {
            return this.GetValStringByKey(ToolbarExcelAttr.OfficeDownLab);
        }
    public final void setOfficeDownLab(String value)
        {
            this.SetValByKey(ToolbarExcelAttr.OfficeDownLab, value);
        }
    
    /// <summary>
    /// 下载
    /// </summary>
    public final boolean getOfficeDownEnable()
    {
            return this.GetValBooleanByKey(ToolbarExcelAttr.OfficeDownEnable);
        }
    public final void setOfficeDownEnable(String value)
        {
            this.SetValByKey(ToolbarExcelAttr.OfficeDownEnable, value);
        }
    /// <summary>
    /// 是否自动套红
    /// </summary>
    public final boolean getOfficeTHEnable()
    {
            return this.GetValBooleanByKey(ToolbarExcelAttr.OfficeTHEnable);
        }
    public final void setOfficeTHEnable(String value)
        {
            this.SetValByKey(ToolbarExcelAttr.OfficeTHEnable, value);
		
        }
    
    /// <summary>
    /// 套红模板
    /// </summary>
    public final String getOfficeTHTemplate()
    {
            return this.GetValStringByKey(ToolbarExcelAttr.OfficeTHTemplate);
        }
    public final void setOfficeTHTemplate(String value)
        {
            this.SetValByKey(ToolbarExcelAttr.OfficeTHTemplate, value);
        }

    //#region 构造方法
    /// <summary>
    /// ToolbarExcel功能控制区域
    /// </summary>
    public ToolbarExcel() { }
    /// <summary>
    /// ToolbarExcel功能控制
    /// </summary>
    /// <param name="no">表单ID</param>
    public ToolbarExcel(String no)
    {
        this.setNo(no);
        this.Retrieve();
    }
    /// <summary>
    /// 重写基类方法 
    /// </summary>
    public Map getEnMap()
    {
            if (this.get_enMap() != null)
                return this.get_enMap();

            Map map = new Map("Sys_MapData");
            map.setEnDesc("ToolbarExcel功能控制");

            map.setDepositaryOfEntity(Depositary.Application);
            map.setDepositaryOfMap(Depositary.Application);

            map.AddTBStringPK(MapDataAttr.No, null, "表单编号", true, false, 1, 20, 20);
            map.AddTBString(MapDataAttr.Name, null, "表单名称", true, false, 0, 500, 20);

            //#region 公文按钮
            map.AddTBString(ToolbarExcelAttr.OfficeOpenLab, "打开本地", "打开本地标签", true, false, 0, 50, 10);
            map.AddBoolean(ToolbarExcelAttr.OfficeOpenEnable, false, "是否启用", true, true);

            map.AddTBString(ToolbarExcelAttr.OfficeOpenTemplateLab, "打开模板", "打开模板标签", true, false, 0, 50, 10);
            map.AddBoolean(ToolbarExcelAttr.OfficeOpenTemplateEnable, false, "是否启用", true, true);

            map.AddTBString(ToolbarExcelAttr.OfficeSaveLab, "保存", "保存标签", true, false, 0, 50, 10);
            map.AddBoolean(ToolbarExcelAttr.OfficeSaveEnable, true, "是否启用", true, true);

            map.AddTBString(ToolbarExcelAttr.OfficeAcceptLab, "接受修订", "接受修订标签", true, false, 0, 50, 10);
            map.AddBoolean(ToolbarExcelAttr.OfficeAcceptEnable, false, "是否启用", true, true);

            map.AddTBString(ToolbarExcelAttr.OfficeRefuseLab, "拒绝修订", "拒绝修订标签", true, false, 0, 50, 10);
            map.AddBoolean(ToolbarExcelAttr.OfficeRefuseEnable, false, "是否启用", true, true);

            map.AddTBString(ToolbarExcelAttr.OfficeOverLab, "套红按钮", "套红按钮标签", true, false, 0, 50, 10);
            map.AddBoolean(ToolbarExcelAttr.OfficeOverEnable, false, "是否启用", true, true);

            map.AddBoolean(ToolbarExcelAttr.OfficeMarksEnable, true, "是否查看用户留痕", true, true);

            map.AddTBString(ToolbarExcelAttr.OfficePrintLab, "打印按钮", "打印按钮标签", true, false, 0, 50, 10);
            map.AddBoolean(ToolbarExcelAttr.OfficePrintEnable, false, "是否启用", true, true);

            map.AddTBString(ToolbarExcelAttr.OfficeSealLab, "签章按钮", "签章按钮标签", true, false, 0, 50, 10);
            map.AddBoolean(ToolbarExcelAttr.OfficeSealEnable, false, "是否启用", true, true);

            map.AddTBString(ToolbarExcelAttr.OfficeInsertFlowLab, "插入流程", "插入流程标签", true, false, 0, 50, 10);
            map.AddBoolean(ToolbarExcelAttr.OfficeInsertFlowEnable, false, "是否启用", true, true);

            map.AddBoolean(ToolbarExcelAttr.OfficeNodeInfo, false, "是否记录节点信息", true, true);
            map.AddBoolean(ToolbarExcelAttr.OfficeReSavePDF, false, "是否该自动保存为PDF", true, true);

            map.AddTBString(ToolbarExcelAttr.OfficeDownLab, "下载", "下载按钮标签", true, false, 0, 50, 10);
            map.AddBoolean(ToolbarExcelAttr.OfficeDownEnable, false, "是否启用", true, true);

            map.AddBoolean(ToolbarExcelAttr.OfficeIsMarks, true, "是否进入留痕模式", true, true);
            map.AddTBString(ToolbarExcelAttr.OfficeTemplate, "", "指定文档模板", true, false, 0, 100, 10);
            map.AddBoolean(ToolbarExcelAttr.OfficeIsParent, true, "是否使用父流程的文档", true, true);

            map.AddBoolean(ToolbarExcelAttr.OfficeTHEnable, false, "是否自动套红", true, true);
            map.AddTBString(ToolbarExcelAttr.OfficeTHTemplate, "", "自动套红模板", true, false, 0, 200, 10);
            //#endregion

            this.set_enMap(map);
            return this.get_enMap();
        
    }
    //#endregion
}

package BP.Sys;

import BP.En.EntitiesNoName;
import BP.En.Entity;

/// <summary>
/// ToolbarExcel表单.   
/// </summary>
public class ToolbarExcels extends EntitiesNoName
{
    /// <summary>
    /// 功能控制
    /// </summary>
    public ToolbarExcels()
    {
    }
    /// <summary>
    /// 得到它的 Entity 
    /// </summary>
    public Entity getGetNewEntity()
    {
           return new ToolbarExcel();
    }
}

package BP.WF.XML;

import java.io.File;
import java.util.ArrayList;

import BP.En.Entities;
import BP.Sys.SystemConfig;
import BP.XML.XmlEn;
import BP.XML.XmlEns;

/**
 * 事件源s
 * 
 */
public class EventSources extends XmlEns {
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #region 构造
	public static ArrayList<EventSource> convertEventSources(Object obj) {
		return (ArrayList<EventSource>) obj;
	}

	/**
	 * 事件源s
	 * 
	 */
	public EventSources() {
	}
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #endregion

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #region 重写基类属性或方法。
	/**
	 * 得到它的 Entity
	 * 
	 */
	@Override
	public XmlEn getGetNewEntity() {
		return new EventSource();
	}

	@Override
	public String getFile() {
		return SystemConfig.getPathOfXML() + File.separator + "EventList.xml";
	}

	/**
	 * 物理表名
	 * 
	 */
	@Override
	public String getTableName() {
		return "Source";
	}

	@Override
	public Entities getRefEns() {
		return null;
	}
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #endregion
}
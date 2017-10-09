package BP.Sys.XML;

import java.io.File;
import java.util.ArrayList;

import BP.En.Entities;
import BP.Sys.SystemConfig;
import BP.XML.XmlEn;
import BP.XML.XmlEns;

/** 
 
 
*/
public class RegularExpressionDtls extends XmlEns {

	public static ArrayList<RegularExpressionDtl> convertRegularExpressionDtls(Object obj) {
		return (ArrayList<RegularExpressionDtl>) obj;
	}

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #region 构造
	/**
	 * 考核率的数据元素
	 * 
	 */
	public RegularExpressionDtls() {
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
		return new RegularExpressionDtl();
	}

	@Override
	public String getFile() {
		return SystemConfig.getPathOfData() + File.separator + "XML" + File.separator + "RegularExpression.xml";
	}

	/**
	 * 物理表名
	 * 
	 */
	@Override
	public String getTableName() {
		return "Dtl";
	}

	@Override
	public Entities getRefEns() {
		return null;
	}
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #endregion

}
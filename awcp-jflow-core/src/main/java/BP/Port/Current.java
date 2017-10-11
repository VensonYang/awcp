package BP.Port;

import cn.org.awcp.core.utils.SessionUtils;

public class Current {
	static {
		Session = new java.util.Hashtable();
	}
	public static java.util.Hashtable Session;

	public static void SetSession(Object key, Object Value) {
		if (Session.containsKey(key)) {
			SessionUtils.getCurrentSession().removeAttribute(key);
		}
		SessionUtils.getCurrentSession().setAttribute(key, Value);
	}

	public static String GetSessionStr(Object key, String isNullAsValue) {
		Object val = SessionUtils.getCurrentSession().getAttribute(key);
		if (val == null) {
			return isNullAsValue;
		}
		return (String) ((val instanceof String) ? val : null);
		// if (Session.ContainsKey(key))
		// {
		// Session.Remove(key);
		// }
		// Session.Add(key, Value);
	}
}
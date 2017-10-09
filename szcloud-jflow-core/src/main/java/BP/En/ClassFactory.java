package BP.En;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import BP.Sys.EventBase;
import BP.Sys.SystemConfig;
import BP.Tools.StringHelper;

/**
 * ClassFactory 的摘要说明。
 */
public class ClassFactory {
	/**
	 * 日志对象
	 */
	private static final Logger logger = LoggerFactory.getLogger(ClassFactory.class);
	static {
		/*
		 * warning String path = AppDomain.CurrentDomain.BaseDirectory;
		 */
		String path = TL.ConvertTools.getPorjectPath();
		File file = new File(path + "bin" + File.separator);
		if (file.exists()) {
			String ccFlowAppPath = SystemConfig.getAppSettings().get("CCFlowAppPath").toString();
			file = new File(path + ccFlowAppPath + "bin" + File.separator);
			if (!StringHelper.isNullOrEmpty(ccFlowAppPath) && file.exists()) {
				_BasePath = path + ccFlowAppPath + "bin" + File.separator;
			} else {
				_BasePath = path + "bin" + File.separator;
			}
		} else {
			_BasePath = path;
		}
	}
	private static String _BasePath = null;

	public static String getBasePath() {
		String installPath = SystemConfig.getAppSettings().get("InstallPath").toString();
		if (_BasePath == null) {
			if (installPath == null) {
				_BasePath = "D:" + File.separator;
			} else {
				_BasePath = installPath;
			}
		}
		return _BasePath;
	}

	public static java.util.Hashtable Htable_Evbase;

	/**
	 * 得到一个事件实体
	 * 
	 * @param className
	 *            类名称
	 * @return BP.Sys.EventBase
	 */
	public static BP.Sys.EventBase GetEventBase(String className) {
		if (Htable_Evbase == null || Htable_Evbase.isEmpty()) {
			Htable_Evbase = new java.util.Hashtable();
			String cl = "BP.Sys.EventBase";
			java.util.ArrayList al = ClassFactory.GetObjects(cl);
			Htable_Evbase.clear();
			for (Object en : al) {
				try {
					Htable_Evbase.put(((EventBase) en).getClass().getName(), en);
				} catch (java.lang.Exception e) {
				}
			}
		}
		BP.Sys.EventBase ens = (EventBase) ((Htable_Evbase.get(className) instanceof EventBase)
				? Htable_Evbase.get(className) : null);
		return ens;
	}

	/**
	 * 根据一个抽象的基类，取出此系统中从他上面继承的子类集合。 非抽象的类。
	 * 
	 * @param baseEnsName
	 *            抽象的类名称
	 * @return ArrayList
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList GetObjects(String baseEnsName) {

		JarFile jar;
		String jarPath = "";
		try {
			Class clazzA = Class.forName(baseEnsName);
			ArrayList arr = new ArrayList();
			jarPath = clazzA.getProtectionDomain().getCodeSource().getLocation().getFile();
			logger.debug("++++++++++++++++++++++++++++++++++++++++" + jarPath);
			File file = new File(jarPath);
			jar = new JarFile(file);
			Enumeration<JarEntry> entries = jar.entries();

			// 打印JAR文件中的所有目录名和文件名
			while (entries.hasMoreElements()) {
				JarEntry o = entries.nextElement();
				if (o.getName().lastIndexOf(".class") >= 0 && o.getName().indexOf("BP/") >= 0
						&& o.getName().indexOf("$") < 0) {
					String classBStr = o.getName().replace("/", ".").replace(".class", "");
					Class classB = Class.forName(classBStr);

					if (clazzA.isAssignableFrom(classB) && !classBStr.equals(baseEnsName)
							&& !Modifier.isAbstract(classB.getModifiers())) {
						Object obj;
						try {
							obj = classB.newInstance();
							arr.add(obj);
						} catch (InstantiationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			return arr;
		} catch (IOException e) {
			logger.debug("++++++++++++++++++++++++++++++++++++++++" + jarPath);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			logger.debug("++++++++++++++++++++++++++++++++++++++++" + jarPath);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	private static Hashtable<String, Object> Htable_En;

	/**
	 * 得到一个实体
	 * 
	 * @param className
	 *            类名称
	 * @return En
	 */
	public static Entity GetEn(String className) {
		if (Htable_En == null) {
			Htable_En = new Hashtable<String, Object>();
			String cl = "BP.En.EnObj";
			ArrayList al = ClassFactory.GetObjects(cl);
			for (Object en : al) {
				Htable_En.put(en.getClass().getName(), en);
			}
		}
		Object tmp = Htable_En.get(className);
		return ((Entity) ((tmp instanceof Entity) ? tmp : null));
	}

	private static Hashtable<String, Object> Htable_Method;

	/*
	 * 得到一个实体
	 * 
	 * @param className 类名称
	 * 
	 * @return En
	 */
	public static Method GetMethod(String className) {
		if (Htable_Method == null) {
			Htable_Method = new Hashtable();
			String cl = "BP.En.Method";
			ArrayList<Method> al = ClassFactory.GetObjects(cl);
			for (Method en : al) {
				Htable_Method.put(en.getClass().getName(), en);
			}
		}
		Object tmp = Htable_Method.get(className);
		return ((BP.En.Method) ((tmp instanceof BP.En.Method) ? tmp : null));
	}

	public static Hashtable<String, Object> Htable_Ens;

	/**
	 * 得到一个实体
	 * 
	 * @param className
	 *            类名称
	 * @return En
	 */
	public static Entities GetEns(String className) {
		if (!className.contains(".")) {
			BP.Sys.GEEntitys myens = new BP.Sys.GEEntitys(className);
			return myens;
		}

		if (Htable_Ens == null || Htable_Ens.isEmpty()) {
			Htable_Ens = new Hashtable<String, Object>();
			String cl = "BP.En.Entities";
			ArrayList al = ClassFactory.GetObjects(cl);

			Htable_Ens.clear();
			for (Object en : al) {
				try {
					Htable_Ens.put(en.getClass().getName(), en);
				} catch (java.lang.Exception e) {
				}
			}
		}
		Entities ens = (Entities) ((Htable_Ens.get(className) instanceof Entities) ? Htable_Ens.get(className) : null);

		return ens;
	}

	public static Hashtable<String, Object> Htable_XmlEns;

	/**
	 * 得到一个实体
	 * 
	 * @param className
	 *            类名称
	 * @return En
	 */
	public static BP.XML.XmlEns GetXmlEns(String className) {
		if (Htable_XmlEns == null) {
			Htable_XmlEns = new Hashtable<String, Object>();
			String cl = "BP.XML.XmlEns";
			ArrayList al = ClassFactory.GetObjects(cl);
			for (Object en : al) {
				Htable_XmlEns.put(en.getClass().getName(), en);
			}
		}
		Object tmp = Htable_XmlEns.get(className);
		return ((BP.XML.XmlEns) ((tmp instanceof BP.XML.XmlEns) ? tmp : null));
	}

	public static Hashtable<String, Object> Htable_XmlEn;

	/**
	 * 得到一个实体
	 * 
	 * @param className
	 *            类名称
	 * @return En
	 */
	public static BP.XML.XmlEn GetXmlEn(String className) {
		if (Htable_XmlEn == null) {
			Htable_XmlEn = new Hashtable<String, Object>();
			String cl = "BP.XML.XmlEn";
			ArrayList al = ClassFactory.GetObjects(cl);
			for (Object en : al) {
				Htable_XmlEn.put(en.getClass().getName(), en);
			}
		}
		Object tmp = Htable_XmlEn.get(className);
		return ((BP.XML.XmlEn) ((tmp instanceof BP.XML.XmlEn) ? tmp : null));
	}

	public static String getClassPath() {
		String path = Thread.currentThread().getContextClassLoader().getResource("").toString();
		path = path.substring(path.indexOf(File.separator) + 1);
		int i = 0;
		while (path.indexOf(File.separator) != -1 && path.length() > 0) {
			path = path.substring(0, path.lastIndexOf(File.separator));
			if (1 == i) {

				String service = SystemConfig.getAppSettings().get("Service").toString().toLowerCase();
				if (service.equals("tomcat")) {
					path += System.getProperty("file.separator") + "lib" + System.getProperty("file.separator");
					return path;
				} else if (service.equals("jetty")) {
					path += System.getProperty("file.separator");// +"lib"+
																	// System.getProperty("file.separator");
					return path;
				}
			}
			i++;
		}
		logger.debug(path);
		return path.substring(path.indexOf("/") + 1, path.lastIndexOf("/") - 7);
	}
}
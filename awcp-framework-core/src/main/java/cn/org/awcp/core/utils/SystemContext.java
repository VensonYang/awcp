package cn.org.awcp.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemContext {
	/**
	 * 日志对象
	 */
	private static final Logger logger = LoggerFactory.getLogger(SystemContext.class);
	private static final SystemContext instance = new SystemContext();
	private static final String DEFAULT_CONFIG = "sys.ini";
	/** 系统上下文属性 **/
	private Properties props;
	/** 系统上下文用的对象 **/
	private Map<String, Object> objPool;

	private SystemContext() {
		this.objPool = new HashMap<String, Object>();
	}

	public static SystemContext getInstance() {
		return instance;
	}

	public Properties getProperties() {
		return this.props;
	}

	public void initConext(Properties context) {
		this.props = context;
	}

	/**
	 * 初始化系统上下文
	 * 
	 * @param fileName
	 * @throws Exception
	 */
	public void initConext(String fileName) throws Exception {
		if (fileName == null) {
			fileName = DEFAULT_CONFIG;
		}
		this.initConext(new File(fileName));
	}

	/**
	 * 初始化系统上下文
	 * 
	 * @param file
	 * @throws Exception
	 */
	public void initConext(File file) throws Exception {
		if (file == null) {
			file = new File(DEFAULT_CONFIG);
		}

		logger.debug("init config-file[" + file.getPath() + "] ...");
		if (!file.exists()) {
			logger.debug("config-file[" + file.getPath() + "] not exist!");
			throw new Exception("config-file[" + file.getPath() + "] not exist!");
		}

		InputStream in = new FileInputStream(file);
		Properties prop = new Properties();
		prop.load(in);
		in.close();
		this.props = prop;
		logger.debug("init config-file finish!");
	}

	/**
	 * 设值方法
	 * 
	 * @param key
	 * @param value
	 */
	public synchronized void setValue(String key, String value) {
		if (this.props == null)
			this.props = new Properties();
		this.props.setProperty(key, value);
	}

	public String getValue(String key) {
		String value = props.getProperty(key);
		return value;
	}

	public String getValue(String key, String defaultValue) {
		String value = props.getProperty(key);
		if (value == null) {
			value = defaultValue;
		}
		return value;
	}

	public int getInt(String key) {
		String value = props.getProperty(key);
		return Integer.valueOf(value).intValue();
	}

	public int getInt(String key, int defaultValue) {
		String value = props.getProperty(key);
		if (value != null && value.trim().length() > 0) {
			defaultValue = Integer.parseInt(value.trim());
		}
		return defaultValue;
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		String value = props.getProperty(key);
		if (value != null && value.trim().length() > 0) {
			defaultValue = Boolean.parseBoolean(value.trim());
		}
		return defaultValue;
	}

	public void setObject(String key, Object o) {
		this.objPool.put(key, o);
	}

	public Object getObject(String key) {
		return this.objPool.get(key);
	}
}
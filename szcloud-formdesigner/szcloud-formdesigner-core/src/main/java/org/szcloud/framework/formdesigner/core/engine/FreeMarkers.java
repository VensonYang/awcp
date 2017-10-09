package org.szcloud.framework.formdesigner.core.engine;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import com.jagregory.shiro.freemarker.ShiroTags;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMarkers {
	/**
	 * 日志对象
	 */
	private static final Logger logger = LoggerFactory.getLogger(FreeMarkers.class);
	private static Configuration configuration = new Configuration();

	static {
		configuration.setDefaultEncoding("UTF-8");
		configuration.setDateFormat("yyyy-MM-dd HH:mm:ss");
		configuration.setNumberFormat("#0.#");
		configuration.setSharedVariable("shiro", new ShiroTags());
		try {
			File file = new File(FreeMarkers.class.getResource("/").getFile());
			configuration.setDirectoryForTemplateLoading(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String renderString(String templateString, Map<String, ?> model) {
		try {
			StringWriter result = new StringWriter();
			Template t = new Template("name", new StringReader(templateString), configuration);
			t.process(model, result);
			return result.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String renderTemplate(Template template, Object model) {
		try {
			StringWriter result = new StringWriter();
			template.process(model, result);
			return result.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Configuration buildConfiguration(String directory) throws IOException {
		Configuration cfg = new Configuration();
		Resource path = new DefaultResourceLoader().getResource(directory);
		cfg.setDirectoryForTemplateLoading(path.getFile());
		return cfg;
	}

	public static void main(String args[]) {
		ScriptEngineManager manager = new ScriptEngineManager();
		for (ScriptEngineFactory factory : manager.getEngineFactories()) {
			// 打印脚本信息
			logger.debug("Name: " + factory.getEngineName() + "Language version: " + factory.getLanguageVersion()
					+ "Extensions:  " + factory.getExtensions() + "Mime types:  " + factory.getMimeTypes() + "Names: "
					+ factory.getNames().toString() + "\n");
		}
	}
}

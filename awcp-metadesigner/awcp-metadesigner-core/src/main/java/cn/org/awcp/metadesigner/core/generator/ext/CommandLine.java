package cn.org.awcp.metadesigner.core.generator.ext;

import java.io.File;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.org.awcp.metadesigner.core.generator.GeneratorFacade;
import cn.org.awcp.metadesigner.core.generator.GeneratorProperties;
import cn.org.awcp.metadesigner.core.generator.util.ArrayHelper;
import cn.org.awcp.metadesigner.core.generator.util.StringHelper;
import cn.org.awcp.metadesigner.core.generator.util.SystemHelper;

/**
 * 命令行工具类,可以直接运行
 * 
 * @author caoyong
 */
public class CommandLine {
	/**
	 * 日志对象
	 */
	private static final Logger logger = LoggerFactory.getLogger(CommandLine.class);

	public static void main(String[] args) throws Exception {
		freemarker.log.Logger.selectLoggerLibrary(freemarker.log.Logger.LIBRARY_NONE);
		startProcess();
	}

	private static void startProcess() throws Exception {
		Scanner sc = new Scanner(System.in);
		logger.debug("templateRootDir:" + new File(getTemplateRootDir()).getAbsolutePath());
		printUsages();
		while (sc.hasNextLine()) {
			try {
				processLine(sc);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				Thread.sleep(700);
				printUsages();
			}
		}
	}

	private static void processLine(Scanner sc) throws Exception {
		GeneratorFacade facade = new GeneratorFacade(null, null);
		String cmd = sc.next();
		if ("gen".equals(cmd)) {
			String[] args = nextArguments(sc);
			if (args.length == 0)
				return;
			facade.g.setIncludes(getIncludes(args, 1));
			facade.generateByTable(args[0], getTemplateRootDir());
			if (SystemHelper.isWindowsOS) {
				Runtime.getRuntime().exec(
						"cmd.exe /c start " + GeneratorProperties.getRequiredProperty("outRoot").replace('/', '\\'));
			}
		} else if ("del".equals(cmd)) {
			String[] args = nextArguments(sc);
			if (args.length == 0)
				return;
			facade.g.setIncludes(getIncludes(args, 1));
			facade.deleteByTable(args[0], getTemplateRootDir());
		} else if ("quit".equals(cmd)) {
			System.exit(0);
		} else {
			System.err.println(" [ERROR] unknow command:" + cmd);
		}
	}

	private static String getIncludes(String[] args, int i) {
		String includes = ArrayHelper.getValue(args, i);
		if (includes == null)
			return null;
		return includes.indexOf("*") >= 0 || includes.indexOf(",") >= 0 ? includes : includes + "/**";
	}

	private static String getTemplateRootDir() {
		return System.getProperty("templateRootDir", "template");
	}

	private static void printUsages() {
		logger.debug("Usage:");
		logger.debug("\tgen table_name [include_path]: generate files by table_name");
		logger.debug("\tdel table_name [include_path]: delete files by table_name");
		logger.debug("\tgen * [include_path]: search database all tables and generate files");
		logger.debug("\tdel * [include_path]: search database all tables and delete files");
		logger.debug("\tquit : quit");
		logger.debug("\t[include_path] subdir of templateRootDir,example: 1. dao  2. dao/**,service/**");
		logger.debug("please input command:");
	}

	private static String[] nextArguments(Scanner sc) {
		return StringHelper.tokenizeToStringArray(sc.nextLine(), " ");
	}
}

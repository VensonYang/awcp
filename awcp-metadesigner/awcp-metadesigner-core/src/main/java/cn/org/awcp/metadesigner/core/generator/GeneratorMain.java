package cn.org.awcp.metadesigner.core.generator;

import java.io.File;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author caoyong
 */
public class GeneratorMain {
	/**
	 * 日志对象
	 */
	private static final Logger logger = LoggerFactory.getLogger(GeneratorMain.class);

	/**
	 * 请直接修改以下代码调用不同的方法以执行相关生成任务.
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static void StartGenerator(Map map) throws Exception {
		GeneratorFacade g = new GeneratorFacade(map.get("outRoot").toString(),
				new File(map.get("templementSrc").toString()));
		g.setBasePackage(map.get("basePackage").toString());
		g.setPersistence(map.get("persistence").toString());
		// g.printAllTableNames(); //打印数据库中的表名称
		String str = (String) map.get("tableName");// 获取表名
		logger.debug(str);
		String[] tableName = str.split(",");// 分割表名
		// g.deleteOutRootDir(); // 删除生成器的输出目录

		for (int i = 0; i < tableName.length; i++) {
			logger.debug(tableName[i]);
			if (map.get("templementSrc").toString().equals("")) {
				g.generateByTable(
						// 表名和模板路径
						tableName[i],
						// 如果测试，要修改模板的默认路径
						"C:\\Users\\Administrator\\Desktop\\新建文件夹\\szcloud-framewrok\\szcloud-metadesigner\\szcloud-metadesigner-core\\src\\main\\resources\\template\\jdbc"); // 通过数据库表生成文件,template为模板的根目录
			} else {
				g.generateByTable(// 表名和模板路径
						tableName[i], map.get("templementSrc").toString()); // 通过数据库表生成文件,template为模板的根目录
			}
			// g.generateByAllTable("template");
			// //自动搜索数据库中的所有表并生成文件,template为模板的根目录
			// g.generateByClass(Blog.class,"template_clazz");

			// g.deleteByTable("table_name", "template"); //删除生成的文件
			// 打开文件夹
		}
		Runtime.getRuntime().exec("cmd.exe /c start " + map.get("outRoot").toString());// 输出路径
	}
}

package BP.DA;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import BP.Tools.StringHelper;

public class DataSet {
	/**
	 * 日志对象
	 */
	private static final Logger logger = LoggerFactory.getLogger(DataSet.class);
	private String name;

	public List<DataTable> Tables;

	public Hashtable<String, DataTable> hashTables;

	private XmlWriteMode xmlWriteMode = XmlWriteMode.IgnoreSchema;

	public DataSet() {
		if (Tables == null) {
			Tables = new ArrayList<DataTable>();
			hashTables = new Hashtable<String, DataTable>();
		}
	}

	public DataSet(String name) {
		if (Tables == null) {
			Tables = new ArrayList<DataTable>();
			hashTables = new Hashtable<String, DataTable>();
		}
		this.name = name;
	}

	/**
	 * DataSet 以xml形式写入文件
	 * 
	 * @param file
	 */
	public void WriteXml(String file) {
		// WriteXml(file, XmlWriteMode.IgnoreSchema);
	}

	/**
	 * DataSet 以xml形式写入文件
	 * 
	 * @param path
	 * @param mode
	 *            暂不支持DiffGram格式
	 */
	public void WriteXml(String path, XmlWriteMode mode, DataSet ds) {
		StringBuilder str = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		str.append("<NewDataSet>");
		// 输出表架构
		// if(mode == XmlWriteMode.WriteSchema){
		for (int i = 0; i < ds.Tables.size(); i++) {
			DataTable dt = ds.Tables.get(i);
			for (int k = 0; k < dt.Rows.size(); k++) {
				str.append("<");
				str.append(dt.TableName);
				str.append(">");
				for (int j = 0; j < dt.Columns.size(); j++) {
					DataColumn dc = dt.Columns.get(j);

					str.append("<");
					str.append(dc.ColumnName);
					str.append(">");
					try {
						str.append(dt.Rows.get(i).getValue(dc));
					} catch (Exception e) {

					}
					str.append("</");
					str.append(dc.ColumnName);
					str.append(">");

				}
				str.append("</");
				str.append(dt.TableName);
				str.append(">");
			}
		}
		// }
		str.append("</NewDataSet>");
		// String str=this.ConvertDataSetToXml(ds);
		logger.debug(str.toString());
		// 写入文件
		File file = new File(path);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			BufferedWriter br = new BufferedWriter(osw);
			br.write(str.toString());
			fos.flush();
			br.close();
			fos.close();
			osw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readXmls(String xmlPath) throws Exception {
		if (StringHelper.isNullOrEmpty(xmlPath)) {
			return;
		}
		XMLWriter writer = null;// 声明写XML的对象
		SAXReader reader = new SAXReader();

		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");// 设置XML文件的编码格式

		File file = new File(xmlPath);
		// DataSet ds=new DataSet();
		if (file.exists()) {
			Document document = reader.read(file);// 读取XML文件
			Element root = document.getRootElement();// 得到根节点
			for (Iterator i = root.elementIterator(); i.hasNext();) {
				Element e = (Element) i.next();
				boolean type = false;
				for (int k = 0; k < this.Tables.size(); k++) {
					if (this.Tables.get(k).TableName.equals(e.getName())) {
						DataTable dt = this.Tables.get(k);
						DataRow dr = dt.NewRow();
						DataColumn dc = null;
						for (Iterator j = e.elementIterator(); j.hasNext();) {
							Element cn = (Element) j.next();
							dc = new DataColumn(cn.getName());
							dr.setValue(dc, cn.getText());
						}
						dt.Columns.Add(dc);
						dt.Rows.add(dr);
						// this.Tables.add(dt);
						// this.hashTables.put(e.getName(), dt);
						type = true;
						break;
					}
				}
				if (type) {
					continue;
				}
				DataTable dt = new DataTable(e.getName());
				DataRow dr = dt.NewRow();
				DataColumn dc = null;
				for (Iterator j = e.elementIterator(); j.hasNext();) {
					Element cn = (Element) j.next();
					dc = new DataColumn(cn.getName());
					dt.Columns.Add(dc);
					dr.setValue(dc, cn.getText());
				}
				dt.Rows.add(dr);
				this.Tables.add(dt);
				this.hashTables.put(e.getName(), dt);
			}
		}
	}

	public void readXmlm(String xml) {
		if (StringHelper.isNullOrEmpty(xml))
			return;
		try {
			xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n <NewDataSets>" + xml + "</NewDataSets>";
			// 创建xml解析对象
			SAXReader reader = new SAXReader();
			// 定义一个文档
			Document document = null;
			// 将字符串转换为
			document = reader.read(new ByteArrayInputStream(xml.getBytes("UTF-8")));
			@SuppressWarnings("unchecked")
			List<Element> elements = document.selectNodes("//NewDataSets/NewDataSet");

			int i = 0;
			DataTable oratb = new DataTable();
			for (Element element : elements) {
				DataRow dr = oratb.NewRow();
				@SuppressWarnings("unchecked")
				Iterator<Element> iter = element.elementIterator();
				int j = 0;
				while (iter.hasNext()) {
					Element itemEle = (Element) iter.next();
					if (i == 0) {
						oratb.Columns.Add(itemEle.getName());
					}
					dr.setValue(j, itemEle.getTextTrim());
					j++;
				}
				oratb.Rows.add(dr);
				i++;
			}
			this.Tables.add(oratb);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DataTable> getTables() {
		return Tables;
	}

	public void setTables(List<DataTable> tables) {
		Tables = tables;
	}

	public Hashtable<String, DataTable> getHashTables() {
		return hashTables;
	}

	public void setHashTables(Hashtable<String, DataTable> hashTables) {
		this.hashTables = hashTables;
	}

	// public static void main(String[] args) {
	// DataSet set = new DataSet();
	// String xml =
	// "<NewDataSet><Table><DATAID>1</DATAID>
	// <XTSJBH>1</XTSJBH><CODE>01</CODE><YYBH>000000000000000001715130000618</YYBH><ZT>1000000000</ZT>"
	// +
	// "<ID>94899</ID><XM>徐宁</XM><CSNY>1983-06-02T00:00:00+08:00</CSNY><JG>411302</JG><MZ>01</MZ><CJGZSJ>2007-09-01T00:00:00+08:00</CJGZSJ><JRBXTGZSJ>2007-09-01T00:00:00+08:00</JRBXTGZSJ></Table></NewDataSet>";
	//
	// set.readXml(xml);
	// logger.debug("sssssssssss");
	// }

	public void writeXml(String string) throws Exception {
		throw new Exception("");

	}

	public String xmlToString(String path) {
		String line = null;
		StringBuffer strBuffer = new StringBuffer();
		try {
			String encoding = "UTF-8"; // 字符编码(可解决中文乱码问题 )
			File file = new File(path);
			if (file.isFile() && file.exists()) {
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				while ((line = bufferedReader.readLine()) != null) {
					strBuffer.append(line + "\n");
				}
				read.close();
			} else {
				logger.debug("找不到指定的文件！");
			}
		} catch (Exception e) {
			logger.debug("读取文件内容操作出错");
			e.printStackTrace();
		}
		return strBuffer.toString();
	}

	public void readXml(String xmlpath) {
		if (StringHelper.isNullOrEmpty(xmlpath))
			return;
		try {
			String xml = xmlToString(xmlpath);
			// 创建xml解析对象
			SAXReader reader = new SAXReader();
			// 定义一个文档
			Document document = null;
			// 将字符串转换为
			document = reader.read(new ByteArrayInputStream(xml.getBytes("UTF-8")));
			@SuppressWarnings("unchecked")
			Element ele = document.getRootElement();
			String name = "";
			for (Iterator e = ele.elementIterator(); e.hasNext();) {
				Element el = (Element) e.next();
				if (!el.getName().equals(name)) {
					name = el.getName();
					List<Element> elements = document.selectNodes("//NewDataSet/" + name);
					DataTable oratb = new DataTable();
					for (Element element : elements) {
						DataRow dr = oratb.NewRow();
						List attrList = element.attributes();
						for (int i = 0; i < attrList.size(); i++) {
							// 属性的取得
							Attribute item = (Attribute) attrList.get(i);
							// if (!isContains(oratb.Columns.subList(0,
							// oratb.Columns.size()), item.getName())) {
							if (!isContains(oratb.Columns.subList(0, oratb.Columns.size()),
									item.getName().toLowerCase())) {

								oratb.Columns.Add(item.getName());
							}
							dr.setValue(item.getName(), item.getValue());

						}
						if (dr.getValue("Enable") == null) {
							dr.setValue("Enable", "");
						}
						oratb.Rows.add(dr);
					}
					this.hashTables.put(name, oratb);
					this.Tables.add(oratb);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isContains(List<DataColumn> dcList, String column) {
		for (DataColumn dc : dcList) {
			if (dc.ColumnName.equals(column)) {
				return true;
			}
		}
		return false;
	}

	public static String ConvertDataSetToXml(DataSet dataSet) {
		if (dataSet != null) {
			String str = "<?xml version=\"1.0\" encoding=\"GBK\"?>";
			str += "<xs:schema id=\"NewDataSet\" xmlns=\"\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:msdata=\"urn:schemas-microsoft-com:xml-msdata\">";

			str += "<xs:element name=\"NewDataSet\" msdata:IsDataSet=\"true\" msdata:UseCurrentLocale=\"true\">";
			str += "<xs:complexType><xs:choice minOccurs=\"0\" maxOccurs=\"unbounded\">";
			// 循环每一个表的列

			for (int n = 0; n < dataSet.getTables().size(); n++) {
				str += "<xs:element name=\"" + dataSet.getTables().get(n).TableName + "\">";
				str += "<xs:complexType><xs:sequence>";
				DataTable table = dataSet.getTables().get(n);
				for (DataColumn col : table.Columns) {
					str += "<xs:element name=\"" + col.ColumnName;
					str += "\" type=\"";
					try {
						col.DataType.toString();
						str += IsType(col.DataType.toString());
					} catch (Exception e) {
						str += "xs:string";
					}
					str += "\" minOccurs=\"0\" />";
				}
				str += "</xs:sequence></xs:complexType></xs:element>";
			}
			str += "</xs:choice></xs:complexType></xs:element><_NewDataSet>";
			for (int i = 0; i < dataSet.getTables().size(); i++) {
				DataTable dt = dataSet.getTables().get(i);
				DataTable table = dataSet.getTables().get(i);
				for (int j = 0; j < table.Rows.size(); j++) {
					str += "<_" + i + ">";
					DataRow row = table.Rows.get(j);
					for (int a = 0; a < row.columns.size(); a++) {
						DataColumn col = table.Columns.get(a);
						str += "<_" + a + ">";
						if (row.getValue(col) == null) {
							str += "";
						} else {
							if (col.ColumnName.equals("icon")) {
								if (row.getValue(col).equals("")) {
									str += "审核";
								} else if (row.getValue(col).equals("Default")) {
									str += "审核";
								} else {
									str += row.getValue(col);
								}
							} else {
								str += row.getValue(col);
							}
						}
						str += "</_" + a + ">";

					}
					str += "</_" + i + ">";
				}
			}
			str += "</_NewDataSet>";
			str += "</xs:schema>";
			return str;
		}
		return null;
	}

	public static void main(String[] args) throws Exception {

		// DataSet set = new DataSet();
		// set.readXmls("D:/android
		// workspace/jflow/jflow-web/src/main/webapp/DataUser/Temp/TempleteSheetOfStartNode.xml");
		// List<DataTable> tableList = new ArrayList<DataTable>();
		// DataTable table = new DataTable("Emp");
		// DataColumn col = new DataColumn("id", Integer.class);
		// DataColumn col1 = new DataColumn("name", String.class);
		// DataColumn col2 = new DataColumn("sex", String.class);
		// DataColumn col3 = new DataColumn("age", Integer.class);
		// table.Columns.Add(col);
		// table.Columns.Add(col1);
		// table.Columns.Add(col2);
		// table.Columns.Add(col3);
		// DataRow dr1 = table.NewRow();
		// dr1.setValue(col, 1);
		// dr1.setValue(col1, "付强");
		// dr1.setValue(col2, "男");
		// dr1.setValue(col3, 21);
		// DataRow dr2 = table.NewRow();
		// dr2.setValue(col, 2);
		// dr2.setValue(col1, "熊伟");
		// dr2.setValue(col2, "男");
		// dr2.setValue(col3, 21);
		// table.Rows.add(dr1);
		// table.Rows.add(dr2);
		//
		// DataTable dept = new DataTable("dept");
		// DataColumn deptName = new DataColumn("name", String.class);
		// DataColumn deptDesc = new DataColumn("desc", String.class);
		// dept.Columns.Add(deptName);
		// dept.Columns.Add(deptDesc);
		// DataRow row = dept.NewRow();
		// row.setValue(deptName, "java开发部");
		// row.setValue(deptDesc, "开发");
		// dept.Rows.add(row);
		// DataRow row1 = dept.NewRow();
		// row1.setValue(deptName, ".net开发部");
		// row1.setValue(deptDesc, "开发");
		// dept.Rows.add(row1);
		//
		// tableList.add(table);
		// tableList.add(dept);
		// set.setTables(tableList);
		// logger.debug(ConvertDataSetToXml(set));

	}

	public static String IsType(String name) {
		if (name.equals("class java.lang.Integer")) {
			name = "xs:int";
		}
		if (name.equals("class java.lang.Long")) {
			name = "xs:long";
		}
		if (name.equals("class java.lang.Float")) {
			name = "xs:float";
		}
		if (name.equals("class java.lang.Double")) {
			name = "xs:double";
		}
		if (name.equals("class java.lang.String")) {
			name = "xs:string";
		}
		if (name.equals("class java.math.BigDecimal")) {
			name = "xs:int";
		}
		return name;
	}

}
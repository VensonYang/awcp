package BP.DA;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import BP.Tools.DealString;
import BP.Tools.Json;
import BP.Tools.StringExpressionCalculate;
import BP.Tools.StringHelper;
import BP.WF.Glo;

public class DataType {

	/// <summary>
	/// 获取两个时间之间的字符串表示形式，如：1天2时34分
	/// <para>added by liuxc,2014-12-4</para>
	/// </summary>
	/// <param name="t1">开始时间</param>
	/// <param name="t2">结束时间</param>
	/// <returns>返回：x天x时x分</returns>
	public static String GetSpanTime(Date t1, Date t2) {
		// 毫秒ms
		long diff = t2.getTime() - t1.getTime();

		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
		long diffDays = diff / (24 * 60 * 60 * 1000);

		// log.debug("两个时间相差：");
		// log.debug(diffDays + " 天, ");
		// log.debug(diffHours + " 小时, ");
		// log.debug(diffMinutes + " 分钟, ");
		// log.debug(diffSeconds + " 秒.");

		String spanStr = "";
		if (diffDays > 0)
			spanStr += diffDays + "天";

		if (diffHours > 0)
			spanStr += diffHours + "时";

		if (diffMinutes > 0)
			spanStr += diffMinutes + "分";

		if (spanStr.length() == 0)
			spanStr = "0分";

		return spanStr;
	}

	public static java.util.Date WeekOfMonday(java.util.Date dt) {
		Calendar monday = Calendar.getInstance();
		monday.setTime(dt);
		int FIRST_DAY_OF_WEEK = Calendar.MONDAY;
		monday.setFirstDayOfWeek(FIRST_DAY_OF_WEEK);
		monday.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return monday.getTime();
	}

	public static java.util.Date WeekOfSunday(java.util.Date dt) {
		Calendar cal = Calendar.getInstance();
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		int day = cal.get(Calendar.DAY_OF_WEEK);
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		cal.add(Calendar.DATE, 6);
		return cal.getTime();
	}

	/**
	 * 去掉周六日
	 * 
	 * @param dt
	 * @param days
	 * @return
	 */
	public static java.util.Date AddDays(java.util.Date dt, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int mod = days % 5;
		int other = days / 5 * 7;
		for (int i = 0; i < mod;) {
			cal.add(Calendar.DATE, 1);
			switch (cal.get(Calendar.DAY_OF_WEEK)) {
			case Calendar.SUNDAY:
			case Calendar.SATURDAY:
				break;
			default:
				i++;
				break;
			}
		}
		if (other > 0)
			cal.add(Calendar.DATE, other);
		return cal.getTime();
	}

	public static java.util.Date AddDays(String sysdt, int days) throws ParseException {
		java.util.Date dt = DataType.ParseSysDate2DateTime(sysdt);
		return AddDays(dt, days);
	}

	/**
	 * 取指定日期是一年中的第几周
	 * 
	 * @param dtime
	 *            给定的日期
	 * @return 数字 一年中的第几周
	 */
	public static int WeekOfYear(java.util.Date dtime) {
		Calendar c = Calendar.getInstance();
		c.setTime(dtime);
		int week_of_year = c.get(Calendar.WEEK_OF_YEAR);
		return week_of_year - 1;

	}

	public static String TurnToJiDuByDataTime(String dt) {
		if (dt.length() <= 6) {
			throw new RuntimeException("@要转化季度的日期格式不正确:" + dt);
		}
		String yf = dt.substring(5, 7);
		// C# TO JAVA CONVERTER NOTE: The following 'switch' operated on a
		// string member and was converted to Java 'if-else' logic:
		// switch (yf)
		// ORIGINAL LINE: case "01":
		if (yf.equals("01") || yf.equals("02") || yf.equals("03")) {
			return dt.substring(0, 4) + "-03";
		}
		// ORIGINAL LINE: case "04":
		else if (yf.equals("04") || yf.equals("05") || yf.equals("06")) {
			return dt.substring(0, 4) + "-06";
		}
		// ORIGINAL LINE: case "07":
		else if (yf.equals("07") || yf.equals("08") || yf.equals("09")) {
			return dt.substring(0, 4) + "-09";
		}
		// ORIGINAL LINE: case "10":
		else if (yf.equals("10") || yf.equals("11") || yf.equals("12")) {
			return dt.substring(0, 4) + "-12";
		} else {
		}
		return null;
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	/// #region Datatable转换为Json
	/**
	 * Datatable转换为Json
	 * 
	 * @param table
	 *            Datatable对象
	 * @return Json字符串
	 */
	public static String ToJson(DataTable dt) {
		StringBuilder jsonString = new StringBuilder();
		jsonString.append("[");
		DataRowCollection drc = dt.Rows;
		for (int i = 0; i < drc.size(); i++) {
			jsonString.append("{");
			for (int j = 0; j < dt.Columns.size(); j++) {
				String strKey = dt.Columns.get(j).ColumnName;
				// *小周鹏修改-2014/11/11----------------------------START*
				// BillNoFormat对应value:{YYYY}-{MM}-{dd}-{LSH4} Format时会产生异常。
				if (strKey.equals("BillNoFormat")) {
					continue;
				}
				// *小周鹏修改-2014/11/11----------------------------END*
				String strValue = drc.get(i).getValue(j).toString();
				// java.lang.Class type = dt.Columns.get(j).DataType;
				jsonString.append("\"" + strKey + "\":");

				// strValue = String.format(strValue, type);
				if (j < dt.Columns.size() - 1) {
					jsonString.append("\"" + strValue + "\",");
				} else {
					jsonString.append("\"" + strValue + "\"");
				}
			}
			jsonString.append("},");
		}
		jsonString.deleteCharAt(jsonString.length() - 1);
		jsonString.append("]");
		return jsonString.toString();
	}

	/**
	 * DataTable转换为Json
	 * 
	 */
	public static String ToJson(DataTable dt, String jsonName) {
		String jsonString = "";
		if (StringHelper.isNullOrEmpty(jsonName)) {
			jsonName = dt.TableName;
		}
		jsonString += "{\"" + jsonName + "\":" + Json.ToJson(dt) + "}";
		return jsonString;

		/*
		 * warning StringBuilder Json = new StringBuilder(); if
		 * (StringHelper.isNullOrEmpty(jsonName)) { jsonName = dt.TableName; }
		 * Json.append("{\"" + jsonName + "\":["); if (dt.Rows.size() > 0) { for
		 * (int i = 0; i < dt.Rows.size(); i++) { Json.append("{"); for (int j =
		 * 0; j < dt.Columns.size(); j++) { java.lang.Class type =
		 * dt.Rows[i][j].getClass(); Json.append("\"" +
		 * dt.Columns[j].ColumnName.toString() + "\":" +
		 * String.format(dt.Rows[i][j].toString(), type)); if (j <
		 * dt.Columns.size() - 1) { Json.append(","); } } Json.append("}"); if
		 * (i < dt.Rows.size() - 1) { Json.append(","); } } } Json.append("]}");
		 * return Json.toString();
		 */
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion

	/**
	 * 转换成MB
	 * 
	 * @param val
	 * @return
	 */
	public static float PraseToMB(long val) {
		try {
			// C# TO JAVA CONVERTER TODO TASK: The '#' format specifier is not
			// converted to Java:
			// ORIGINAL LINE: return float.Parse(String.Format("{0:##.##}", val
			// / 1048576));
			return Float.parseFloat(String.format("%0s", val / 1048576));
		} catch (java.lang.Exception e) {
			return 0;
		}
	}

	/**
	 * @param strs
	 * @param isNumber
	 * @return
	 */
	public static String PraseAtToInSql(String strs, boolean isNumber) {
		strs = strs.replace("@", "','");
		strs = strs + "'";
		strs = strs.substring(2);
		if (isNumber) {
			strs = strs.replace("'", "");
		}
		return strs;
	}

	/**
	 * 把内容里面的东西处理成超连接。
	 * 
	 * @param strContent
	 * @return
	 */
	public static String DealSuperLink(String doc) {
		if (doc == null) {
			return null;
		}
		return doc;
	}

	/**
	 * 写文件
	 * 
	 * @param file
	 *            路径
	 * @param Doc
	 *            内容
	 * @throws IOException
	 */
	public static void WriteFile(String file, String Doc) throws IOException {
		File fl = new File(file);

		if (fl.exists()) {
			fl.delete();
		}
		try {
			fl.createNewFile();
			FileWriter fw = new FileWriter(fl.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(Doc.trim());
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw e;
		}

	}

	/**
	 * 读取URL内容
	 * 
	 * @param url
	 *            要读取的url
	 * @param timeOut
	 *            超时时间
	 * @param encode
	 *            text code.
	 * @return 返回读取内容
	 */
	public static String ReadURLContext(String url, int timeOut) {
		String doc = "";
		try {
			// xiaozhoupeng 20150106 update Start
			if (!url.contains("http")) {
				String temp_url = url.substring(1, url.length()).replace(".aspx", ".jsp");
				url = Glo.getCCFlowAppPath() + temp_url;
			}
			// xiaozhoupeng 20150106 update End
			URL ul = new URL(url);
			java.net.HttpURLConnection conn = (java.net.HttpURLConnection) ul.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setReadTimeout(timeOut);
			java.io.BufferedReader in = new java.io.BufferedReader(
					new java.io.InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				doc += line;
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

	/*
	 * warning public static String ReadURLContext(String url, int timeOut,
	 * Encoding encode) throws IOException { HttpWebRequest webRequest = null;
	 * try { webRequest = (HttpWebRequest) WebRequest.Create(url);
	 * webRequest.Timeout = timeOut; String str =
	 * webRequest.Address.AbsoluteUri; str = str.substring(0,
	 * str.lastIndexOf("/")); } catch (RuntimeException ex) { try {
	 * BP.DA.Log.DefaultLogWriteLineWarning(""@读取URL出现错误:URL=" + url + "@错误信息："
	 * + ex.getMessage()); return null; } catch (java.lang.Exception e) { return
	 * ex.getMessage(); } } //
	 * 因为它返回的实例类型是WebRequest而不是HttpWebRequest,因此记得要进行强制类型转换//
	 * 接下来建立一个HttpWebResponse以便接收服务器发送的信息，它是调用HttpWebRequest.GetResponse来获取的：
	 * HttpWebResponse webResponse; try { webResponse = (HttpWebResponse)
	 * webRequest.GetResponse(); } catch (RuntimeException ex) { try { //
	 * 如果出现死连接 BP.DA.Log.DefaultLogWriteLineWarning("@获取url=" + url + "失败。异常信息:"
	 * + ex.getMessage(), true); return null; } catch (java.lang.Exception e2) {
	 * return ex.getMessage(); } }
	 * //如果webResponse.StatusCode的值为HttpStatusCode.OK，表示成功，那你就可以接着读取接收到的内容了：//
	 * 获取接收到的流 Stream stream = webResponse.GetResponseStream();
	 * System.IO.StreamReader streamReader = new StreamReader(stream, encode);
	 * String content = streamReader.ReadToEnd(); webResponse.Close(); return
	 * content;
	 * 
	 * }
	 */

	/**
	 * 读取文件
	 * 
	 * @param file
	 *            路径
	 * @return 内容
	 */
	public static String ReadTextFile(String file) {
		String doc = "";
		FileReader fr;
		try {
			fr = new FileReader(file);
			char[] chars = new char[1024];
			int b = 0;
			while ((b = fr.read(chars)) != -1) {
				doc += String.valueOf(chars);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doc.trim();
		/*
		 * warning System.IO.StreamReader read = new
		 * System.IO.StreamReader(file, System.Text.Encoding.UTF8); // 文件流.
		 * String doc = read.ReadToEnd(); //读取完毕。 read.Close(); // 关闭。 return
		 * doc;
		 */
	}

	public static boolean SaveAsFile(String filePath, String doc) {
		File fl = new File(filePath);
		if (fl.exists()) {
			fl.delete();
		}
		try {
			fl.createNewFile();
			FileWriter fw = new FileWriter(fl.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(doc);
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
		/*
		 * warning System.IO.StreamWriter sw = new
		 * System.IO.StreamWriter(filePath, false); sw.Write(doc); sw.Close();
		 * return true;
		 */
	}

	public static String ReadTextFile2Html(String file) {
		String doc = ReadTextFile(file);
		return DataType.ParseText2Html(doc);
	}

	/**
	 * 判断是否全部是汉字
	 * 
	 * @param htmlstr
	 * @return
	 */
	public static boolean CheckIsChinese(String htmlstr) {
		char[] chs = htmlstr.toCharArray();
		for (char c : chs) {
			int i = (new Character(c)).toString().length();
			if (i == 1) {
				return false;
			}
		}
		return true;
	}

	public static String Html2Text(String htmlstr) {
		htmlstr = htmlstr.replace("<BR>", "\n");
		return htmlstr.replace("&nbsp;", " ");
		// return htmlstr;
	}

	public static String ByteToString(byte[] bye) {
		String s = "";
		for (byte b : bye) {
			s += (new Byte(b)).toString();
		}
		return s;
	}

	public static byte[] StringToByte(String s) {
		byte[] bs = new byte[s.length()];
		char[] cs = s.toCharArray();
		int i = 0;
		for (char c : cs) {
			bs[i] = (byte) c;
			i++;
		}
		return bs;
	}

	/**
	 * 取道百分比
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static String GetPercent(java.math.BigDecimal a, java.math.BigDecimal b) {
		java.math.BigDecimal p = a.divide(b);
		DecimalFormat df = new DecimalFormat("0.00%");
		return df.format(p);
		/*
		 * warning java.math.BigDecimal p = a.divide(b); return
		 * p.ToString("0.00%");
		 */
	}

	public static String GetWeek(int weekidx) {
		switch (weekidx) {
		case 0:
			return "星期日";
		case 1:
			return "星期一";
		case 2:
			return "星期二";
		case 3:
			return "星期三";
		case 4:
			return "星期四";
		case 5:
			return "星期五";
		case 6:
			return "星期六";
		default:
			throw new RuntimeException("error weekidx=" + weekidx);
		}
	}

	public static String GetABC(String abc) {
		// C# TO JAVA CONVERTER NOTE: The following 'switch' operated on a
		// string member and was converted to Java 'if-else' logic:
		// switch (abc)
		// ORIGINAL LINE: case "A":
		if (abc.equals("A")) {
			return "B";
		}
		// ORIGINAL LINE: case "B":
		else if (abc.equals("B")) {
			return "C";
		}
		// ORIGINAL LINE: case "C":
		else if (abc.equals("C")) {
			return "D";
		}
		// ORIGINAL LINE: case "D":
		else if (abc.equals("D")) {
			return "E";
		}
		// ORIGINAL LINE: case "E":
		else if (abc.equals("E")) {
			return "F";
		}
		// ORIGINAL LINE: case "F":
		else if (abc.equals("F")) {
			return "G";
		}
		// ORIGINAL LINE: case "G":
		else if (abc.equals("G")) {
			return "H";
		}
		// ORIGINAL LINE: case "H":
		else if (abc.equals("H")) {
			return "I";
		}
		// ORIGINAL LINE: case "I":
		else if (abc.equals("I")) {
			return "J";
		}
		// ORIGINAL LINE: case "J":
		else if (abc.equals("J")) {
			return "K";
		}
		// ORIGINAL LINE: case "K":
		else if (abc.equals("K")) {
			return "L";
		}
		// ORIGINAL LINE: case "L":
		else if (abc.equals("L")) {
			return "M";
		}
		// ORIGINAL LINE: case "M":
		else if (abc.equals("M")) {
			return "N";
		}
		// ORIGINAL LINE: case "N":
		else if (abc.equals("N")) {
			return "O";
		}
		// ORIGINAL LINE: case "Z":
		else if (abc.equals("Z")) {
			return "A";
		} else {
			throw new RuntimeException("abc error" + abc);
		}
	}

	/*
	 * warning public static String GetBig5_Del(String text) {
	 * System.Text.Encoding e2312 = System.Text.Encoding.GetEncoding("GB2312");
	 * byte[] bs = e2312.GetBytes(text); System.Text.Encoding e5 =
	 * System.Text.Encoding.GetEncoding("Big5"); byte[] bs5 =
	 * System.Text.Encoding.Convert(e2312, e5, bs); return e5.GetString(bs5); }
	 */

	/**
	 * 返回 data1 - data2 的天数.
	 * 
	 * @param data1
	 *            fromday
	 * @param data2
	 *            today
	 * @return 相隔的天数
	 */
	public static int SpanDays(String fromday, String today) throws ParseException {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = fmt.parse(fromday);
		Date date2 = fmt.parse(today);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		long time1 = cal.getTimeInMillis();
		cal.setTime(date2);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		int days = Integer.parseInt(String.valueOf(between_days));
		return days;
	}

	/**
	 * 返回 QuarterFrom - QuarterTo 的季度.
	 * 
	 * @param QuarterFrom
	 *            QuarterFrom
	 * @param QuarterTo
	 *            QuarterTo
	 * @return 相隔的季度
	 */
	public static int SpanQuarter(String _APFrom, String _APTo) throws ParseException {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Date fromdate = fmt.parse(_APFrom + "-01");
		Date todate = fmt.parse(_APTo + "-01");
		// java.util.Date fromdate = Convert.ToDateTime(_APFrom + "-01");
		// java.util.Date todate = Convert.ToDateTime(_APTo + "-01");
		int i = 0;
		if (fromdate.compareTo(todate) > 0) {
			throw new RuntimeException("选择出错！起始时期" + _APFrom + "不能大于终止时期" + _APTo + "!");
		}

		while (fromdate.compareTo(todate) <= 0) {
			i++;

			Calendar cd = Calendar.getInstance();
			cd.setTime(fromdate);
			cd.add(Calendar.MONTH, +1);
			fromdate = cd.getTime();
			/*
			 * warningfromdate = fromdate.AddMonths(1);
			 */
		}

		int j = (i + 2) / 3;
		return j;
	}

	/**
	 * 到现在的天数。
	 * 
	 * @param data1
	 * @return
	 */
	public static int SpanDays(String data1) throws ParseException {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Date fromday = fmt.parse(data1);
		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(fromday);
		long time1 = cal.getTimeInMillis();
		cal.setTime(today);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		int days = Integer.parseInt(String.valueOf(between_days));
		return days;
	}

	/**
	 * 检查是否是一个字段或者表名称
	 * 
	 * @param str
	 *            要检查的字段或者表名称
	 * @return 是否合法
	 */
	public static boolean CheckIsFieldOrTableName(String str) {
		String s = str.substring(0, 1);
		if (DataType.IsNumStr(s)) {
			return false;
		}

		String chars = "~!@#$%^&*()_+`{}|:'<>?[];',./";
		if (chars.contains(s)) {
			return false;
		}
		return true;
	}

	public static String ParseText2Html(String val) {
		return val.replace("\n", "<BR>").replace("~", "'");
	}

	public static String ParseHtmlToText(String val) {
		if (val == null) {
			return val;
		}

		val = val.replace("&nbsp;", " ");
		val = val.replace("  ", " ");

		val = val.replace("</td>", "");
		val = val.replace("</TD>", "");

		val = val.replace("</tr>", "");
		val = val.replace("</TR>", "");

		val = val.replace("<tr>", "");
		val = val.replace("<TR>", "");

		val = val.replace("</font>", "");
		val = val.replace("</FONT>", "");

		val = val.replace("</table>", "");
		val = val.replace("</TABLE>", "");

		val = val.replace("<BR>", "\n\t");
		val = val.replace("<BR>", "\n\t");
		val = val.replace("&nbsp;", " ");

		val = val.replace("<BR><BR><BR><BR>", "<BR><BR>");
		val = val.replace("<BR><BR><BR><BR>", "<BR><BR>");
		val = val.replace("<BR><BR>", "<BR>");

		char[] chs = val.toCharArray();

		boolean isStartRec = false;
		String recStr = "";
		for (char c : chs) {
			if (c == '<') {
				recStr = "";
				isStartRec = true; // 开始记录
			}

			if (isStartRec) {
				recStr += (new Character(c)).toString();
			}

			if (c == '>') {
				isStartRec = false;

				if (recStr.equals("")) {
					isStartRec = false;
					continue;
				}

				// 开始分析这个标记内的东西。
				String market = recStr.toLowerCase();
				if (market.contains("<img")) {
					// 这是一个图片标记
					isStartRec = false;
					recStr = "";
					continue;
				} else {
					val = val.replace(recStr, "");
					isStartRec = false;
					recStr = "";
				}
			}
		}

		val = val.replace("字体：大中小", "");
		val = val.replace("字体:大中小", "");

		val = val.replace("  ", " ");
		val = val.replace("\t", "");
		val = val.replace("\n", "");
		val = val.replace("\r", "");
		return val;
	}

	/**
	 * 将中文转化成拼音
	 * 
	 * @param exp
	 * @return
	 */
	public static String ParseStringToPinyin(String exp) {
		exp = exp.trim();
		String pinYin = "", str = null;
		char[] chars = exp.toCharArray();
		for (char c : chars) {
			try {
				str = String.valueOf(c);
				// str = BP.En.Ext.chs2py.convert((new
				// Character(c)).toString());
				pinYin += str.substring(0, 1).toUpperCase() + str.substring(1);
			} catch (java.lang.Exception e) {
				pinYin += c;
			}
		}
		return pinYin;
	}

	/**
	 * 转化成拼音第一个字母大字
	 * 
	 * @param str
	 *            要转化的中文串
	 * @return 拼音
	 */
	public static String ParseStringToPinyinWordFirst(String str) {
		try {
			String _Temp = null;
			for (int i = 0; i < str.length(); i++) {
				_Temp = _Temp + BP.DA.DataType.ParseStringToPinyin(str.substring(i, i + 1));
			}
			return _Temp;
		} catch (RuntimeException ex) {
			throw new RuntimeException("@错误：" + str + "，不能转换成拼音。");
		}
	}

	/**
	 * 转化成拼音第一个字母大字
	 * 
	 * @param str
	 *            要转化的中文串
	 * @return 拼音
	 */
	public static String ParseStringToPinyinJianXie(String str) {
		try {
			String _Temp = null;
			for (int i = 0; i < str.length(); i++) {
				_Temp += BP.DA.DataType.ParseStringToPinyin(str.substring(i, i + 1)).substring(0, 1);
			}
			return _Temp;
		} catch (RuntimeException ex) {
			throw new RuntimeException("@错误：" + str + "，不能转换成拼音。");
		}
	}

	/**
	 * 转化成 decimal
	 * 
	 * @param exp
	 * @return
	 */
	public static BigDecimal ParseExpToDecimal(String exp) {
		BigDecimal bigDecimal = new BigDecimal(0);
		if (exp.trim().equals("")) {
			throw new RuntimeException("DataType.ParseExpToDecimal要转换的表达式为空。");
		}

		exp = exp.replace("+-", "-");
		exp = exp.replace("￥", "");
		// exp=exp.Replace(" ",""); 不能替换，因为有sql表达公式时间，会出现错误。
		exp = exp.replace("\n", "");
		exp = exp.replace("\t", "");

		exp = exp.replace("＋", "+");
		exp = exp.replace("－", "-");
		exp = exp.replace("＊", "*");
		exp = exp.replace("／", "/");
		exp = exp.replace("）", ")");
		exp = exp.replace("（", "(");

		exp = exp.replace(".00.00", "00");

		exp = exp.replace("--", "- -");

		if (exp.indexOf("@") != -1) {
			return bigDecimal;
		}

		String val = exp.substring(0, 1);
		if (val.equals("-")) {
			exp = exp.substring(1);
		}

		// exp = exp.Replace("*100%", "*100");

		exp = exp.replace("*100%", "*1");

		try {
			BigDecimal bigDecimal1 = new BigDecimal(exp);
			return bigDecimal1;
		} catch (java.lang.Exception e) {
		}

		try {
			String sql = "SELECT  " + exp + " as Num  ";

			for (DBType g : DBType.values()) {
				switch (g) {
				case MSSQL:
					break;
				case Oracle:
					sql = "SELECT  " + exp + " NUM from DUAL ";
					return DBAccess.RunSQLReturnValDecimal(sql, bigDecimal, 2);
				default:
					break;
				}
			}

			/*
			 * warning switch (SystemConfig.getAppCenterDBType()) { case
			 * DBType.MSSQL: case DBType.Oracle: sql = "SELECT  " + exp +
			 * " NUM from DUAL "; return DBAccess.RunSQLReturnValDecimal(sql, 0,
			 * 2); default: break; }
			 */
		} catch (RuntimeException ex) {
			// Log.DefaultLogWriteLineInfo(ex.getMessage());
			return bigDecimal;
		}

		exp = exp.replace("-0", "");

		try {
			StringExpressionCalculate sc = new StringExpressionCalculate();

			return new BigDecimal(sc.TurnToDecimal(exp));
		} catch (RuntimeException ex) {
			if (exp.indexOf("/") != -1) {
				return bigDecimal;
			}
			throw new RuntimeException("表达式(\"" + exp + "\")计算错误：" + ex.getMessage());
		}
	}

	public static String ParseFloatToCash(float money) {
		if (money == 0) {
			return "零圆零角零分";
		}
		DealString d = new DealString();
		d.setInputString((new Float(money)).toString());
		d.ConvertToChineseNum();
		return d.getOutString();
	}

	public static String ParseFloatToRMB(float money) {
		if (money == 0) {
			return "零圆零角零分";
		}
		DealString d = new DealString();
		d.setInputString((new Float(money)).toString());
		d.ConvertToChineseNum();
		return d.getOutString();
	}

	/**
	 * 得到一个日期,根据系统
	 * 
	 * @param dataStr
	 * @return
	 */
	public final java.util.Date Parse(String dataStr) {
		return new java.util.Date(java.util.Date.parse(dataStr));
	}

	/**
	 * 系统定义的时间格式 yyyy-MM-dd .
	 * 
	 */
	public static String getSysDataFormat() {
		return "yyyy-MM-dd";
	}

	/**
	 * 当前的日期
	 * 
	 */
	public static String getCurrentData() {
		Date dt = new Date();
		SimpleDateFormat matter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = matter.format(dt);
		return date;
	}

	public static Date getDate() {
		return new Date();
	}

	public static String getCurrentTime() {
		Date dt = new Date();
		SimpleDateFormat matter = new SimpleDateFormat("hh:mm");
		String date = matter.format(dt);
		return date;
	}

	/**
	 * 把日期对象转换成指定格式的字符串
	 * 
	 * @param dDate
	 *            - 日期对象
	 * @param sFormat
	 *            - 日期格式@return String yyyy-MM-dd HH:mm:ss
	 */
	public static String dateToStr(Date date, String sFormat) {
		if (null == date)
			return "";

		SimpleDateFormat df = new SimpleDateFormat(sFormat);
		return df.format(date);
	}

	public static Date stringToDate(String str) {
		if (null == str || str.equals(""))
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(str);
		} catch (ParseException e) {
			return null;
		}
	}

	public static String getCurrentTimeQuarter() {
		Date dt = new Date();
		SimpleDateFormat matter = new SimpleDateFormat("hh:mm");
		String date = matter.format(dt);
		return date;
	}

	/**
	 * 给一个时间，返回一个刻种时间。
	 * 
	 * @param time
	 * @return
	 */
	public static String ParseTime2TimeQuarter(String time) {
		String hh = time.substring(0, 3);
		int mm = Integer.parseInt(time.substring(3, 5));
		if (mm == 0) {
			return hh + "00";
		}

		if (mm < 15) {
			return hh + "00";
		}
		if (mm >= 15 && mm < 30) {
			return hh + "15";
		}

		if (mm >= 30 && mm < 45) {
			return hh + "30";
		}

		if (mm >= 45 && mm < 60) {
			return hh + "45";
		}
		return time;
	}

	public static String getCurrentDay() {
		Date dt = new Date();
		String date = String.format("%d", dt);
		return date;
		/*
		 * warning return new String.format("%d", java.util.Date());
		 */
	}

	/**
	 * 当前的会计期间
	 * 
	 */
	public static String getCurrentAP() {
		Date dt = new Date();
		SimpleDateFormat matter = new SimpleDateFormat("yyyy-MM");
		String date = matter.format(dt);
		return date;
	}

	/**
	 * 当前的会计期间
	 * 
	 */
	public static String getCurrentYear() {
		Date dt = new Date();
		SimpleDateFormat matter = new SimpleDateFormat("yyyy");
		String date = matter.format(dt);
		return date;
	}

	public static String getCurrentMonth() {
		Date dt = new Date();
		SimpleDateFormat matter = new SimpleDateFormat("MM");
		String date = matter.format(dt);
		return date;
	}

	/**
	 * 当前的会计期间 yyyy-MM
	 * 
	 */
	public static String getCurrentYearMonth() {
		Date dt = new Date();
		SimpleDateFormat matter = new SimpleDateFormat("yyyy-MM");
		String date = matter.format(dt);
		return date;
	}

	public static String GetJDByMM(String mm) {
		String jd = "01";
		// C# TO JAVA CONVERTER NOTE: The following 'switch' operated on a
		// string member and was converted to Java 'if-else' logic:
		// switch (mm)
		// ORIGINAL LINE: case "01":
		if (mm.equals("01") || mm.equals("02") || mm.equals("03")) {
			jd = "01";
		}
		// ORIGINAL LINE: case "04":
		else if (mm.equals("04") || mm.equals("05") || mm.equals("06")) {
			jd = "04";
		}
		// ORIGINAL LINE: case "07":
		else if (mm.equals("07") || mm.equals("08") || mm.equals("09")) {
			jd = "07";
		}
		// ORIGINAL LINE: case "10":
		else if (mm.equals("10") || mm.equals("11") || mm.equals("12")) {
			jd = "10";
		} else {
			throw new RuntimeException("@不是有效的月份格式" + mm);
		}
		return jd;
	}

	/**
	 * 当前的季度期间yyyy-MM
	 * 
	 */
	public static String getCurrentAPOfJD() {
		Date dt = new Date();
		SimpleDateFormat matter = new SimpleDateFormat("yyyy");
		String date = matter.format(dt);
		SimpleDateFormat matter1 = new SimpleDateFormat("MM");
		String date1 = matter1.format(dt);
		String date2 = GetJDByMM(date1);
		return date + "-" + date2;
	}

	/**
	 * 当前的季度的前一个季度.
	 * 
	 */
	public static String getCurrentAPOfJDOfFrontFamily() {
		Calendar cd = Calendar.getInstance();
		cd.set(Calendar.DAY_OF_MONTH, -3);
		cd.add(Calendar.MONTH, -3);
		Date d = cd.getTime();
		SimpleDateFormat matter = new SimpleDateFormat("yyyy");
		String date = matter.format(d);
		SimpleDateFormat matter1 = new SimpleDateFormat("MM");
		String date1 = matter1.format(d);
		String date2 = GetJDByMM(date1);
		return date + "-" + date2;
	}

	/**
	 * yyyy-JD
	 */
	/*
	 * warning public static String getCurrentAPOfPrevious() { int m =
	 * Integer.parseInt(new java.util.Date().ToString("MM")); return new
	 * java.util.Date().ToString("yyyy-MM"); }
	 */

	/**
	 * 取出当前月份的上一个月份
	 * 
	 */
	public static String getCurrentNYOfPrevious() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		String date = format.format(calendar.getTime());
		return date;
	}

	/**
	 * 当前的日期时间
	 * 
	 */
	public static String getCurrentDataTime() {
		Date dt = new Date();
		SimpleDateFormat matter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = matter.format(dt);
		return date;
	}

	public static String getCurrentDateByFormart(String formart) {
		Date dt = new Date();
		SimpleDateFormat matter = new SimpleDateFormat(formart);
		String date = matter.format(dt);
		return date;
	}

	public static String getDateByFormart(Date date, String formart) {
		SimpleDateFormat matter = new SimpleDateFormat(formart);
		String dateStr = matter.format(date);
		return dateStr;
	}

	public static String getCurrentDataTimeCNOfShort() {
		SimpleDateFormat matter = new SimpleDateFormat("yy年MM月dd日 HH时mm分");
		return matter.format(new Date());
	}

	public static String getCurrentDataCNOfShort() {
		SimpleDateFormat matter = new SimpleDateFormat("yy年MM月dd日");
		return matter.format(new Date());
	}

	public static String getCurrentDataCNOfLong() {
		SimpleDateFormat matter = new SimpleDateFormat("yyyy年MM月dd日");
		return matter.format(new Date());
	}

	/**
	 * 当前的日期时间
	 * 
	 */
	public static String getCurrentDataTimeCN() {
		SimpleDateFormat matter = new SimpleDateFormat(DataType.getSysDataFormatCN());
		Calendar calendar = Calendar.getInstance();
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		return matter.format(new Date()) + "，" + GetWeekName(dayOfWeek);
	}

	private static String GetWeekName(int dw) {
		final String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		return dayNames[dw];
	}

	/**
	 * 当前的日期时间
	 * 
	 */
	public static String getCurrentDataTimess() {
		SimpleDateFormat matter = new SimpleDateFormat(DataType.getSysDataTimeFormat());
		return matter.format(new Date());
	}

	public static String ParseSysDateTime2SysDate(String sysDateformat) {
		try {
			return sysDateformat.substring(0, 10);
		} catch (RuntimeException ex) {
			throw new RuntimeException("日期格式错误:" + sysDateformat + " errorMsg=" + ex.getMessage());
		}
	}

	/**
	 * 把chichengsoft本系统日期格式转换为系统日期格式。
	 * 
	 * @param sysDateformat
	 *            yyyy-MM-dd
	 * @return DateTime
	 */
	public static java.util.Date ParseSysDate2DateTime(String sysDateformat) {
		if (sysDateformat == null || sysDateformat.trim().length() == 0) {
			return new java.util.Date();
		}

		try {
			if (sysDateformat.length() > 10) {
				return ParseSysDateTime2DateTime(sysDateformat);
			}

			sysDateformat = sysDateformat.trim();
			// DateTime.Parse(sysDateformat,
			String[] strs = null;
			if (sysDateformat.indexOf("-") != -1) {
				strs = sysDateformat.split("[-]", -1);
			}

			if (sysDateformat.indexOf("/") != -1) {
				strs = sysDateformat.split("[/]", -1);
			}

			int year = Integer.parseInt(strs[0]);
			int month = Integer.parseInt(strs[1]);
			int day = Integer.parseInt(strs[2]);

			// DateTime dt= DateTime.Now;
			return new java.util.Date(year, month, day, 0, 0, 0);
		} catch (RuntimeException ex) {
			throw new RuntimeException("日期[" + sysDateformat + "]转换出现错误:" + ex.getMessage() + "无效的日期是格式。");
		}
		// return dt;
	}

	/**
	 * 2005-11-04 09:12
	 * 
	 * @param sysDateformat
	 * @return
	 * @throws ParseException
	 */
	public static java.util.Date ParseSysDateTime2DateTime(String sysDateformat) {
		try {
			/*
			 * modifier venson 如果没有秒位，则自动补齐
			 */
			if (sysDateformat.length() == 16) {
				sysDateformat += ":00";
			}
			DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = fmt.parse(sysDateformat);
			return date;
		} catch (Exception ex) {
			throw new RuntimeException("@时间格式不正确:" + sysDateformat + "@技术信息:" + ex.getMessage());
		}
	}

	/**
	 * @param dtoffrom
	 * @param dtofto
	 * @return
	 * @throws ParseException
	 */
	public static int GetSpanDays(String dtoffrom, String dtofto) {
		java.util.Date dtfrom = DataType.ParseSysDate2DateTime(dtoffrom);
		java.util.Date dtto = DataType.ParseSysDate2DateTime(dtofto);
		long diff = dtto.getTime() - dtfrom.getTime();
		long day = diff / (1000 * 60 * 60 * 24);
		int ds = (int) day;
		return ds;
	}

	public static int GetSpanMinute(String fromdatetim, String toDateTime) throws ParseException {
		java.util.Date dtfrom = DataType.ParseSysDateTime2DateTime(fromdatetim);
		java.util.Date dtto = DataType.ParseSysDateTime2DateTime(toDateTime);
		long diff = dtto.getTime() - dtfrom.getTime();
		long day = diff / (24 * 60 * 60 * 1000);
		long hour = (diff / (60 * 60 * 1000) - day * 24);
		long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
		int mn = (int) min;
		return mn;
	}

	/**
	 * 到现在的时间
	 * 
	 * @param fromdatetim
	 * @return 分中数
	 */
	public static int GetSpanMinute(String fromdatetim) throws ParseException {
		java.util.Date dtfrom = DataType.ParseSysDateTime2DateTime(fromdatetim);
		java.util.Date dtto = new java.util.Date();
		long diff = dtto.getTime() - dtfrom.getTime();
		long day = diff / (24 * 60 * 60 * 1000);
		long hour = (diff / (60 * 60 * 1000) - day * 24);
		long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
		int mn = (int) min;
		int hu = (int) hour;
		return mn + hu * 60;
	}

	/**
	 * 系统定义日期时间格式 yyyy-MM-dd hh:mm
	 * 
	 */
	public static String getSysDataTimeFormat() {
		return "yyyy-MM-dd HH:mm:ss";
	}

	public static String getSysDataFormatCN() {
		return "yyyy年MM月dd日";
	}

	public static String getSysDatatimeFormatCN() {
		return "yyyy年MM月dd日 HH时mm分";
	}

	public static DBUrlType GetDBUrlByString(String strDBUrl) {
		return DBUrlType.AppCenterDSN;
	}

	public static int GetDataTypeByString(String datatype) {
		// C# TO JAVA CONVERTER NOTE: The following 'switch' operated on a
		// string member and was converted to Java 'if-else' logic:
		// switch (datatype)
		// ORIGINAL LINE: case "AppBoolean":
		if (datatype.equals("AppBoolean")) {
			return DataType.AppBoolean;
		}
		// ORIGINAL LINE: case "AppDate":
		else if (datatype.equals("AppDate")) {
			return DataType.AppDate;
		}
		// ORIGINAL LINE: case "AppDateTime":
		else if (datatype.equals("AppDateTime")) {
			return DataType.AppDateTime;
		}
		// ORIGINAL LINE: case "AppDouble":
		else if (datatype.equals("AppDouble")) {
			return DataType.AppDouble;
		}
		// ORIGINAL LINE: case "AppFloat":
		else if (datatype.equals("AppFloat")) {
			return DataType.AppFloat;
		}
		// ORIGINAL LINE: case "AppInt":
		else if (datatype.equals("AppInt")) {
			return DataType.AppInt;
		}
		// ORIGINAL LINE: case "AppMoney":
		else if (datatype.equals("AppMoney")) {
			return DataType.AppMoney;
		}
		// ORIGINAL LINE: case "AppString":
		else if (datatype.equals("AppString")) {
			return DataType.AppString;
		} else {
			throw new RuntimeException("@没有此类型[" + DataType.AppString + "]");
		}
	}

	public static String GetDataTypeDese(int datatype) {
		if (BP.Port.WebUser.getSysLang().equals("CH")) {
			switch (datatype) {
			case DataType.AppBoolean:
				return "布尔(Int)";
			case DataType.AppDate:
				return "日期nvarchar";
			case DataType.AppDateTime:
				return "日期时间nvarchar";
			case DataType.AppDouble:
				return "双精度(double)";
			case DataType.AppFloat:
				return "浮点(float)";
			case DataType.AppInt:
				return "整型(int)";
			case DataType.AppMoney:
				return "货币(float)";
			case DataType.AppString:
				return "字符(nvarchar)";
			default:
				throw new RuntimeException("@没有此类型");
			}
		}

		switch (datatype) {
		case DataType.AppBoolean:
			return "Boolen";
		case DataType.AppDate:
			return "Date";
		case DataType.AppDateTime:
			return "Datetime";
		case DataType.AppDouble:
			return "Double";
		case DataType.AppFloat:
			return "Float";
		case DataType.AppInt:
			return "Int";
		case DataType.AppMoney:
			return "Money";
		case DataType.AppString:
			return "Nvarchar";
		default:
			throw new RuntimeException("@没有此类型");
		}
	}

	/**
	 * string
	 */
	public static final int AppString = 1;
	/**
	 * int
	 */
	public static final int AppInt = 2;
	/**
	 * float
	 */
	public static final int AppFloat = 3;
	/**
	 * AppBoolean
	 */
	public static final int AppBoolean = 4;
	/**
	 * AppDouble
	 */
	public static final int AppDouble = 5;
	/**
	 * AppDate
	 */
	public static final int AppDate = 6;
	/**
	 * AppDateTime
	 */
	public static final int AppDateTime = 7;
	/**
	 * AppMoney
	 */
	public static final int AppMoney = 8;
	/**
	 * 率百分比。
	 * 
	 */
	public static final int AppRate = 9;

	public static String StringToDateStr(String str) {
		// try
		// {
		// java.util.Date dt = new java.util.Date(java.util.Date.parse(str));
		// String year = dt.Year.toString();
		// String month = dt.Month.toString();
		// String day = dt.Day.toString();
		// return year + "-" + month.PadLeft(2, '0') + "-" + day.PadLeft(2,
		// '0');
		// //return str;
		// }
		// catch (RuntimeException ex)
		// {
		// throw ex;
		// }
		return "";
	}

	public static String GenerSpace(int spaceNum) {
		if (spaceNum <= 0) {
			return "";
		}

		String strs = "";
		while (spaceNum != 0) {
			strs += "&nbsp;&nbsp;";
			spaceNum--;
		}
		return strs;
	}

	public static String GenerBR(int spaceNum) {
		String strs = "";
		while (spaceNum != 0) {
			strs += "<BR>";
			spaceNum--;
		}
		return strs;
	}

	public static boolean IsImgExt(String ext) {
		ext = ext.replace(".", "").toLowerCase();
		if (ext.equals("jpg") || ext.equals("gif") || ext.equals("jepg") || ext.equals("bmp") || ext.equals("png")
				|| ext.equals("tif") || ext.equals("gsp") || ext.equals("mov") || ext.equals("psd")
				|| ext.equals("tiff") || ext.equals("wmf")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean IsVoideExt(String ext) {
		ext = ext.replace(".", "").toLowerCase();
		if (ext.equals("mp3") || ext.equals("mp4") || ext.equals("asf") || ext.equals("wma") || ext.equals("rm")
				|| ext.equals("rmvb") || ext.equals("mpg") || ext.equals("wmv") || ext.equals("quicktime")
				|| ext.equals("avi") || ext.equals("flv") || ext.equals("mpeg")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean IsNumStr(String str) {

		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public static boolean IsQS(int num) {
		int ii = 0;
		for (int i = 0; i < 500; i++) {
			if (num == ii) {
				return false;
			}
			ii = ii + 2;
		}
		return true;
	}

	public static boolean StringToBoolean(String str) {
		if (str == null || str.equals("") || str.equals(",nbsp;")) {
			return false;
		}

		if (str.equals("0") || str.equals("1")) {
			if (str.equals("0")) {
				return false;
			} else {
				return true;
			}
		} else if (str.equals("true") || str.equals("false")) {
			if (str.equals("false")) {
				return false;
			} else {
				return true;
			}

		} else if (str.equals("是") || str.equals("否")) {
			if (str.equals("否")) {
				return false;
			} else {
				return true;
			}
		} else {
			throw new RuntimeException("@要转换的[" + str + "]不是bool 类型");
		}
	}
}

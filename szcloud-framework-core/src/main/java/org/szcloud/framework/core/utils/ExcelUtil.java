package org.szcloud.framework.core.utils;


import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class ExcelUtil<T> {

	public void exportExcel(Collection<T> dataset, OutputStream out) {
		exportExcel("sheet1", null, dataset, out, "yyyy-MM-dd");
	}

	public void exportExcel(String[] headers, Collection<T> dataset,
			OutputStream out) {
		exportExcel("sheet1", headers, dataset, out, "yyyy-MM-dd");
	}

	public void exportExcel(String[] headers, Collection<T> dataset,
			OutputStream out, String pattern) {
		exportExcel("sheet1", headers, dataset, out, pattern);
	}

	/**
	 * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
	 * 
	 * @param title表格标题名
	 * @param headers表格属性列名数组
	 * @param dataset
	 *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @param out
	 *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 * @param pattern
	 *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
	 */

	@SuppressWarnings({ "unchecked", "deprecation", "resource", "rawtypes" })
	public void exportExcel(String title, String[] headers,
			Collection<T> dataset, OutputStream out, String pattern) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 15);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);
		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
//		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
//				0, 0, 0, (short) 4, 2, (short) 6, 5));
//		// 设置注释内容
//		comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
//		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
//		comment.setAuthor("leno");
		
		// 产生表格标题行	
		HSSFRow row = sheet.createRow(0);
		for (short i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
		
		int size=headers.length;	
		// 遍历集合数据，产生数据行
		Iterator<T> it = dataset.iterator();
		int index = 0;
		while (it.hasNext()) {
			index++;						
			row = sheet.createRow(index);
			T t = (T) it.next();
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			Field[] fields = t.getClass().getDeclaredFields();
			int length=0;
			for (short i = 0; i < fields.length; i++) {
				length++;			
				//超过标题长度，不显示
				if(length>size){
					break;
				}
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(style2);
				Field field = fields[i];
				field.setAccessible(true);
				String fieldName = field.getName();
				String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				try {
					Class tCls = t.getClass();
					Method getMethod = tCls.getMethod(getMethodName,new Class[] {});
					Object value = getMethod.invoke(t, new Object[] {});
					// 判断值的类型后进行强制类型转换
					String textValue = "";
					if (value != null) {
						if (value instanceof Boolean) {
							boolean bValue = (Boolean) value;
							textValue = "男";
							if (!bValue) {
								textValue = "女";
							}
						} else if (value instanceof Date) {
							Date date = (Date) value;
							SimpleDateFormat sdf = new SimpleDateFormat(pattern);
							textValue = sdf.format(date);
						} else if (value instanceof byte[]) {
							// 有图片时，设置行高为60px;
							row.setHeightInPoints(60);
							// 设置图片所在列宽度为80px,注意这里单位的一个换算
							sheet.setColumnWidth(i, (short) (35.7 * 80));
							// sheet.autoSizeColumn(i);
							byte[] bsValue = (byte[]) value;
							HSSFClientAnchor anchor = new HSSFClientAnchor(0,0, 1023, 255, (short) 6, index, (short) 6,index);
							anchor.setAnchorType(2);
							patriarch.createPicture(anchor, workbook.addPicture(bsValue,HSSFWorkbook.PICTURE_TYPE_JPEG));
						} else {
							// 其它数据类型都当作字符串简单处理
							textValue = value.toString();
						}
					}
					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
					if (textValue != null) {
						Pattern p = Pattern.compile("^\\d+(\\.\\d+)?$");
						Matcher matcher = p.matcher(textValue);
						if (matcher.matches()) {
							cell.setCellValue(Double.parseDouble(textValue));
						} else {
							HSSFRichTextString richString = new HSSFRichTextString(textValue);
							HSSFFont font3 = workbook.createFont();
							font3.setColor(HSSFColor.BLUE.index);
							richString.applyFont(font3);
							cell.setCellValue(richString);
						}
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} finally {
					// 清理资源
				}
			}
		}
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 一个简单的，使用反射填充EXCEL表格方法，为springEXCEL框架定制
	 * @param workbook  HSSFWorkbook
	 * @param sheetName  sheet名
	 * @param headers  表头信息
	 * @param dataset 数据集
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static HSSFWorkbook  simExportExcel(HSSFWorkbook workbook,String sheetName,
			List<ExcelUtil.EXCELHeaders> headers,List<Object> dataset) {		
		/* 生成一个表格*/
		HSSFSheet sheet = workbook.createSheet(sheetName);
		// 生成表头样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成数据样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);
		
		// 产生表格标题行	
		HSSFRow row = sheet.createRow(0);
		for(int i = 0; i < headers.size(); i++){
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString((String) headers.get(i).getHeaderName());
			cell.setCellValue(text);
		}
		
		// 遍历集合数据，产生数据行
		for (int i= 0; i < dataset.size(); i++) {						
			row = sheet.createRow(i+1);
			Object o = dataset.get(i);		
			for (int j = 0; j < headers.size(); j++){
				HSSFCell cell = row.createCell(j);
				cell.setCellStyle(style2);
				String fname = headers.get(j).fieldName;
				//获取GET参数方法名
				String getMethodName  = "get" + fname.substring(0, 1).toUpperCase() + fname.substring(1);					
				Method getMethod = null;
				Object textValue = null;
				try {
					getMethod = o.getClass().getMethod(getMethodName, new Class[] {});
					textValue = getMethod.invoke(o, new Object[]{});
					if(textValue != null){
						cell.setCellValue(textValue.toString());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		/*调整表格列宽*/
		for(int i = 0 ;i < headers.size() ; i++){
			if(headers.get(i).getColumnWidth() > 0){
				sheet.setColumnWidth(i, headers.get(i).getColumnWidth());	
			}else{
				sheet.autoSizeColumn(i);
			}
		}
		return workbook;
	}
	
	public static class EXCELHeaders{
		private String fieldName;
		private String headerName;
		private int columnWidth;
		
		/**
		 * 自定义表格列宽
		 * @param fieldName 属性名  
		 * @param headerName 表格头
		 * @param columnWidth  列宽
		 */
		public EXCELHeaders(String fieldName, String headerName, int columnWidth ){
			this.fieldName = fieldName;
			this.headerName = headerName;
			this.columnWidth = columnWidth;
		}
		
		/**
		 * 自动设置表格列宽
		 * @param fieldName 属性名
		 * @param headerName 表格头
		 */
		public EXCELHeaders(String fieldName, String headerName){
			this.fieldName = fieldName;
			this.headerName = headerName;
		}
		
		public String getFieldName() {
			return fieldName;
		}
		
		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}
		
		public String getHeaderName() {
			return headerName;
		}
		
		public void setHeaderName(String headerName) {
			this.headerName = headerName;
		}
		
		public int getColumnWidth() {
			return columnWidth;
		}
		
		public void setColumnWidth(int columnWidth) {
			this.columnWidth = columnWidth;
		}	
	}
}

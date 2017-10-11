package BP.Web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import TL.ContextHolderUtils;
import BP.En.Attr;
import BP.En.Attrs;
import BP.En.Entities;
import BP.En.Entity;
import BP.En.FieldType;
import BP.En.Map;
import BP.Port.WebUser;
import BP.Sys.PubClass;
import BP.WF.Glo;

public class WebPage{
	/** 
	 ExportDGToExcel
	 
	 @param ens
	 @return 
*/
	public static String ExportDGToExcel(Entities ens)
	{

		Map map = ens.getGetNewEntity().getEnMap();
		String fileName = WebUser.getNo() + ".xls";
		String fileDir = Glo.getFlowFileTemp();
		String filePth = Glo.getFlowFileTemp();
		///#region 参数及变量设置
		//如果导出目录没有建立，则建立.
		File file = new File(fileDir);
		if(!file.exists()){
			file.mkdirs();
		}

		filePth = filePth + fileName;
		file = new File(filePth);
		if (file.exists())
		{
			file.delete();
		}
		
//		String httpFilePath = Glo.getCCFlowAppPath()+"DataUser/Temp/"+fileName;
		
		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet(map.getPhysicsTable());  
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        HSSFRow row = sheet.createRow((int) 0);  
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
        
        HSSFCell cell =null;
		///#endregion

		StringBuffer stringBuffer = new StringBuffer();
		int i = 0;
		 // 生成标题
		cell = row.createCell(i);
        cell.setCellStyle(style);  
        
        Attrs attrs = map.getAttrs();
        for (Attr attr : attrs) {
        	if (attr.getKey().indexOf("Text") == -1)
			{
				if (attr.getUIVisible() == false)
				{
					continue;
				}
			}

			if (attr.getMyFieldType() == FieldType.Enum || attr.getMyFieldType() == FieldType.PKEnum || attr.getMyFieldType() == FieldType.PKFK || attr.getMyFieldType() == FieldType.FK){
				continue;
			}
			stringBuffer.append(attr.getDesc());
        }
        cell.setCellValue(stringBuffer.toString());
		
		for (Entity en : ens) {
			i++;
			cell = row.createCell(i);
			cell.setCellStyle(style);
			
			//生成文件内容
			for (Attr attr : attrs){
				if (attr.getKey().indexOf("Text") == -1) {
					if (attr.getUIVisible() == false) {
						continue;
					}
				}

				if (attr.getMyFieldType() == FieldType.Enum || attr.getMyFieldType() == FieldType.PKEnum || attr.getMyFieldType() == FieldType.PKFK || attr.getMyFieldType() == FieldType.FK){
					continue;
				}

				String str = en.GetValStringByKey(attr.getKey());
				if (str.equals("") || str == null){
					str = " ";
				}
				stringBuffer.append(str);
			}
			cell.setCellValue(stringBuffer.toString());
		}
		///#endregion
		return fileName;
//		 // 第六步，将文件存到指定位置  
//        try  
//        {  
//            FileOutputStream fout = new FileOutputStream(filePth);  
//            wb.write(fout);  
//            fout.flush();
//            fout.close(); 
//            ContextHolderUtils.getResponse().sendRedirect(httpFilePath);
//            return fileName;
//        }  catch (Exception e)  
//        {  
//            e.printStackTrace();  
//            return null;
//        }  
        
	}
}

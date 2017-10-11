package cn.org.awcp.formdesigner.service;

import java.io.IOException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontProvider;
import com.itextpdf.text.pdf.BaseFont;

public class MyFontFactory implements FontProvider{

	public Font getFont(String fontname, String encoding, boolean embedded,float size, int style, BaseColor color) {
		BaseFont bfChinese = null;
		try {
			bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
		} catch (DocumentException e) {
			//  Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			//  Auto-generated catch block
			e.printStackTrace();
		}// 设置中文字体
		return new Font(bfChinese, size, style, color);
	}

	public boolean isRegistered(String fontname) {
		return false;
	}
}

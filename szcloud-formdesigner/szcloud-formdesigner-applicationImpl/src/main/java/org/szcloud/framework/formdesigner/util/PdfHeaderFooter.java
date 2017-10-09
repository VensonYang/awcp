package org.szcloud.framework.formdesigner.util;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

public class PdfHeaderFooter extends PdfPageEventHelper {
	/**
	 * 日志对象
	 */
	private static final Logger logger = LoggerFactory.getLogger(PdfHeaderFooter.class);
	private String headerStr;

	private String bottomStr;

	private String waterMark;

	private String barCodePosX;

	private String barCodePosY;

	private String isGutter;

	private String gutterPosX;

	private String gutterPosY;

	private String showPage;

	public PdfHeaderFooter(String headerStr, String bottomStr) {
		this.headerStr = headerStr;
		this.bottomStr = bottomStr;
	}

	public PdfHeaderFooter(String headerStr, String bottomStr, String waterMark) {
		this.headerStr = headerStr;
		this.bottomStr = bottomStr;
		this.waterMark = waterMark;
	}

	public PdfHeaderFooter(String headerStr, String bottomStr, String waterMark, String showPage) {
		this.headerStr = headerStr;
		this.bottomStr = bottomStr;
		this.waterMark = waterMark;
		this.showPage = showPage;
	}

	public PdfHeaderFooter(Map<String, String> map) {
		this.headerStr = map.get("headerStr");
		this.bottomStr = map.get("bottomStr");
		this.waterMark = map.get("waterMark");
		this.showPage = map.get("showPage");
		this.setBarCodePosX(map.get("barCodePosX"));
		this.setBarCodePosY(map.get("barCodePosY"));
		this.setIsGutter(map.get("isGutter"));
		this.setGutterPosX(map.get("gutterPosX"));
		this.setGutterPosY(map.get("gutterPosY"));
	}

	public void onEndPage(PdfWriter writer, Document document) {
		Rectangle rect = writer.getBoxSize("art");
		Font waterMarkFont = new Font(Font.FontFamily.HELVETICA, 52, Font.NORMAL, new GrayColor(0.75f));

		BaseFont bfChinese = null;

		LineSeparator separator = new LineSeparator();
		separator.setLineWidth(0.85f);
		Chunk linebreak = new Chunk(separator);

		PdfContentByte cb = writer.getDirectContent();

		if (writer.getPageNumber() == 1) {
			// 生成条形码
			if (StringUtils.isNotBlank(waterMark)) {
				Barcode128 code128 = new Barcode128();
				code128.setCode(waterMark);
				code128.setSize(10);
				Image image = code128.createImageWithBarcode(cb, null, null);
				image.setAbsolutePosition(70, 795);
				try {
					cb.addImage(image);
				} catch (DocumentException e2) {
					e2.printStackTrace();
				}
			}
		}

		if (StringUtils.isNotBlank(headerStr)) {
			// 生成页眉上的横线
			ColumnText ct = new ColumnText(cb);
			float[] left = { rect.getLeft(), rect.getBottom(), rect.getLeft(), rect.getBottom() - 18 };
			float[] right = { rect.getRight(), rect.getTop(), rect.getRight(), rect.getTop() - 70 };
			logger.debug("left" + left.toString());
			logger.debug("right" + right.toString());

			ct.setColumns(left, right);
			ct.addText(linebreak);
			try {
				ct.go();
			} catch (DocumentException e1) {
				e1.printStackTrace();
			}
		}

		try {
			bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 设置中文字体
		Font headFont = new Font(bfChinese, 10f, Font.NORMAL);// 设置字体大小
		Phrase p = new Phrase();
		p.add(linebreak);
		String finalHeader;
		if (StringUtils.isNotEmpty(headerStr)) {
			finalHeader = headerStr;
		} else {
			finalHeader = "";
		}

		rect.setBorderColor(BaseColor.BLACK);
		ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, new Phrase(finalHeader, headFont),
				rect.getRight(), rect.getTop() + 18, 0);

		if ("1".equals(showPage)) { // 是否分页
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER,
					new Phrase(String.format("%d", writer.getPageNumber())), (document.left() + document.right()) / 2,
					rect.getBottom() - 18, 0);
		}

		if (StringUtils.isNotBlank(waterMark)) { // 是否显示水印号
			if (StringUtils.isNotBlank(waterMark)) {
				ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER,
						new Phrase(waterMark, waterMarkFont), 297.5f, 351, 45);
			}
		}
	}

	public String getHeaderStr() {
		return headerStr;
	}

	public void setHeaderStr(String headerStr) {
		this.headerStr = headerStr;
	}

	public String getBottomStr() {
		return bottomStr;
	}

	public void setBottomStr(String bottomStr) {
		this.bottomStr = bottomStr;
	}

	public String getWaterMark() {
		return waterMark;
	}

	public void setWaterMark(String waterMark) {
		this.waterMark = waterMark;
	}

	public String getShowPage() {
		return showPage;
	}

	public void setShowPage(String showPage) {
		this.showPage = showPage;
	}

	public String getBarCodePosX() {
		return barCodePosX;
	}

	public void setBarCodePosX(String barCodePosX) {
		this.barCodePosX = barCodePosX;
	}

	public String getBarCodePosY() {
		return barCodePosY;
	}

	public void setBarCodePosY(String barCodePosY) {
		this.barCodePosY = barCodePosY;
	}

	public String getIsGutter() {
		return isGutter;
	}

	public void setIsGutter(String isGutter) {
		this.isGutter = isGutter;
	}

	public String getGutterPosX() {
		return gutterPosX;
	}

	public void setGutterPosX(String gutterPosX) {
		this.gutterPosX = gutterPosX;
	}

	public String getGutterPosY() {
		return gutterPosY;
	}

	public void setGutterPosY(String gutterPosY) {
		this.gutterPosY = gutterPosY;
	}

}

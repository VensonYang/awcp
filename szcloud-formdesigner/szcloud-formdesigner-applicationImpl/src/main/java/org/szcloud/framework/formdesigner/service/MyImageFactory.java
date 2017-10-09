package org.szcloud.framework.formdesigner.service;

import java.io.IOException;
import java.util.Map;

import com.itextpdf.text.DocListener;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.html.simpleparser.ChainedProperties;
import com.itextpdf.text.html.simpleparser.ImageProvider;

@SuppressWarnings("deprecation")
public class MyImageFactory implements ImageProvider{

	private String servletContextPath;
	
	public MyImageFactory(String servletContextPath){
		this.servletContextPath = servletContextPath;
	}
	
	@SuppressWarnings("rawtypes")
	public Image getImage(String src, Map h, ChainedProperties cprops,
			DocListener doc) {
		String url = servletContextPath + src;
		try {
			return Image.getInstance(url);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getServletContextPath() {
		return servletContextPath;
	}

	public void setServletContextPath(String servletContextPath) {
		this.servletContextPath = servletContextPath;
	}
		
}

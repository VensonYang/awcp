package org.szcloud.framework.core.utils;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

public class WordToHtml {
	/**
	 * 日志对象
	 */
	private static final Logger logger = LoggerFactory.getLogger(WordToHtml.class);
	private final static String encode = "UTF-8";// GB2312
	private final static String targetPath = "word2html";// GB2312
	private final static String targetFile = "tmp.html";// GB2312

	public static void main(String[] args) {
		try {
			WordToHtml wt = new WordToHtml();
			wt.convert(new FileInputStream("f://test.doc"), "f://test.html");
			logger.debug("ok");
			logger.debug(wt.getTmpPath());
			logger.debug(wt.getTmpPath2());
			logger.debug(wt.getTmpPath3());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getTmpPath() {
		return WordToHtml.class.getResource("").toString();
	}

	public String getTmpPath2() {
		return WordToHtml.class.getResource("/").toString();
	}

	public String getTmpPath3() {
		return System.getProperty("user.dir");
	}

	public String getTmpPath4() {
		return WordToHtml.class.getResource("/").getFile();
	}

	public String convert(InputStream is, String outPutFile)
			throws TransformerException, IOException, ParserConfigurationException {
		String tmpPath = targetPath + "/" + UUID.randomUUID().toString();
		String rootPath = WordToHtml.class.getResource("/").getFile().replaceFirst("WEB-INF/classes/", "")
				.replaceFirst("classes/", "");// web根目录
		String tmpDir = rootPath + tmpPath;
		File file = new File(tmpDir);
		if (!file.exists()) {
			file.mkdirs();
		}
		HWPFDocument wordDocument = new HWPFDocument(is);
		WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
				DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
		wordToHtmlConverter.setPicturesManager(new PicturesManager() {
			public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches,
					float heightInches) {
				// logger.debug("suggestedName : " + suggestedName + " |
				// pictureType "+ pictureType);
				return suggestedName;
			}
		});
		wordToHtmlConverter.processDocument(wordDocument);
		// save pictures
		List<Picture> pics = wordDocument.getPicturesTable().getAllPictures();
		if (pics != null) {
			for (int i = 0; i < pics.size(); i++) {
				Picture pic = pics.get(i);
				try {
					// logger.debug("suggestFullFileName : " +
					// pic.suggestFullFileName());
					pic.writeImageContent(new FileOutputStream(tmpDir + File.separator + pic.suggestFullFileName()));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		Document htmlDocument = wordToHtmlConverter.getDocument();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DOMSource domSource = new DOMSource(htmlDocument);
		StreamResult streamResult = new StreamResult(out);

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer serializer = tf.newTransformer();
		serializer.setOutputProperty(OutputKeys.ENCODING, encode);
		serializer.setOutputProperty(OutputKeys.INDENT, "yes");
		serializer.setOutputProperty(OutputKeys.METHOD, "html");
		serializer.transform(domSource, streamResult);
		out.close();
		if (StringUtils.isBlank(outPutFile)) {
			outPutFile = tmpDir + "/" + targetFile;
		}
		writeFile(new String(out.toByteArray()), outPutFile);
		return tmpPath + "/" + targetFile;
	}

	public static void writeFile(String content, String path) {
		FileOutputStream fos = null;
		BufferedWriter bw = null;
		try {
			File file = new File(path);
			fos = new FileOutputStream(file);
			bw = new BufferedWriter(new OutputStreamWriter(fos, encode));
			bw.write(content);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fos != null)
					fos.close();
			} catch (IOException ie) {
			}
		}
	}

}

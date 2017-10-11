package cn.org.awcp.formdesigner.application.vo;

import java.io.Serializable;

public class CommondWordsVO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id;
	
	private Long userId;
	
	private String wordContent;
	
	private String typeId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getWordContent() {
		return wordContent;
	}

	public void setWordContent(String wordContent) {
		this.wordContent = wordContent;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	
}


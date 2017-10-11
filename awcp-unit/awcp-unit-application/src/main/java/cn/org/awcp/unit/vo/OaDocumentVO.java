package cn.org.awcp.unit.vo;

import java.util.Date;

public class OaDocumentVO{

	private Long id;

    //类型1:编号办文2:内部办文 type
    private String type;

    //发文标题 title
    private String title;

    //紧急程度 degree
    private String degree;

    //密级 level
    private String level;

    //发文字号 word_size
    private String wordSize;

    //公文种类 kind
    private String kind;

    //主送 main_send
    private String mainSend;

    //抄送 copy_to
    private String copyTo;

    //正文 body
    private String body;

    //附件 attachment
    private String attachment;

    //主题词 theme
    private String theme;

    //制文编号 make_num
    private String makeNum;

    //内容摘要 content
    private String content;

    //印数 print_num
    private Byte printNum;

    //核稿 auditor
    private Long auditor;

    //领导批示 comment
    private String comment;

    //创建日期 create_date
    private Date createDate;

    //修改日期 modify_date
    private Date modifyDate;

    private Long creator;

    private Long modifier;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getWordSize() {
        return wordSize;
    }

    public void setWordSize(String wordSize) {
        this.wordSize = wordSize;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getMainSend() {
        return mainSend;
    }

    public void setMainSend(String mainSend) {
        this.mainSend = mainSend;
    }

    public String getCopyTo() {
        return copyTo;
    }

    public void setCopyTo(String copyTo) {
        this.copyTo = copyTo;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getMakeNum() {
        return makeNum;
    }

    public void setMakeNum(String makeNum) {
        this.makeNum = makeNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Byte getPrintNum() {
        return printNum;
    }

    public void setPrintNum(Byte printNum) {
        this.printNum = printNum;
    }

    public Long getAuditor() {
        return auditor;
    }

    public void setAuditor(Long auditor) {
        this.auditor = auditor;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Long getModifier() {
        return modifier;
    }

    public void setModifier(Long modifier) {
        this.modifier = modifier;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	@Override
	public String toString() {
		return "OaDocumentVO [id=" + id + ", type=" + type + ", title=" + title + ", degree=" + degree + ", level="
				+ level + ", wordSize=" + wordSize + ", kind=" + kind + ", mainSend=" + mainSend + ", copyTo=" + copyTo
				+ ", body=" + body + ", attachment=" + attachment + ", theme=" + theme + ", makeNum=" + makeNum
				+ ", content=" + content + ", printNum=" + printNum + ", auditor=" + auditor + ", comment=" + comment
				+ ", createDate=" + createDate + ", modifyDate=" + modifyDate + ", creator=" + creator + ", modifier="
				+ modifier + ", remark=" + remark + "]";
	}
    
}
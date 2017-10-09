package org.szcloud.framework.unit.vo;

public class OaWorkVO {
    private Long id;

    //WORK_ID
    private Long workId;

    //FID
    private Long fid;

    //CONTENT
    private String content;

    //JSON
    private String json;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorkId() {
        return workId;
    }

    public void setWorkId(Long workId) {
        this.workId = workId;
    }

    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	@Override
	public String toString() {
		return "OaWorkVO [id=" + id + ", workId=" + workId + ", fid=" + fid + ", content=" + content + ", json=" + json
				+ ", remark=" + remark + "]";
	}
    
}
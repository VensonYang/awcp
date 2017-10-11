package cn.org.awcp.unit.vo;

import java.io.Serializable;

public class PunUrlFilterVO implements Serializable{
	
	private static final long serialVersionUID = -752465829752347626L;
	private Long id;  
    private String name; //url名称或描述  
    private String url; //地址  
    private String roles; //所需要的角色
    private String permissions; //所需要的权限
    
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRoles() {
		return roles;
	}
	
	public void setRoles(String roles) {
		this.roles = roles;
	}
	
	public String getPermissions() {
		return permissions;
	}
	
	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	@Override
	public String toString() {
		return "PunUrlFilterVO [id=" + id + ", name=" + name + ", url=" + url + ", roles=" + roles + ", permissions="
				+ permissions + "]";
	}

}

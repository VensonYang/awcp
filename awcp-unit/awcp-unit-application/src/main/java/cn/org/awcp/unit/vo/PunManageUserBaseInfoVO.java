package cn.org.awcp.unit.vo;

import cn.org.awcp.core.domain.BaseEntity;

public class PunManageUserBaseInfoVO extends BaseEntity{
	private static final long serialVersionUID = 6210891886254626208L;
	private Long userId;
	private String userName;
	private String userPwd;
	private String userIdCardNumber;
	private String name;
	private String userBirthPlace;
	private String userHouseholdRegist;
	private String userDomicile;
	private String userOfficePhone;
	private String userHousePhone;
	private String mobile;
	private String userFax;
	private String userEmail;
	private String employeeId;
	private String userTitle;
	private String userDossierNumber;
	private String userOfficeNum;
	private Long groupId;
	private String orgCode;
	private String verifyCode;
	
	public PunManageUserBaseInfoVO(){
	}

	public PunManageUserBaseInfoVO(Long userId){
		this.userId = userId;
	}

	public void setUserId(Long value) {
		this.userId = value;
	}
	
	public Long getUserId() {
		return this.userId;
	}
	
	public void setUserName(String value) {
		this.userName = value;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public void setUserPwd(String value) {
		this.userPwd = value;
	}
	
	public String getUserPwd() {
		return this.userPwd;
	}
	
	public void setUserIdCardNumber(String value) {
		this.userIdCardNumber = value;
	}
	
	public String getUserIdCardNumber() {
		return this.userIdCardNumber;
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setUserBirthPlace(String value) {
		this.userBirthPlace = value;
	}
	
	public String getUserBirthPlace() {
		return this.userBirthPlace;
	}
	
	public void setUserHouseholdRegist(String value) {
		this.userHouseholdRegist = value;
	}
	
	public String getUserHouseholdRegist() {
		return this.userHouseholdRegist;
	}
	
	public void setUserDomicile(String value) {
		this.userDomicile = value;
	}
	
	public String getUserDomicile() {
		return this.userDomicile;
	}
	
	public void setUserOfficePhone(String value) {
		this.userOfficePhone = value;
	}
	
	public String getUserOfficePhone() {
		return this.userOfficePhone;
	}
	
	public void setUserHousePhone(String value) {
		this.userHousePhone = value;
	}
	
	public String getUserHousePhone() {
		return this.userHousePhone;
	}
	
	public void setMobile(String value) {
		this.mobile = value;
	}
	
	public String getMobile() {
		return this.mobile;
	}
	
	public void setUserFax(String value) {
		this.userFax = value;
	}
	
	public String getUserFax() {
		return this.userFax;
	}
	
	public void setUserEmail(String value) {
		this.userEmail = value;
	}
	
	public String getUserEmail() {
		return this.userEmail;
	}
	
	public void setEmployeeId(String value) {
		this.employeeId = value;
	}
	
	public String getEmployeeId() {
		return this.employeeId;
	}
	
	public void setUserTitle(String value) {
		this.userTitle = value;
	}
	
	public String getUserTitle() {
		return this.userTitle;
	}
	
	public void setUserDossierNumber(String value) {
		this.userDossierNumber = value;
	}
	
	public String getUserDossierNumber() {
		return this.userDossierNumber;
	}
	
	public void setUserOfficeNum(String value) {
		this.userOfficeNum = value;
	}
	
	public String getUserOfficeNum() {
		return this.userOfficeNum;
	}
	
	public void setGroupId(Long value) {
		this.groupId = value;
	}
	
	public Long getGroupId() {
		return this.groupId;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Override
	public String toString() {
		return "PunManageUserBaseInfoVO [userId=" + userId + ", userName=" + userName + ", userPwd=" + userPwd
				+ ", userIdCardNumber=" + userIdCardNumber + ", name=" + name + ", userBirthPlace=" + userBirthPlace
				+ ", userHouseholdRegist=" + userHouseholdRegist + ", userDomicile=" + userDomicile
				+ ", userOfficePhone=" + userOfficePhone + ", userHousePhone=" + userHousePhone + ", mobile=" + mobile
				+ ", userFax=" + userFax + ", userEmail=" + userEmail + ", employeeId=" + employeeId + ", userTitle="
				+ userTitle + ", userDossierNumber=" + userDossierNumber + ", userOfficeNum=" + userOfficeNum
				+ ", groupId=" + groupId + ", orgCode=" + orgCode + ", verifyCode=" + verifyCode + "]";
	}
		
}


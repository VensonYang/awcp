package cn.org.awcp.unit.vo;

import java.io.Serializable;
import java.util.List;

import cn.org.awcp.unit.core.domain.PunPosition;

public class PunUserBaseInfoVO implements Serializable {

	private static final long serialVersionUID = 312914286556929041L;
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
	private String orgCode;
	private String userStatus;
	private Long groupId;
	private String number;
	private String deptId;// 科室编号
	private String deptName;// 科室名称
	private String signatureImg;
	private String userHeadImg;

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	private Long userRoleId;// 用户与角色关联ID
	private List<Long> roleList;// 用户与角色关联ID
	private List<PunPosition> positionList;
	private Long positionGroupId;
	private Long positionId;
	private String updatePassword;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserIdCardNumber() {
		return userIdCardNumber;
	}

	public void setUserIdCardNumber(String userIdCardNumber) {
		this.userIdCardNumber = userIdCardNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserBirthPlace() {
		return userBirthPlace;
	}

	public void setUserBirthPlace(String userBirthPlace) {
		this.userBirthPlace = userBirthPlace;
	}

	public String getUserHouseholdRegist() {
		return userHouseholdRegist;
	}

	public void setUserHouseholdRegist(String userHouseholdRegist) {
		this.userHouseholdRegist = userHouseholdRegist;
	}

	public String getUserDomicile() {
		return userDomicile;
	}

	public void setUserDomicile(String userDomicile) {
		this.userDomicile = userDomicile;
	}

	public String getUserOfficePhone() {
		return userOfficePhone;
	}

	public void setUserOfficePhone(String userOfficePhone) {
		this.userOfficePhone = userOfficePhone;
	}

	public String getUserHousePhone() {
		return userHousePhone;
	}

	public void setUserHousePhone(String userHousePhone) {
		this.userHousePhone = userHousePhone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserFax() {
		return userFax;
	}

	public void setUserFax(String userFax) {
		this.userFax = userFax;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getUserTitle() {
		return userTitle;
	}

	public void setUserTitle(String userTitle) {
		this.userTitle = userTitle;
	}

	public String getUserDossierNumber() {
		return userDossierNumber;
	}

	public void setUserDossierNumber(String userDossierNumber) {
		this.userDossierNumber = userDossierNumber;
	}

	public String getUserOfficeNum() {
		return userOfficeNum;
	}

	public void setUserOfficeNum(String userOfficeNum) {
		this.userOfficeNum = userOfficeNum;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public List<Long> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Long> roleList) {
		this.roleList = roleList;
	}

	public List<PunPosition> getPositionList() {
		return positionList;
	}

	public void setPositionList(List<PunPosition> positionList) {
		this.positionList = positionList;
	}

	public Long getPositionGroupId() {
		return positionGroupId;
	}

	public void setPositionGroupId(Long positionGroupId) {
		this.positionGroupId = positionGroupId;
	}

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public String getUpdatePassword() {
		return updatePassword;
	}

	public void setUpdatePassword(String updatePassword) {
		this.updatePassword = updatePassword;
	}

	public Long getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(Long userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getSignatureImg() {
		return signatureImg;
	}

	public void setSignatureImg(String signatureImg) {
		this.signatureImg = signatureImg;
	}

	public String getUserHeadImg() {
		return userHeadImg;
	}

	public void setUserHeadImg(String userHeadImg) {
		this.userHeadImg = userHeadImg;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

}

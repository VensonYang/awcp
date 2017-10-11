package cn.org.awcp.unit.core.domain;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import cn.org.awcp.core.common.exception.MRTException;
import cn.org.awcp.core.domain.BaseEntity;
import cn.org.awcp.core.domain.BaseExample;

/**
 * 用户实体类
 * @author Administrator
 *
 */
public class PunUserBaseInfo extends BaseEntity {
	private static final long serialVersionUID = 2471309498373330411L;
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
	private String userStatus;
	private String userDossierNumber;
	private String userOfficeNum;
	private Long groupId;
	private List<PunPosition> positionList;
	private Long id;
	private String orgCode;
	private String number;
	private String deptId;
	private String deptName;
	private String signatureImg;
	private String userHeadImg;

	public PunUserBaseInfo() {
		
	}

	public PunUserBaseInfo(Long userId) {
		this.userId = userId;
	}
	
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

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public List<PunPosition> getPositionList() {
		return positionList;
	}

	public void setPositionList(List<PunPosition> positionList) {
		this.positionList = positionList;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
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
	
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("UserId", getUserId())
				.append("UserName", getUserName()).append("UserPwd", getUserPwd())
				.append("UserIdCardNumber", getUserIdCardNumber()).append("Name", getName())
				.append("UserBirthPlace", getUserBirthPlace()).append("UserHouseholdRegist", getUserHouseholdRegist())
				.append("UserDomicile", getUserDomicile()).append("UserOfficePhone", getUserOfficePhone())
				.append("UserHousePhone", getUserHousePhone()).append("Mobile", getMobile())
				.append("UserFax", getUserFax()).append("UserEmail", getUserEmail())
				.append("EmployeeId", getEmployeeId()).append("UserTitle", getUserTitle())
				.append("UserDossierNumber", getUserDossierNumber()).append("UserOfficeNum", getUserOfficeNum())
				.append("GroupId", getGroupId()).append("deptId", getDeptId()).append("deptName", getDeptName())
				.toString();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getUserId()).toHashCode();
	}

	public boolean equals(Object obj) {
		if (obj instanceof PunUserBaseInfo == false)
			return false;
		if (this == obj)
			return true;
		PunUserBaseInfo other = (PunUserBaseInfo) obj;
		return new EqualsBuilder().append(getUserId(), other.getUserId()).isEquals();
	}

	public static List<PunUserBaseInfo> findAll() throws MRTException {
		return PunUserBaseInfo.getRepository().findAll(PunUserBaseInfo.class);
	}

	public void save() throws MRTException {
		PunUserBaseInfo.getRepository().save(this);
	}

	public void delete() throws MRTException {
		PunUserBaseInfo.getRepository().remove(this);
	}

	public List<PunUserBaseInfo> findByIdCard(BaseExample example) throws MRTException {
		return PunUserBaseInfo.getRepository().selectByExample(PunUserBaseInfo.class, example);
	}

}

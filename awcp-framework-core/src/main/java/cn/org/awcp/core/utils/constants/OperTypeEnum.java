package cn.org.awcp.core.utils.constants;

/**
 * 操作类型枚举
 * @author ljw
 *
 */
public enum OperTypeEnum {
	/**
	 * 所有权限：00000
	 */
	OPER_NONE(0L,"无权限"),
	
	/**
	 * 只读：1
	 */
	OPER_READ(1L,"只读"),
	
	/**
	 * 只写：2
	 */
	OPER_WRITE(2L,"只写"),
	
	/**
	 * 读写：3
	 */
	OPER_READ_AND_WRITE(3L,"读写"),
	/**
	 * 所有权限：11111
	 */
	OPER_ALL(11111L,"所有权限");
		
	private Long key;
	private String value = "";
	
	private OperTypeEnum(Long key , String value){
		this.key = key;
		this.value = value;
	}
	public Long getkey() {
		return key;
	}
	public String getvalue() {
		return value;
	}
	
	/**
	 * 根据key获取枚举
	 * @param key
	 * @return
	 */
	public static OperTypeEnum getResourceType(Long key){
		for (OperTypeEnum operTypeEnum : values()) {
			if (operTypeEnum.getkey() == key) {
				return operTypeEnum;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public static String getResourceTypeValue(Long key) {
		for (OperTypeEnum operTypeEnum : values()) {
			if (operTypeEnum.getkey() == key){
				return operTypeEnum.getvalue();
			}
		}
		return null;
	}
}

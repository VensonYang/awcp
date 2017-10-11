package cn.org.awcp.core.common.exception;

public class ErrorException extends Exception{
	private static final long serialVersionUID = -6837708164187953673L;
	public int errorCode;
	
	public ErrorException(int errorCode){
		this.errorCode = errorCode;
	}
	
	public int getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}	
}

package cn.org.awcp.unit.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 通用AJAX返回值；
 * @author Bobo
 *
 */
public class PunAJAXStatusVO implements Serializable{
	
	private static final long serialVersionUID = 2694530406919364544L;

	/**
	 * 状态码，“0”表示成功，非“0”表示失败的错误代码；
	 */
	private int status;
	
	/**
	 * 错误信息，“status"是非”0“时才有意义；
	 */
	private String message;
	
	private Date maturedDate;
	
	private String signature;
	
	/**
	 * 返回数据；
	 */
	private Object data;

	public PunAJAXStatusVO(){
		status = 0;
		message = "";
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public Date getMaturedDate() {
		return maturedDate;
	}
	
	public void setMaturedDate(Date maturedDate) {
		this.maturedDate = maturedDate;
	}
	
	public String getSignature() {
		return signature;
	}
	
	public void setSignature(String signature) {
		this.signature = signature;
	}

	@Override
	public String toString() {
		return "PunAJAXStatusVO [status=" + status + ", message=" + message + ", maturedDate=" + maturedDate
				+ ", signature=" + signature + ", data=" + data + "]";
	}

}

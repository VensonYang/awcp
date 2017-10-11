package cn.org.awcp.core.common.exception;

/**
 * 权限相关运行时异常
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class MAuthRTException extends MRTException {

	private static final long serialVersionUID = 8827061579511630246L;

	public MAuthRTException() {
        super();
    }

    public MAuthRTException(String string) {
        super(string);
    }

    public MAuthRTException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public MAuthRTException(Throwable throwable) {
        super(throwable);
    }
}

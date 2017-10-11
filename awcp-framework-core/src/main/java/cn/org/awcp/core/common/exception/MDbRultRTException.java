package cn.org.awcp.core.common.exception;

/**
 * 数据库规则相关运行时异常
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
public class MDbRultRTException extends MRTException {

	private static final long serialVersionUID = 5635517056832050318L;

	public MDbRultRTException() {
        super();
    }

    public MDbRultRTException(String string) {
        super(string);
    }

    public MDbRultRTException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public MDbRultRTException(Throwable throwable) {
        super(throwable);
    }
}

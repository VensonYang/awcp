package org.szcloud.framework.metadesigner.core.generator.util.paranamer;

public class ParameterNamesNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -6220481931016987642L;
	public static final String __PARANAMER_DATA = "v1.0 \n" + "<init> java.lang.String message \n";
    private Exception cause;

    public ParameterNamesNotFoundException(String message, Exception cause) {
        super(message);
        this.cause = cause;
    }

    public ParameterNamesNotFoundException(String message) {
        super(message);
    }

    public Throwable getCause() {
        return cause;
    }
}

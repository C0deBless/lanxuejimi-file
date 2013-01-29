package tech.exception;

public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = -8627835042329042318L;

	public ServiceException(Throwable e) {
		super(e);
	}
}
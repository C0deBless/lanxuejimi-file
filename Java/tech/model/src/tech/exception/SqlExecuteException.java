package tech.exception;


public class SqlExecuteException extends RuntimeException {
	public SqlExecuteException(Throwable e) {
		super(e);
	}

	private static final long serialVersionUID = -2414903687516223600L;

}

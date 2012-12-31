package tech.service;

public final class ServiceResult<T> {

	private boolean successful;
	private T result;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public T getResult() {
		return result;
	}

	public void setResult(final T result) {
		this.result = result;
	}

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(final boolean successful) {
		this.successful = successful;
	}

	public static final class Null {
		private Null() {
		}
	}
}

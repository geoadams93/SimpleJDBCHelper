package jdbc_helper.exception;

public class RuntimeSQLException extends RuntimeException
{
	/**
	 * Generated.
	 */
	private static final long serialVersionUID = 4177783454671788691L;

	public RuntimeSQLException()
	{
		super();
	}

	public RuntimeSQLException(String s)
	{
		super(s);
	}

	public RuntimeSQLException(String s, Throwable throwable)
	{
		super(s, throwable);
	}

	public RuntimeSQLException(Throwable throwable)
	{
		super(throwable);
	}
}

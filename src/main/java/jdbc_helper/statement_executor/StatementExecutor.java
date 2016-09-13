package jdbc_helper.statement_executor;

import java.sql.ResultSet;

public interface StatementExecutor
{	
	public int executeStatement();
	
	public ResultSet executeQuery();
}

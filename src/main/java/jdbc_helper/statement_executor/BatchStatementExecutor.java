package jdbc_helper.statement_executor;

import java.sql.ResultSet;

public class BatchStatementExecutor implements StatementExecutor
{
	private int batchSize;
	
	private BatchStatementExecutor()
	{
		
	}
	
	@Override
	public int executeStatement()
	{
		return 0;
	}

	@Override
	public ResultSet executeQuery()
	{
		// TODO Auto-generated method stub
		return null;
	}

}

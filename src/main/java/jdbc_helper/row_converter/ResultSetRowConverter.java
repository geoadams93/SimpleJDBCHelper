package jdbc_helper.row_converter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import jdbc_helper.exception.RuntimeSQLException;

public class ResultSetRowConverter
{	
	public String getRowResultAsString(ResultSet rs, String columnLabel)
	{
		try
		{
			return rs.getString(columnLabel);
		}
		catch (SQLException e)
		{
			throw new RuntimeSQLException();
		}
	}

	public LocalDateTime getRowResultAsLocalDateTime(ResultSet rs, String columnLabel)
	{
		try
		{
			return LocalDateTime.ofInstant(rs.getTimestamp(columnLabel).toInstant(), ZoneOffset.ofHours(0));
		}
		catch (SQLException e)
		{
			throw new RuntimeSQLException();
		}
	}

	public short getRowResultAsShort(ResultSet rs, String columnLabel)
	{
		try
		{
			return rs.getShort(columnLabel);
		}
		catch (SQLException e)
		{
			throw new RuntimeSQLException();
		}
	}

	public int getRowResultAsInt(ResultSet rs, String columnLabel)
	{
		try
		{
			return rs.getInt(columnLabel);
		}
		catch (SQLException e)
		{
			throw new RuntimeSQLException();
		}
	}

	public long getRowResultAsLong(ResultSet rs, String columnLabel)
	{
		try
		{
			return rs.getLong(columnLabel);
		}
		catch (SQLException e)
		{
			throw new RuntimeSQLException();
		}
	}
}

package jdbc_helper;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.function.BiFunction;

public class DatabaseTableTypeBinder
{
	private static HashMap<String, BiFunction<ResultSet, String, ?>> sharedConversionStorage;
	private HashMap<String, BiFunction<ResultSet, String, ?>> instanceConversionStorage;
	
	public static void setSharedColumnDatatypeAssociation(String columnname, BiFunction<ResultSet, String, ?> resultSetConversion)
	{
		sharedConversionStorage.put(columnname, resultSetConversion);
	}
	
	public void setColumnDatatypeAssociation(String columnname, BiFunction<ResultSet, String, ?> resultSetConversion)
	{
		instanceConversionStorage.put(columnname, resultSetConversion);
	}

	@SuppressWarnings("unchecked")
	public <R> R getRowFromResultSet(String rowName)
	{
		return (R) instanceConversionStorage.getOrDefault(rowName, instanceConversionStorage.getOrDefault(rowName, null));
	}
}

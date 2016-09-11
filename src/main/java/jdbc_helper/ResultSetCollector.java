package jdbc_helper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class ResultSetCollector
{
	private static ResultSetCollector instance = new ResultSetCollector();
	
	private ResultSetCollector()
	{
		
	}
	
	public static ResultSetCollector get()
	{
		return instance;
	}
	
	public <T> T getSingleResult(ResultSet results, Function<ResultSet, T> conversion) throws SQLException
	{
		return results.next() ? conversion.apply(results) : null;
	}

	public <T> Optional<T> getOptionalResult(ResultSet results, Function<ResultSet, T> conversion) throws SQLException
	{
		return Optional.ofNullable(conversion.apply(results));
	}
	
	public <T> Collection<T> getAllResults(ResultSet results, Function<ResultSet, T> conversion) throws SQLException
	{
		List<T> listToReturn = new ArrayList<>();
		while (results.next())
		{
			listToReturn.add(conversion.apply(results));
		}

		return listToReturn;
	}
	
	public <T> Collection<T> getAllResults(ResultSet results, Function<ResultSet, T> conversion, Collection<T> containerCollection) throws SQLException
	{
		while (results.next())
		{
			containerCollection.add(conversion.apply(results));
		}

		return containerCollection;
	}
	
	public <T, C extends Collection<T>> Collection<T> getAllResults(ResultSet results, Function<ResultSet, T> conversion, Supplier<C> collectionConstructor) throws SQLException
	{
		Collection<T> collectionToReturn = collectionConstructor.get();
		while (results.next())
		{
			collectionToReturn.add(conversion.apply(results));
		}

		return collectionToReturn;
	}

	public <K, V> Map<K, V> getAllResultsAsMap(ResultSet results, Function<ResultSet, Map.Entry<K, V>> conversion) throws SQLException
	{
		Map<K,V> mapToReturn = new HashMap<>();
		Map.Entry<K, V> entry;
		while (results.next())
		{
			entry = conversion.apply(results);
			mapToReturn.put(entry.getKey(), entry.getValue());
		}

		return mapToReturn;
	}
	
	public <K, V> Map<K, V> getAllResultsAsMap(ResultSet results, Function<ResultSet, Map.Entry<K, V>> conversion, Map<K, V> containerMap) throws SQLException
	{
		Map.Entry<K, V> entry;
		while (results.next())
		{
			entry = conversion.apply(results);
			containerMap.put(entry.getKey(), entry.getValue());
		}

		return containerMap;
	}
	
	public <K, V, M extends Map<K, V>> Map<K, V> getAllResultsAsMap(ResultSet results, Function<ResultSet, Map.Entry<K, V>> conversion, Supplier<M> mapConstructor) throws SQLException
	{
		Map<K,V> mapToReturn = mapConstructor.get();
		Map.Entry<K, V> entry;
		while (results.next())
		{
			entry = conversion.apply(results);
			mapToReturn.put(entry.getKey(), entry.getValue());
		}

		return mapToReturn;
	}
}

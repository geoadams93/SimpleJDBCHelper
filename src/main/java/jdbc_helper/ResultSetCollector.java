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
import java.util.stream.Collector;

/**
 * Used as the endpoint for JDBC operations, this class contains methods that
 * accept a {@link ResultSet} and a {@link Function} that is used to convert the
 * {@link ResultSet} to usable Java Objects or Collections.
 */
public class ResultSetCollector
{
	private static ResultSetCollector instance = new ResultSetCollector();

	private ResultSetCollector()
	{

	}

	/**
	 * Gets an instance of a {@link ResultSetCollector}. Will return a singleton
	 * instance.
	 * 
	 * @return The {@link ResultSetCollector} instance.
	 */
	public static ResultSetCollector get()
	{
		return instance;
	}

	/**
	 * Applies the provided {@link Function} to the first row in the
	 * {@link ResultSet} to return a single value. If the {@link ResultSet}
	 * contains no rows, {@code null} will be returned.
	 * 
	 * @param results The {@link ResultSet} to get the row's data from.
	 * @param conversion The {@link Function} that will be used to convert the
	 *        {@link ResultSet}'s row.
	 * @return The result of converting the first row of the {@link ResultSet}
	 *         with the provided {@link Function}.
	 * @throws SQLException If the reading of the {@link ResultSet} fails.
	 */
	public <T> T getSingleRow(ResultSet results, Function<ResultSet, T> conversion) throws SQLException
	{
		return results.next() ? conversion.apply(results) : null;
	}

	/**
	 * Applies the provided {@link Function} to the first row in the
	 * {@link ResultSet} to return a single value wrapped in an {@link Optional}
	 * .
	 * 
	 * @param results The {@link ResultSet} to get the row's data from.
	 * @param conversion The {@link Function} that will be used to convert the
	 *        {@link ResultSet}'s row.
	 * @return The result of converting the first row of the {@link ResultSet}
	 *         with the provided {@link Function}, wrapped in an
	 *         {@link Optional}.
	 * @throws SQLException If the reading of the {@link ResultSet} fails.
	 */
	public <T> Optional<T> getOptionalRow(ResultSet results, Function<ResultSet, T> conversion) throws SQLException
	{
		return Optional.ofNullable(results.next() ? conversion.apply(results) : null);
	}

	/**
	 * Applies the provided {@link Function} to all the rows in the
	 * {@link ResultSet} to return the values in a {@link Collection}.
	 * 
	 * @param results The {@link ResultSet} to get the row's data from.
	 * @param conversion The {@link Function} that will be used to convert the
	 *        {@link ResultSet}'s row.
	 * @return The result of converting the rows of the {@link ResultSet} with
	 *         the provided {@link Function}, stored in a {@link Collection}. No
	 *         guarantees are made about the type, thread safety, or efficiency
	 *         of the {@link Collection}.
	 * @throws SQLException If the reading of the {@link ResultSet} fails.
	 */
	public <T> Collection<T> getAllRows(ResultSet results, Function<ResultSet, T> conversion) throws SQLException
	{
		List<T> listToReturn = new ArrayList<>();
		while (results.next())
		{
			listToReturn.add(conversion.apply(results));
		}

		return listToReturn;
	}

	/**
	 * Applies the provided {@link Function} to all the rows in the
	 * {@link ResultSet} to return the values in the passed in
	 * {@link Collection}.
	 * 
	 * @param results The {@link ResultSet} to get the row's data from.
	 * @param conversion The {@link Function} that will be used to convert the
	 *        {@link ResultSet}'s row.
	 * @param containerCollection The {@link Collection} to deposit the
	 *        converted values into.
	 * @return The result of converting the rows of the {@link ResultSet} with
	 *         the provided {@link Function}, stored in the passed in
	 *         {@link Collection} .
	 * @throws SQLException If the reading of the {@link ResultSet} fails.
	 */
	public <T, C extends Collection<T>> C getAllRows(ResultSet results, Function<ResultSet, T> conversion, C containerCollection) throws SQLException
	{
		while (results.next())
		{
			containerCollection.add(conversion.apply(results));
		}

		return containerCollection;
	}

	/**
	 * Applies the provided {@link Function} to all the rows in the
	 * {@link ResultSet} to return the values that will be returned inside a new
	 * collection that is created with the given {@link Supplier}.
	 * 
	 * @param results The {@link ResultSet} to get the row's data from.
	 * @param conversion The {@link Function} that will be used to convert the
	 *        {@link ResultSet}'s row.
	 * @param collectionConstructor The {@link Supplier} that will be used to
	 *        create a new {@link Collection} to deposit the converted values
	 *        into.
	 * @return The result of converting the rows of the {@link ResultSet} with
	 *         the provided {@link Function}, stored in a new instance of the
	 *         {@link Collection}.
	 * @throws SQLException If the reading of the {@link ResultSet} fails.
	 */
	public <T, C extends Collection<T>> Collection<T> getAllRows(ResultSet results, Function<ResultSet, T> conversion, Supplier<C> collectionConstructor) throws SQLException
	{
		Collection<T> collectionToReturn = collectionConstructor.get();
		while (results.next())
		{
			collectionToReturn.add(conversion.apply(results));
		}

		return collectionToReturn;
	}
	
	public <T, A, R> R getAllRows(ResultSet results, Function<ResultSet, T> conversion, Collector<T, A, R> collector) throws SQLException
	{
		A accumulator  = collector.supplier().get();
		while (results.next())
		{
			collector.accumulator().accept(accumulator , conversion.apply(results));
		}

		return collector.finisher().apply(accumulator);
	}

	public <K, V> Map<K, V> getAllRowsAsMap(ResultSet results, Function<ResultSet, Map.Entry<K, V>> conversion) throws SQLException
	{
		Map<K, V> mapToReturn = new HashMap<>();
		Map.Entry<K, V> entry;
		while (results.next())
		{
			entry = conversion.apply(results);
			mapToReturn.put(entry.getKey(), entry.getValue());
		}

		return mapToReturn;
	}

	public <K, V> Map<K, V> getAllRowsAsMap(ResultSet results, Function<ResultSet, Map.Entry<K, V>> conversion, Map<K, V> containerMap) throws SQLException
	{
		Map.Entry<K, V> entry;
		while (results.next())
		{
			entry = conversion.apply(results);
			containerMap.put(entry.getKey(), entry.getValue());
		}

		return containerMap;
	}

	public <K, V, M extends Map<K, V>> Map<K, V> getAllRowsAsMap(ResultSet results, Function<ResultSet, Map.Entry<K, V>> conversion, Supplier<M> mapConstructor) throws SQLException
	{
		Map<K, V> mapToReturn = mapConstructor.get();
		Map.Entry<K, V> entry;
		while (results.next())
		{
			entry = conversion.apply(results);
			mapToReturn.put(entry.getKey(), entry.getValue());
		}

		return mapToReturn;
	}
}

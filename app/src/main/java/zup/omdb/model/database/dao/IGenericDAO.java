package zup.omdb.model.database.dao;

import java.sql.SQLException;
import java.util.List;

import zup.omdb.model.request.Movie;

/**
 * Created by Yumi.
 * <p>
 * <p>
 * Listener padrão do BD
 */
public interface IGenericDAO <T extends Movie> {

	public void persist(final T entity) throws SQLException;

	public void update(final T entity) throws SQLException;

	public void delete(final T entity) throws SQLException;
	
	public List<T> getAllPersistedObjects() throws SQLException;

	public List<T> getPersistedObjectByColumn(final String name, final String column) throws SQLException;
}

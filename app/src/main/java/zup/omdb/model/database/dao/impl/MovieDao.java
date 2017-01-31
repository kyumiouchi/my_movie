package zup.omdb.model.database.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import zup.omdb.model.database.DataBaseManager;
import zup.omdb.model.database.dao.IGenericDAO;
import zup.omdb.model.request.Movie;

/**
 * Created by Yumi.
 * <p>
 * <p>
 * Acessa o dataManager
 */

public class MovieDao implements IGenericDAO<Movie> {

    private DataBaseManager dbManager;

    public MovieDao() {
        dbManager = DataBaseManager.getInstance();
    }

    @Override
    public void persist(Movie entity) throws SQLException {
        dbManager.persistObject(Movie.class, entity);
    }

    @Override
    public void update(Movie entity) throws SQLException {
        dbManager.updateObject(Movie.class, entity);

    }

    @Override
    public void delete(Movie entity) throws SQLException {
        dbManager.deleteObject(Movie.class, entity);
    }

    @Override
    public List<Movie> getAllPersistedObjects() throws SQLException {

        final List<Movie> list = dbManager.getAllObjects(Movie.class);

        final CopyOnWriteArrayList<Movie> newList = new CopyOnWriteArrayList<>();

        if (list != null) {
            for (Movie movie : list) {
                newList.add(movie);
            }
        }
        return newList;
    }

    @Override
    public List<Movie> getPersistedObjectByColumn(String name, String columnName) throws SQLException {

        final List<Movie> list = dbManager.getObjectsByField(Movie.class, columnName, name);

        CopyOnWriteArrayList<Movie> newList = new CopyOnWriteArrayList<Movie>();
        newList.addAll(list);

        return newList;
    }

}

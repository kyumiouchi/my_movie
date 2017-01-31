package zup.omdb.model.bo;

import android.app.Dialog;

import java.sql.SQLException;
import java.util.List;

import zup.omdb.model.database.dao.impl.MovieDao;
import zup.omdb.model.request.Movie;
import zup.omdb.util.Constants;
import zup.omdb.util.CustomLog;

/**
 * Created by Yumi.
 * <p>
 * <p>
 * Business Object tratamento do Movie DAO
 */

public class MovieBO {

    private final static Object SYNC_INST = new Object();
    private static MovieBO instance;
    private final MovieDao movieDao;
    private Dialog dialog;

    public static MovieBO getInstance() {

        synchronized (SYNC_INST) {

            if (instance == null) {
                instance = new MovieBO();
            }

            return instance;
        }
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }


    private MovieBO() {
        movieDao = new MovieDao();
    }

    public List<Movie> getAllPersistedMovies() throws SQLException {
        return movieDao.getAllPersistedObjects();
    }

    public void saveMovie(Movie movie) throws SQLException {
        movieDao.persist(movie);
    }

    public void updateMovie(Movie movie) throws SQLException {
        movieDao.update(movie);
    }

    public void deleteMovie(Movie movie) throws SQLException {
        movieDao.delete(movie);
    }

    public Movie getMovieByName(String param) throws SQLException  {

        List<Movie> listMovie = movieDao.getPersistedObjectByColumn(param, Constants.COLUMN_NAME);
        if (listMovie != null && listMovie.size() > 0) {
            return listMovie.get(0);
        }
        return null;
    }

    public Movie getMovieByImdbID(String param) {

        CustomLog.error("getMovieByImdbID " + param);
        try {
            List<Movie> listMovie = movieDao.getPersistedObjectByColumn(param, Constants.COLUMN_IMDBID);
            if (listMovie != null && listMovie.size() > 0) {
                CustomLog.error("listMovie " + listMovie.size());
                return listMovie.get(0);
            }
        } catch (SQLException e){
            CustomLog.error("getMovieByImdbID " + e.getMessage());
        }
        return null;
    }
}

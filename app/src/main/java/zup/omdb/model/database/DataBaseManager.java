package zup.omdb.model.database;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import zup.omdb.model.request.Movie;
import zup.omdb.util.CustomLog;

/**
 * Created by Yumi.
 * <p>
 * <p>
 * Gerencia o DB
 */
public class DataBaseManager {

	static private DataBaseManager instance;
	private DataBaseHelper helper;
	private final static Object SYNC_INST = new Object();

	private DataBaseManager(Context ctx) {
		helper = new DataBaseHelper(ctx);
	}

	static public void init(Context ctx) {
		synchronized (SYNC_INST) {
			if (null == instance) {
				instance = new DataBaseManager(ctx);
			}
		}
	}

	static public DataBaseManager getInstance() {
		return instance;
	}

	@SuppressWarnings("unused")
	private DataBaseHelper getHelper() {
		return this.helper;
	}

	public <T extends Movie> List<T> getAllObjects(final Class<T> className) throws SQLException {

		List<T> list = new ArrayList<T>();
		CustomLog.error("getAllObjects className");
		try {
			list = helper.getDao(className).queryForAll();
		} catch (Exception e) {
			CustomLog.error("Error:getAllObjects:"+e.getMessage());
		}
		return list;
	}

	public <T extends Movie> T getObjectById(final Class<T> className, final Long idObj) {
		CustomLog.error("getObjectById");
		T object = null;

		try {

			final Dao<T, Long> dao = helper.getDao(className);

			object = dao.queryForId(idObj);

		} catch (SQLException e) {
			CustomLog.error("ERRO getObjById: " + e.getMessage());
		}

		return object;
	}

	public <T extends Movie> void persistObject(final Class<T> className, final T object) throws SQLException {
		CustomLog.error("persist className DataBaseManager: " + className.getName());
		helper.getDao(className).create(object);
	}

	public <T extends Movie> void updateObject(final Class<T> className, final T object) throws SQLException {
		CustomLog.error("updateObject id:" + (object).getId());
		helper.getDao(className).update(object);
	}

	public <T extends Movie> void deleteObject(final Class<T> className, final T object) throws SQLException {
		CustomLog.error("deleteObject id:" + (object).getId());
		try {
			helper.getDao(className).delete(object);
		} catch (Exception e) {
			CustomLog.error("ERRO deleteObject: " + e.getMessage());
		}
	}

	public <T extends Movie> List<T> getObjectsByField(final Class<T> className, String fieldName, Object value) {
		CustomLog.error("getObjectsByField");
		List<T> list = null;
		try {
			list = helper.getDao(className).queryForEq(fieldName, value);
		} catch (SQLException e) {
			CustomLog.error("ERRO getObjByField: " + e.getMessage());
		}

		return list;
	}
}

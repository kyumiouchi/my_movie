package zup.omdb.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import zup.omdb.model.request.Movie;
import zup.omdb.util.CustomLog;


/**
 * Created by Yumi.
 * <p>
 * <p>
 * Acessa o DB da aplicação
 */
public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "moviedb.sqlite";
    private static final int DATABASE_VERSION = 1;


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            CustomLog.error("Create TableUtils: " + DataBaseHelper.class.getName());
            TableUtils.createTableIfNotExists(connectionSource, Movie.class);

        } catch (SQLException e) {
            CustomLog.error("SQLException " + e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            CustomLog.error("Upgrade: " + DataBaseHelper.class.getName());
            TableUtils.dropTable(connectionSource, Movie.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            CustomLog.error("SQLException " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        super.close();
    }

    @SuppressWarnings("unchecked")
    public <D extends Dao<T, ?>, T> D getDAO(final Class<T> clazz) {

        Dao<T, ?> dao = null;
        try {
            dao = getDao(clazz);

            if(clazz == Movie.class){
                CustomLog.error("Movie.class - DAO...");
            }
        } catch (SQLException e) {
            CustomLog.error("SQLException " + e.getMessage());
        }

        return (D) dao;
    }

    public static int getDatabaseVersion() {
        return DATABASE_VERSION;
    }


}

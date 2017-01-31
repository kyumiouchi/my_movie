package zup.omdb.model.bo;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import org.apache.commons.lang3.StringUtils;

import zup.omdb.model.request.Movie;
import zup.omdb.util.Constants;
import zup.omdb.util.CustomLog;
import zup.omdb.util.Utils;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by Yumi.
 * <p>
 * <p>
 * Business Object da aplicação
 */

public class OmdbBO {
    private static OmdbBO instance;

    private DownloadManager downloadManager;
    private Movie itemMovie;

    private long lastDownload;

    public static OmdbBO getInstance() {

        if (instance == null) {
            instance = new OmdbBO();
        }

        return instance;
    }

    private OmdbBO() {
    }

    public Movie getItemMovie() {
        return itemMovie;
    }

    public void setItemMovie(Movie itemMovie) {
        this.itemMovie = itemMovie;
    }

    public DownloadManager getDownloadManager() {
        return downloadManager;
    }

    public boolean downloadImage(Movie movie, Context context) {

        boolean startedDownload = false;
        String fileName = Utils.saveFile(movie);

        if (StringUtils.isNotEmpty(fileName) && movie.getPoster() != null && !movie.getPoster().toString().trim().equalsIgnoreCase("N/A")) {
            downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
            Uri downloadUri = Uri.parse(movie.getPoster());
            DownloadManager.Request request = new DownloadManager.Request(downloadUri);

            //Restrict the types of networks over which this download may proceed.
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
            //Set whether this download may proceed over a roaming connection.
            request.setAllowedOverRoaming(false);
            //Set the local destination for the downloaded file to a path within the public external storage directory
            request.setDestinationInExternalPublicDir(Constants.FILE_DIR_NAME, fileName);

            //Enqueue a new download and same the referenceId
            lastDownload = downloadManager.enqueue(request);
            startedDownload = true;
        }
        return startedDownload;
    }

    public boolean queryStatus() {
        if(lastDownload < 0)
            return  false;

        Cursor c = downloadManager.query(new DownloadManager.Query().setFilterById(lastDownload));


        if (c == null) {
            CustomLog.error("Download not found!");
        } else {
            c.moveToFirst();
            int result = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));

            switch(result) {
                case DownloadManager.STATUS_FAILED:
                    CustomLog.error("Download STATUS_FAILED");
                    break;

                case DownloadManager.STATUS_PAUSED:
                    CustomLog.error("Download STATUS_PAUSED");
                    return true;

                case DownloadManager.STATUS_PENDING:
                    CustomLog.error("Download STATUS_PENDING");
                    break;

                case DownloadManager.STATUS_RUNNING:
                    CustomLog.error("Download STATUS_RUNNING");
                    break;

                case DownloadManager.STATUS_SUCCESSFUL:
                    CustomLog.error("Download STATUS_SUCCESSFUL");
                    return true;

                default:
                    break;
            }
        }
        return false;
    }

    public long getLastDownload() {
        return lastDownload;
    }
}

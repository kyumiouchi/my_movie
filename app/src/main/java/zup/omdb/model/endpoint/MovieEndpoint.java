package zup.omdb.model.endpoint;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import zup.omdb.model.request.Movie;

/**
 * Created by Yumi.
 * <p>
 * <p>
 * Endpoint do filme
 */

public interface MovieEndpoint {
    @GET("/")
    Call<Movie> getDataMovie(@Query("t") String movie, @Query("plot") String sort, @Query("r") String type);
}

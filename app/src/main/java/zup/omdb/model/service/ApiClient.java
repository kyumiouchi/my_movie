package zup.omdb.model.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import zup.omdb.util.Constants;

/**
 * Created by Yumi.
 * <p>
 * <p>
 * Classe responsável em acessa a url e retornar um Gson
 */

public class ApiClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
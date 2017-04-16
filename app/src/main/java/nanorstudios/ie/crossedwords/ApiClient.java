package nanorstudios.ie.crossedwords;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * TODO Update this line
 */

public class ApiClient {

    private static final String baseUrl = "https://wordsapiv1.p.mashape.com/words/";

    private static WordService sWordService;

    public static WordService getWordService() {
        if (sWordService == null) {

            Interceptor interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain
                            .request()
                            .newBuilder()
                            .addHeader("X-Mashape-Key", "UUqvJBUTjfmshSQqzRvgAPsBn8Azp1zvxnWjsnHMTXlMxfAcQc")
                            .addHeader("Accept", "application/json")
                            .build();
                    return chain.proceed(request);
                }
            };

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.interceptors().add(interceptor);
            OkHttpClient client = builder.build();


            sWordService = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
                    .create(WordService.class);
        }

        return sWordService;
    }
}
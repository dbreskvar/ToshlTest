package dbug.toshltest.network;

import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    private static Retrofit retrofit;

    private static String API_BASE_URL = "https://api.toshl.com/";

    private static final String API_KEY = "";
    private static /*final*/ String secretKey = "";
    protected static final String clientID = "";
    protected static final String clientName = "";

    private static class MyDeserializer implements JsonDeserializer<SyncResponse> {
        @Override
        public SyncResponse deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
            // Get the "content" element from the parsed JSON
            JsonElement content = je.getAsJsonObject().get("data");

            // Deserialize it. You use a new instance of Gson to avoid infinite recursion
            // to this deserializer
            return new Gson().fromJson(content, SyncResponse.class);
        }
    }

    public static <S> S setupRestClient(Class<S> apiClass, final String url) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(SyncResponse.class, new MyDeserializer())
                .create();
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                //If additional API KEY is requested, add it here under headers
                Request.Builder builder = request.newBuilder()
                        .header("Content-Type", "application/json")
                        .method(request.method(), request.body());

                //builder.header("Authorization", token);

                Request main = builder.build();

                return chain.proceed(main);
            }
        });

        OkHttpClient client = httpClient.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        setRetrofit(retrofit);

        return retrofit.create(apiClass);
    }

    private static void setRetrofit(Retrofit mRetro) {
        retrofit = mRetro;
    }

    public static Retrofit retrofit() {
        if (retrofit != null) return retrofit;
        else return null;
    }
}

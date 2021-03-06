package dbug.toshltest.network;

import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    private static Retrofit retrofit;

    private static String API_BASE_URL = "https://api.toshl.com/";

    private static final String API_KEY = "57814ff5-e790-414c-aa1c-424184b2803ac3b76cfc-ce50-4bf9-a7f7-ab73b76b1fd7";
    private static final String secretKey = "";
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

    public static <S> S setupRestClient(Class<S> apiClass, @Nullable final String token) {
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

                if (token != null) builder.header("Authorization", token);
                else builder.header("Authorization", API_KEY);

                Request main = builder.build();

                return chain.proceed(main);
            }
        });

        OkHttpClient client = httpClient.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(new NullOnEmptyConverterFactory())
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

    public static class NullOnEmptyConverterFactory extends Converter.Factory {

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
            return new Converter<ResponseBody, Object>() {
                @Override
                public Object convert(ResponseBody body) throws IOException {
                    if (body.contentLength() == 0) return null;
                    return delegate.convert(body);                }
            };
        }
    }

}

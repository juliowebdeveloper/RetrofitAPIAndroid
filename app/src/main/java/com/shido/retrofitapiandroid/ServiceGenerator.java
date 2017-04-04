package com.shido.retrofitapiandroid;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mira on 04/04/2017.
 */

public class ServiceGenerator {

    private static String BASE_URL = "https://api.github.com/";

    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();


    public static void changeApiBaseUrl(String newBaseUrl){
        BASE_URL = newBaseUrl;
    }


    public static <S> S createService(Class<S> serviceClass){
        /* Logging with Retrofit 2 is done by an interceptor called HttpLoggingInterceptor. You’ll need to add
an instance of this interceptor to the OkHttpClient. For example, you could solve it the following
way: */
        if(!httpClient.interceptors().contains(logging)){
            httpClient.addInterceptor(logging);
            builder.client(httpClient.build());
            retrofit = builder.build();
        }
        return retrofit.create(serviceClass);
    }




    /*
    The ServiceGenerator class uses Retrofit’s Retrofit builder to create a new REST client with the
given API base url (BASE_URL). For example, GitHub’s API base url is located at https://api.github.com/
and you must update the provided example url with your own url to call your API instead of
GitHub’s.
The createService method takes a serviceClass, which is the annotated interface for API requests,
as a parameter and creates a usable client from it. On the resulting client you’ll be able to execute
your network requests.



You might wonder why we use static fields and methods within the ServiceGenerator class.
Actually, it has one simple reason: we want to use the same objects (OkHttpClient, Retrofit, …)
throughout the app to just open one socket connection that handles all the request and responses
including caching and many more. It’s common practice to just use one OkHttpClient instance to
reuse open socket connections. That means, we either need to inject the OkHttpClient to this class
via dependency injection or use a static field. As you can see, we chose to use the static field. And
because we use the OkHttpClient throughout this class, we need to make all fields and methods
static.

     */


}

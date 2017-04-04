package com.shido.retrofitapiandroid.interfaces;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;

/**
 * Created by mira on 04/04/2017.
 */

public interface FutureStudioClient {

    @GET("/user/info")
   // Call<UserInfo> getUserInfo();

    @PUT("/user/info")
   // Call<UserInfo> updateUserInfo(@Body UserInfo userInfo);

    /*
    @Body: send Java objects as request body
    • @Url: use dynamic URLs
    • @Field: send data as form-urlencoded

     */

    @DELETE("/user")
    Call<Void> deleteUser();

    // example for passing a full URL
   // @GET("https://futurestud.io/tutorials/rss/")
   // Call<FutureStudioRssFeed> getRssFeed();

   // @GET("/tutorials")
   // Call<List<Tutorial>> getTutorials(@Query("page") Integer page, @Query ("order") String order, @Query("author") String author);

/*
Another large part of dynamic URLs are query parameters. If you’ve used our filters already, you’ve
seen them on our website: https://futurestud.io/tutorials?filter=video. The ?filter=video
is a query parameter which further describes the request resource. Unlike the path parameters, you
don’t need to add them to the annotation URL. You can simply add a method parameter with
@Query() and a query parameter name, describe the type and you’re good to go. Retrofit will
automatically attach it to the request. If you pass a null value as query parameter, Retrofit will
ignore it. You can also add multiple query parameters.
In the example above, you could also remove the first getTutorials() method and just use the
second one by passing a null value as the value for the last three parameters.
This was only an introduction to describing API endpoints. You’ve learned the basics of adding new
endpoints to your interfaces. You can adjust the resource locations, the HTTP method, return types,
path and query parameters.
 */


}

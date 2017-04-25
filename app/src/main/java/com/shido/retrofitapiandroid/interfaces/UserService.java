package com.shido.retrofitapiandroid.interfaces;

import com.shido.retrofitapiandroid.GitHubRepo;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by mira on 05/04/2017.
 */

public interface UserService {

    /*Dynamic Urls for Requests */

    @GET
    public Call<ResponseBody> profilePicture(@Url String url);


/*How Urls Resolve Against Your Base Url
That’s another interesting part and needs attention: how will the dynamic url be resolved against
the defined base url? Retrofit uses OkHttp’s HttpUrl and resolves each endpoint url like a link on
any website (<a href="">…</a>).
Let’s touch some scenarios and at first look at a url that points to a profile picture stored on Amazon
S3. For this example, we’ll use the UserService from above. Within the following snippet, we define
a base url and the actual call of the profilePicture method uses a completely different one.*/


    //Query Parameter — Introduction

    @GET("tasks")
    Call<ResponseBody> getTask(@Query("id") long taskId);

    /*The method getTask(…) requires the parameter taskId. This parameter will be mapped by Retrofit
to a query parameter with the name specified within the @Query() annotation. In this case, the
parameter name is id which will result in a request resource url like:

    tasks?id=<taskId>
*/

    //Multiple Query Parameters


    @GET("tasks")
    Call<List<GitHubRepo>> getTasks(
            @Query("id") long taskId, @Query("order") String order, @Query("page") int page); //Pass null and Retrofit will ignore it



    //The resulting resource url of the request looks like:
        // tasks?id=<taskId>&order=<order>&page=<page>

/*Adding every possible query parameter to your method definition can cause a lot headache. Every
time when calling this method you need to pass all the given parameters, even though their values
doesn’t matter. The following shows you how to avoid passing all method parameters.*/




            //Multiple Query Parameter Using QueryMap

    /*Retrofit uses annotations to translate defined keys and values into appropriate format. Using the
@Query("key") String value annotation will add a query parameter with name key and the
respective string value to the request url (of course you can use other types than string :)).
Actually, there are APIs with endpoints allowing you to pass (optionally) multiple query parameters.
You want to avoid a service method declaration like the one below with “endless” options for request
parameters:*/


    @GET("tasks")
    Call<List<GitHubRepo>> getTasks(
            @QueryMap Map<String, String> options);

    /*If you want to request the tasks for Marcus from page 2, you only need to add the map entries for
page and owner. There’s no need to worry about the other available options (taskId, order).
The resulting url with the assumed example (page 2 of Marcus):
 tasks?page=2&owner=Marcus*/

    //@QueryMap(encoded=true)


/*Enabling encoding will encode individual characters before appended to the request url. Using
the key author with value marcus-poehls will result in the following encoded url: author=marcus%2Dpoehls.
Setting encoding to false (by default), the url for the example above would be author=marcuspoehls.
There’s another use case where Retrofit’s query map would be impractical: adding multiple query
parameters with the same key. The next section shows you how to add multiple query parameters
with the same name.*/



    //Multiple Query Parameters with the Same Name


    @GET("tasks")
    List<GitHubRepo> getTask(@Query("id") List<Long> taskIds);

    //Given a list of taskIds=[1,2,3] the resulting request url looks like this:
    //http://api.example.com/tasks?id=1&id=2&id=3



  /*  Optional Query Parameters
    In most cases, the sorting option is not required to successfully execute a request. The backend
    generally provides a default sorting and you can just use the data as is. The important thing to know
    is how to use optional query parameter in your client.
    Let’s assume the following code snippet defines our API client on Android and we don’t want to
    pass a sort query parameter on every request. Only if the user specifies a sorting style, we use it.*/

            @GET("tasks")
            Call<List<GitHubRepo>> getTasks(@Query("sort") String order);

    /*Depending on the API design, the sort parameter might be optional. If you don’t want to pass it
    with the actual request, just pass null as the value for order during the method call.
            service.getTasks(null);

            Retrofit skips null parameters and ignores them while assembling the request. Please keep in mind,
that you can’t pass null for primitive data types like int, float, long, etc. Instead, use their class
representations Integer, Float, Long, etc. and the compiler won’t be grumpy.


Remember: query parameters are a powerful way to request filtered data from an API. Use them
intentionally to find exactly what you’re looking for. Usually, computing on server side is much
more effective and you don’t need to apply any further computing on client-side if you’re already
receiving the desired results

*/



    //PATH Parameter
 /*   Besides the query parameter, you can customize your request url with path parameters.
    Path parameters are defined as placeholders within your resource endpoint url and get substituted
    with parameter values from your method definition .The placeholders within the endpoint url are defined
    by a string value surrounded by curly brackets, like{my-path-placeholder}.
    Additionally,you need to annotate a method parameter with the @Path("my-path-placeholder") annotation.
*/
    @GET("/tasks/{id}")
    Call<GitHubRepo> getTaskWithPath( @Path("id") long taskId );





}

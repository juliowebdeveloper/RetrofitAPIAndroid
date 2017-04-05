package com.shido.retrofitapiandroid.interfaces;

import com.shido.retrofitapiandroid.GitHubRepo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by mira on 04/04/2017.
 */

public interface GitHubService {


    @GET("users/{user}/repos")
    Call<List<GitHubRepo>> reposForUser(@Path("user") String username); //@Path para caminhos @Query para parametros

    /*
    Here, the {user} indicates to Retrofit that the value is dynamic and will be set when the request
is being made. If you include a path parameter in the URL, you need to add a @Path() function
parameter, where the @Path value matches the placeholder in the URL (in this case it’d be
@Path("user")). You can use multiple placeholders, if necessary. Just make sure you always have
the exact amount of matching parameters. You can even use optional path parameters.
     */


    /*
    The @GET() annotation explicitly defines that a GET request will be executed once the method gets
called. Further, the @GET() definition takes a string parameter representing the endpoint url of your
API. Additionally, the endpoint url can be defined with placeholders which get substituted by path
parameters.
The method signature contains a @Path() annotation for the user parameter. This @Path annotation
maps the provided parameter value during the method call to the path within the request url. The
declared {user} part within the endpoint url will be replaced by the provided value of username.
We’ll catch up path parameters in more detail in a later chapte
     */
}

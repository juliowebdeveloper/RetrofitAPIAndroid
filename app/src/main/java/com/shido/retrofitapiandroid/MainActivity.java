package com.shido.retrofitapiandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.shido.retrofitapiandroid.interfaces.GitHubService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Change base url to Github API
        ServiceGenerator.changeApiBaseUrl("https://api.github.com/");

        //Create a simple REST adapter which points to Github's API
        GitHubService service = ServiceGenerator.createService(GitHubService.class);

        Call<List<GitHubRepo>> call = service.reposForUser("juliowebdeveloper");
        call.enqueue(new Callback<List<GitHubRepo>>() {
            @Override
            public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {
                if(response.isSuccessful()){
                    for(GitHubRepo repo:response.body()){
                        Log.e("Repo:", repo.getName() + " (ID: " + repo.getId() + " )");
                    }
                }else{
                    Log.e("REQUEST FAILED:", "Cannot request Github repositories");
                }
            }

            @Override
            public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {
                Log.e("Error fetching repos", t.getMessage());
            }
        });


        UserService userService = ServiceGenerator.createService(UserService.class);
        userService.profilePicture("https://s3.amazon.com/my/profile-picture/path");

        /*Because you set a completely different host including a scheme (https://s3.amazon.com vs.
https://your.api.url), the HttpUrl from OkHttp will resolve it to the dynamic one.
Another example: this time we point the dynamic url for our profile picture to the same server as
we’ve already defined as the base url.*/
        userService.profilePicture("my/profile-picture/path");

            /*This time, the final request url gets resolved to a concatenation of the base url and our dynamically
defined endpoint url. HttpUrl will recognizes that we didn’t specify a scheme and host and therefore
appends the endpoint url to the base url.*/





    }
}

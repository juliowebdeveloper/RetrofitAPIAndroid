package com.shido.retrofitapiandroid;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.shido.retrofitapiandroid.interfaces.GitHubService;
import com.shido.retrofitapiandroid.interfaces.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private List<GitHubRepo> gits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView t = (TextView) findViewById(R.id.hello);
        t.setOnClickListener(v -> {
           // Toast.makeText(this, "Teste", Toast.LENGTH_SHORT).show();
            streamGits(gits);
        });
        //final List<GitHubRepo> gits = new ArrayList<GitHubRepo>();

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
                    gits = response.body();
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


    @TargetApi(Build.VERSION_CODES.N)
    public void streamTeste(){
        List<String> strings = new ArrayList<>();
        strings.add("Ola"); strings.add("Oi"); strings.add("He");
        strings.add("Hello");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            int sum = strings.stream().filter(b -> b.contains("he")).mapToInt(b -> b.length()).sum();
            Log.e("Log", String.valueOf(sum));
        }
    }


    @TargetApi(Build.VERSION_CODES.N)
    public void streamGits(List<GitHubRepo> gits){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Stream<GitHubRepo> streamGit = gits.stream();
            Stream<GitHubRepo> streamGit2 = gits.stream();
           int sum = streamGit.filter(gitHubRepo ->
                    gitHubRepo.getName().contains("Broad"))
                    .mapToInt(gitHubRepo -> gitHubRepo.getId()).sum();
            //Predicate<GitHubRepo> predicate = s -> s.getName().contains("Broad");
            long count = streamGit2.filter(s -> s.getName().contains("Broad")).count();

            Log.e("Log", String.valueOf(sum)); //Soma dos Ids cujo nome contem int
            Toast.makeText(this, "Count " + count, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Sum " + sum, Toast.LENGTH_SHORT).show();

        }
    }

}

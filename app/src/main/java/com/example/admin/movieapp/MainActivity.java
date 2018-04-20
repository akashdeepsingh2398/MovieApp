package com.example.admin.movieapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private ListView lst;
    private String movieid;
    private int identifier=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);


        lst = (ListView) findViewById(R.id.lst);
        new JsonTask().execute("https://api.themoviedb.org/3/movie/popular?api_key=aea98560d69710b3e9cd0a5e28a60613&language=en-US&page=1","https://api.themoviedb.org/3/movie/","/videos?api_key=aea98560d69710b3e9cd0a5e28a60613&language=en-US");
        Button searchb = (Button)findViewById(R.id.searchb);



        searchb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText searchtxt = (EditText)findViewById(R.id.searchtxt);
                String searh = searchtxt.getText().toString();
                EditText searchreg = (EditText)findViewById(R.id.searchreg);
                String searchrg = searchreg.getText().toString();
                EditText searchyr = (EditText)findViewById(R.id.searchyear);
                String searchy = searchyr.getText().toString();

                if (searh.length()==0){

                    searh="a";
                }

                String website = "https://api.themoviedb.org/3/search/movie?api_key=aea98560d69710b3e9cd0a5e28a60613&language=en-US&query="+searh+"&page=1&include_adult=false&region="+searchrg+"&year="+searchy;
               // String website = "https://api.themoviedb.org/3/search/movie?api_key=aea98560d69710b3e9cd0a5e28a60613&language=en-US&query="+searh+"&page=1&include_adult=false";
                new JsonTask().execute(website,"https://api.themoviedb.org/3/movie/","/videos?api_key=aea98560d69710b3e9cd0a5e28a60613&language=en-US");

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu, menu);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_popular) {

            new JsonTask().execute("https://api.themoviedb.org/3/movie/popular?api_key=aea98560d69710b3e9cd0a5e28a60613&language=en-US&page=1","https://api.themoviedb.org/3/movie/","/videos?api_key=aea98560d69710b3e9cd0a5e28a60613&language=en-US");

        }else if(item.getItemId() == R.id.action_now){

            new JsonTask().execute("https://api.themoviedb.org/3/movie/now_playing?api_key=aea98560d69710b3e9cd0a5e28a60613&language=en-US&page=1","https://api.themoviedb.org/3/movie/","/videos?api_key=aea98560d69710b3e9cd0a5e28a60613&language=en-US");
        }else if(item.getItemId() == R.id.action_top ){

            new JsonTask().execute("https://api.themoviedb.org/3/movie/top_rated?api_key=aea98560d69710b3e9cd0a5e28a60613&language=en-US&page=1","https://api.themoviedb.org/3/movie/","/videos?api_key=aea98560d69710b3e9cd0a5e28a60613&language=en-US");
        }else if(item.getItemId() ==R.id.action_up ){

            new JsonTask().execute("https://api.themoviedb.org/3/movie/upcoming?api_key=aea98560d69710b3e9cd0a5e28a60613&language=en-US&page=1","https://api.themoviedb.org/3/movie/","/videos?api_key=aea98560d69710b3e9cd0a5e28a60613&language=en-US");
        }

        return true;
    }




    public class JsonTask extends AsyncTask<String, String, List<Movies>> {

        @Override
        protected List<Movies> doInBackground(String... params) {
            BufferedReader bufferedReader = null;
            HttpsURLConnection connection = null;
            BufferedReader bufferedReader2 = null;   //video
            HttpsURLConnection connection2 = null;   //video

            try {
                URL url = new URL(params[0]);

                connection = (HttpsURLConnection) url.openConnection();

                connection.connect();

                InputStream inputStream = connection.getInputStream();
                if (inputStream == null) {
                    return null;
                }

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer buffer = new StringBuffer();

                String st = "";

                while ((st = bufferedReader.readLine()) != null) {

                    buffer.append(st);

                }

                String fin = buffer.toString();

                JSONObject parentObject = new JSONObject(fin);
                JSONArray parentArray = parentObject.getJSONArray("results");

                List<Movies> moviesList = new ArrayList<>();
                List<String> urls = new ArrayList<>();

                URL url2;
                InputStream inputStream2;
                StringBuffer buffer2;
                String st2;
                for (int l = 0; l < parentArray.length(); l++) {

                    Movies movieModel = new Movies();
                    JSONObject finalObject = parentArray.getJSONObject(l);


                    movieModel.setMovieName(finalObject.getString("title"));
                    movieModel.setRealeaseDate(finalObject.getString("release_date"));
                    movieModel.setRating((float) finalObject.getDouble("vote_average"));
                    movieModel.setAdult(finalObject.getBoolean("adult"));
                    movieModel.setImage(finalObject.getString("poster_path"));
                    movieModel.setStory(finalObject.getString("overview"));
                    movieModel.setId(finalObject.getString("id"));
                    urls.add(finalObject.getString("id"));

                     url2 = new URL(params[1] + finalObject.getString("id") + params[2]);

                    connection2 = (HttpsURLConnection) url2.openConnection();
                    connection2.connect();

                     inputStream2 = connection2.getInputStream();
                    if (inputStream2 == null) {
                        return null;
                    }

                    bufferedReader2 = new BufferedReader(new InputStreamReader(inputStream2));

                     buffer2 = new StringBuffer();

                     st2 = "";

                    while ((st2 = bufferedReader2.readLine()) != null) {

                        buffer2.append(st2);

                    }

                    String fin2 = buffer2.toString();

                    JSONObject parentObject2 = new JSONObject(fin2);
                    JSONArray parentArray2 = parentObject2.getJSONArray("results");

                    if (parentObject2.getJSONArray("results").length()==0){
                        movieModel.setVideo("Not Availabe");
                    }
                    else {

                        JSONObject finalObject2 = parentArray2.getJSONObject(0);

                        movieModel.setVideo(finalObject2.getString("key"));
                    }



                    moviesList.add(movieModel);

                }

                return moviesList;

            } catch (MalformedURLException e) {
              e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Movies> movies) {
            super.onPostExecute(movies);


            movieAdapter adapter = new movieAdapter(getApplicationContext(),R.layout.layout,movies);
            lst.setAdapter(adapter);
        }
    }

    public class movieAdapter extends ArrayAdapter {

        private List<Movies> movieModelList;
        private int resource;
        private LayoutInflater inflater;

        public movieAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Movies> objects) {
            super(context, resource, objects);
            movieModelList = objects;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(resource, null);
            }

            ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView);
            TextView movieName =(TextView)convertView.findViewById(R.id.movieName);
            TextView Year=(TextView)convertView.findViewById(R.id.year);
            RatingBar ratingBar=(RatingBar) convertView.findViewById(R.id.ratingBar);
            CheckedTextView adult=(CheckedTextView)convertView.findViewById(R.id.adult);
            TextView story=(TextView)convertView.findViewById(R.id.story);
            Button video = (Button)convertView.findViewById(R.id.button);

            movieName.setText(movieModelList.get(position).getMovieName());
            adult.setText("Adult : " + movieModelList.get(position).isAdult());
            Year.setText( "Release date : \n" + movieModelList.get(position).getRealeaseDate());
            story.setText(movieModelList.get(position).getStory());
            ratingBar.setRating(movieModelList.get(position).getRating()/2);

            video.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v="+movieModelList.get(position).getVideo())));
                                             Log.i("Video", "Video Playing....");
                                         }
                                     }
            );

            Picasso.with(this.getContext())
                    .load("http://image.tmdb.org/t/p/original/"+movieModelList.get(position).getImage())
                    .into(imageView);

            return convertView;
        }


    }
}




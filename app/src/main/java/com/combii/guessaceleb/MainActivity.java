package com.combii.guessaceleb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }



    public void downloadImage(View view) {

        ImageDownloader task = new ImageDownloader();
        Bitmap myImage;

        try {
            myImage = task.execute("https://upload.wikimedia.org/wikipedia/en/a/aa/Bart_Simpson_200px.png").get();
            downloadedImg.setImageBitmap(myImage);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }


        Log.i("Button", "Clicked on Button");
    }

    private class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {

            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream inputStream = connection.getInputStream();

                return BitmapFactory.decodeStream(inputStream);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private Map<String, String> getCelebsMap() {
        DownloadMapOfCelebs task = new DownloadMapOfCelebs();
        try {
            return task.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class DownloadMapOfCelebs extends AsyncTask<String, Void, Map<String, String>> {

        @Override
        protected Map<String, String> doInBackground(String... urls) {

            StringBuffer buffer = null;

            Map<String,String> celebs = new HashMap<>();


            try {
                URL url = new URL("http://www.posh24.se/kandisar");
                InputStream is = url.openStream();
                int ptr = 0;
                buffer = new StringBuffer();
                while ((ptr = is.read()) != -1) {
                    buffer.append((char) ptr);
                }

                Pattern p = Pattern.compile("<img src=\"(.*?)\" alt=\"(.*?)\"");

                Matcher m = p.matcher(buffer);

                while(m.find()) {
                    celebs.put(m.group(1), m.group(2));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return celebs;
        }
    }

}

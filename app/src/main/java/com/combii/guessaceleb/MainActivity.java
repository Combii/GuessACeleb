package com.combii.guessaceleb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }





    public Map<String, String> findCelebs(String html) {

        Map<String,String> celebs = new HashMap<>();

        Pattern p = Pattern.compile("<img src=\"(.*?)\" alt=\"(.*?)\"");

        Matcher m = p.matcher(html);

        while(m.find()) {
            System.out.println(m.group(1) + " " + m.group(2));
        }

    }

}

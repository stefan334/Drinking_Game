package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

public class gameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        //hide action bar
        getSupportActionBar();
        getSupportActionBar().hide();

        //hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        String[] nume = getIntent().getStringArrayExtra("NUME");

        TextView test= (TextView)findViewById(R.id.textView3);
        Context mContext = this;


        String[] intrebari= new String[100];
        int i=0;
        try {   ///aici se citeste din fisier
            InputStream is = mContext.getAssets().open("Intrebari.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String intrebare;
            while((intrebare=reader.readLine())!=null){
                intrebari[i++] = intrebare;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String randomString= nume[new Random().nextInt(nume.length)];
        String intrebareInlocuita;
        intrebareInlocuita=intrebari[0];
        intrebareInlocuita=intrebareInlocuita.replaceAll("\\*", nume[new Random().nextInt(nume.length)]);
        intrebareInlocuita=intrebareInlocuita.replaceAll("\\$",nume[new Random().nextInt(nume.length)]);
        test.setText(intrebareInlocuita);
        





    }
}
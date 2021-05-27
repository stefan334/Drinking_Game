    package com.example.HaiSaBem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

    public class gameActivity extends AppCompatActivity {
    private int i=1;
    private String replace_name(String intrebareInlocuita, ArrayList<String> nume){
        Collections.shuffle(nume);
        intrebareInlocuita=intrebareInlocuita.replaceAll("\\*", nume.get(0));
        intrebareInlocuita=intrebareInlocuita.replaceAll("\\$",nume.get(1));
        return intrebareInlocuita;

    }

    private ArrayList<String> random_intrebari(ArrayList<String> intrebari){
        Collections.shuffle(intrebari);
        return intrebari;
    }



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
        ArrayList<String> nume = getIntent().getStringArrayListExtra("NUME");

        TextView textBox= (TextView)findViewById(R.id.textView3);
        Context mContext = this;


        ArrayList<String> intrebari= new ArrayList<String>();   ///declarare exact cate intrebari avem

        try {   ///aici se citeste din fisier
            InputStream is = mContext.getAssets().open("Intrebari.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String intrebare;
            while((intrebare=reader.readLine())!=null){
                intrebari.add(intrebare);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        intrebari=random_intrebari(intrebari);


        ConstraintLayout ecran=(ConstraintLayout) findViewById(R.id.ConstraintLayout);

        ArrayList<String> finalIntrebari = intrebari;
        //seteaza prima intrebare
        finalIntrebari.set(0,replace_name(finalIntrebari.get(0), nume));
        textBox.setText(finalIntrebari.get(0));

        ecran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalIntrebari.set(i,replace_name(finalIntrebari.get(i), nume));
                textBox.setText(finalIntrebari.get(i));
                i++;
                //animatie pentru text
                Animation text_animation = AnimationUtils.loadAnimation(mContext,R.anim.text_animation);
                textBox.startAnimation(text_animation);
            }
        });
    }
    public void function(){

    }

}


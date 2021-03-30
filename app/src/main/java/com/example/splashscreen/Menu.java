package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        //hide action bar
        getSupportActionBar();
        getSupportActionBar().hide();

        //hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);



        Button playerButton= (Button)findViewById(R.id.playButton);
        Intent intent = new Intent(Menu.this, playerScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        playerButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v){
                startActivity(intent);
                overridePendingTransition(0,0);


            }

        });
    }
}
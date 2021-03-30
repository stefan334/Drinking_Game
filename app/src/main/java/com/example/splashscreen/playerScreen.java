package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class playerScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_select);

        //hide action bar
        getSupportActionBar();
        getSupportActionBar().hide();

        //hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);




        EditText et4= findViewById(R.id.editTextTextPersonName4);
        EditText et5= findViewById(R.id.editTextTextPersonName5);
        EditText et6= findViewById(R.id.editTextTextPersonName6);

        et4.setVisibility(View.GONE);
        et5.setVisibility(View.GONE);
        et6.setVisibility(View.GONE);
        Button addPlayer =findViewById(R.id.addPlayer);

        addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(et4.getVisibility()==View.GONE){ //  if (i==4)
                    et4.setVisibility(View.VISIBLE);
                }else if(et5.getVisibility()==View.GONE &&et4.getVisibility()==View.VISIBLE) {  //if(i==5)
                    et5.setVisibility(View.VISIBLE);
                }else if(et6.getVisibility()==View.GONE&& et5.getVisibility()==View.VISIBLE) { // if(i==6&&et6.getVisibility()==View.GONE)
                    et6.setVisibility(View.VISIBLE);
                }else
                if(et6.getVisibility()==View.VISIBLE)  { //if(i==6&&et6.getVisibility()==View.VISIBLE)
                    Toast errorToast = Toast.makeText(playerScreen.this, "Maxim 5 jucatori!", Toast.LENGTH_SHORT);
                    errorToast.show();
                }

            }

        });
        Button removePlayer= findViewById(R.id.deletePlayer);
        Button startGame=findViewById(R.id.startGame);
        removePlayer.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if(et6.getVisibility()==View.VISIBLE) {  //if(i==6&&et6.getVisibility()==View.VISIBLE)
                    et6.setVisibility(View.GONE);

                }else if(et5.getVisibility()==View.VISIBLE&&et6.getVisibility()==View.GONE){ //if(i==6&&et6.getVisibility()==View.GONE)
                    et5.setVisibility(View.GONE);

                }else if(et4.getVisibility()==View.VISIBLE && et5.getVisibility()==View.GONE){// if(i==5&& et5.getVisibility()==View.VISIBLE)
                    et4.setVisibility(View.GONE);

                }/*else if(i==5&& et5.getVisibility()==View.GONE){
                    et4.setVisibility(View.GONE);
                    i=i-2;*/
                else
                if(et4.getVisibility()==View.GONE){
                    Toast errorToast = Toast.makeText(playerScreen.this, "Minim 2 jucatori!", Toast.LENGTH_SHORT);
                    errorToast.show();
                }

            }


        });
    }
}
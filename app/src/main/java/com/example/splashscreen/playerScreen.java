package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Trace;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

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

        boolean butoane[]= new boolean[10];     ///vector pt verificarea vizibilitatii butoanelor aka cati playeri sunt
        butoane[0]=TRUE;
        butoane[1]=TRUE;
        String nume[]=new String[10];

        EditText et[]= new EditText[10];
         et[2]= findViewById(R.id.editTextTextPersonName2);
         et[3]= findViewById(R.id.editTextTextPersonName3);
         et[4]= findViewById(R.id.editTextTextPersonName4);
         et[5]= findViewById(R.id.editTextTextPersonName5);
         et[6]= findViewById(R.id.editTextTextPersonName6);

        et[4].setVisibility(View.GONE);
        et[5].setVisibility(View.GONE);
        et[6].setVisibility(View.GONE);
        Button addPlayer =findViewById(R.id.addPlayer);

        addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(et[4].getVisibility()==View.GONE){ //  if (i==4)
                    et[4].setVisibility(View.VISIBLE);
                    butoane[2]= TRUE;
                }else if(et[5].getVisibility()==View.GONE &&et[4].getVisibility()==View.VISIBLE) {  //if(i==5)
                    et[5].setVisibility(View.VISIBLE);
                    butoane[3]= TRUE;
                }else if(et[6].getVisibility()==View.GONE&& et[5].getVisibility()==View.VISIBLE) { // if(i==6&&et[6].getVisibility()==View.GONE)
                    et[6].setVisibility(View.VISIBLE);
                    butoane[4]= TRUE;
                }else
                if(et[6].getVisibility()==View.VISIBLE)  { //if(i==6&&et[6].getVisibility()==View.VISIBLE)
                    Toast errorToast = Toast.makeText(playerScreen.this, "Maxim 5 jucatori!", Toast.LENGTH_SHORT);
                    errorToast.show();
                }

            }

        });
        Button removePlayer= findViewById(R.id.deletePlayer);
        Button startGame=findViewById(R.id.startGame);
        removePlayer.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if(et[6].getVisibility()==View.VISIBLE) {  //if(i==6&&et[6].getVisibility()==View.VISIBLE)
                    et[6].setVisibility(View.GONE);
                    butoane[4]= FALSE;

                }else if(et[5].getVisibility()==View.VISIBLE&&et[6].getVisibility()==View.GONE){ //if(i==6&&et[6].getVisibility()==View.GONE)
                    et[5].setVisibility(View.GONE);
                    butoane[3]= FALSE;

                }else if(et[4].getVisibility()==View.VISIBLE && et[5].getVisibility()==View.GONE){// if(i==5&& et[5].getVisibility()==View.VISIBLE)
                    et[4].setVisibility(View.GONE);
                    butoane[2]= FALSE;

                }/*else if(i==5&& et[5].getVisibility()==View.GONE){
                    et[4].setVisibility(View.GONE);
                    i=i-2;*/
                else
                if(et[4].getVisibility()==View.GONE){
                    Toast errorToast = Toast.makeText(playerScreen.this, "Minim 2 jucatori!", Toast.LENGTH_SHORT);
                    errorToast.show();
                }

            }




        });

        startGame.setOnClickListener(new View.OnClickListener(){



            public void onClick(View view){
                for(int i = 0; i<=4; i++){
                    if(butoane[i]==TRUE){      ///verifica daca exista jucatorul respectiv
                        nume[i]= et[i+2].getText().toString();

                    }
                }
                Intent intent = new Intent(playerScreen.this, gameActivity.class);
                intent.putExtra("NUME", nume);   ///paseaza vectoru de nume catre urmat activitate
                startActivity(intent);


            }
        });

    }
}
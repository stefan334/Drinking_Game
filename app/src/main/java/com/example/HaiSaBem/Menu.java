package com.example.HaiSaBem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Menu extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText username;
    private Button goButton,cancelButton;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button onlineButton= (Button)findViewById(R.id.onlineButton);

        onlineButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                selectName();
            }
        });




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

    public void selectName() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View contactPopupView = getLayoutInflater().inflate(R.layout.popup, null);
        username = (EditText) contactPopupView.findViewById(R.id.username);
        goButton = (Button) contactPopupView.findViewById(R.id.goButton);
        cancelButton = (Button) contactPopupView.findViewById(R.id.cancelButton);

        dialogBuilder.setView(contactPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        goButton.setOnClickListener(new View.OnClickListener() {




            public void onClick(View v){

                Intent intent = new Intent(Menu.this, online_Lobby.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                String name = username.getText().toString();
                //nu e Menu pentru ca esti in popup
                if(name.isEmpty()){
                    Toast errorToast = Toast.makeText(Menu.this, "Introduceti un nume!", Toast.LENGTH_SHORT);
                    errorToast.show();
                }else{

                intent.putExtra("NUME", name);   ///paseaza vectoru de nume catre urmatoarea activitate



                startActivity(intent);
                overridePendingTransition(0,0);}


            }
        });

    }
}
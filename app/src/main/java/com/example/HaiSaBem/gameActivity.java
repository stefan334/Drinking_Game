    package com.example.HaiSaBem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


    public class gameActivity extends AppCompatActivity {
    private int i=1;
      private  String intrebare;

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
        String gameType= getIntent().getStringExtra("TIP");

        //hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        if(gameType.equals("offline")) {
            ArrayList<String> nume = getIntent().getStringArrayListExtra("NUME");
            TextView textBox = (TextView) findViewById(R.id.textView3);
            Context mContext = this;


            ArrayList<String> intrebari = new ArrayList<String>();   ///declarare exact cate intrebari avem

            try {   ///aici se citeste din fisier
                InputStream is = mContext.getAssets().open("Intrebari.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                while ((intrebare = reader.readLine()) != null) {
                    intrebari.add(intrebare);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            intrebari = random_intrebari(intrebari);


            ConstraintLayout ecran = (ConstraintLayout) findViewById(R.id.ConstraintLayout);

            ArrayList<String> finalIntrebari = intrebari;
            //seteaza prima intrebare
            finalIntrebari.set(0, replace_name(finalIntrebari.get(0), nume));
            textBox.setText(finalIntrebari.get(0));

            ecran.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalIntrebari.set(i, replace_name(finalIntrebari.get(i), nume));
                    textBox.setText(finalIntrebari.get(i));
                    i++;
                    //animatie pentru text
                    Animation text_animation = AnimationUtils.loadAnimation(mContext, R.anim.text_animation);
                    textBox.startAnimation(text_animation);
                }
            });
        }
        else  //Jocul este online deci:
        {
            Game game= (Game) getIntent().getSerializableExtra("JOC");
            TextView textBox = (TextView) findViewById(R.id.textView3);
            Context mContext = this;
            DocumentReference gameRef;
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            gameRef = db.collection("games").document(game.getId());
            ArrayList<String> intrebari = game.getIntrebariArrayList();
            int nrIntrebare = game.getQuestionNr();

             intrebare = intrebari.get(nrIntrebare);
            ArrayList<String> playeri = new ArrayList<>();
                playeri.add(game.getPlayer1().getName());
                playeri.add(game.getPlayer2().getName());
                playeri.add(game.getPlayer3().getName());
                playeri.add(game.getPlayer4().getName());
            ArrayList<String> finalIntrebari = new ArrayList<>();

            for(String intreb : intrebari){
                finalIntrebari.add(replace_name(intreb,playeri));

            }



            ConstraintLayout ecran = (ConstraintLayout) findViewById(R.id.ConstraintLayout);
            ecran.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    game.setQuestionNr(game.getQuestionNr()+1);
                    gameRef.update("questionNr", game.getQuestionNr());

                    //animatie pentru text

                }
            });
            gameRef.update("intrebariArrayList",finalIntrebari);

            gameRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot snapshot,
                                    @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        return;
                    }
                    if(snapshot.exists())
                    {
                        game.setIntrebariArrayList((ArrayList<String>) snapshot.get("intrebariArrayList"));
                        game.setQuestionNrLong((Long) snapshot.get("questionNr"));
                        textBox.setText(game.getIntrebariArrayList().get(game.getQuestionNr()));
                        //animatie pentru text
                        Animation text_animation = AnimationUtils.loadAnimation(mContext, R.anim.text_animation);
                        textBox.startAnimation(text_animation);

                    }


                }
            });

        //    textBox.setText(gameRef.child())

        }
    }


}


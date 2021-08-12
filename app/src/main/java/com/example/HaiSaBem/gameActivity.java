    package com.example.HaiSaBem;

    import android.content.Context;
    import android.content.pm.ActivityInfo;
    import android.os.Bundle;
    import android.view.View;
    import android.view.WindowManager;
    import android.view.animation.Animation;
    import android.view.animation.AnimationUtils;
    import android.widget.TextView;

    import androidx.annotation.Nullable;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.constraintlayout.widget.ConstraintLayout;

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
    import java.util.Objects;

    import static java.lang.Boolean.FALSE;
    import static java.lang.Boolean.TRUE;


    public class gameActivity extends AppCompatActivity {
    private int i=1;

        private String replace_name(String intrebareInlocuita, ArrayList<String> nume){
        Collections.shuffle(nume);
        intrebareInlocuita=intrebareInlocuita.replaceAll("\\*", nume.get(0));
        intrebareInlocuita=intrebareInlocuita.replaceAll("\\$",nume.get(1));
        return intrebareInlocuita;

    }

    private void    random_intrebari(ArrayList<String> intrebari){
        Collections.shuffle(intrebari);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        //hide action bar
        getSupportActionBar();
        Objects.requireNonNull(getSupportActionBar()).hide();
        String gameType= getIntent().getStringExtra("TIP");

        //hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        String intrebare;
        if(gameType.equals("offline")) {
            ArrayList<String> nume = getIntent().getStringArrayListExtra("NUME");
            TextView textBox = findViewById(R.id.textView3);
            Context mContext = this;


            ArrayList<String> intrebari = new ArrayList<>();   ///declarare exact cate intrebari avem

            try {   ///aici se citeste din fisier
                InputStream is = mContext.getAssets().open("Intrebari.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                while ((intrebare = reader.readLine()) != null) {
                    intrebari.add(intrebare);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            random_intrebari(intrebari);


            ConstraintLayout ecran = findViewById(R.id.ConstraintLayout);

            //seteaza prima intrebare
            intrebari.set(0, replace_name(intrebari.get(0), nume));
            textBox.setText(intrebari.get(0));

            ecran.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intrebari.set(i, replace_name(intrebari.get(i), nume));
                    textBox.setText(intrebari.get(i));
                    i++;
                    //animatie pentru text
                    Animation text_animation = AnimationUtils.loadAnimation(mContext, R.anim.text_animation);
                    textBox.startAnimation(text_animation);
                }
            });
        }
        else online(); //Jocul este online deci:

    }

    public void online(){
        {
            Game game= (Game) getIntent().getSerializableExtra("JOC");
            TextView textBox = findViewById(R.id.textView3);
            Context mContext = this;
            DocumentReference gameRef;
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            gameRef = db.collection("games").document(game.getId());
            ArrayList<String> intrebari = game.getIntrebariArrayList();
            int nrIntrebare = game.getQuestionNr();

            ArrayList<String> playeri = new ArrayList<>();
            playeri.add(game.getPlayer1().getName());
            playeri.add(game.getPlayer2().getName());
            if(game.getPlayer3().userExists()==TRUE) {
                playeri.add(game.getPlayer3().getName());
                System.out.println("SUNT AICI SI NU TREBUIA");
            }
            if(game.getPlayer4().userExists()==TRUE)
                playeri.add(game.getPlayer4().getName());
            ArrayList<String> finalIntrebari = new ArrayList<>();

            for(String intreb : intrebari){
                finalIntrebari.add(replace_name(intreb,playeri));
            }
            gameRef.update("intrebariArrayList",finalIntrebari);


            ConstraintLayout ecran = findViewById(R.id.ConstraintLayout);
            ecran.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    game.setQuestionNr(game.getQuestionNr()+1);
                    gameRef.update("questionNr", game.getQuestionNr());

                    //animatie pentru text

                }
            });


            gameRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot snapshot,
                                    @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        return;
                    }

                    assert snapshot != null;
                    if(snapshot.exists())
                    {
                        game.setIntrebariArrayList((ArrayList<String>) snapshot.get("intrebariArrayList"));
                        game.setQuestionNrLong((Long) Objects.requireNonNull(snapshot.get("questionNr")));
                        System.out.println(game.getQuestionNr());
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


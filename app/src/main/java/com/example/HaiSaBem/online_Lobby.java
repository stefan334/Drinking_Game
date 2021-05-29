package com.example.HaiSaBem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

public class online_Lobby extends AppCompatActivity {
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
        getSupportActionBar();
        getSupportActionBar().hide();
        Context mContext = this;
        //hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_online_lobby);
        String player1 = getIntent().getStringExtra("NUME");

        TextView player1Textview;
        player1Textview = findViewById(R.id.player1Textview);
        player1Textview.setText(player1);

        Button sharebtn;
        sharebtn = (Button) findViewById(R.id.sharebtn);
        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent shareIntent = new Intent (Intent.ACTION_SEND);
               shareIntent.setType("text/plain");
               String shareBody="Copiaza linkul :-https://www.google.com/";
               shareIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
               startActivity(Intent.createChooser(shareIntent,"Share Using"));

            }
        });




        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        ArrayList<User> userArrayList= new ArrayList<>();
        User user= new User();
        user.setID(randomUUIDString);
        user.setName(player1);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
       // DocumentReference gameRef = db.collection("games").document();
        Game game=new Game();
        game.setUser(user);

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

        game.setIntrebariArrayList(intrebari);
        

        db.collection("games")
                .add(game)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("FragmentActivity", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("FragmentActivity", "Error adding document", e);
                    }
                });









    }


        /*
        ArrayList<String> nume = getIntent().getStringArrayListExtra("NUME");
        Map<String, String> users = new HashMap<>();

        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        ArrayList<User> userArrayList= new ArrayList<>();
        User user= new User();
        for(int i = 0; i< nume.size();i++){
            user.setID(randomUUIDString);
            user.setName(nume.get(i));
            userArrayList.set(i, user);
            users.put(user.getID(), user.getName());
            uuid=UUID.randomUUID();
            randomUUIDString = uuid.toString();
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        for(Map.Entry me : users.entrySet()) {
            db.collection("users")
                    .add(me)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("FragmentActivity", "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("FragmentActivity", "Error adding document", e);
                        }
                    });
        }

*/


}
package com.example.HaiSaBem;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
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

import java.util.Map;
import java.util.UUID;

public class online_Lobby extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText username;
    private Button goButton, cancelButton;
    private TextView player2Textview;
    private TextView player3Textview;
    private TextView player4Textview;
    private TextView player1Textview;
    private ArrayList<User> useri;
    private int jucatori = 1;
    private DocumentReference gameRef;
    private Game game;
    private Uri _link;


    //terminat
    private String replace_name(String intrebareInlocuita, ArrayList<String> nume) {
        Collections.shuffle(nume);
        intrebareInlocuita = intrebareInlocuita.replaceAll("\\*", nume.get(0));
        intrebareInlocuita = intrebareInlocuita.replaceAll("\\$", nume.get(1));
        return intrebareInlocuita;

    }

    private ArrayList<String> random_intrebari(ArrayList<String> intrebari) {
        Collections.shuffle(intrebari);
        return intrebari;
    }

    private Button sharebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar();
        getSupportActionBar().hide();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        gameRef = db.collection("games").document();
        game = new Game();
        Context mContext = this;
        //hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_online_lobby);
        useri = new ArrayList<>();

        sharebtn = (Button) findViewById(R.id.sharebtn);


        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                            String link = deepLink.toString();
                            _link = deepLink;
                            link = link.substring(link.indexOf("=") + 1);
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            gameRef = db.collection("games").document(link);
                            gameRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot snapshot,
                                                    @Nullable FirebaseFirestoreException e) {
                                    if (e != null) {
                                        return;
                                    }

                                    if (snapshot.exists()) {
                                        //daca exista
                                        updateView(snapshot);



                                    }
                                }
                            });
                            joinedGame(deepLink);

                        } else {
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            gameRef = db.collection("games").document();
                            gameRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot snapshot,
                                                    @Nullable FirebaseFirestoreException e) {
                                    if (e != null) {
                                        return;
                                    }

                                    if (snapshot.exists()) {
                                        //daca exista
                                        updateView(snapshot);



                                    }
                                }
                            });
                            hostCreated();

                        }
                        // Handle the deep link. For example, open the linked
                        // content, or apply promotional credit to the user's
                        // account.
                        // ...

                        // ...
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        gameRef = db.collection("games").document();
                        gameRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                                @Nullable FirebaseFirestoreException e) {
                                if (e != null) {
                                    return;
                                }

                                if (snapshot.exists()) {
                                    //daca exista
                                    updateView(snapshot);


                                }
                            }
                        });
                        hostCreated();

                    }
                });

        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareBody = "Da click pe link pentru a te alatura: " + _link;

                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(shareIntent, "Share using "));


            }


        });

        Button startOnlineButton = (Button) findViewById(R.id.startOnlineButton);
        startOnlineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameRef.update("started", 1);
                Intent intent = new Intent(online_Lobby.this, gameActivity.class);
                startActivity(intent);
            }

        });


    }


    public void updateView(DocumentSnapshot snapshot) {
        useri = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        player1Textview = findViewById(R.id.player1Textview);
        player2Textview = findViewById(R.id.player2Textview);
        player3Textview = findViewById(R.id.player3Textview);
        player4Textview = findViewById(R.id.player4Textview);
        gameRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                game.setStarted((Long) snapshot.get("started"));

                if(game.getStarted()!=0L)
                {
                    Intent intent = new Intent(online_Lobby.this, gameActivity.class);
                    startActivity(intent);
                }
                
                game.setPlayerNumber(Integer.valueOf(snapshot.get("playerNumber").toString()));
                Map<String, String> user1 = (Map<String, String>) snapshot.get("player1");
                Map<String, String> user2 = (Map<String, String>) snapshot.get("player2");
                Map<String, String> user3 = (Map<String, String>) snapshot.get("player3");
                Map<String, String> user4 = (Map<String, String>) snapshot.get("player4");

                User _user1 = new User();
                _user1.setName(user1.get("name"));
                _user1.setID(user1.get("id"));
                game.setPlayer1(_user1);

                User _user2 = new User();
                _user2.setName(user2.get("name"));
                _user2.setID(user2.get("id"));
                game.setPlayer2(_user2);

                User _user3 = new User();
                _user3.setName(user3.get("name"));
                _user3.setID(user3.get("id"));
                game.setPlayer3(_user3);

                User _user4 = new User();
                _user4.setName(user4.get("name"));
                _user4.setID(user4.get("id"));
                game.setPlayer4(_user4);

                player1Textview.setText(game.getPlayer1().getName());

                player2Textview.setText(game.getPlayer2().getName());

                player3Textview.setText(game.getPlayer3().getName());

                player4Textview.setText(game.getPlayer4().getName());

            }
        });


    }


    public void joinedGame(Uri deeplink) {
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
                dialog.dismiss();  ///te scoate din joc
            }
        });


        goButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) { ///te adauga in lobby
                String name = username.getText().toString();
                if (name.isEmpty()) {
                    Toast errorToast = Toast.makeText(online_Lobby.this, "Introduceti un nume!", Toast.LENGTH_SHORT);
                    errorToast.show();
                } else {
                    UUID uuid = UUID.randomUUID();
                    String randomUUIDString = uuid.toString();
                    User user = new User();
                    user.setID(randomUUIDString);
                    user.setName(name);

                    gameRef.update("player" + (game.getPlayerNumber() + 1), user);
                    gameRef.update("playerNumber", game.getPlayerNumber() + 1);

                    dialog.dismiss();

                }


            }
        });
        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://www.example.com/game_id=" + gameRef.getId()))
                .setDomainUriPrefix("https://thunderstudios.page.link")
                .setAndroidParameters(
                        new DynamicLink.AndroidParameters.Builder("com.ThunderStudios.HaiSaBem")
                                .setMinimumVersion(1)
                                .build())
                .setGoogleAnalyticsParameters(
                        new DynamicLink.GoogleAnalyticsParameters.Builder()
                                .setSource("orkut")
                                .setMedium("social")
                                .setCampaign("example-promo")
                                .build())
                .setSocialMetaTagParameters(
                        new DynamicLink.SocialMetaTagParameters.Builder()
                                .setTitle("Haide bai sa bem ca nu se mai poate")
                                .setDescription("Sa inceapa bauta").setImageUrl(Uri.parse("https://www.example.com/beer.png"))
                                .build())
                .buildShortDynamicLink()
                .addOnCompleteListener(this, new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {
                            // Short link created
                            _link = task.getResult().getShortLink();
                            System.out.println(task.getResult().getShortLink());
                            Uri shortLink = task.getResult().getShortLink();
                            Uri flowchartLink = task.getResult().getPreviewLink();
                        } else {
                            // Error
                            // ...
                        }
                    }
                });


    }

    public void hostCreated() {
        String player1 = getIntent().getStringExtra("NUME");
        Context mContext = this;

        player1Textview = findViewById(R.id.player1Textview);
        player1Textview.setText(player1);


        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        ArrayList<User> userArrayList = new ArrayList<>();
        User user = new User();
        user.setID(randomUUIDString);
        user.setName(player1);


        Game game = new Game();
        game.setPlayer1(user);
        game.setPlayerNumber(1);

        ArrayList<String> intrebari = new ArrayList<>();   ///declarare exact cate intrebari avem

        try {   ///aici se citeste din fisier
            InputStream is = mContext.getAssets().open("Intrebari.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String intrebare;
            while ((intrebare = reader.readLine()) != null) {
                intrebari.add(intrebare);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        intrebari = random_intrebari(intrebari);

        game.setIntrebariArrayList(intrebari);


        gameRef.set(game);


        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://www.example.com/game_id=" + gameRef.getId()))
                .setDomainUriPrefix("https://thunderstudios.page.link")
                .setAndroidParameters(
                        new DynamicLink.AndroidParameters.Builder("com.ThunderStudios.HaiSaBem")
                                .setMinimumVersion(1)
                                .build())
                .setGoogleAnalyticsParameters(
                        new DynamicLink.GoogleAnalyticsParameters.Builder()
                                .setSource("orkut")
                                .setMedium("social")
                                .setCampaign("example-promo")
                                .build())
                .setSocialMetaTagParameters(
                        new DynamicLink.SocialMetaTagParameters.Builder()
                                .setTitle("Haide bai sa bem ca nu se mai poate")
                                .setDescription("Sa inceapa bauta").setImageUrl(Uri.parse("https://www.example.com/beer.png"))
                                .build())
                .buildShortDynamicLink()
                .addOnCompleteListener(this, new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {
                            // Short link created
                            _link = task.getResult().getShortLink();
                            System.out.println(task.getResult().getShortLink());
                            Uri shortLink = task.getResult().getShortLink();
                            Uri flowchartLink = task.getResult().getPreviewLink();
                        } else {
                            // Error
                            // ...
                        }
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
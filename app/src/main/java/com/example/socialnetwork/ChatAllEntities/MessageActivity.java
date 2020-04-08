package com.example.socialnetwork.ChatAllEntities;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.socialnetwork.R;
import com.example.socialnetwork.model.Profile;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.ExecutionException;


public class MessageActivity extends AppCompatActivity {

    ImageView profile_image;
    TextView username;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    Intent intent;

    public void getData() {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Profile");


        Query query = reference.orderByChild("id").equalTo(firebaseUser.getUid());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Profile user = snapshot.getValue(Profile.class);


                    if (user.getId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        readMessages(firebaseUser.getUid(), userId, user.getDp());

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    String otherUserDp = "default";

    public void getOtherUserDp(String userId) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Profile");
        Query query = reference.orderByChild("id").equalTo(userId);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Profile user = snapshot.getValue(Profile.class);


                    if (user.getDp().equals("")) {
                        otherUserDp = "default";

                    } else {

                        username.setText(user.getUserName());
                        otherUserDp = user.getDp();
                        updateCallerImage(otherUserDp);


                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void updateCallerImage(String id) {

        Picasso.get()
                .load(id)
                .resize(95, 95)
                .centerCrop()
                .into(profile_image);
    }

    public void endCall(View view)
    {
        mySpeechRecognizer.stopListening();
        mySpeechRecognizer.destroy();
        System.exit(0);

    }


    @Override
    public void onBackPressed() {

    }

    String userId;
    ImageView anyTalkSwitchIv;
    Intent mSpeechRecognizerIntent;
    AudioManager audioManager;

    private void mute() {
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
    }

    public void unmute() {
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oncall_anytalk);


          intent = getIntent();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userId = intent.getStringExtra("userid");

        profile_image = findViewById(R.id.anytalkCallerPic);
        username = findViewById(R.id.anytalkCallerName);
        anyTalkSwitchIv= findViewById(R.id.anytalk_switch_iv);

        listenLangPair = ChatActivity.receiverCountryModel.getLanng() + "-" + ChatActivity.receiverCountryModel.getNameSmall();



        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, listenLangPair);

        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1);


        initailzeSpeechRecognizer();




        anyTalkSwitchIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yetToComplete();
            }
        });


        profile_image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                speakFlag = true;
                Toast.makeText(MessageActivity.this, "Now speak", Toast.LENGTH_SHORT).show();
                mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                        listenLangPair);

                System.out.println("yasir lang pair "+ listenLangPair);



                mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1);

                initailzeSpeechRecognizer();
                mySpeechRecognizer.startListening(mSpeechRecognizerIntent);
                mute();
                return true;

            }
        });



        username.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                speakFlag = false;
                Toast.makeText(MessageActivity.this, "Now Listen ", Toast.LENGTH_SHORT).show();
                mySpeechRecognizer.destroy();
                unmute();
                return true;

            }
        });


        getOtherUserDp(userId);
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        getData();

        languagePair = ChatActivity.senderCountryModel.getLanng() + "-" + ChatActivity.receiverCountryModel.getLanng();
        listenLangPair = ChatActivity.receiverCountryModel.getLanng() + "-" + ChatActivity.receiverCountryModel.getNameSmall();

        locale = new Locale(ChatActivity.receiverCountryModel.getLanng(), ChatActivity.receiverCountryModel.getNameSmall());


    }

    String listenLangPair;

    public void yetToComplete()
    {
        Snackbar.make(anyTalkSwitchIv,"This Service is yet to Come", Snackbar.LENGTH_SHORT).show();
    }

    public void send(String msg) {

        if (!msg.equals("")) {
            sendMessage(firebaseUser.getUid(), userId, msg);
        } else {
            Toast.makeText(MessageActivity.this, "You can't send empty message ", Toast.LENGTH_SHORT).show();
        }
    }

    String languagePair = ""; //English to French ("<source_language>-<target_language>")


    String translatedResult = "";

    //Function for calling executing the Translator Background Task
    void Translate(String textToBeTranslated, String languagePair) {
        TranslatorBackgroundTask translatorBackgroundTask = new TranslatorBackgroundTask(getApplicationContext());

        AsyncTask<String, Void, String> translationResult = translatorBackgroundTask.execute(textToBeTranslated, languagePair);

        try {
            translatedResult = translationResult.get();

            say(translatedResult);


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    private void sendMessage(String sender, String receiver, String message) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

        reference.child("Chats").push().setValue(hashMap);

    }

    boolean speakFlag = false;

    String phoneOutput = "";
    ArrayList<String> speechSay = new ArrayList<>();


    private void readMessages(final String myid, final String userid, final String imageurl) {


        databaseReference = FirebaseDatabase.getInstance().getReference("Chats");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);

                    if (chat.getReceiver().equals(myid) && chat.getSender().equals(userid) ||
                            chat.getReceiver().equals(userid) && chat.getSender().equals(myid)) {

                        if (chat.getReceiver().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                            if (!speakFlag) {
                                speechSay.add(chat.getMessage());

                            }

                        }

                    }

                }
                if (!speakFlag) {
                    if (speechSay.size() > 0)
                        phoneOutput = speechSay.get(speechSay.size() - 1);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            Translate(phoneOutput, languagePair);
                        }
                    }).start();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private TextToSpeech textToSpeechSystem;
    Locale locale;

    public void say(final String ans) {

        textToSpeechSystem = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {

                  //  Toast.makeText(getApplicationContext(), ans, Toast.LENGTH_SHORT).show();
                    speechSay.clear();
                   // System.out.println("yasir listening " + ans + "  " + locale.getCountry() + " - " + locale.getLanguage());

                    textToSpeechSystem.setLanguage(locale);
                    textToSpeechSystem.speak(ans, TextToSpeech.QUEUE_FLUSH, null);

                }
            }
        });

    }


    private SpeechRecognizer mySpeechRecognizer;


    private void initailzeSpeechRecognizer() {

        // check speech recognizer is availabe

        if(SpeechRecognizer.isRecognitionAvailable(this)){

            mySpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
            mySpeechRecognizer.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {

                }

                @Override
                public void onBeginningOfSpeech() {

                }

                @Override
                public void onRmsChanged(float rmsdB) {

                }

                @Override
                public void onBufferReceived(byte[] buffer) {

                }

                @Override
                public void onEndOfSpeech() {



                }

                @Override
                public void onError(int error) {

                    mySpeechRecognizer.startListening(mSpeechRecognizerIntent);


                }

                @Override
                public void onResults(Bundle results) {

                    ArrayList<String> result = results.getStringArrayList(
                            SpeechRecognizer.RESULTS_RECOGNITION
                    );

                    // output string;
                  //  System.out.println("yasir speaking " + result.get(0));
                  //  Toast.makeText(MessageActivity.this, ""+ result.get(0), Toast.LENGTH_SHORT).show();
                    send(result.get(0));
                    mySpeechRecognizer.startListening(mSpeechRecognizerIntent);



                }

                @Override
                public void onPartialResults(Bundle partialResults) {

                }

                @Override
                public void onEvent(int eventType, Bundle params) {

                }
            });

        }
    }

}
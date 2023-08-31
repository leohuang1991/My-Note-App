package com.example.mymultipleabilityapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.klinker.android.link_builder.Link;
import com.klinker.android.link_builder.LinkBuilder;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import kotlin.jvm.internal.markers.KMutableList;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    int count=0;
    LinearLayout linearLayout1,linearLayout2;
    TextView textView,textViewstart;
    ProgressBar progressBar;
    ImageView imageViewpen,imageViewya,imageView;
    RecyclerView recyclerView;
    Adapter adapter;
    List<Object> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initcomponent();
        startlink();
        Start();
        imagecllick();
    }

    private void initcomponent() {
        //@SuppressLint({"MissingInflatedId", "LocalSuppress"})
        textView=findViewById(R.id.text);
        linearLayout1=findViewById(R.id.linearLayout1);
        linearLayout2=findViewById(R.id.linearLayout2);
        //@SuppressLint({"MissingInflatedId", "LocalSuppress"})
        textViewstart=findViewById(R.id.starttextView);
        //@SuppressLint({"MissingInflatedId", "LocalSuppress"})
        progressBar=findViewById(R.id.pb);
        imageViewpen=findViewById(R.id.pen);
        imageViewya=findViewById(R.id.imageya);
        imageView=findViewById(R.id.image);
        recyclerView=findViewById(R.id.rec);
        list=new ArrayList<>();
        adapter=new Adapter(MainActivity.this,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(adapter);
        //linearLayout1.setGravity(LinearLayout.HORIZONTAL);
        mediaPlayer=MediaPlayer.create(MainActivity.this,R.raw.wine);
        mediaPlayer.start();
    }

    private void startlink() {
        @SuppressLint("Range")
        Link link=new Link(textView.getText().toString())
                .setTextColor(Color.parseColor("#4152AA"))
                .setTextColorOfHighlightedLink(Color.parseColor("#4152AA"))
                .setOnClickListener(new Link.OnClickListener() {
                    @Override
                    public void onClick(@NonNull String s) {
                        Toast.makeText(MainActivity.this,/*System.currentTimeMillis()+*/"hey",Toast.LENGTH_SHORT).show();
                    }
                });
        LinkBuilder.on(textView).addLink(link).build();
    }

    private void Start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (count<=100){
                    Animation animation= AnimationUtils.loadAnimation(MainActivity.this,R.anim.start);
                    textViewstart.startAnimation(animation);
                    progressBar.setProgress(count);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(count==100){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textViewstart.setVisibility(View.INVISIBLE);
                                progressBar.setVisibility(View.INVISIBLE);
                                textView.setVisibility(View.VISIBLE);
                                linearLayout1.setVisibility(View.VISIBLE);
                                linearLayout2.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                    count+=10;
                }
            }
        }).start();
    }

    private void imagecllick() {
        imageViewpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText=new EditText(MainActivity.this);
                editText.setLayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                        ,LinearLayout.LayoutParams.WRAP_CONTENT));
                //linearLayout1.addView(editText);
                list.add(editText);
                adapter.getlist(list);
            }
        });
        imageViewya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap more= BitmapFactory.decodeResource(getResources(), R.drawable.oni);
                list.add(more);
                adapter.getlist(list);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,100);
            }
        });
    }

    @SuppressLint("Recycle")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            if(resultCode == RESULT_OK){
                Uri uri= data.getData();
                InputStream inputStream=null;
                try {
                    assert uri !=null;
                    inputStream=getContentResolver().openInputStream(uri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                BitmapFactory.decodeStream(inputStream);
                list.add(uri);
                adapter.getlist(list);
            }
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isDestroyed()) {
                    if (!mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
                    }
                }
            }
        }).start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }
}
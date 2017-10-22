package com.example.besay.appmusica;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ElvisActivity extends AppCompatActivity {

    MediaPlayer mPlayer = new MediaPlayer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elvis);

        final ImageButton botonElvis = (ImageButton) findViewById(R.id.imageButton);
        mPlayer = MediaPlayer.create(this,R.raw.elvis);
        botonElvis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mPlayer.isPlaying()){
                    mPlayer.pause();
                    ((ImageButton) botonElvis).setImageResource(R.drawable.ic_play_arrow_black_24dp);
                } else {
                    ((ImageButton) botonElvis).setImageResource(R.drawable.ic_pause_black_24dp);
                    mPlayer.start();
                }

            }
        });

    }
}

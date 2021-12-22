package com.example.musicappbasic;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    TextView txvSongTitle, txvNameArtist, txvCountTime , txvTotalTime;
    SeekBar seekBarSong;
    ImageView imgViewDisc;
    ArrayList<Song> arraySong;
    Button btnPlay,btnPause;
    MediaPlayer mediaPlayer;
    int position = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        addSong();
        PlaySong();
    }
    private void initView() {
        txvSongTitle = (TextView) findViewById(R.id.text_view_title);
        txvNameArtist = (TextView) findViewById(R.id.text_view_artist);
        txvCountTime = (TextView) findViewById(R.id.text_view_count_time);
        txvTotalTime = (TextView) findViewById(R.id.text_view_total_count);
        imgViewDisc = (ImageView) findViewById(R.id.image_view_disc);
        seekBarSong = (SeekBar) findViewById(R.id.seek_bar_music);
        btnPlay = (Button) findViewById(R.id.image_button_play);

    }
    private  void addSong() {
        arraySong = new ArrayList<>();
        arraySong.add(new Song("Save Your Tears","The Weekend",R.raw.save_your_tears));
        arraySong.add(new Song("Bad Habits","Ed Sharen",R.raw.bad_habits));
        arraySong.add(new Song("Unstoppable","Sia",R.raw.unstop));
    }
    private void PlaySong() {
        mediaPlayer = MediaPlayer.create(this,arraySong.get(position).getFile());
        txvSongTitle.setText(arraySong.get(position).getNameSong());
        txvNameArtist.setText(arraySong.get(position).getArtist());
    }

    public void eventButtonShuffle(View view) {
        Collections.shuffle(arraySong);
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        PlaySong();
        mediaPlayer.start();
        setTimeTotal();
    }

    public void eventButtonReWind(View view) {
        position--;
        if(position < 0) {
            position = arraySong.size() -1;
        }
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        PlaySong();
        mediaPlayer.start();
        btnPlay.setBackgroundResource(R.drawable.ic_baseline_pause_24);
        setTimeTotal();
        updateTimeSong();

    }

    public void eventButtonFoward(View view) {
        position++;
        if(position > arraySong.size()-1) {
            position = 0;
        }
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        PlaySong();
        mediaPlayer.start();
        btnPlay.setBackgroundResource(R.drawable.ic_baseline_pause_24);
        setTimeTotal();
        updateTimeSong();
    }

    public void eventButtonPlay(View view) {
        if(mediaPlayer.isPlaying()) {
            view.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
            view.setTag("Pause");
            mediaPlayer.pause();

        }
        else {
            view.setTag("Play");
            mediaPlayer.start();
            view.setBackgroundResource(R.drawable.ic_baseline_pause_24);
        }
        eventImageViewDiscRotae(view);
        setTimeTotal();
        updateTimeSong();
    }

    public void eventButtonRepeat(View v) {
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
 /*       if(mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            btnPlay.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);

        }
                PlaySong();
                mediaPlayer.start();
                new CountDownTimer(mediaPlayer.getDuration(),1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        PlaySong();
                        mediaPlayer.start();
                        this.start();
                    }
                }.start();
                btnPlay.setBackgroundResource(R.drawable.ic_baseline_pause_24);

                */
        setTimeTotal();
        updateTimeSong();
    }

    private void setTimeTotal()  {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        txvTotalTime.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        seekBarSong.setMax(mediaPlayer.getDuration());
        seekBarSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // retrieve range music
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
    }
    private void updateTimeSong() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                txvCountTime.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                seekBarSong.setProgress(mediaPlayer.getCurrentPosition());
                handler.postDelayed(this,1000);
               mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                   @Override
                   public void onCompletion(MediaPlayer mp) {
                       position++;
                       if(position > arraySong.size()-1) {
                           position = 0;
                       }
                       if(mediaPlayer.isPlaying()) {
                           mediaPlayer.stop();
                           mediaPlayer.release();
                       }
                       PlaySong();
                       mediaPlayer.start();
                       btnPlay.setBackgroundResource(R.drawable.ic_baseline_pause_24);
                       setTimeTotal();
                       updateTimeSong();
                   }
               });

            }
        },1000);
    }
    public void eventImageViewDiscRotae(View v) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
               imgViewDisc.animate().rotationBy(360).withEndAction(this).setDuration(1000)
                       .setInterpolator(new LinearInterpolator()).start();
            }
        };
        if(v.getTag() == "Play") {
            imgViewDisc.animate().rotationBy(360).rotationX(360).withEndAction(runnable).setDuration(1000).setInterpolator(new LinearInterpolator()).start();

        }
        else if(v.getTag()=="Pause") {
            imgViewDisc.animate().rotationBy(360).rotationX(360).withEndAction(runnable).setDuration(1000).setInterpolator(new LinearInterpolator()).cancel();
        }
        else  {
            imgViewDisc.animate().rotationBy(360).rotationX(360).withEndAction(runnable).setDuration(1000).setInterpolator(new LinearInterpolator()).cancel();

        }

    }
}
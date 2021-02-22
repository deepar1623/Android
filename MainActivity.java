package com.example.sonyvaio.musicplayer;

import android.content.ContentResolver;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    ListView lvMusic;
    ImageButton imageButtonPause;
    ImageButton imageButtonStop;
    String name[],path[];
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvMusic= (ListView) findViewById(R.id.lvMusic);
        imageButtonPause = (ImageButton) findViewById(R.id.imagebuttonPause);
        imageButtonStop = (ImageButton) findViewById(R.id.imagebuttonStop);

        mp=new MediaPlayer();

        ContentResolver cr=getContentResolver();
        Cursor cursor=cr.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,null);

        name=new String[cursor.getCount()];
        path=new String[cursor.getCount()];

        int i=0;

        while (cursor.moveToNext()) {

            name[i]=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
            path[i]=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            i++;


        }

        ArrayAdapter<String>adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,name);

        lvMusic.setAdapter(adapter);

        final int finalI = i;
        lvMusic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                String p=path[i];
                try{
                    mp.reset();
                    mp.setDataSource(p);
                    mp.prepare();
                    mp.start();
                    imageButtonPause.setEnabled(true);
                    imageButtonStop.setEnabled(true);
                    imageButtonPause.setVisibility(R.id.imagebuttonPause);

                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        });


        imageButtonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mp.isPlaying()) {
                    mp.pause();
                    imageButtonPause.setVisibility(R.id.imagebuttonPause);
                }
                else{
                    mp.start();
                    imageButtonPause.setVisibility(R.id.imagebuttonPause);

                }

            }
        });


        imageButtonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                imageButtonStop.setEnabled(false);
                imageButtonStop.setEnabled(false);
            }
        });


    }
}

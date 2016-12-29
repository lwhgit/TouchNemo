package com.nemo.touchnemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.nemo.touchnemo.view.NemoTrackEditor;

public class MainActivity extends AppCompatActivity {

    private int speed = 1;
    private boolean isPlaying = false;

    private RelativeLayout mainLayout = null;
    private Button newProjectBtn = null;
    private Button loadProjectBtn = null;
    private Button exportProjectBtn = null;
    private Button shareProjectBtn = null;
    private Button turnThemeBtn = null;
    private Button speedBtn = null;
    private Button delTrackBtn = null;
    private Button playNpauseBtn = null;
    private Button stopBtn = null;
    private Button addBtn = null;

    private NemoTrackEditor nemoTrackEditor= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Property.currentTheme = Property.THEME_BALCK;

        mainLayout = (RelativeLayout) findViewById(R.id.main_layout);

        newProjectBtn = (Button) findViewById(R.id.new_project_btn);

        loadProjectBtn = (Button) findViewById(R.id.load_project_btn);

        exportProjectBtn = (Button) findViewById(R.id.export_project_btn);

        shareProjectBtn = (Button) findViewById(R.id.share_project_btn);

        turnThemeBtn = (Button) findViewById(R.id.turn_theme_btn);
        turnThemeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Property.currentTheme == Property.THEME_BALCK) {
                    Property.currentTheme = Property.THEME_WHITE;
                }else if (Property.currentTheme == Property.THEME_WHITE) {
                    Property.currentTheme = Property.THEME_BALCK;
                }

                if (speed == 1) {
                    speedBtn.setBackgroundResource(Property.getResource(Property.RESOURCE_X1_BUTTON));
                }else if (speed == 2) {
                    speedBtn.setBackgroundResource(Property.getResource(Property.RESOURCE_X2_BUTTON));
                }else if (speed == 4) {
                    speedBtn.setBackgroundResource(Property.getResource(Property.RESOURCE_X4_BUTTON));
                }
                delTrackBtn.setBackgroundResource(Property.getResource(Property.RESOURCE_TRACK_DELETE_BUTTON));
                playNpauseBtn.setBackgroundResource(Property.getResource(Property.RESOURCE_PLAY_N_PAUSE_BUTTON));
                stopBtn.setBackgroundResource(Property.getResource(Property.RESOURCE_STOP_BUTTON));
                addBtn.setBackgroundResource(Property.getResource(Property.RESOURCE_TRACK_ADD_BUTTON));
            }
        });

        speedBtn = (Button) findViewById(R.id.speed_btn);
        speedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (speed == 1) {
                    speedBtn.setBackgroundResource(Property.getResource(Property.RESOURCE_X1_BUTTON));
                    speed = 2;
                }else if (speed == 2) {
                    speedBtn.setBackgroundResource(Property.getResource(Property.RESOURCE_X2_BUTTON));
                    speed = 4;
                }else if (speed == 4) {
                    speedBtn.setBackgroundResource(Property.getResource(Property.RESOURCE_X4_BUTTON));
                    speed = 1;
                }
            }
        });

        delTrackBtn = (Button) findViewById(R.id.del_btn);
        delTrackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nemoTrackEditor.getTrackCount()>1)
                    nemoTrackEditor.deleteTrack();
            }
        });

        playNpauseBtn = (Button) findViewById(R.id.play_and_pause_btn);
        playNpauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playNpauseBtn.setSelected(!playNpauseBtn.isSelected());
                isPlaying = playNpauseBtn.isSelected();
            }
        });

        stopBtn = (Button) findViewById(R.id.stop_btn);

        addBtn = (Button) findViewById(R.id.add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nemoTrackEditor.addTrack();
            }
        });

        nemoTrackEditor = (NemoTrackEditor) findViewById(R.id.track_editor);
    }
}

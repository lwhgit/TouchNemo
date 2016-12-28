package com.nemo.touchnemo;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nemo.touchnemo.view.NemoTrackEditor;

public class MainActivity extends AppCompatActivity {

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

        newProjectBtn = (Button) findViewById(R.id.new_project_btn);

        loadProjectBtn = (Button) findViewById(R.id.load_project_btn);

        exportProjectBtn = (Button) findViewById(R.id.export_project_btn);

        shareProjectBtn = (Button) findViewById(R.id.share_project_btn);

        turnThemeBtn = (Button) findViewById(R.id.turn_theme_btn);

        speedBtn = (Button) findViewById(R.id.speed_btn);

        delTrackBtn = (Button) findViewById(R.id.del_btn);
        delTrackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nemoTrackEditor.getTrackCount()>1)
                    nemoTrackEditor.deleteTrack();
            }
        });

        playNpauseBtn = (Button) findViewById(R.id.play_and_pause_btn);

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

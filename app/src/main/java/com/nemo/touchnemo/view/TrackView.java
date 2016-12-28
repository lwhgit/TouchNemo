package com.nemo.touchnemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.nemo.touchnemo.R;

import java.util.ArrayList;

/**
 * Created by W on 2016-12-26.
 */

public class TrackView extends LinearLayout {

    private int max = 84;
    private int width = 0;
    private int height = 0;
    private int blockSize = 0;

    private View[] noteView = null;
    private Context context = null;
    private TrackData trackData = null;

    public TrackView(Context context, int blockSize) {
        super(context);
        this.context = context;
        this.blockSize = blockSize;
        init();
    }

    public void  init() {
        trackData = new TrackData();
        setOrientation(VERTICAL);

        noteView = new View[max];
        for (int i = 0;i < max;i ++) {
            noteView[i] = new View(context);
            noteView[i].setLayoutParams(new LayoutParams(blockSize, blockSize));
            noteView[i].setBackgroundResource(R.mipmap.black_nemo1);
            noteView[i].setTag(new NoteData(i));
            noteView[i].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    NoteData noteData = (NoteData) view.getTag();
                    if (noteData.isEnable()){
                        view.setBackgroundResource(R.mipmap.black_nemo1);
                        noteData.setEnable(false);
                        trackData.setTrack(noteData.getNumber(), noteData.isEnable());
                    }else {
                        view.setBackgroundResource(R.mipmap.black_nemo2);
                        noteData.setEnable(true);
                        trackData.setTrack(noteData.getNumber(), noteData.isEnable());
                    }
                }
            });
            addView(noteView[i]);
        }
    }

    public TrackData getTrackData() {
        return trackData;
    }


    public class TrackData {
        private ArrayList<Boolean> noteList = null;

        public TrackData() {
            noteList = new ArrayList<>();
            for (int i = 0;i < max;i ++) {
                noteList.add(false);
            }
        }

        public void setTrack(int num, boolean enable) {
            noteList.set(num, enable);
        }

        public boolean getNoteState(int num) {
            return noteList.get(num);
        }
    }

    private class NoteData {
        private int number = 0;
        private boolean enable = false;

        public NoteData(int number) {
            this.number = number;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }

        public int getNumber() {
            return number;
        }

        public boolean isEnable() {
            return enable;
        }
    }
}

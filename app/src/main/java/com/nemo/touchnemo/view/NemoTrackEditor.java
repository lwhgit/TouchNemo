package com.nemo.touchnemo.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nemo.touchnemo.R;

import java.util.ArrayList;

/**
 * Created by W on 2016-12-26.
 */

public class NemoTrackEditor extends LinearLayout {

    private int width = 0;
    private int height = 0;
    private int blockSize = 0;
    private int verMaxScroll = 0;

    private Context context = null;
    private ArrayList<NemoTrackView> nemoTrackViewList = null;

    private NemoSeekBar nemoSeekBar = null;
    private ScrollView verScrollView = null;
    private LinearLayout syllableNameLayout = null;
    private TextView[] syllableNameView = null;
    private LinearLayout allLayout = null;
    private HorizontalScrollView horScrollView = null;
    private LinearLayout trackLayout = null;

    public NemoTrackEditor(Context context) {
        super(context);
        this.context = context;
    }

    public NemoTrackEditor(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public NemoTrackEditor(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        init();
    }

    private void init() {
        nemoTrackViewList = new ArrayList<>();
        blockSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height/20, context.getResources().getDisplayMetrics());

        setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        setOrientation(HORIZONTAL);

        nemoSeekBar = new NemoSeekBar(context, blockSize, height-100);
        nemoSeekBar.setMax(500);
        nemoSeekBar.setProgressListener(new NemoSeekBar.ProgressListener() {
            @Override
            public void onProgressChanged(int progress) {
                verScrollView.scrollTo(0, verMaxScroll - (progress*verMaxScroll/nemoSeekBar.getMax()));
            }
        });
        addView(nemoSeekBar);

        verScrollView = new ScrollView(context);
        verScrollView.setFillViewport(true);
        verScrollView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        allLayout = new LinearLayout(context);
        allLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        allLayout.setOrientation(HORIZONTAL);

        syllableNameLayout = new LinearLayout(context);
        syllableNameLayout.setLayoutParams(new LayoutParams(blockSize, LayoutParams.MATCH_PARENT));
        syllableNameLayout.setOrientation(VERTICAL);

        syllableNameView = new TextView[84];
        for (int i = 0;i <  syllableNameView.length;i ++) {
            int oct = (83-i)/12;
            int sya = (83-i)%12;
            syllableNameView[i] = new TextView(context);
            syllableNameView[i].setLayoutParams(new LayoutParams(blockSize, blockSize));
            syllableNameView[i].setBackgroundResource(R.drawable.black_syallable_name_view);
            syllableNameView[i].setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
            syllableNameView[i].setPadding(10, 0, 0, 0);
            syllableNameView[i].setTextColor(0xFFFFFFFF);
            syllableNameView[i].setText(getAlphabetFromInt(sya) + "" + oct);
            syllableNameLayout.addView(syllableNameView[i]);
        }
        allLayout.addView(syllableNameLayout);

        horScrollView = new HorizontalScrollView(context);
        horScrollView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        horScrollView.setHorizontalScrollBarEnabled(true);
        horScrollView.setFillViewport(true);

        trackLayout = new LinearLayout(context);
        trackLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        trackLayout.setOrientation(HORIZONTAL);

        horScrollView.addView(trackLayout);
        allLayout.addView(horScrollView);
        verScrollView.addView(allLayout);
        addView(verScrollView);

        ScrollListener scrollListener = new ScrollListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                float curX = motionEvent.getRawX();
                float curY = motionEvent.getRawY();
                int action = motionEvent.getAction();

                if (action == MotionEvent.ACTION_MOVE) {
                    if (firstEvent) {
                        oldX = curX;
                        oldY = curY;
                        firstEvent = false;
                    }

                    verScrollView.scrollBy(0, (int) (oldY-curY));
                    horScrollView.scrollBy((int) (oldX-curX), 0);
                }else if (action == MotionEvent.ACTION_UP) {
                    firstEvent = true;
                }

                oldX = curX;
                oldY = curY;


                nemoSeekBar.setProgress(nemoSeekBar.getMax() - (verScrollView.getScrollY() * nemoSeekBar.getMax() / verMaxScroll));
                return true;
            }
        };

        verScrollView.setOnTouchListener(scrollListener);
        horScrollView.setOnTouchListener(scrollListener);

        final ProgressDialog progressDialog = ProgressDialog.show(context, "", "Please wait...", false, false);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                verScrollView.fullScroll(View.FOCUS_DOWN);
            }
        },1000);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                verMaxScroll = verScrollView.getScrollY();
                verScrollView.scrollTo(0, verMaxScroll/2);
                progressDialog.hide();
                addTrack();
            }
        },2000);

    }

    public void addTrack() {
        NemoTrackView nemoTrackView = new NemoTrackView(context, blockSize);
        nemoTrackView.setLayoutParams(new LayoutParams(blockSize, LayoutParams.MATCH_PARENT));
        trackLayout.addView(nemoTrackView);
        nemoTrackViewList.add(nemoTrackView);
    }

    public void deleteTrack() {
        trackLayout.removeViewAt(trackLayout.getChildCount()-1);
        nemoTrackViewList.remove(nemoTrackViewList.size()-1);
    }

    public int getTrackCount() {
        return nemoTrackViewList.size();
    }

    public boolean getNote(int trackNumber, int pos) {
        return nemoTrackViewList.get(trackNumber).getTrackData().getNoteState(pos);
    }

    private String getAlphabetFromInt(int i) {
        switch (i) {
            case 0:
                return "C";
            case 1:
                return "C#";
            case 2:
                return "D";
            case 3:
                return "D#";
            case 4:
                return "E";
            case 5:
                return "F";
            case 6:
                return "F#";
            case 7:
                return "G";
            case 8:
                return "G#";
            case 9:
                return "A";
            case 10:
                return "A#";
            case 11:
                return "B";
            default:
                return null;
        }
    }

    class ScrollListener implements OnTouchListener {
        boolean firstEvent = true;
        float oldX = -1;
        float oldY = -1;

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return false;
        }
    }

    private static class NemoTrackView extends LinearLayout {

        private int max = 84;
        private int width = 0;
        private int height = 0;
        private int blockSize = 0;

        private View[] noteView = null;
        private Context context = null;
        private TrackData trackData = null;

        public NemoTrackView(Context context, int blockSize) {
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
}

package com.nemo.touchnemo.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nemo.touchnemo.Property;
import com.nemo.touchnemo.R;

import java.util.ArrayList;
import java.util.HashMap;

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

    private NemoSeekBar nemoVerSeekBar = null;
    private ScrollView verScrollView = null;
    private HorizontalScrollView trackNumViewScrView = null;
    private LinearLayout trackNumViewWrapperLayout = null;
    private LinearLayout syllableNameLayout = null;
    private InfoView[] syllableNameView = null;
    private LinearLayout allLayout = null;
    private LinearLayout trackWrapperLayout = null;
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

        nemoVerSeekBar = new NemoSeekBar(context, blockSize, height-100, NemoSeekBar.STYLE_VERTICAL);
        nemoVerSeekBar.setMax(500);
        nemoVerSeekBar.setProgressListener(new NemoSeekBar.ProgressListener() {
            @Override
            public void onProgressChanged(int progress) {
                verScrollView.scrollTo(0, verMaxScroll - (progress*verMaxScroll/nemoVerSeekBar.getMax()));
            }
        });
        addView(nemoVerSeekBar);

        allLayout = new LinearLayout(context);
        allLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        allLayout.setOrientation(VERTICAL);

        trackNumViewScrView = new HorizontalScrollView(context);
        LayoutParams tnvsvParams = new LayoutParams(LayoutParams.MATCH_PARENT, blockSize);
        tnvsvParams.setMargins(blockSize, 0, 0, 0);
        trackNumViewScrView.setLayoutParams(tnvsvParams);
        trackNumViewScrView.setFillViewport(true);
        trackNumViewScrView.setHorizontalScrollBarEnabled(false);

        trackNumViewWrapperLayout = new LinearLayout(context);
        trackNumViewWrapperLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        trackNumViewWrapperLayout.setOrientation(HORIZONTAL);

        verScrollView = new ScrollView(context);
        verScrollView.setFillViewport(true);
        verScrollView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, height-blockSize));
        verScrollView.setVerticalScrollBarEnabled(false);

        trackWrapperLayout = new LinearLayout(context);
        trackWrapperLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        trackWrapperLayout.setOrientation(HORIZONTAL);

        syllableNameLayout = new LinearLayout(context);
        syllableNameLayout.setLayoutParams(new LayoutParams(blockSize, LayoutParams.MATCH_PARENT));
        syllableNameLayout.setOrientation(VERTICAL);

        syllableNameView = new InfoView[84];
        for (int i = 0;i <  syllableNameView.length;i ++) {
            int oct = (83-i)/12;
            int sya = (83-i)%12;
            syllableNameView[i] = new InfoView(context);
            syllableNameView[i].setLayoutParams(new LayoutParams(blockSize, blockSize));
            syllableNameView[i].setBackgroundResource(Property.getResource(Property.RESOURCE_INFO_VIEW));
            syllableNameView[i].setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
            syllableNameView[i].setPadding(10, 0, 0, 0);
            syllableNameView[i].setTextColor(0xFFFFFFFF);
            syllableNameView[i].setText(getAlphabetFromInt(sya) + "" + oct);
            syllableNameLayout.addView(syllableNameView[i]);
        }

        horScrollView = new HorizontalScrollView(context);
        horScrollView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        horScrollView.setHorizontalScrollBarEnabled(false);
        horScrollView.setFillViewport(true);

        trackLayout = new LinearLayout(context);
        trackLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        trackLayout.setOrientation(HORIZONTAL);

        horScrollView.addView(trackLayout);
        trackNumViewScrView.addView(trackNumViewWrapperLayout);
        allLayout.addView(trackNumViewScrView);
        trackWrapperLayout.addView(syllableNameLayout);
        trackWrapperLayout.addView(horScrollView);
        verScrollView.addView(trackWrapperLayout);
        allLayout.addView(verScrollView);
        addView(allLayout);

        ScrollListener scrollListener = new ScrollListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                float curX = motionEvent.getX();
                float curY = motionEvent.getY();
                int action = motionEvent.getAction();

                /*if (motionEvent.getPointerCount()==2) {
                    float curX2 = motionEvent.getX(1);
                    float curY2 = motionEvent.getY(1);
                    float curDis = (float) Math.sqrt(Math.pow(curX - curX2, 2) + Math.pow(curY - curY2, 2));

                    if (action == MotionEvent.ACTION_MOVE) {
                        if (oldDis == -1) {
                            System.out.println("F");
                            oldDis = curDis;
                        }

                        blockSize += (curDis - oldDis);
                        if (blockSize < 5) {
                            blockSize = 5;
                        }

                        syllableNameLayout.setLayoutParams(new LayoutParams(blockSize, LayoutParams.MATCH_PARENT));
                        for (View v : syllableNameView) {
                            v.setLayoutParams(new LayoutParams(blockSize, blockSize));
                        }
                        LayoutParams p = new LayoutParams(LayoutParams.MATCH_PARENT, blockSize);
                        p.setMargins(blockSize, 0, 0, 0);
                        trackNumViewScrView.setLayoutParams(p);
                        for (int i = 0;i < nemoTrackViewList.size();i ++) {
                            trackNumViewWrapperLayout.getChildAt(i).setLayoutParams(new LayoutParams(blockSize, blockSize));
                            nemoTrackViewList.get(i).setLayoutParams(new LayoutParams(blockSize, LayoutParams.MATCH_PARENT));
                        }
                        for (LinearLayout l : nemoTrackViewList) {
                            for (int i = 0;i < l.getChildCount();i ++) {
                                View v  = l.getChildAt(i);
                                v.setLayoutParams(new LayoutParams(blockSize, blockSize));
                            }
                        }

                        oldDis = curDis;
                    }

                }else*/ if (motionEvent.getPointerCount()==1) {
                    oldDis = -1;
                    if (action == MotionEvent.ACTION_MOVE) {
                        if (firstEvent) {
                            oldX = curX;
                            oldY = curY;
                            firstEvent = false;
                        }

                        verScrollView.scrollBy(0, (int) (oldY - curY));
                        horScrollView.scrollBy((int) (oldX - curX), 0);
                        trackNumViewScrView.scrollBy((int) (oldX - curX), 0);
                    } else if (action == MotionEvent.ACTION_UP) {
                        firstEvent = true;
                    }

                    oldX = curX;
                    oldY = curY;
                }

                nemoVerSeekBar.setProgress(nemoVerSeekBar.getMax() - (verScrollView.getScrollY() * nemoVerSeekBar.getMax() / verMaxScroll));
                return true;
            }
        };

        verScrollView.setOnTouchListener(scrollListener);
        horScrollView.setOnTouchListener(scrollListener);
        trackNumViewScrView.setOnTouchListener(scrollListener);

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

        InfoView trackNumberView = new InfoView(context);
        trackNumberView.setLayoutParams(new LayoutParams(blockSize, blockSize));
        trackNumberView.setGravity(Gravity.CENTER);
        trackNumberView.setTextColor(Property.currentTheme == Property.THEME_BALCK ? 0xFFFFFFFF : 0xFF000000);
        trackNumberView.setText(String.valueOf(nemoTrackViewList.size()));
        trackNumViewWrapperLayout.addView(trackNumberView);
    }

    public void deleteTrack() {
        trackLayout.removeViewAt(trackLayout.getChildCount()-1);
        nemoTrackViewList.remove(nemoTrackViewList.size()-1);

        trackNumViewWrapperLayout.removeViewAt(trackNumViewWrapperLayout.getChildCount()-1);
    }

    public int getTrackCount() {
        return nemoTrackViewList.size();
    }

    public boolean getNoteState(int trackNumber, int pos) {
        return nemoTrackViewList.get(trackNumber).getTrackData().getNoteState(pos);
    }

    public ArrayList<NoteData> getEnableNoteList(int trackNumber) {
        return nemoTrackViewList.get(trackNumber).getTrackData().getEnableNoteList();
    }

    public TrackData getTrackData(int trackNumber) {
        return nemoTrackViewList.get(trackNumber).getTrackData();
    }

    public void setTheme() {
        nemoVerSeekBar.setTheme();

        for (int i = 0;i < syllableNameLayout.getChildCount();i ++) {
            syllableNameLayout.getChildAt(i).setBackgroundResource(Property.getResource(Property.RESOURCE_INFO_VIEW));
            ((InfoView) syllableNameLayout.getChildAt(i)).setTextColor(Property.currentTheme == Property.THEME_WHITE ? 0xFF000000 : 0xFFFFFFFF);
        }
        for (int i = 0;i < trackNumViewWrapperLayout.getChildCount();i ++) {
            trackNumViewWrapperLayout.getChildAt(i).setBackgroundResource(Property.getResource(Property.RESOURCE_INFO_VIEW));
            ((InfoView) trackNumViewWrapperLayout.getChildAt(i)).setTextColor(Property.currentTheme == Property.THEME_WHITE ? 0xFF000000 : 0xFFFFFFFF);
            ((NemoTrackView) trackLayout.getChildAt(i)).setTheme();
        }
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

    private class ScrollListener implements OnTouchListener {
        boolean firstEvent = true;
        float oldX = -1;
        float oldY = -1;

        float oldDis = -1;

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return false;
        }
    }

    private static class NemoTrackView extends LinearLayout {

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

        @Override
        public void setLayoutParams(ViewGroup.LayoutParams params) {
            super.setLayoutParams(params);
        }

        public void  init() {
            trackData = new TrackData();
            setOrientation(VERTICAL);

            noteView = new View[84];
            for (int i = 0;i < 84;i ++) {
                trackData.setTrack(i, false);
                noteView[i] = new View(context);
                noteView[i].setLayoutParams(new LayoutParams(blockSize, blockSize));
                noteView[i].setBackgroundResource(Property.getResource(Property.RESOURCE_NEMO_NONE));
                noteView[i].setTag(i);
                noteView[i].setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NoteData noteData = trackData.getNoteData((int) view.getTag());
                        if (noteData.isEnable()){
                            view.setBackgroundResource(Property.getResource(Property.RESOURCE_NEMO_NONE));
                            noteData.setEnable(false);
                            trackData.setTrack(noteData.getNumber(), noteData.isEnable());
                        }else {
                            view.setBackgroundResource(Property.getResource(Property.RESOURCE_NEMO_ENABLE));
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

        public void setTheme() {
            System.out.println("T");

            for (int i = 0;i < 84;i ++) {
                if (trackData.getNoteState(i)) {
                    noteView[i].setBackgroundResource(Property.getResource(Property.RESOURCE_NEMO_ENABLE));
                }else {
                    noteView[i].setBackgroundResource(Property.getResource(Property.RESOURCE_NEMO_NONE));
                }
            }
        }
    }

    private class InfoView extends TextView {
        private Context context = null;

        public InfoView(Context context) {
            super(context);
            this.context = context;
            init();
        }

        private void init() {
            setBackgroundResource(Property.getResource(Property.RESOURCE_INFO_VIEW));
        }
    }

    public static class TrackData {
        private ArrayList<NoteData> noteList = null;

        public TrackData() {
            noteList = new ArrayList<>();
            for (int i = 0;i < 84;i ++) {
                noteList.add(new NoteData(i, false));
            }
        }

        public void setTrack(int num, boolean enable) {
            NoteData data = new NoteData(num, enable);
            noteList.set(num, data);
        }

        public NoteData getNoteData(int position) {
            return noteList.get(position);
        }

        public boolean getNoteState(int num) {
            return noteList.get(num).isEnable();
        }

        public ArrayList<NoteData> getEnableNoteList() {
            ArrayList<NoteData> list = new ArrayList<>();
            for (NoteData data : noteList) {
                if (data.isEnable()) {
                    list.add(data);
                }
            }
            return list;
        }

    }

    public static class NoteData {
        private int number = 0;
        private boolean enable = false;

        public NoteData(int number, boolean enable) {
            this.number = number;
            this.enable = enable;
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

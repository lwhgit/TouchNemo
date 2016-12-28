package com.nemo.touchnemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nemo.touchnemo.R;

public class NemoSeekBar extends RelativeLayout {

    private int width = 0;
    private int height = 0;
    private int seekSize = 0;
    private int max = 100;
    private int def = 0;

    private View seekBgView = null;
    private View seekView = null;
    private Context context = null;

    private ProgressListener progressListener = null;

    public NemoSeekBar(Context context, int width, int height) {
        super(context);
        this.context = context;
        this.width = width;
        this.height = height;
        this.seekSize = height/10;
        init();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchY =(int) event.getY();
        int progress = max-(touchY*max/height);
        setProgress(progress);
        if (0 > touchY)
            setProgress(max);
        else if (height < touchY)
            setProgress(0);

        progressListener.onProgressChanged(progress);
        return true;
    }

    private void init() {
        def = height-seekSize/2;
        setGravity(Gravity.CENTER_VERTICAL);
        setLayoutParams(new LayoutParams(width, height));

        LayoutParams seekBgParams = new LayoutParams(30, LayoutParams.MATCH_PARENT);
        seekBgParams.addRule(CENTER_HORIZONTAL);
        seekBgView = new View(context);
        seekBgView.setLayoutParams(seekBgParams);
        seekBgView.setBackgroundColor(0xFFDDDDDD);
        addView(seekBgView);

        LayoutParams seekViewParams = new LayoutParams(seekSize, seekSize);
        seekViewParams.addRule(CENTER_HORIZONTAL);
        seekView = new View(context);
        seekView.setLayoutParams(seekViewParams);
        setProgress(50);
        seekView.setBackgroundResource(R.drawable.track_note_seek_bar_progress_black_shape);
        addView(seekView);
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setProgress(int progress) {
        seekView.setY(def-progress*height/max);
    }

    public void setProgressListener(ProgressListener l) {
        this.progressListener = l;
    }

    public int getMax() {
        return max;
    }

    public interface ProgressListener {
        public void onProgressChanged(int progress);
    }
}

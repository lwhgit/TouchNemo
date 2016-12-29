package com.nemo.touchnemo.view;

import android.content.Context;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nemo.touchnemo.Property;
import com.nemo.touchnemo.R;

public class NemoSeekBar extends RelativeLayout {

    public static final int STYLE_HORIZONTAL = 1;
    public static final int STYLE_VERTICAL = 2;

    private int style = STYLE_HORIZONTAL;
    private int width = 0;
    private int height = 0;
    private int seekSize = 0;
    private int max = 100;
    private int def = 0;

    private View seekBgView = null;
    private View seekView = null;
    private Context context = null;

    private ProgressListener progressListener = null;

    public NemoSeekBar(Context context, int width, int height, int style) {
        super(context);
        this.context = context;
        this.width = width;
        this.height = height;
        this.seekSize = 100;
        this.style = style;
        init();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (style == NemoSeekBar.STYLE_VERTICAL) {
            int touchY = (int) event.getY();
            int progress = max - (touchY * max / height);
            setProgress(progress);
            if (0 > touchY)
                setProgress(max);
            else if (height < touchY)
                setProgress(0);

            progressListener.onProgressChanged(progress);
        }else if (style == NemoSeekBar.STYLE_HORIZONTAL) {
            int touchX = (int) event.getX();
            int progress = (touchX * max / width);
            setProgress(progress);
            if (0 > touchX)
                setProgress(0);
            else if (width < touchX)
                setProgress(max);

            progressListener.onProgressChanged(progress);
        }
        return true;
    }

    private void init() {
        if (style == NemoSeekBar.STYLE_VERTICAL) {
            def = height - seekSize / 2;
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
            seekView.setBackgroundResource(Property.getResource(Property.RESOURCE_TRACK_NOTE_SEEK_BAR_PROGRESS_SHAPE));
            addView(seekView);
        }else if (style == NemoSeekBar.STYLE_HORIZONTAL) {
            def = - seekSize / 2;
            setGravity(Gravity.CENTER_HORIZONTAL);
            setLayoutParams(new LinearLayout.LayoutParams(width,height));

            LayoutParams seekBgParams = new LayoutParams(LayoutParams.MATCH_PARENT, 30);
            seekBgParams.addRule(CENTER_VERTICAL);
            seekBgView = new View(context);
            seekBgView.setLayoutParams(seekBgParams);
            seekBgView.setBackgroundColor(0xFFDDDDDD);
            addView(seekBgView);

            LayoutParams seekViewParams = new LayoutParams(seekSize, seekSize);
            seekViewParams.addRule(CENTER_VERTICAL);
            seekView = new View(context);
            seekView.setLayoutParams(seekViewParams);
            setProgress(0);
            seekView.setBackgroundResource(Property.getResource(Property.RESOURCE_TRACK_NOTE_SEEK_BAR_PROGRESS_SHAPE));
            addView(seekView);
        }
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setProgress(int progress) {
        if (style == NemoSeekBar.STYLE_VERTICAL) {
            seekView.setY(def - progress * height / max);
        }else if (style == NemoSeekBar.STYLE_HORIZONTAL) {
            seekView.setX(def + progress * width / max);
        }
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

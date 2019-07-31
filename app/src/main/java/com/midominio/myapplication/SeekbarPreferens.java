package com.midominio.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class SeekbarPreferens extends Preference implements SeekBar.OnSeekBarChangeListener {
    private SeekBar mSeekBar;
    private int mProgress = 0;
    private TextView sumary ;
    private TextView title ;
    private int myTitlePos;
    private static int posTitle = 0;
    private static String titles[] = {"Rojo", "Verde", "Azul"};
    private View view;
    public SeekbarPreferens(Context context) {
        this(context, null, 0);

    }

    public SeekbarPreferens(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SeekbarPreferens(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if(this.posTitle<3){
            myTitlePos = posTitle;
            posTitle++;
            if(this.posTitle==3){
                this.posTitle =0;
            }
        }

        setLayoutResource(R.layout.preferences_seekbar);
    }


    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        if (this.view == null) {
            mSeekBar = (SeekBar) view.findViewById(R.id.seekbar);
            sumary = view.findViewById(R.id.sumary);
            if (title == null) {
                title = view.findViewById(R.id.titulo);
                title.setText(titles[myTitlePos]);
            }
            Float aux = (int) mProgress * 2.55f;
            sumary.setText(String.valueOf(aux.intValue()));
            mSeekBar.setProgress(mProgress);
            mSeekBar.setOnSeekBarChangeListener(this);
        }else{
            this.view = view;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Float aux = (progress * 2.55f);
        sumary.setText(String.valueOf(aux.intValue()));
        if (!fromUser)
            return;

        setValue(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // not used
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // not used
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        setValue(restoreValue ? getPersistedInt(mProgress) : (Integer) defaultValue);
    }

    public void setValue(int value) {
        if (shouldPersist()) {
            persistInt(value);
        }

        if (value != mProgress) {
            mProgress = value;
            notifyChanged();
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getInt(index, 0);
    }
}
package com.glowman.amidrunk.gui;

import android.annotation.TargetApi;
import android.os.Build;
import android.widget.ImageView;

import com.glowman.amidrunk.MainActivity;
import com.glowman.amidrunk.R;

public class Cell {
    private ImageView _view;
    private Boolean _state;

    public Cell(ImageView view) {
        _view = view;
        _state = false;
    }

    public ImageView view() {
        return _view;
    }

    public Boolean state() {
        return _state;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void setState(Boolean state) {
        Integer pic;
        if (state && _state != state) {
            _state = state;
            if (MainActivity.VIBRO.hasVibrator()) {
                MainActivity.VIBRO.vibrate(20);
            }
            pic = R.drawable.selbtn;
            _view.setImageResource(pic);
        } else if (!state) {
            _state = state;
            pic = R.drawable.btn;
            _view.setImageResource(pic);
        }

    }

}

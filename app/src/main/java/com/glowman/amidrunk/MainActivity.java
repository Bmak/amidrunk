package com.glowman.amidrunk;

import com.glowman.amidrunk.gui.Cell;
import com.glowman.amidrunk.gui.ImageAdapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends Activity {
    public static Vibrator VIBRO;

    GridView _gridview;
    ImageAdapter _imageAdapter;
    ArrayList<Cell> _cells;

    public static enum TransitionType {
        Zoom, SlideLeft, Diagonal
    }
    public static TransitionType transitionType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        VIBRO = (Vibrator)this.getSystemService(VIBRATOR_SERVICE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        TextView title = (TextView) findViewById(R.id.fullscreen_content);
        Typeface tf = Typeface.createFromAsset(getAssets(), getString(R.string.title_font));
        title.setTypeface(tf);

        _gridview = (GridView) findViewById(R.id.gridview);
        _cells = new ArrayList<Cell>();
        _imageAdapter = new ImageAdapter(this, _cells);
        _gridview.setAdapter(_imageAdapter);

        _gridview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // нажатие
                        checkOnSelect(event.getX(), event.getY());
                        break;
                    case MotionEvent.ACTION_MOVE: // движение
                        checkOnSelect(event.getX(), event.getY());
                        break;
                    case MotionEvent.ACTION_UP: // отпускание
                        getResult();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        break;
                }
                return true;
            }
        });


    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void checkOnSelect(float x, float y) {
        for (Cell cell : _cells) {
            ImageView item = cell.view();
            if (checkBounds(x, y, item.getX(), item.getY(), item.getWidth(), item.getHeight())) {
                cell.setState(true);
            }
        }
    }

    private Boolean checkForDrunk() {
        for (Cell cell : _cells) {
            if (!cell.state()) {
                return true;
            }
        }
        return false;
    }

    private void getResult() {
        this.finish();

        Boolean result = checkForDrunk();

        Intent drunkWindow = new Intent(MainActivity.this,DrunkActivity.class);
        drunkWindow.putExtra("result",result);
        startActivity(drunkWindow);

        transitionType = TransitionType.Zoom;
        if (transitionType == TransitionType.Diagonal) {
            overridePendingTransition(R.anim.diagonal_in, R.anim.diagonal_out);
        } else if (transitionType == TransitionType.Zoom) {
            overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
        } else if (transitionType == TransitionType.SlideLeft) {
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        }

        clearSelections();
    }

    private void clearSelections() {
        for (Cell cell : _cells) {
            cell.setState(false);
        }
    }

    private Boolean checkBounds(float x1,float y1,float x2,float y2,float w,float h) {
        return x1 >= x2 && x1 <= x2 + w && y1 >= y2 && y1 <= y2 + h;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}

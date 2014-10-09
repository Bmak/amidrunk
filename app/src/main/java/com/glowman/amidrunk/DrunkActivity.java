package com.glowman.amidrunk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;



public class DrunkActivity extends Activity {

    private Boolean _result;
    private Button _map_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_drunk);

        _result = getIntent().getExtras().getBoolean("result");
        ImageView image = (ImageView) findViewById(R.id.drunkResult);

        if (_result) {
            image.setImageResource(R.drawable.yes);
        } else {
            image.setImageResource(R.drawable.no);
        }
/*
        addContentView(image,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));*/
        image.setY(150);

        _map_btn = (Button) findViewById(R.id.map_btn);
        _map_btn.setOnClickListener((View.OnClickListener) new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapWindow = new Intent(DrunkActivity.this,MapActivity.class);
                startActivity(mapWindow);
            }
        });

        if (MainActivity.transitionType == MainActivity.TransitionType.Diagonal) {
            overridePendingTransition(R.anim.diagonal_in, R.anim.diagonal_out);
        } else if (MainActivity.transitionType == MainActivity.TransitionType.Zoom) {
            overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
        } else if (MainActivity.transitionType == MainActivity.TransitionType.SlideLeft) {
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        }
    }

    public void onBackPressed() {
        this.finish();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}

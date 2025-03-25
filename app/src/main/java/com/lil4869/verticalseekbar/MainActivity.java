package com.lil4869.verticalseekbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    VerticalSeekBar verticalSeekBar;
    TextView progressTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verticalSeekBar=findViewById(R.id.center_volume_seek_bar);
        progressTextView=findViewById(R.id.progress_val);
        verticalSeekBar.setVerticalProgressChangedListener(new VerticalSeekBar.VerticalProgressChangedListener() {
            @Override
            public void onVerticalProgressChange(boolean fromUser, int progress) {
                progressTextView.setText(String.valueOf(progress));
            }
        });
    }
}
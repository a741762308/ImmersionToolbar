package com.jsqix.immersiontoolbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ComplexActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complex);
        StatusBarCompat.compat(this);
    }
}

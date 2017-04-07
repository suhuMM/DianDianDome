package com.example.besselline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BesselLineView bessel;
    private List<Bean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bessel = (BesselLineView) findViewById(R.id.bessel);
        list = new ArrayList<>();
        for (int i = 1; i < 16; i++) {
            Bean b = new Bean();
            b.setKey(""+i);
            b.setValue((int) (Math.random()*100));
            list.add(b);
        }
        bessel.setList(list);
    }
}

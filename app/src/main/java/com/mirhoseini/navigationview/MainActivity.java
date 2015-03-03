package com.mirhoseini.navigationview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements NavigationView.OnNavigationListener {

    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = (NavigationView) findViewById(R.id.navigationView);

        // set navigation disabled color
        // navigationView.setFillDisabledColor(Color.BLUE);

        // set navigation fill color
        // navigationView.setFillColor(Color.RED);

        // set on Navigation Listener
        // navigationView.setOnNavigationListener(this);

        // you can disable any navigation button
        navigationView.setButtonsEnabled(false, true, true, true);
    }

    @Override
    public void onDownClick(View v) {
        // down navigation pressed
        Toast.makeText(this, "onDownClick", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLeftClick(View v) {
        // left navigation pressed
        Toast.makeText(this, "onLeftClick", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpClick(View v) {
        // up navigation pressed
        Toast.makeText(this, "onUpClick", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRightClick(View v) {
        // right navigation pressed
        Toast.makeText(this, "onRightClick", Toast.LENGTH_SHORT).show();
    }
}

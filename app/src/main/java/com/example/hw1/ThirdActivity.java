package com.example.hw1;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class ThirdActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Intent i = getIntent();
        String story = i.getExtras().getString("story");
        LinearLayout L = (LinearLayout) findViewById(R.id.transition_layout_save);
        //add header
        TextView tv1 = new TextView(this);
        tv1.setText("Your Madlib");
        tv1.setGravity(Gravity.CENTER);
        tv1.setPadding(0, 5, 0, 20);
        tv1.setTypeface(tv1.getTypeface(), Typeface.BOLD);
        tv1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30f);
        L.addView(tv1);
        //add madlib
        TextView tv2 = new TextView(this);
        tv2.setText(story);
        tv2.setPadding(0, 0, 0, 20);
        tv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30f);
        L.addView(tv2);
        //add button to start over
        Button b = new Button(this);
        b.setText("Start Over");
        b.setPadding(0, 0, 0, 10);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                toBeginning();
            }
        });
        L.addView(b);
    }

    public void toBeginning(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}

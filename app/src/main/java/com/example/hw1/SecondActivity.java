package com.example.hw1;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Arrays;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent i = getIntent();
        String data = i.getExtras().getString("data");
        JSONObject jsn = null;
        LinearLayout lLayout = (LinearLayout) findViewById(R.id.transition_layout_save);
        try {
            jsn = new JSONObject(data);
            String title = jsn.getString("title");
            JSONArray blanks = jsn.getJSONArray("blanks");
            JSONArray story = jsn.getJSONArray("value");
            String[] libs = new String[blanks.length()];
            addTitle(lLayout, title);
            addInputs(blanks, lLayout, libs);
            addButton(lLayout, libs, story);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void addTitle(LinearLayout L, String title){
        TextView tv = new TextView(this);
        tv.setText(title);
        tv.setGravity(Gravity.CENTER);
        tv.setPadding(0, 10, 0, 2);
        tv.setTypeface(tv.getTypeface(), Typeface.BOLD);
        L.addView(tv);
    }

    public void addInputs(JSONArray array, LinearLayout L, String[] arr){
        for(int i=0; i<array.length(); i++){
            try{
                addInput(L, array.getString(i), i, arr);
            } catch(JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void addInput(LinearLayout L, String pos, int i, String[] arr){
        TextView tv = new TextView(this);
        tv.setText(pos);
        EditText et = new EditText(this);
        et.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                arr[i] = sanitize(s.toString());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        et.setId(i);
        et.setSingleLine();
        tv.setPadding(0,5, 0, 5);
        L.addView(tv);
        L.addView(et);
    }

    public void addButton(LinearLayout L, String[] arr, JSONArray jsn){
        Button b = new Button(this);
        b.setText("Generate");
        b.setPadding(0, 5, 0, 15);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int empties = checkEmpties(arr);
                if(empties > 0) showToast(empties);
                else nextActivity(arr, jsn);
            }
        });
        L.addView(b);
    }

    public int checkEmpties(String[] arr){
        Log.i("array: ", Arrays.toString(arr));
        int empties = 0;
        for(int i=0; i<arr.length; i++){
            if(arr[i] == null || arr[i].equals("")) empties +=1;
        }
        return empties;
    }

    public String sanitize(String s){
        return s.trim();
    }

    public String composeMadLib(String[] arr, JSONArray jsn) throws JSONException {
        String res = "";
        for(int i=0; i<jsn.length()-2; i++){
            String sentence = (String) jsn.get(i);
            String line = sentence + arr[i];
            res += line;
        }
        res += (String) jsn.get(jsn.length()-2);
        return res;
    }

    public void showToast(int empties){
        String msg = Integer.toString(empties) + " entries missing";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, msg, duration);
        toast.show();
    }

    public void nextActivity(String[] array, JSONArray jsn){
        Intent i = new Intent(this, ThirdActivity.class);
        try{
            String msg = composeMadLib(array, jsn);
            i.putExtra("story", msg);
            startActivity(i);
        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }

}

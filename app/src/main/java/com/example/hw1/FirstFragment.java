package com.example.hw1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class FirstFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void sendReq(RequestQueue queue){
        String url = "https://madlibz.herokuapp.com/api/random?minlength=5&maxlength=10";
        JsonObjectRequest Req = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Intent i = new Intent(getActivity().getApplicationContext(), SecondActivity.class);
                        String res = response.toString();
                        i.putExtra("data", res);
                        startActivity(i);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        });
        // Add the request to the RequestQueue.
        queue.add(Req);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //for http request
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        //button stuff
        Button button = (Button) view.findViewById(R.id.button_first);
        button.setText("Get Started");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendReq(queue);
            }
        });
        //textview stuff
        TextView txt = (TextView) view.findViewById(R.id.textview_first);
        txt.setText("Welcome to MadLibz");
    }
}
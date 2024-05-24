package com.example.api;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.api.adaptadores.PersonajeAdaptador;
import com.example.api.clases.Personaje;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    List<Personaje> ListaPersonaje = new ArrayList<>();

    RecyclerView rcv_personaje;
    Button btn_guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        rcv_personaje = findViewById(R.id.rcv_personaje);
        btn_guardar = findViewById(R.id.btn_guardar);

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarInformacion();
            }
        });

        cargarInformacion();
    }

    private void guardarInformacion() {
        HashMap<String, Object> json = new HashMap<>();
        json.put("id", 101);
        json.put("title", "foo");
        json.put("body", "bar");
        json.put("userId", 1);

        JSONObject jsonObject = new JSONObject(json);

        String url = "https://jsonplaceholder.typicode.com/posts";

        JsonObjectRequest myRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                respuestaGuardar(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error en el servidor", Toast.LENGTH_LONG).show();
            }

        });
        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        rq.add(myRequest);

    }

    private void respuestaGuardar(JSONObject respuesta) {

        try {
            if (respuesta.getInt("id")==101){
                Toast.makeText(getApplicationContext(), "Se guardó correctamenre", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(), "Error al guardar", Toast.LENGTH_LONG).show();
            }
        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error en el servidor", Toast.LENGTH_LONG).show();
        }

    }

    private void cargarInformacion() {
        String url = "https://rickandmortyapi.com/api/character";
        StringRequest myRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    recibirRespuesta(new JSONObject(response));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error en el servidor", Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error en el servidor", Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        rq.add(myRequest);
    }

    private void recibirRespuesta(JSONObject respuesta) {
        try {
            for (int i =0; i<=respuesta.getJSONArray("results").length();i++){

                String nombre = respuesta.getJSONArray("results").getJSONObject(i).getString("name");
                String estado = respuesta.getJSONArray("results").getJSONObject(i).getString("status");
                String especie = respuesta.getJSONArray("results").getJSONObject(i).getString("species");
                String imagen = respuesta.getJSONArray("results").getJSONObject(i).getString("image");

                Personaje p = new Personaje(nombre,estado, especie, imagen);

                ListaPersonaje.add(p);
                rcv_personaje.setLayoutManager(new LinearLayoutManager(this));
                rcv_personaje.setAdapter(new PersonajeAdaptador(ListaPersonaje));
            }


        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error en el servidor", Toast.LENGTH_LONG).show();
        }

    }
}
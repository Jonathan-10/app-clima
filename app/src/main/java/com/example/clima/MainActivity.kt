package com.example.clima

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    var tvCiudad: TextView? = null
    var tvGrados: TextView? = null
    var tvEstatus: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvCiudad = findViewById(R.id.tvCiudad)
        tvGrados = findViewById(R.id.tvGrados)
        tvEstatus = findViewById(R.id.tvEstatus)

        val ciudad = intent.getStringExtra("com.example.clima.ciudades.CIUDAD")


        if (Network.hayRed(this)) {
            solicitudHTTPVolley("https://api.openweathermap.org/data/2.5/weather?q=$ciudad&appid=2e645064b9b1397b268b754eeb2dcc5f&units=metric&lang=es")
        } else {
            //Mostrar mensaje Error
            Toast.makeText(this, "No hay Red", Toast.LENGTH_SHORT).show()
        }
    }

    private fun solicitudHTTPVolley(url: String) {
        val queue = Volley.newRequestQueue(this)

        val solicitud = StringRequest(
            Request.Method.GET, url, Response.Listener<String> { response ->
                try {
                    Log.d("solicitudHTTPVolley", response)

                    val gson = Gson()
                    val ciudad = gson.fromJson(response, Ciudad::class.java)
                    tvCiudad?.text = ciudad.name
                    tvGrados?.text = ciudad.main?.temp.toString() + "°"
                    tvEstatus?.text = ciudad.weather?.get(0)?.description

                } catch (e: Exception) {

                }

            },
            Response.ErrorListener { })

        queue.add(solicitud)

    }
}

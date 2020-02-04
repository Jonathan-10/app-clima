package com.example.clima

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.Ciudad
import com.Network
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

        val ciudad = intent.getStringExtra("idCiudad")


        if (Network.hayRed(this)) {
            //Ejecutar solicitud HTTP
            try {
                solicitudHTTPVolley("http://api.openweathermap.org/data/2.5/weather?id=" + ciudad + "&appid=ab82e98cbc36df41768c33aa5fed53b8&units=metric")
            } catch (e: Exception) {
                Log.e(TAG, e.message)

            }
            //ab82e98cbc36df41768c33aa5fed53b8
            //ciudad de mexico 3530597
        } else {
            //Mostrar mensaje Error
            Toast.makeText(this, "No hay Red", Toast.LENGTH_SHORT).show()

        }
        /*
    val ciudadmx = Ciudad("Ciudad de Mexico", grados = 15, estatus = "Soleado")
    val ciudadBerlin = Ciudad("Ciudad de Berlin", grados = 30, estatus = "Cielo Despejado")

    if (ciudad == "ciudad_mexico") {
        //Mostrar informacion ciudadmx
        tvCiudad?.text = ciudadmx.nombre
        tvGrados?.text = ciudadmx.grados.toString() + "°"
        tvEstatus?.text = ciudadmx.estatus

    }else if (ciudad == "ciudad_berlin") {
        //Mostrar informacion ciudadmx
        tvCiudad?.text = ciudadBerlin.nombre
        tvGrados?.text = ciudadBerlin.grados.toString() + "°"
        tvEstatus?.text = ciudadBerlin.estatus

    }else{
        Toast.makeText(this, "No se encuentra la informacion", Toast.LENGTH_SHORT).show()
    }
     */
    }

    private fun solicitudHTTPVolley(url: String) {


        try {
            val queue = Volley.newRequestQueue(this)
            val solicitud = StringRequest(Request.Method.GET, url, Response.Listener<String>
            { response ->
                try {
                    Log.d("solicitudHTTPVolley", response)

                    val gson = Gson()
                    val ciudad = gson.fromJson(response, Ciudad::class.java)
                    tvCiudad!!.text = ciudad.name
                    tvGrados!!.text = ciudad.main?.temp.toString() + "°"
                    tvEstatus!!.text = ciudad.weather?.get(0)?.description

                } catch (e: Exception) {
                    Log.e(TAG, e.message)
                }

            }, Response.ErrorListener { })

            queue.add(solicitud)
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }


    }

    companion object {
        const val TAG = "MainActivity"
    }
}

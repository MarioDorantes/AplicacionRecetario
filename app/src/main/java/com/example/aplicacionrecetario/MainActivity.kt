package com.example.aplicacionrecetario

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val btnAbrirBebidas = findViewById<Button>(R.id.btnBebidas)
        btnAbrirBebidas.setOnClickListener {
            val intento1 = Intent(this, GestionarBebidas::class.java)
            startActivity(intento1)
        }

        val btnAbrirPostres = findViewById<Button>(R.id.btnPostres)
        btnAbrirPostres.setOnClickListener {
            val intento1 = Intent(this, GestionarPostres::class.java)
            startActivity(intento1)
        }

        val btnAbrirPlatosFuertes = findViewById<Button>(R.id.btnPlatoFuerte)
        btnAbrirPlatosFuertes.setOnClickListener {
            val intento1 = Intent(this, GestionarPlatosFuertes::class.java)
            startActivity(intento1)
        }


        val btnMasRecetas = findViewById<Button>(R.id.btnConsultarMasRecetas)
        btnMasRecetas.setOnClickListener{
            val intento1 = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.recetasnestle.com.mx/"))
            startActivity(intento1)
        }
    }
}
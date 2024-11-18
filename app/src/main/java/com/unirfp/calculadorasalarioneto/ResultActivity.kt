package com.unirfp.calculadorasalarioneto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // Obtener los datos enviados desde MainActivity
        val salarioBruto = intent.getDoubleExtra("salarioBruto", 0.0)
        val salarioNeto = intent.getDoubleExtra("salarioNeto", 0.0)
        val retencionIRPF = intent.getDoubleExtra("retencionIRPF", 0.0)
        val deducciones = intent.getDoubleExtra("deducciones", 0.0)

        // Referencias a los TextViews donde mostraremos los resultados
        val salarioBrutoTextView = findViewById<TextView>(R.id.SalarioBrutoResultado)
        val salarioNetoTextView = findViewById<TextView>(R.id.SalarioNetoResultado)
        val retencionIRPFTextView = findViewById<TextView>(R.id.RetencionIRPFResultado)
        val deduccionesTextView = findViewById<TextView>(R.id.DeduccionesResultado)

        // Mostrar los resultados en los TextViews
        salarioBrutoTextView.text = "Salario Bruto: $salarioBruto"
        salarioNetoTextView.text = "Salario Neto: $salarioNeto"
        retencionIRPFTextView.text = "Retención IRPF: $retencionIRPF"
        deduccionesTextView.text = "Deducciones: $deducciones"

        val volverButton = findViewById<Button>(R.id.volverButton)

        volverButton.setOnClickListener {
            // Crear un Intent para volver a MainActivity
            val intent = Intent(this, MainActivity::class.java)
            // Iniciar MainActivity
            startActivity(intent)
            // Opcionalmente, puedes terminar ResultActivity para que no quede en el stack de actividades
            finish()
        }

    }
}
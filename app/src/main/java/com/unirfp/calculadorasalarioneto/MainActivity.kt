package com.unirfp.calculadorasalarioneto

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Declaración de variables
        val salarioBrutoAnual = findViewById<EditText>(R.id.salarioBrutoDato)
        val numeroPagas = findViewById<EditText>(R.id.numeroPagasDato)
        val edad = findViewById<EditText>(R.id.edadDato)
        val grupoProfesional = findViewById<EditText>(R.id.grupoProfesionalDato)
        val discapacidad33 = findViewById<CheckBox>(R.id.gradoDiscapacidadCheckBox1)
        val discapacidad65 = findViewById<CheckBox>(R.id.gradoDiscapacidadCheckBox2)
        val estadoCivil = findViewById<EditText>(R.id.estadoCivilDato)
        val numeroHijos = findViewById<EditText>(R.id.numeroHijosDato)
        val validarButton = findViewById<Button>(R.id.validarButton)

        //Conversión de las variables anteriores (primero en objetos de tipo Editable con la propiedad .text y después en el tipo final deseado)
        validarButton.setOnClickListener {
            val salarioBrutoConvertido = salarioBrutoAnual.text.toString().toDoubleOrNull() ?: 0.0
            val numeroPagasConvertido = numeroPagas.text.toString().toIntOrNull() ?: 1
            val edadConvertido = edad.text.toString().toIntOrNull() ?: 0
            val grupoProfesionalConvertido = grupoProfesional.text.toString().toIntOrNull() ?: 3
            val discapacidad33Convertido = discapacidad33.isChecked
            val discapacidad65Convertido = discapacidad65.isChecked
            val estadoCivilConvertido = estadoCivil.text.toString()
            val numeroHijosConvertido = numeroHijos.text.toString().toIntOrNull() ?: 0

            // Cálculo del salario neto inicial
            var salarioNeto = salarioBrutoConvertido * 0.8

            // Ajuste salarioNeto por grado de discapacidad
            if (discapacidad65Convertido) {
                salarioNeto += 800
            } else if (discapacidad33Convertido) {
                salarioNeto += 400
            }

            // Ajuste salarioNeto por estado civil
            if (estadoCivilConvertido.equals("Casado", ignoreCase = true)) {
                salarioNeto += 150
            }

            // Ajuste salarioNeto por número de hijos
            salarioNeto += (numeroHijosConvertido * 40)

            // Ajuste salarioNeto por grupo profesional
            if (grupoProfesionalConvertido in 1..6) {
                // Aquí aplicas el cálculo para los grupos 1 a 6
                salarioNeto += salarioBrutoConvertido * 0.10  // Ejemplo de bonificación para grupos 1 a 6
            } else {
                // Aquí aplicas el cálculo para los grupos 7 a 11
                salarioNeto -= salarioBrutoConvertido * 0.05  // Ejemplo de deducción para grupos 7 a 11
            }

            // Cálculo de la retención de IRPF (10% del salario bruto)
            val retencionIRPF = salarioBrutoConvertido * 0.1


            // Cálculo de deducciones (5% del salario bruto)
            var deducciones = salarioBrutoConvertido * 0.05

            // Deducción adicional por número de hijos
            val deduccionPorHijo = 20  // Establecemos 50 como deducción por cada hijo
            deducciones += numeroHijosConvertido * deduccionPorHijo

            // Pasar los datos a ResultActivity
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("salarioBruto", salarioBrutoConvertido)
            intent.putExtra("salarioNeto", salarioNeto)
            intent.putExtra("retencionIRPF", retencionIRPF)
            intent.putExtra("deducciones", deducciones)
            startActivity(intent)
        }
    }
}
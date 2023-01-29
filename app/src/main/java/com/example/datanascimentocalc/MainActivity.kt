package com.example.datanascimentocalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.Duration.Companion.minutes

class MainActivity : AppCompatActivity() {

    private var dataSelecionada: TextView? = null
    private var idadeEmMinutos: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker: Button = findViewById(R.id.btn)
        dataSelecionada = findViewById(R.id.dataSelecionada)
        idadeEmMinutos = findViewById(R.id.idadeMinutos)

        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }
    }

    private fun clickDatePicker() {
        val calendario = Calendar.getInstance()
        val ano = calendario.get(Calendar.YEAR)
        val mes = calendario.get(Calendar.MONTH)
        val dia = calendario.get(Calendar.DAY_OF_MONTH)

        val dialog = DatePickerDialog(
            this,
            { view, anoSelecionado, mesSelecionado, diaSelecionado ->
                dataSelecionada?.setText(null)
                idadeEmMinutos?.setText(null)

                val datePickerSelecionado = "${diaSelecionado.toString().padStart(2, '0')}/${(mesSelecionado+1).toString().padStart(2, '0')}/${anoSelecionado.toString().padStart(4, '0')}"

                Toast.makeText(this, "Data selecionada: $datePickerSelecionado", Toast.LENGTH_SHORT).show()
                dataSelecionada?.setText(datePickerSelecionado)

                val format = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val data = format.parse(datePickerSelecionado)

                data?.let {
                    val dataEmMinutos = data.time / 60000
                    val hojeEmMinutos = (format.parse(format.format(System.currentTimeMillis()))).time / 60000

                    idadeEmMinutos?.setText((hojeEmMinutos - dataEmMinutos).toString())
                }
            },
            ano,
            mes,
            dia
        )

        dialog.datePicker.maxDate = System.currentTimeMillis() / 86400000
        dialog.show()
    }
}
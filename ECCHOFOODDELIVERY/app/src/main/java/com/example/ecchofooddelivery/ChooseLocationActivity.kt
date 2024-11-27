package com.example.ecchofooddelivery

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView

import androidx.appcompat.app.AppCompatActivity

import com.example.ecchofooddelivery.databinding.ActivityChooseLocationBinding

class ChooseLocationActivity : AppCompatActivity() {
    private val binding: ActivityChooseLocationBinding by lazy {
        ActivityChooseLocationBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val listalocacion: Array<String> = arrayOf("Ancon","Callao","Comas","Independencia","SJL","SJM","San Martín de Porres")
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,listalocacion)
        val autoCompleteTextView : AutoCompleteTextView = binding.listalocacion
        autoCompleteTextView.setAdapter(adapter)
    }
}
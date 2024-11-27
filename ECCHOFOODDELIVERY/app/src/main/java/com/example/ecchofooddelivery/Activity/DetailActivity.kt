package com.example.ecchofooddelivery.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.ecchofooddelivery.Domain.Foods
import com.example.ecchofooddelivery.Helper.ManagmentCart
import com.example.ecchofooddelivery.databinding.ActivityDetailBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var database: DatabaseReference
    private lateinit var food: Foods
    private lateinit var managmentCart: ManagmentCart
    private var num: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = FirebaseDatabase.getInstance().reference
        getIntentExtras()
        setVariable()
    }

    private fun setVariable() {
        managmentCart = ManagmentCart(this)
        binding.backBtn.setOnClickListener { finish() }
        // Cargar imagen usando Glide si imagePath no es null
        food.imagePath?.let {
            Glide.with(this)
                .load(it)
                .into(binding.pic)
        }

        binding.priceTxt.text = "S/.${food.price}"
        binding.titleTxt.text = food.title
        binding.descriptionTxt.text = food.description
        binding.ratingBar.rating = food.star.toFloat()
        binding.totalTxt.text = "${num * food.price}"
        binding.plusBtn.setOnClickListener {
            num += 1
            binding.numTxt.text = "$num "
            binding.totalTxt.text = "S/.${num * food.price}"
        }

        binding.minusBtn.setOnClickListener {
            if (num > 1) {
                num -= 1
                binding.numTxt.text = "$num"
                binding.totalTxt.text = "S/.${num * food.price}"
            }
        }

        binding.addBtn.setOnClickListener {
            food.numberIncart = num
            managmentCart.insertFood(food)
        }
    }

    private fun getIntentExtras() {
        // Verificar si se ha pasado correctamente el objeto Foods
        if (intent.hasExtra("object")) {
            food = intent.getSerializableExtra("object") as Foods
        } else {
            // Manejar el caso donde no se pasa correctamente el objeto
            finish() // Cerrar la actividad si no se pasa correctamente el objeto
        }
    }
}

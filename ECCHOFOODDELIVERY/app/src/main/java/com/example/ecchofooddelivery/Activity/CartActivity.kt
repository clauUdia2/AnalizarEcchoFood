package com.example.ecchofooddelivery.Activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecchofooddelivery.Adapter.CartAdapter
import com.example.ecchofooddelivery.Helper.ManagmentCart
import com.example.ecchofooddelivery.databinding.ActivityCartBinding
import kotlin.math.pow
import kotlin.math.roundToInt

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var managmentCart: ManagmentCart
    private lateinit var adapter: CartAdapter
    private var tax: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart = ManagmentCart(this)
        setVariables()
        calculateCart()
        initList()
    }

    private fun initList() {
        if (managmentCart.listCart.isEmpty()) {
            binding.emptyTxt.visibility = View.VISIBLE
            binding.scrollviewCart.visibility = View.GONE
        } else {
            binding.emptyTxt.visibility = View.GONE
            binding.scrollviewCart.visibility = View.VISIBLE
        }

        val linearLayoutManager = LinearLayoutManager(this)
        binding.cardView.layoutManager = linearLayoutManager
        adapter = CartAdapter(managmentCart.listCart, this) {
            calculateCart()
        }
        binding.cardView.adapter = adapter
    }

    private fun calculateCart() {
        val percentTax = 0.18
        val delivery = 3.0
        tax = (managmentCart.totalFee * percentTax).roundTo(2)
        val total = (managmentCart.totalFee + tax + delivery).roundTo(2)
        val itemTotal = managmentCart.totalFee.roundTo(2)
        binding.totalFeeTxt.text = "S/.$itemTotal"
        binding.taxTxt.text = "S/.$tax"
        binding.deliveryTxt.text = "S/.$delivery"
        binding.totalTxt.text = "S/.$total"
    }

    private fun setVariables() {
        binding.backBtn.setOnClickListener { finish() }
    }

    private fun Double.roundTo(decimals: Int): Double {
        val factor = 10.0.pow(decimals)
        return (this * factor).roundToInt() / factor
    }
}

package com.example.ecchofooddelivery.Activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecchofooddelivery.Adapter.FoodListAdapter
import com.example.ecchofooddelivery.Domain.Foods
import com.example.ecchofooddelivery.databinding.ActivityListFoodBinding
import com.google.firebase.database.*

class ListFoodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListFoodBinding
    private lateinit var database: DatabaseReference
    private lateinit var adapterListFood: FoodListAdapter
    private var categoryId: Int = 0
    private var categoryName: String? = null
    private var searchText: String? = null
    private var isSearch: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = FirebaseDatabase.getInstance().reference
        getIntentExtras()
        initList()
    }

    private fun getIntentExtras() {
        categoryId = intent.getIntExtra("CategoryId", 0)
        categoryName = intent.getStringExtra("CategoryName")
        searchText = intent.getStringExtra("text")
        isSearch = intent.getBooleanExtra("isSearch", false)
        binding.titleTxt.text = categoryName
        binding.backBtn.setOnClickListener { finish() }
    }

    private fun initList() {
        val myRef = database.child("Foods")
        binding.progressBar.visibility = View.VISIBLE
        val list = ArrayList<Foods>()
        val query: Query = if (isSearch) {
            myRef.orderByChild("Title").startAt(searchText).endAt("$searchText\uf8ff")
        } else {
            myRef.orderByChild("CategoryId").equalTo(categoryId.toDouble())
        }

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        val food = issue.getValue(Foods::class.java)
                        food?.let {
                            list.add(it)
                        }
                    }
                    if (list.isNotEmpty()) {
                        binding.foodListView.layoutManager = GridLayoutManager(this@ListFoodActivity, 2)
                        adapterListFood = FoodListAdapter(list)
                        binding.foodListView.adapter = adapterListFood
                    } else {
                        Toast.makeText(this@ListFoodActivity, "No se encontraron alimentos", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@ListFoodActivity, "No se encontraron datos", Toast.LENGTH_SHORT).show()
                }
                binding.progressBar.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ListFoodActivity, "Error al obtener alimentos: ${error.message}", Toast.LENGTH_LONG).show()
                binding.progressBar.visibility = View.GONE
            }
        })
    }
}

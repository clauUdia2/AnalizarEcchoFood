package com.example.ecchofooddelivery.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecchofooddelivery.Adapter.BestFoodsAdapter
import com.example.ecchofooddelivery.Adapter.CategoryAdapter
import com.example.ecchofooddelivery.Domain.Category
import com.example.ecchofooddelivery.Domain.Foods
import com.example.ecchofooddelivery.Domain.Location
import com.example.ecchofooddelivery.Domain.Price
import com.example.ecchofooddelivery.Domain.Time
import com.example.ecchofooddelivery.LoginActivity
import com.example.ecchofooddelivery.R
import com.example.ecchofooddelivery.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().reference

        initLocation()
        initTime()
        initBestFood()
        initCategory()
        setVariable()
    }

    private fun setVariable() {
        binding.logootBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.searchBtn.setOnClickListener {
            val text = binding.searchEdt.text.toString()
            if (text.isNotEmpty()) {
                val intent = Intent(this, ListFoodActivity::class.java)
                intent.putExtra("text", text)
                intent.putExtra("isSearch", true)
                startActivity(intent)
            }
        }
        binding.cartBtn.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
    }
    //metodo para selecionar la mejor comida que tiene true
    private fun initBestFood() {
        val myRef = database.child("Foods")
        binding.progressBarBestFood.visibility = View.VISIBLE
        val list = ArrayList<Foods>()
        val query: Query = myRef.orderByChild("BestFood").equalTo(true)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        val food = issue.getValue(Foods::class.java)
                        if (food != null) {
                            list.add(food)
                        }
                    }
                    if (list.isNotEmpty()) {
                        binding.bestFoodView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                        val adapter: RecyclerView.Adapter<*> = BestFoodsAdapter(list)
                        binding.bestFoodView.adapter = adapter
                    }
                    binding.progressBarBestFood.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Error al obtener ubicaciones: ${error.message}", Toast.LENGTH_LONG).show()
                binding.progressBarBestFood.visibility = View.GONE
            }
        })
    }

    private fun initCategory() {
        val myRef = database.child("Category")
        binding.progressBarCategory.visibility = View.VISIBLE
        val list = ArrayList<Category>()

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        val category = issue.getValue(Category::class.java)
                        if (category != null) {
                            list.add(category)
                        }
                    }
                    if (list.isNotEmpty()) {
                        binding.categoryView.layoutManager = GridLayoutManager(this@MainActivity, 4)
                        val adapter: RecyclerView.Adapter<*> = CategoryAdapter(list)
                        binding.categoryView.adapter = adapter
                    }
                    binding.progressBarCategory.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Error al obtener categorías: ${error.message}", Toast.LENGTH_LONG).show()
                binding.progressBarCategory.visibility = View.GONE
            }
        })
    }

    private fun initLocation() {
        val myRef = database.child("Location")
        val list = ArrayList<Location>()

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        val location = issue.getValue(Location::class.java)
                        if (location != null) {
                            list.add(location)
                        }
                    }
                    val adapter = ArrayAdapter(this@MainActivity, R.layout.sp_item, list)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


                } else {
                    Toast.makeText(this@MainActivity, "No se encontraron ubicaciones", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Error al obtener ubicaciones: ${error.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun initTime() {
        val myRef = database.child("Time")
        val list = ArrayList<Time>()


    }

    private fun initPrice() {
        val myRef = database.child("Price")
        val list = ArrayList<Price>()

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        val price = issue.getValue(Price::class.java)
                        if (price != null) {
                            list.add(price)
                        }
                    }
                    val adapter = ArrayAdapter(this@MainActivity, R.layout.sp_item, list)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


                } else {
                    Toast.makeText(this@MainActivity, "No se encontraron precios", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Error al obtener precios: ${error.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}

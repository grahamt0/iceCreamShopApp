package com.example.assignment2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_orders.*

class OrdersActivity : AppCompatActivity() {
    var new_order = ArrayList<Order>()
    var order_string = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)

        val intent = intent
        new_order = intent.getSerializableExtra("Order") as ArrayList<Order>
        println(new_order)

        for (order in new_order) {
            order_string.add(order.toString())
        }

        val adapter = ArrayAdapter<String>(this@OrdersActivity, android.R.layout.simple_list_item_1, order_string)
        listView.adapter = adapter
    }
}
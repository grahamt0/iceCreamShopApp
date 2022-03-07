package com.example.assignment2

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.NumberFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class MainActivity : AppCompatActivity() {
    var fmt: NumberFormat? = null
    var orders = ArrayList<Order>()
    var total = 0.00
    //  defaults
    var flavor = "Vanilla"
    var size = "Small"
    var fudge_label = "1 oz"
    var size_cost = 2.99
    var toppings_cost = 0.00
    var fudge_cost = 0.15
    var flavor_spinnerItems = ArrayList<String>()
    var size_spinnerItems = ArrayList<String>()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fmt = NumberFormat.getCurrencyInstance()
        flavor_spinnerItems = arrayListOf("Vanilla", "Chocolate", "Strawberry")
        var flavor_spinnerAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, flavor_spinnerItems)
        flavor_spinner.adapter = flavor_spinnerAdapter

        size_spinnerItems = arrayListOf("Small", "Medium", "Large")
        var size_spinnerAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, size_spinnerItems)
        size_spinner.adapter = size_spinnerAdapter

        flavor_spinner.onItemSelectedListener = object:AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = flavor_spinner.selectedItem
                when (position) {
                    0 -> flavor = "Vanilla"
                    1 -> flavor = "Chocolate"
                    2 -> flavor = "Strawberry"
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        size_spinner.onItemSelectedListener = object:AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = size_spinner.selectedItem
                when (position) {
                    0 -> {
                        size_cost = 2.99
                        size = "Small"
                    }
                    1 -> {
                        size_cost = 3.99
                        size = "Medium"
                    }
                    2 -> {
                        size_cost = 4.99
                        size = "Large"
                    }
                }
                updateUI()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        hotfudge_seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                when (progress) {
                    0 -> fudge_cost = 0.00
                    1 -> fudge_cost = 0.15
                    2 -> fudge_cost = 0.25
                    3 -> fudge_cost = 0.30
                }
                updateUI()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        theworks_button.setOnClickListener {
            size_spinner.setSelection(2)
            hotfudge_seekBar.progress = 3
            fudge_cost = 0.30
            peanuts_checkBox.isChecked = true
            almonds_checkBox.isChecked = true
            strawberries_checkBox.isChecked = true
            gummybears_checkBox.isChecked = true
            mandms_checkBox.isChecked = true
            brownies_checkBox.isChecked = true
            oreos_checkBox.isChecked = true
            marshmallows_checkBox.isChecked = true
            updateUI()
        }

        reset_button.setOnClickListener {
            reset()
        }

        order_button.setOnClickListener {
            Toast.makeText(this@MainActivity, "Order placed!", Toast.LENGTH_SHORT).show()
            val now = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
            val formatted = now.format(formatter)
            updateUI()
            val new_order = Order(formatted, flavor, size, "$" + total.toString())
            orders.add(new_order)
        }

    }

    fun handleCheckBox(view: android.view.View) {
        val checkBoxID = view.id

        when(checkBoxID) {
            R.id.peanuts_checkBox -> {
                updateUI()
            }
            R.id.almonds_checkBox -> {
                updateUI()
            }
            R.id.strawberries_checkBox -> {
                updateUI()
            }
            R.id.gummybears_checkBox -> {
                updateUI()
            }
            R.id.mandms_checkBox -> {
                updateUI()
            }
            R.id.brownies_checkBox -> {
                updateUI()
            }
            R.id.oreos_checkBox -> {
                updateUI()
            }
            R.id.marshmallows_checkBox -> {
                updateUI()
            }
        }
    }

    fun updateUI() {
        var total_dollars = ""
        try {
            when (hotfudge_seekBar.progress) {
                0 -> fudge_label = "0 oz"
                1 -> fudge_label = "1 oz"
                2 -> fudge_label = "2 oz"
                3 -> fudge_label = "3 oz"
            }
            fudge_textView.text = fudge_label
            toppings_cost = 0.00
            if (peanuts_checkBox.isChecked) {
                toppings_cost += 0.15
            }
            if (almonds_checkBox.isChecked) {
                toppings_cost += 0.15
            }
            if (strawberries_checkBox.isChecked) {
                toppings_cost += 0.20
            }
            if (gummybears_checkBox.isChecked) {
                toppings_cost += 0.20
            }
            if (mandms_checkBox.isChecked) {
                toppings_cost += 0.25
            }
            if (brownies_checkBox.isChecked) {
                toppings_cost += 0.20
            }
            if (oreos_checkBox.isChecked) {
                toppings_cost += 0.20
            }
            if (marshmallows_checkBox.isChecked) {
                toppings_cost += 0.15
            }
            total = size_cost + toppings_cost + fudge_cost
            total_dollars = fmt!!.format(total)
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Invalid Action", Toast.LENGTH_SHORT).show();
        } finally {
            price.text = total_dollars
        }
    }

    fun reset() {
        flavor_spinner.setSelection(0)
        size_spinner.setSelection(0)
        hotfudge_seekBar.progress = 1
        peanuts_checkBox.isChecked = false
        almonds_checkBox.isChecked = false
        strawberries_checkBox.isChecked = false
        gummybears_checkBox.isChecked = false
        mandms_checkBox.isChecked = false
        brownies_checkBox.isChecked = false
        oreos_checkBox.isChecked = false
        marshmallows_checkBox.isChecked = false
        updateUI()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.about_id) {
            val intent = Intent(this@MainActivity, AboutActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this@MainActivity, OrdersActivity::class.java)
            intent.putExtra("Order", orders)
            startActivity(intent)
        }
        return true
    }

}
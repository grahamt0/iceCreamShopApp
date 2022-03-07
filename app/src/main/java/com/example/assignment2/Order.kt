package com.example.assignment2

import java.io.Serializable

class Order (var date: String, var flavor: String, var size: String, var cost: String) : Serializable {
    override fun toString(): String {
        return "$date, $flavor, $size, $cost"
    }
}
package com.example.emuapp.components

import com.example.emuapp.data.Item

fun getTotalDiscount(items: ArrayList<Item>): Double {
    var totalDiscount = 0.00
    for (item in items){
        totalDiscount += item.price *(item.discountPercentage / 100)
    }
    return totalDiscount
}

fun getSubTotal(items: ArrayList<Item>, quantity: Int): Double{
    var total = 0.00
    for (item in items ){
        total += item.price* quantity

    }
    return total
}

fun getTotal(items: ArrayList<Item>, quantity: Int): Double{
    var total = 0.00
    for (item in items ){
        total += item.price* quantity

    }
    return total - getTotalDiscount(items)
}

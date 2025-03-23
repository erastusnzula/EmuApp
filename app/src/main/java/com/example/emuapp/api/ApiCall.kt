package com.example.emuapp.api

import com.example.emuapp.data.ApiItems
import com.example.emuapp.data.Dimensions
import com.example.emuapp.data.InitialValues
import com.example.emuapp.data.Item
import com.example.emuapp.data.Meta
import com.example.emuapp.data.Reviews
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun singleItemViewOffline():Item{
    val itemSample = Item(
        id = 1,
        title = "Sampleflgldkldjgdgj",
        description = "Detail-oriented and optimistic customer service representative, with over a \n" +
                "year of professional experience assisting customers in solving complex \n" +
                "issues. Keen to support your organization in becoming a market leader \n" +
                "through proven customer support skills.",
        category = "Samplecjfkdjgdggdg",
        price = 123.4,
        discountPercentage = 5.4,
        rating = 5.4,
        stock = 12,
        tags = listOf("be"),
        brand = "Sample",
        sku = "Sample",
        weight = 34,
        dimensions = Dimensions(23.4, 34.5, 434.4),
        warrantyInformation = "Sample",
        shippingInformation = "Sample",
        availabilityStatus = "Sample",
        reviews = listOf(Reviews(23, "4fdf", "4343", "rer", "ewew")),
        returnPolicy = "kfjd",
        minimumOrderQuantity = 34,
        meta = Meta("rer", "rere", "ere", "re"),
        thumbnail = "R.drawable.ic_launcher_background",
        images = listOf("fdfj","fjdkfjd")
    )
    return itemSample

}

fun dataOffline():ArrayList<Item>{
    val itemSample = Item(
        id = 1,
        title = "Sample",
        description = "Sample",
        category = "laptops",
        price = 123.4,
        discountPercentage = 5.4,
        rating = 5.4,
        stock = 12,
        tags = listOf("be"),
        brand = "Sample",
        sku = "Sample",
        weight = 34,
        dimensions = Dimensions(23.4, 34.5, 434.4),
        warrantyInformation = "Sample",
        shippingInformation = "Sample",
        availabilityStatus = "Sample",
        reviews = listOf(Reviews(23, "4fdf", "4343", "rer", "ewew")),
        returnPolicy = "kfjd",
        minimumOrderQuantity = 34,
        meta = Meta("rer", "rere", "ere", "re"),
        thumbnail = "thumn",
        images = listOf("fdfj")
    )
    val allItems = ArrayList<Item>()
    for (i in 1..100){
        allItems.add(itemSample)
    }
    return allItems
}


fun apiCall() :ArrayList<Item> {
    val allItems = ArrayList<Item>()
    val call = ApiClient.apiService.getData(limit = 200)
    call.enqueue(object : Callback<ApiItems> {
        override fun onResponse(p0: Call<ApiItems>, p1: Response<ApiItems>) {
            if (p1.isSuccessful) {
                InitialValues.error.value = ""
                InitialValues.snackBarMessage.value = "Data successfully fetched."
                allItems.clear()
                p1.body()?.products?.forEach {
                    allItems.add(it)
                }
            }
            else{
                InitialValues.error.value = "failed"
            }
        }

        override fun onFailure(p0: Call<ApiItems>, p1: Throwable) {
            InitialValues.error.value = p1.message.toString()
        }

    })
    return allItems
}

fun apiCallByCategory() :ArrayList<Item> {
    val allItems = ArrayList<Item>()
    val call = ApiClient.apiService.getDataByCategory(limit = 200)
    call.enqueue(object : Callback<ApiItems> {
        override fun onResponse(p0: Call<ApiItems>, p1: Response<ApiItems>) {
            if (p1.isSuccessful) {
//                InitialValues.error.value = ""
                InitialValues.snackBarMessage.value = "Data successfully fetched."
                allItems.clear()
                p1.body()?.products?.forEach {
                    allItems.add(it)
                }
            }
            else{
                InitialValues.error.value = "failed"
            }
        }

        override fun onFailure(p0: Call<ApiItems>, p1: Throwable) {
            InitialValues.error.value = p1.message.toString()
        }

    })
    return allItems
}
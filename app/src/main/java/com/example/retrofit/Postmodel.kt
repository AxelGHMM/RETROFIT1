package com.example.retrofit

import com.google.gson.annotations.SerializedName

data class Postmodel (
    @SerializedName("order_id")
    var order_id: Int,
    @SerializedName("customer_name")
    var customer_name: String,
    @SerializedName("product_name")
    var product_name: String

)
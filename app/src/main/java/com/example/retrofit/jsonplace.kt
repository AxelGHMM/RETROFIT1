package com.example.retrofit

import android.text.Editable

data class jsonplace(
    val fullname: String,
    val nickname: String,
    val age: Int,
    val isActive: Boolean,
    val id: Int = 0
)
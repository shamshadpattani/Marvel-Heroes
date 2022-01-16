package muhammedshamshadp.hope.marvelapp.data.model

import com.google.gson.annotations.SerializedName


data class CategoryBody<T>(
    val c2code: String,
    val fun_name: String,
    val idx: String,
    val res_format: String
)
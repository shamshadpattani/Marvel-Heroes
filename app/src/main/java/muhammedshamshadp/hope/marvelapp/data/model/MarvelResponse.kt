package muhammedshamshadp.hope.marvelworld.data.model

import com.google.gson.annotations.SerializedName


data class MarvelResponse<T>(
    @SerializedName("results") val characters: List<T>,
    val offset: Int,
    val total: Int
)

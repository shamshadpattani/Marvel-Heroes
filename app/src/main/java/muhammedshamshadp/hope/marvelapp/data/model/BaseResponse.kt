package muhammedshamshadp.hope.marvelworld.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BaseResponse<T>(

        @SerializedName("code")
        @Expose
        var code: Int,
        var status: String,
        var data: T?
)
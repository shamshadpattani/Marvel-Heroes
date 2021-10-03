package muhammedshamshadp.hope.marvelapp.remote

import android.app.Application
import muhammedshamshadp.hope.marvelapp.BuildConfig
import muhammedshamshadp.hope.marvelapp.data.remote.APIInterface
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigInteger
import java.security.MessageDigest


object APIClient {
    var timeStamp = System.currentTimeMillis()
    private val httpClient = OkHttpClient.Builder().addInterceptor { chain ->
        val defaultRequest = chain.request()
       val hashSignature = "$timeStamp${BuildConfig.PRIVATE_API_KEY}${BuildConfig.PUBLIC_API_KEY}".md5()
        val defaultHttpUrl = defaultRequest.url
        val httpUrl = defaultHttpUrl.newBuilder()
            .addQueryParameter(TS, timeStamp.toString())
            .addQueryParameter(APIKEY, BuildConfig.PUBLIC_API_KEY)
            .addQueryParameter(HASH, hashSignature)
            .build()

        val requestBuilder = defaultRequest.newBuilder().url(httpUrl)
        chain.proceed(requestBuilder.build())
    }

    private val builder = Retrofit.Builder()
        .baseUrl(APIInterface.BASE_API_URL)
        .addConverterFactory(GsonConverterFactory.create())

    fun getApiService(context: Application): APIInterface {
       var apiService: APIInterface? = null
        val retrofit = builder.client(httpClient.build()).build()
            apiService = retrofit.create(APIInterface::class.java)
        return apiService!!
    }

    fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
    }

    const val HASH = "hash"
    const val APIKEY = "apikey"
    const val TS = "ts"
}
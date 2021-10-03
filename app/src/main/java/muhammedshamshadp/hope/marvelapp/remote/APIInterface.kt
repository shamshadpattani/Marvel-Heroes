package muhammedshamshadp.hope.marvelworld.data.remote

import muhammedshamshadp.hope.marvelworld.data.model.BaseResponse
import muhammedshamshadp.hope.marvelworld.data.model.CharacterResponse
import muhammedshamshadp.hope.marvelworld.data.model.ComicResponse
import muhammedshamshadp.hope.marvelworld.data.model.MarvelResponse
import retrofit2.Response
import retrofit2.http.*

interface APIInterface {

    @GET("/v1/public/characters")
    suspend fun getCharacter(
                       @Query("offset") offset: Int?=0,
                       @Query("nameStartsWith") query: String?): Response<BaseResponse<MarvelResponse<CharacterResponse>>>?


    @GET("/v1/public/comics")
    suspend fun getComics(
        @Query("offset") offset: Int?=0,
        @Query("dateDescriptor") query: String?): Response<BaseResponse<MarvelResponse<ComicResponse>>>?

    companion object {
        const val BASE_API_URL = "https://gateway.marvel.com"
    }
}

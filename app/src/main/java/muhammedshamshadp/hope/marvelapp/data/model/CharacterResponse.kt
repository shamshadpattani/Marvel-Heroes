package muhammedshamshadp.hope.marvelworld.data.model

data class CharacterResponse (
val id: Int,
val name: String,
val description: String,
val thumbnail: ThumbnailResponse,
val comics: ComicsResponse,
val urls: List<UrlResponse>
){

    data class ComicsResponse(
        val available: Int
    )
    data class UrlResponse(
        val type: String,
        val url: String
    )

}

package muhammedshamshadp.hope.marvelworld.data.model



data class ComicResponse(

    val id: Int,
    val images: List<Image>,
    val thumbnail: ThumbnailResponse,
    val title: String,

){
    data class Image(
        val extension: String,
        val path: String
    )
}




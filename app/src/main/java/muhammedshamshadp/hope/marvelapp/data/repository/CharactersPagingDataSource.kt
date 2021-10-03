package muhammedshamshadp.hope.marvelworld.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import muhammedshamshadp.hope.marvelworld.data.model.BaseResponse
import muhammedshamshadp.hope.marvelworld.data.model.CharacterResponse
import muhammedshamshadp.hope.marvelworld.data.model.MarvelResponse
import muhammedshamshadp.hope.marvelapp.data.remote.APIInterface
import muhammedshamshadp.hope.marvelworld.data.repository.MarvelRepository.Companion.DEFAULT_PAGE_INDEX
import muhammedshamshadp.hope.marvelworld.data.repository.MarvelRepository.Companion.DEFAULT_PAGE_SIZE
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


class CharactersPagingDataSource(private val service: APIInterface, private val search:String?) :PagingSource<Int, CharacterResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterResponse> {

        return try {
            var query=search ?: null
            val page = params.key ?: DEFAULT_PAGE_INDEX
          var  response: Response<BaseResponse<MarvelResponse<CharacterResponse>>>? = null
            if(!query.isNullOrEmpty()){
                response = service.getCharacter(offset = page*params.loadSize,query)
            }else{
                response = service.getCharacter(offset = page*params.loadSize,null)
            }

        //    LoadResult.Page()
          val repos = response?.body()?.data?.characters?: mutableListOf()

            LoadResult.Page(
                data =  repos,
                prevKey = null,
                nextKey = if (repos.isEmpty()) null else   page + (params.loadSize / DEFAULT_PAGE_SIZE )
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }

    }

    // The refresh key is used for subsequent refresh calls to PagingSource.load after the initial load
    override fun getRefreshKey(state: PagingState<Int, CharacterResponse>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }




}
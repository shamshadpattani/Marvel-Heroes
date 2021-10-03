package muhammedshamshadp.hope.marvelworld.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import muhammedshamshadp.hope.marvelworld.data.model.CharacterResponse
import muhammedshamshadp.hope.marvelapp.data.remote.APIInterface


class MarvelRepository (private val service: APIInterface) {

    companion object {
        const val DEFAULT_PAGE_INDEX = 0
        const val DEFAULT_PAGE_SIZE = 20

    }
  fun getCharacters( search:String?): Flow<PagingData<CharacterResponse>> {
      return Pager(
          config =PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = false),
          pagingSourceFactory = { CharactersPagingDataSource(service,search = search) }
      ).flow
  }



}
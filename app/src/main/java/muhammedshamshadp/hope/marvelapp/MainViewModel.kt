package muhammedshamshadp.hope.marvelapp

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import muhammedshamshadp.hope.marvelapp.remote.APIClient
import muhammedshamshadp.hope.marvelworld.data.model.CharacterResponse
import muhammedshamshadp.hope.marvelworld.data.model.ComicResponse
import muhammedshamshadp.hope.marvelapp.data.repository.MarvelRepository


class MainViewModel(application: Application, private val savedStateHandle: SavedStateHandle) : AndroidViewModel(application) {

    private val service = APIClient.getApiService(application)
    private val charRepo: MarvelRepository = MarvelRepository(service)


    val searchQuery = savedStateHandle.getLiveData("searchQuery", "")
    val filter = savedStateHandle.getLiveData("filter", "")

    val charactersLive = Transformations.switchMap(searchQuery) {
        getCharactersList()
    }
    val comicsLiveData = Transformations.switchMap(filter) {
        getComics()
    }



    private fun getCharactersList(): LiveData<PagingData<CharacterResponse>> {
        return charRepo.getCharacters(searchQuery.value)
            .cachedIn(viewModelScope).asLiveData()
    }

    private fun getComics(): LiveData<PagingData<ComicResponse>> {
        return charRepo.getComics(filter.value)
            .cachedIn(viewModelScope).asLiveData()
    }
}






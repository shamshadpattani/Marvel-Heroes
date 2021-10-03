package muhammedshamshadp.hope.marvelapp

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.*
import muhammedshamshadp.hope.marvelapp.remote.APIClient
import muhammedshamshadp.hope.marvelworld.data.model.CharacterResponse
import muhammedshamshadp.hope.marvelworld.data.repository.MarvelRepository


class MainViewModel(application: Application, private val savedStateHandle: SavedStateHandle) : AndroidViewModel(application) {

    private val service = APIClient.getApiService(application)
    private val charRepo: MarvelRepository = MarvelRepository(service)


    val searchQuery = savedStateHandle.getLiveData("searchQuery", "")

    val charactersLive = Transformations.switchMap(searchQuery) {
        getCharactersList(it)
    }


    private fun getCharactersList(query:String): LiveData<PagingData<CharacterResponse>> {
        return charRepo.getCharacters(searchQuery.value)
            .cachedIn(viewModelScope).asLiveData()
    }
}






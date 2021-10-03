package muhammedshamshadp.hope.marvelworld.data.repository.utils

import muhammedshamshadp.hope.marvelworld.data.model.CharacterResponse

sealed class RepoResult {
    data class Success(val data: List<CharacterResponse>) : RepoResult()
    data class Error(val error: Exception) : RepoResult()
}
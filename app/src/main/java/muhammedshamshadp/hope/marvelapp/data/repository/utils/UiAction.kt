package muhammedshamshadp.hope.marvelworld.data.repository.utils


sealed class UiAction {
    data class Search(val query: String) : UiAction()
    data class Scroll(val currentQuery: String) : UiAction()
}

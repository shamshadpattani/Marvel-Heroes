package muhammedshamshadp.hope.marvelapp.ui.charcter

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.launch
import muhammedshamshadp.hope.marvelapp.MainActivity
import muhammedshamshadp.hope.marvelapp.MainViewModel
import muhammedshamshadp.hope.marvelapp.R
import muhammedshamshadp.hope.marvelapp.databinding.FragmentCharctersBinding
import muhammedshamshadp.hope.marvelapp.utils.LoaderStateAdapter
import muhammedshamshadp.hope.marvelworld.utils.MySuggestionProvider



class CharacterListFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    lateinit var binding: FragmentCharctersBinding
    lateinit var loaderStateAdapter: LoaderStateAdapter
    lateinit var adapter: CharacterMarvelAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        binding = FragmentCharctersBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        observe()
        setUpViews(view)
    }


    private fun setUpViews(view: View) {
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerview.adapter = adapter.withLoadStateFooter(loaderStateAdapter)
    }

    private fun observe() {
        viewModel.charactersLive.observe(viewLifecycleOwner,{
            lifecycleScope.launch {
                adapter.submitData(it)
            }
        })

    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu, menu)
        val item = menu.findItem(R.id.action_search)
        val searchView = SearchView(
            (context as MainActivity).supportActionBar!!.themedContext
        )
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
        item.actionView = searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchQuery.value = query
                observe()
                val suggestions = SearchRecentSuggestions(
                    activity,
                    MySuggestionProvider.AUTHORITY,
                    MySuggestionProvider.MODE
                )
                suggestions.saveRecentQuery(query, null)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.searchQuery.value = newText
                observe()
                return true
            }
        })
        searchView.setOnClickListener {

        }
        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.apply {
            // Assumes current activity is the searchable activity
            setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            setIconifiedByDefault(false) // Do not iconify the widget; expand it by default
        }
    }



    private fun initAdapter() {
        adapter = CharacterMarvelAdapter()
        adapter.addLoadStateListener { loadState ->

            if (loadState.refresh is LoadState.Loading) {
                binding.pBar.visibility = View.VISIBLE
            } else {
                binding.pBar.visibility = View.GONE

              /*  // getting the error
                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                error?.let {
                    Toast.makeText(requireContext(), it.error.message, Toast.LENGTH_LONG).show()
                }*/
            }
            loaderStateAdapter = LoaderStateAdapter { adapter.retry() }


        }
    }
}
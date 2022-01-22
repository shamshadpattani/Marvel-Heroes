package muhammedshamshadp.hope.marvelapp.ui.charcter

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.launch
import muhammedshamshadp.hope.marvelapp.MainActivity
import muhammedshamshadp.hope.marvelapp.MainViewModel
import muhammedshamshadp.hope.marvelapp.R
import muhammedshamshadp.hope.marvelapp.databinding.FragmentCharctersBinding
import muhammedshamshadp.hope.marvelapp.utils.LoaderStateAdapter
import muhammedshamshadp.hope.marvelapp.utils.MySuggestionProvider
import androidx.appcompat.app.AppCompatActivity






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
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        observe()
        setUpViews()
    }


    private fun setUpViews() {
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
   /* override fun onMenuItemClick(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.action_search ->  {

                val searchView = SearchView(requireContext())
//                item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
//                item.actionView = searchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        viewModel.searchQuery.value = query
                        val suggestions = SearchRecentSuggestions(
                            requireContext(),
                            MySuggestionProvider.AUTHORITY,
                            MySuggestionProvider.MODE
                        )
                        suggestions.saveRecentQuery(query, null)
                        return true
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        viewModel.searchQuery.value = newText
                        return true
                    }
                })
                searchView.setOnClickListener {

                }
                val searchManager = requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager
                searchView.apply {
                    // Assumes current activity is the searchable activity
                    setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
                    setIconifiedByDefault(false) // Do not iconify the widget; expand it by default
                }
            }
        else ->{

        }

        }


    }*/
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
                val suggestions = SearchRecentSuggestions(
                    requireContext(),
                    MySuggestionProvider.AUTHORITY,
                    MySuggestionProvider.MODE
                )
                suggestions.saveRecentQuery(query, null)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.searchQuery.value = newText
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
        adapter = CharacterMarvelAdapter{ id ->
            Navigation.findNavController(requireView()).navigate(R.id.action_characterListFragment_to_characterDetailsFragment);

        }
        adapter.addLoadStateListener { loadState ->

            if (loadState.source.refresh is LoadState.NotLoading &&
                loadState.append.endOfPaginationReached &&
                adapter.itemCount < 1
            ) {
                binding.recyclerview.isVisible = false
                binding.emptyView.isVisible = true
            } else {
                binding.emptyView.isVisible = false
                binding.recyclerview.isVisible = true
            }

            if (loadState.refresh is LoadState.Loading) {
                binding.pBar.visibility = View.VISIBLE
            } else {
                binding.pBar.visibility = View.GONE

                // getting the error
                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                error?.let {
                    Toast.makeText(requireContext(), resources.getString(R.string.label_error), Toast.LENGTH_LONG).show()
                }
            }
            loaderStateAdapter = LoaderStateAdapter { adapter.retry() }
        }

    }


}
package muhammedshamshadp.hope.marvelworld.character

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import muhammedshamshadp.hope.marvelapp.MainViewModel
import muhammedshamshadp.hope.marvelapp.databinding.FragmentCharctersBinding


class CharacterListFragment : Fragment() {

     private val viewModel: MainViewModel by viewModels()
     lateinit var binding: FragmentCharctersBinding


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

    }



}
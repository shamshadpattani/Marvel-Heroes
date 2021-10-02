package muhammedshamshadp.hope.marvelworld.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import muhammedshamshadp.hope.marvelapp.MainViewModel
import muhammedshamshadp.hope.marvelapp.databinding.FragmentComicsBinding


class ComicListFragment : Fragment() {

    lateinit var viewModel: MainViewModel
    lateinit var binding: FragmentComicsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding = FragmentComicsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }



}
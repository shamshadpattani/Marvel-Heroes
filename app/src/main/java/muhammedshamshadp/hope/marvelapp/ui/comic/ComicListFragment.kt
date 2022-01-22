package muhammedshamshadp.hope.marvelapp.ui.comic

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch
import muhammedshamshadp.hope.marvelapp.MainViewModel
import muhammedshamshadp.hope.marvelapp.R
import muhammedshamshadp.hope.marvelapp.databinding.FragmentComicsBinding
import muhammedshamshadp.hope.marvelapp.utils.LoaderStateAdapter
import java.util.Locale.filter


class ComicListFragment : Fragment() {

        lateinit var viewModel: MainViewModel
        lateinit var binding: FragmentComicsBinding

        lateinit var adapter: ComicMarvelAdapter
    lateinit var loaderStateAdapter: LoaderStateAdapter

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            // Inflate the layout for this fragment
            setHasOptionsMenu(true)
            viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
            binding = FragmentComicsBinding.inflate(inflater, container, false)

            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            initAdapter()
            observe()
            setUpViews()
            (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        }


        private fun setUpViews() {
            binding.recyclerview.adapter = adapter
            binding.recyclerview.layoutManager = GridLayoutManager(context, 2)
            binding.recyclerview.adapter = adapter.withLoadStateFooter(loaderStateAdapter)

            binding.chipGroupChoice.setOnCheckedChangeListener { group, checkedId ->
                val chip: Chip? = group.findViewById(checkedId)
//            if(checkedId==-1) {
//               binding.chipGroupChoice.clearCheck()
//            }
                chip?.let {
                    //Toast.makeText(context, it.id.toString(), Toast.LENGTH_SHORT).show()
                    when (it.text) {
                        resources.getString(R.string.this_month_chip) -> {
                            viewModel.filter.value=FILTER_THIS_MONTH
                        }
                        resources.getString(R.string.this_week_chip) -> {
                            viewModel.filter.value= FILTER_THIS_WEEK
                        }
                        resources.getString(R.string.last_week_chip) -> {
                            viewModel.filter.value= FILTER_LAST_WEEK
                        }
                        resources.getString(R.string.next_week_chip) -> {
                            viewModel.filter.value= FILTER_NEXT_WEEK
                        }else ->{
                        viewModel.filter.value= null
                    }
                    }

                } ?: kotlin.run {
                    viewModel.filter.value= null
                }
            }
        }

        private fun observe() {
            viewModel.comicsLiveData.observe(viewLifecycleOwner,{
                lifecycleScope.launch {
                    adapter.submitData(it)
                }

            })
        }

        private fun initAdapter() {
            adapter = ComicMarvelAdapter()
            adapter.addLoadStateListener { loadState ->

                if (loadState.refresh is LoadState.Loading) {
                    binding.progress.visibility = View.VISIBLE
                } else {
                    binding.progress.visibility = View.GONE

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
        override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
            super.onCreateOptionsMenu(menu, inflater)
            menu.clear()
            inflater.inflate(R.menu.filter, menu)

        }
        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.action_filter -> {
                    if (binding.chipView.visibility == View.VISIBLE) {
                        binding.chipView.visibility = View.GONE
                        binding.chipGroupChoice.clearCheck()
                    }else{
                        binding.chipView.visibility = View.VISIBLE
                    }
                    true
                }

                else -> super.onOptionsItemSelected(item)
            }
        }

        companion object {
            const val FILTER_THIS_WEEK = "thisWeek"
            const val FILTER_LAST_WEEK = "lastWeek"
            const val FILTER_NEXT_WEEK = "nextWeek"
            const val FILTER_THIS_MONTH = "thisMonth"
        }
    }


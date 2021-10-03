package muhammedshamshadp.hope.marvelapp.ui.comic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import muhammedshamshadp.hope.marvelapp.R
import muhammedshamshadp.hope.marvelapp.databinding.CharacterListItemBinding
import muhammedshamshadp.hope.marvelworld.data.model.ComicResponse

class ComicMarvelAdapter() :PagingDataAdapter<ComicResponse, ComicMarvelAdapter.ViewHolder>(REPO_COMPARATOR) {

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<ComicResponse>() {
            override fun areItemsTheSame(oldItem: ComicResponse, newItem: ComicResponse): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ComicResponse, newItem: ComicResponse): Boolean =
                oldItem.id == newItem.id
        }
    }

    class ViewHolder(var binding: CharacterListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.itemName.text=item?.title.toString()
        val img = "${item?.thumbnail?.path}.${item?.thumbnail?.extension}"
            holder.binding.imageUser.load( img) {
                placeholder(R.drawable.ic_book)
                error(R.drawable.ic_book)
            }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CharacterListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }






}
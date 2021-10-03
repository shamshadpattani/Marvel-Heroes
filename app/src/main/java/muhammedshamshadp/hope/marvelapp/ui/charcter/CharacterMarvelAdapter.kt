package muhammedshamshadp.hope.marvelapp.ui.charcter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import muhammedshamshadp.hope.marvelapp.R
import muhammedshamshadp.hope.marvelapp.databinding.CharacterListItemBinding
import muhammedshamshadp.hope.marvelworld.data.model.CharacterResponse


class CharacterMarvelAdapter() :PagingDataAdapter<CharacterResponse, CharacterMarvelAdapter.ViewHolder>(
    REPO_COMPARATOR
) {

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<CharacterResponse>() {
            override fun areItemsTheSame(oldItem: CharacterResponse, newItem: CharacterResponse): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: CharacterResponse, newItem: CharacterResponse): Boolean =
                oldItem.id == newItem.id
        }
    }

    class ViewHolder(var binding: CharacterListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.itemName.text=item?.name.toString()
        val img = "${item?.thumbnail?.path}.${item?.thumbnail?.extension}"
            holder.binding.imageUser.load( img) {
                placeholder(R.drawable.ic_superhero)
                error(R.drawable.ic_superhero)
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
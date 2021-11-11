package es.borjavg.thingiverse.features.popular.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.borjavg.thingiverse.databinding.ItemThingBinding
import es.borjavg.thingiverse.features.main.ui.models.ThingModel
import es.borjavg.thingiverse.ui.common.DefaultItemCallback
import es.borjavg.thingiverse.ui.common.ImageLoader

class ThingsAdapter(private val imageLoader: ImageLoader) :
    ListAdapter<ThingModel, MovieViewHolder>(DefaultItemCallback<ThingModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieViewHolder(
        ItemThingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)

        with(holder.binding) {
            title.text = item.name
            likeCount.text = item.likeCount
            imageLoader.load(url = item.thumbUrl, into = imageView)
        }
    }
}

class MovieViewHolder(val binding: ItemThingBinding) : RecyclerView.ViewHolder(binding.root)

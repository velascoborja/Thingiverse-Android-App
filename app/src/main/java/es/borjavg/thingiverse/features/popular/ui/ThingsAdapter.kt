package es.borjavg.thingiverse.features.popular.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.borjavg.thingiverse.databinding.ItemThingBinding
import es.borjavg.thingiverse.features.main.ui.models.ThingModel
import es.borjavg.thingiverse.ui.common.DefaultItemCallback
import es.borjavg.thingiverse.ui.common.EmptyChangedPayload
import es.borjavg.thingiverse.ui.common.ImageLoader

class ThingsAdapter(
    private val imageLoader: ImageLoader,
    private val onItemClickListener: (ThingModel) -> Unit,
    private val onItemLikeChanged: (ThingModel) -> Unit
) : ListAdapter<ThingModel, ThingsAdapter.ThingsViewHolder>(
    DefaultItemCallback<ThingModel>(
        idSelector = ThingModel::id,
        changePayload = EmptyChangedPayload()
    )
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ThingsViewHolder(
        ItemThingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ThingsViewHolder, position: Int) {
        val item = getItem(position)

        with(holder.binding) {
            title.text = item.name
            commentCount.text = item.commentCount
            imageLoader.load(url = item.thumbUrl, into = imageView)
            checkboxLike.isChecked = item.liked
        }
    }

    inner class ThingsViewHolder(val binding: ItemThingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onItemClickListener(getItem(adapterPosition))
            }

            binding.checkboxLike.setOnCheckedChangeListener { _, checked ->
                onItemLikeChanged(getItem(adapterPosition).copy(liked = checked))
            }
        }
    }
}

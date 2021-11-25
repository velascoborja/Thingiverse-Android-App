package es.borjavg.thingiverse.features.popular

import android.content.Intent
import es.borjavg.thingiverse.R
import es.borjavg.thingiverse.util.BaseTestRobot

class PopularRobot : BaseTestRobot() {
    fun selectPopularTab() = clickButton(R.id.menu_item_popular)
    fun clickFirstThing() = clickListItem(R.id.recyclerView, 0)
    fun likeFirstThing() = clickListItemChildView(R.id.recyclerView, R.id.checkboxLike, 0)
    fun matchThingDetailOpens() = matchIntent(Intent.ACTION_VIEW)
    fun matchErrorVisible() = matchVisible(R.id.error_animation_view)
    fun matchLoadingVisible() = matchVisible(R.id.loading_animation_view)
}
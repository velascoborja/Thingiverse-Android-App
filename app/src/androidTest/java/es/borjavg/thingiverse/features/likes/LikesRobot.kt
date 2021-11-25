package es.borjavg.thingiverse.features.likes

import es.borjavg.thingiverse.R
import es.borjavg.thingiverse.util.BaseTestRobot

class LikesRobot : BaseTestRobot() {
    fun matchEmptyView() = matchVisible(R.id.emptyView)
    fun clickLikesMenu() = clickButton(R.id.menu_item_likes)
    fun matchItemLiked() = matchRecyclerViewSize(R.id.recyclerView)
}
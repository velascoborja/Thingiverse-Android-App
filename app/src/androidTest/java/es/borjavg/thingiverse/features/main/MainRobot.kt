package es.borjavg.thingiverse.features.main

import es.borjavg.thingiverse.R
import es.borjavg.thingiverse.util.BaseTestRobot

class MainRobot : BaseTestRobot() {
    fun selectPopularTab() = clickButton(R.id.menu_item_popular)
    fun selectLikesTab() = clickButton(R.id.menu_item_likes)
    fun matchToolbarTitle(title: String) = matchToolbarTitle(R.id.toolbar, title)
}
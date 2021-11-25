package es.borjavg.thingiverse.features.popular

import android.content.Intent
import es.borjavg.thingiverse.R
import es.borjavg.thingiverse.util.BaseTestRobot

class PopularRobot : BaseTestRobot() {
    fun clickThing(itemIndex: Int) = clickListItem(R.id.recyclerView, itemIndex)
    fun checkThingDetailOpens() = matchIntent(Intent.ACTION_VIEW)
}
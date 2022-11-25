package es.borjavg.thingiverse.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import coil.compose.AsyncImage
import es.borjavg.thingiverse.features.main.ui.models.ThingModel
import es.borjavg.thingiverse.ui.theme.Dimens

@Composable
fun ThingItem(modifier: Modifier = Modifier, thing: ThingModel) {
    Row(
        modifier = modifier.background(shape = RoundedCornerShape(Dimens.smallSpacing), color = Color.White),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        AsyncImage(model = thing.thumbUrl, contentDescription = "Thing preview")
    }
}
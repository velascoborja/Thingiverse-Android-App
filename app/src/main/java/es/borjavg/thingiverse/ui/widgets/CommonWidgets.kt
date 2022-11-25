package es.borjavg.thingiverse.ui.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import es.borjavg.thingiverse.R
import es.borjavg.thingiverse.features.main.ui.models.ThingModel
import es.borjavg.thingiverse.ui.theme.AppTheme
import es.borjavg.thingiverse.ui.theme.Dimens

@Composable
fun ThingItem(modifier: Modifier = Modifier, thing: ThingModel) {
    Card(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = Dimens.standardSpacing),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(60.dp)
                        .padding(end = Dimens.smallSpacing),
                    model = thing.thumbUrl,
                    contentDescription = "Thing preview"
                )

                Column {
                    Text(text = thing.name, maxLines = 2, overflow = TextOverflow.Ellipsis)

                    Spacer(modifier = Modifier.size(size = Dimens.smallSpacing))

                    Icon(
                        painter = painterResource(id = R.drawable.ic_popular),
                        contentDescription = "Comments count icon"
                    )
                }
            }

            IconButton(onClick = { /*TODO*/ }) {
                Icon(painter = painterResource(id = R.drawable.ic_likes), contentDescription = "Like button")
            }
        }
    }
}

@Preview
@Composable
fun PreviewThingItem() {
    AppTheme {
        ThingItem(
            modifier = Modifier.width(300.dp),
            thing = ThingModel(
                id = "ID",
                thumbUrl = "https://picsum.photos/200/300",
                name = "very very very long namesdsdsdsdsdsdsddsdsdsds",
                commentCount = "30",
                clickable = true,
                detailUrl = "https://www.thingiverse.com/thing:5590308",
                liked = true
            )
        )
    }
}
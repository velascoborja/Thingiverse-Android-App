package es.borjavg.thingiverse.ui.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
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
fun ThingItem(
    modifier: Modifier = Modifier,
    thing: ThingModel,
    onThingClick: (ThingModel) -> Unit = {},
    onLikeClick: (ThingModel) -> Unit = {}
) {
    Card(modifier = modifier.clickable(enabled = thing.clickable, onClick = { onThingClick(thing) })) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        start = Dimens.standardSpacing,
                        top = Dimens.standardSpacing,
                        bottom = Dimens.standardSpacing
                    ),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(shape = RoundedCornerShape(size = Dimens.smallSpacing)),
                    model = thing.thumbUrl,
                    contentDescription = "Thing preview",
                    contentScale = ContentScale.Crop,
                )

                Spacer(modifier = Modifier.size(size = Dimens.standardSpacing))

                Column {
                    Text(text = thing.name, maxLines = 2, overflow = TextOverflow.Ellipsis)

                    Spacer(modifier = Modifier.size(size = Dimens.smallSpacing))

                    Text(text = thing.commentCount, style = MaterialTheme.typography.labelSmall)
                }
            }

            IconButton(onClick = { onLikeClick(thing) }) {
                Icon(
                    painter = painterResource(id = if (thing.liked) R.drawable.ic_likes else R.drawable.ic_likes_unchecked),
                    contentDescription = "Like button",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ThingList(
    items: List<ThingModel>,
    onThingClick: (ThingModel) -> Unit = {},
    onThingLikeClick: (ThingModel) -> Unit = {},
    isLoading: Boolean
) {
    AnimatedVisibility(visible = isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(modifier = Modifier.size(50.dp))
        }
    }

    AnimatedVisibility(visible = !isLoading) {
        LazyColumn(
            contentPadding = PaddingValues(
                vertical = Dimens.standardSpacing,
                horizontal = Dimens.standardSpacing
            )
        ) {
            items(items) { thingModel ->
                ThingItem(
                    modifier = Modifier.animateItemPlacement(),
                    thing = thingModel,
                    onThingClick = onThingClick,
                    onLikeClick = onThingLikeClick
                )
                Spacer(modifier = Modifier.size(Dimens.standardSpacing))
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

@Preview
@Composable
fun ThingList() {
    AppTheme {
        ThingList(items = (0..10).map { index ->
            ThingModel(
                id = "ID$index",
                thumbUrl = "https://picsum.photos/200/300",
                name = "$index thing name",
                commentCount = "$index",
                clickable = true,
                detailUrl = "https://www.thingiverse.com/thing:5590308",
                liked = true
            )
        }, isLoading = false)
    }
}
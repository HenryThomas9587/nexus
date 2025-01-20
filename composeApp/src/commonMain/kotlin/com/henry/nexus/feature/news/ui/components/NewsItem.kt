import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.henry.nexus.feature.news.domain.model.NewsModel
import com.henry.nexus.feature.news.ui.components.NewsActionBar

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsItem(
    modifier: Modifier = Modifier,
    newsModel: NewsModel,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .width(290.dp)
            .height(316.dp),
        elevation = 2.dp,
        shape = MaterialTheme.shapes.medium,
        backgroundColor = MaterialTheme.colors.surface,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            NewsItemHeader(
                imageUrl = newsModel.imageUrl ?: "",
                category = newsModel.category,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            )
            NewsItemContent(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp), newsModel
            )

            AuthorInfo(
                modifier = Modifier.padding(horizontal = 16.dp),
                author = newsModel.author,
                timeAgo = newsModel.timeAgo
            )
            NewsActionBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                views = newsModel.views,
                comments = newsModel.comments
            )
        }
    }
}

@Composable
private fun NewsItemHeader(
    imageUrl: String,
    category: String,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 4.dp,
                        topEnd = 4.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                ),
            contentScale = ContentScale.Crop,
        )
        CategoryTag(
            category = category,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart),
        )
    }
}

@Composable
private fun NewsItemContent(
    modifier: Modifier = Modifier,
    newsModel: NewsModel,
) {
    Column(modifier = modifier) {
        Text(
            text = newsModel.title,
            style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = newsModel.description,
            style = MaterialTheme.typography.body2,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun AuthorInfo(modifier: Modifier = Modifier, author: String, timeAgo: String) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = author,
            style = MaterialTheme.typography.caption,
        )
        Text(
            text = "• $timeAgo",
            style = MaterialTheme.typography.caption,
        )
    }
}


@Composable
private fun CategoryTag(category: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(4.dp),
        color = MaterialTheme.colors.primary,
    ) {
        Text(
            text = category,
            style = MaterialTheme.typography.caption.copy(fontSize = 10.sp),
            color = Color.White,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

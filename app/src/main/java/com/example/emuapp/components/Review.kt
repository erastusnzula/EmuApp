package com.example.emuapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.size.Size
import com.example.emuapp.R
import com.example.emuapp.api.singleItemViewOffline
import com.example.emuapp.data.Reviews
import com.example.emuapp.data.Sizes
import com.example.emuapp.ui.theme.EmuAppTheme
import okhttp3.internal.format

@Composable
fun Review (reviews: List<Reviews>){
    val width = LocalConfiguration.current.screenWidthDp.dp
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
        modifier=Modifier
            .fillMaxWidth()

    ) {
        Column(
            modifier=Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(colorResource(R.color.white))
        ) {
            Text(
                text="Reviews",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(bottom = 5.dp)

            )
            reviews.forEach{
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth().clip(RoundedCornerShape(topStart =10.dp, topEnd = 10.dp))
                        .background(colorResource(R.color.white))
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = null,
                            tint = colorResource(R.color.primary),
                            modifier = Modifier
                                .size(50.dp)
                                .padding(end = 5.dp)

                        )
                        Column {
                            Text(
                                text = it.reviewerName,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = it.reviewerEmail,
                                color = colorResource(R.color.primary),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .width(width/2-20.dp)
                                    .horizontalScroll(rememberScrollState())
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .padding(start = 10.dp, end=10.dp)
                    ) {
                        Text(
                            text = it.date,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .horizontalScroll(rememberScrollState())
                        )
                        Text(
                            text = "Rating: ${it.rating}"
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colorResource(R.color.white))
                ){
                    Text(
                        textAlign = TextAlign.Justify,
                        text = it.comment,
                        modifier = Modifier
                            .fillMaxWidth().clip(RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp))
                            .padding(bottom = 10.dp, start = 50.dp, end = 10.dp)
                    )
                }
                Spacer(Modifier.height(Sizes.spacer))
        }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun ReviewPreview(){
    EmuAppTheme {
        Review(reviews = singleItemViewOffline().reviews)
    }
}
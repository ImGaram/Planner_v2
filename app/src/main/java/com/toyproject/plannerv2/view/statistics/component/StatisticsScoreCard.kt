package com.toyproject.plannerv2.view.statistics.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun StatisticsScoreCard(
    modifier: Modifier = Modifier,
    title: String,
    body: Int?,
    cardColor: CardColors = CardDefaults.cardColors(),
    cardShape: Shape = CardDefaults.shape,
    titleTextStyle: TextStyle = TextStyle(),
    bodyTextStyle: TextStyle = TextStyle()
) {
    Card(
        modifier = modifier,
        colors = cardColor,
        shape = cardShape
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(all = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = body.toString(),
                style = bodyTextStyle
            )
            Text(
                text = title,
                style = titleTextStyle
            )
        }
    }
}
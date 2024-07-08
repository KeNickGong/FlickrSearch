package com.example.flickrsearch.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.flickrsearch.data.model.FlickrImage

@Composable
fun ImageDetailScreen(image: FlickrImage) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val painter = rememberImagePainter(data = image.media.m)
        Image(
            painter = painter,
            contentDescription = image.title,
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = image.title, style = MaterialTheme.typography.headlineMedium)
        Text(buildAnnotatedString {
            appendBoldText("Author: ")
            append(image.author)
        }, style = MaterialTheme.typography.bodySmall)
        Text(buildAnnotatedString {
            appendBoldText("Published: ")
            append(image.published)
        }, style = MaterialTheme.typography.bodyMedium)
        Text(buildAnnotatedString {
            appendBoldText("Tags: ")
            append(image.tags)
        }, style = MaterialTheme.typography.bodyMedium)
        Text(buildAnnotatedString {
            appendBoldText("Descriptions: ")
            append(image.description)
        }, style = MaterialTheme.typography.bodyMedium)
    }
}

fun AnnotatedString.Builder.appendBoldText(text: String) {
    pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
    append(text)
    pop()
}
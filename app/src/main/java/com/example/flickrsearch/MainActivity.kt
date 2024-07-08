package com.example.flickrsearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.flickrsearch.data.FlickrApiService
import com.example.flickrsearch.data.FlickrRepository
import com.example.flickrsearch.data.model.FlickrImage
import com.example.flickrsearch.ui.ImageDetailScreen
import com.example.flickrsearch.ui.theme.FlickrSearchTheme
import com.example.flickrsearch.ui.viewmodel.FlickrViewModel
import com.example.flickrsearch.ui.viewmodel.FlickrViewModelFactory

class MainActivity : ComponentActivity() {
    private val viewModel: FlickrViewModel by viewModels {
        FlickrViewModelFactory(FlickrRepository(FlickrApiService.create()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlickrSearchTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "home") {
                    composable("home") {
                        FlickrSearchApp(viewModel, navController)
                    }
                    composable("detail/{imageIndex}") { backStackEntry ->
                        val imageIndex = backStackEntry.arguments?.getString("imageIndex")?.toIntOrNull()
                        val images by viewModel.images.collectAsState()
                        val image = imageIndex?.let { images.getOrNull(it) }
                        if (image != null) {
                            ImageDetailScreen(image)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FlickrSearchApp(viewModel: FlickrViewModel, navController: NavHostController) {
    val images by viewModel.images.collectAsState()
    val loading by viewModel.loading.collectAsState()
    var query by remember { mutableStateOf(TextFieldValue("")) }

    Column {
        SearchBar(query) { newQuery ->
            query = newQuery
            viewModel.searchImages(newQuery.text)
        }
        if (loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            ImageGrid(images, navController)
        }
    }
}

@Composable
fun SearchBar(query: TextFieldValue, onQueryChange: (TextFieldValue) -> Unit) {
    BasicTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.Gray.copy(alpha = 0.2f)),
        textStyle = LocalTextStyle.current.copy(color = Color.Black)
    )
}

@Composable
fun ImageGrid(images: List<FlickrImage>, navController: NavHostController) {
    LazyColumn {
        items(images.size) { index ->
            val image = images[index]
            ImageItem(image, navController, index)
        }
    }
}

@Composable
fun ImageItem(image: FlickrImage, navController: NavHostController, index: Int) {
    val painter = rememberImagePainter(data = image.media.m)
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                navController.navigate("detail/$index")
            }
    ) {
        Image(
            painter = painter,
            contentDescription = image.title,
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Text(text = image.title, style = MaterialTheme.typography.headlineLarge)
    }
}
package com.movie.movieapplication.screens.searchscreen

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.movie.movieapplication.components.BasicScreen
import com.movie.movieapplication.screens.viewmodels.SearchMovieInfoViewModel
import com.movie.movieapplication.ui.theme.DeepMainColor
import com.movie.movieapplication.ui.theme.MainColor
import com.movie.movieapplication.widgets.MovieAppBar
import com.movie.movieapplication.widgets.MovieImageCard
import com.movie.movieapplication.widgets.SearchBar
import com.movie.movieapplication.widgets.SearchItems
import org.json.JSONArray

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SearchScreen(navController: NavController) {
    BasicScreen {
        SearchScreenScaffold(navController = navController)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SearchScreenScaffold(navController: NavController) {
    Scaffold(modifier = Modifier,
        backgroundColor = MainColor,
        topBar = {
            MovieAppBar(navController = navController, title = "Search")
        }) {
        SearchScreenContent(navController = navController)
    }
}

@Composable
fun SearchScreenContent(navController: NavController, searchViewModel: SearchViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize()
    ) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            searchViewModel.updateSearchMovieList(it)
        }
        SearchItems(navController = navController)
    }
}

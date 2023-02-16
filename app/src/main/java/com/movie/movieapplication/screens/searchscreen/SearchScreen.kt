package com.movie.movieapplication.screens.searchscreen

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.JsonObject
import com.movie.movieapplication.components.BasicScreen
import com.movie.movieapplication.data.DataOrException
import com.movie.movieapplication.model.searchmovieinfo.SearchMovieInfo
import com.movie.movieapplication.screens.moviedetailscreen.MovieDetailContent
import com.movie.movieapplication.screens.viewmodels.MovieViewModel
import com.movie.movieapplication.ui.theme.MainColor
import com.movie.movieapplication.widgets.CenterCircularProgressIndicator
import com.movie.movieapplication.widgets.MovieAppBar
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
fun SearchItems(navController: NavController, searchViewModel: SearchViewModel = hiltViewModel()) {
    val searchedMovieList = searchViewModel.movieList.collectAsState().value
    if (searchedMovieList.loading == true) {
        CenterCircularProgressIndicator()
    } else if (searchedMovieList.exception != null) {
        Log.d("로그", "SearchItesms : Error while fetching data! ${searchedMovieList.exception}")
    } else if (searchedMovieList.data != null) {
        Log.d("로그", "data is not null : ${searchedMovieList.data}")
        Column(modifier = Modifier.padding(4.dp)) {

        }
    }
    searchedMovieList.data?.run {
        Log.d("로그", "$this")
//        val items = JSONArray(it.get("items").toString())
//
//        val list = mutableListOf<String>()
//
//        for (i in 0 until items.length()) {
//            val value = items.get(i).get
//            list.add(value)
//        }

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
            searchViewModel.updateSearchBarWithMovieName(it)
        }
        SearchItems(navController = navController)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit = {}
) {
    val searchQueryState = rememberSaveable {
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(searchQueryState.value) {
        searchQueryState.value.trim().isNotEmpty()
    }
    Column {
        CommonTextField(
            valueState = searchQueryState,
            placeholder = "Search",
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onSearch(searchQueryState.value.trim())
//                searchQueryState.value = ""
                keyboardController?.hide()
            }
        )
    }
}

@Composable
fun CommonTextField(
    valueState: MutableState<String>,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    onAction: KeyboardActions = KeyboardActions.Default,
    imeAction: ImeAction = ImeAction.Next
) {
    OutlinedTextField(value = valueState.value, onValueChange = { valueState.value = it }, label = {
        Text(
            text = placeholder,
            color = Color.LightGray
        )
    }, maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Black,
            cursorColor = Color.DarkGray,
            textColor = Color.LightGray
        ),
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
    )
}

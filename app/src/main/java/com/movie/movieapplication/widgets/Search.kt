package com.movie.movieapplication.widgets

import android.util.Log
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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
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
import com.movie.movieapplication.screens.searchscreen.SearchViewModel
import com.movie.movieapplication.screens.viewmodels.SearchMovieInfoViewModel
import com.movie.movieapplication.ui.theme.DeepMainColor
import com.movie.movieapplication.utils.bstrip


@Composable
fun SearchItems(navController: NavController, searchViewModel: SearchViewModel = hiltViewModel()) {
    val searchedMovieList = searchViewModel.movieList.collectAsState().value
    LazyColumn(modifier = Modifier
        .padding(10.dp)
        .fillMaxSize(), contentPadding = PaddingValues(4.dp)
    ) {
        items(items = searchedMovieList) { item ->
            SearchMovieCard(navController = navController, item = item)
        }
    }
}

@Composable
fun SearchMovieCard(navController: NavController, item: JsonObject, searchViewModel: SearchViewModel = hiltViewModel(), movieInfoViewModel: SearchMovieInfoViewModel = hiltViewModel()) {
    val itemInfo = item.getAsJsonArray("items").get(0).asJsonObject
    val movieInfo = Gson().fromJson(itemInfo.toString(), JsonObject::class.java)
    val movieName = movieInfo.get("title").toString().replace("\"", "").bstrip()
    val directorName = movieInfo.get("director").toString().split("|")[0].substring(1)
    val uriHandler = LocalUriHandler.current
    Surface(modifier = Modifier.padding(4.dp), color = DeepMainColor, elevation = 2.dp, shape = RoundedCornerShape(
        CornerSize(15.dp)
    )
    ) {
        Row {
            MovieImageCard(modifier = Modifier
                .height(160.dp)
                .padding(6.dp)
                .aspectRatio(0.707f), navController = navController, movieInformation = item, movieInfo = null)
            Box(contentAlignment = Alignment.Center) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(6.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Text(text = movieName, fontWeight = FontWeight.Bold, fontSize = 20.sp, textAlign = TextAlign.Center, color = Color.LightGray)
                    Spacer(modifier = Modifier.height(10.dp))
                    val textStyle = TextStyle(fontSize = 12.sp, color = Color.LightGray, textAlign = TextAlign.Center, fontWeight = FontWeight.SemiBold)
                    Text(text = directorName, style = textStyle)
                    Text(text = "평점 : ${itemInfo.get("userRating")}", style = textStyle)
                    Button(onClick = { if (itemInfo.get("link") != null) uriHandler.openUri(itemInfo.get("link").toString().replace("\"", "")) }, modifier = Modifier
                        .padding(4.dp)
                        .wrapContentSize(), colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray), shape = RoundedCornerShape(
                        CornerSize(15.dp)
                    )) {
                        Text(text = "영화 정보 보러가기", style = textStyle)
                    }
                }
            }
        }
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

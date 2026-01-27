package com.example.mapptuu.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.mapptuu.R
import com.example.mapptuu.ui.component.Header

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
){
    Scaffold (
        topBar = {
            Header() {  }
        },
    ){ innerPadding ->
        Box(modifier = modifier.fillMaxSize().padding(innerPadding)) {

            Image(
                painter = painterResource(id = R.drawable.fondorealista),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop

            )
        }
    }
}
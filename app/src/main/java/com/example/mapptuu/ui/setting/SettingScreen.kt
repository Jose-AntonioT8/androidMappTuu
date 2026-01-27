package com.example.mapptuu.ui.setting

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.mapptuu.ui.component.Header

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier
){
    Scaffold(topBar = {
        Header() {  }
    },) { innerPadding ->

        Box(modifier = modifier.padding(innerPadding))
    }
}
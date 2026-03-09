package com.example.mapptuu.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mapptuu.R
import com.example.mapptuu.ui.component.Header
import com.example.mapptuu.ui.navigation.Route
import com.example.mapptuu.ui.navigation.navigateToCamera

@Composable
fun ProfileScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
){
    Scaffold (
        topBar = {
            Header(onBackClick = { navController.popBackStack() })
        },
    ){ innerPadding ->
        Box(modifier = modifier.fillMaxSize().padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(96.dp)
                        .clip(CircleShape)
                        .background(
                            color = Color(0xFF60A5FA),
                            shape = CircleShape
                        )
                        .clickable{
                            navController.navigateToCamera()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "👤",
                        fontSize = 48.sp
                    )
                }
            }
        }
    }
}
package com.example.mapptuu.ui.navigation


import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController


@Composable
fun NavGraph(){
    val navController = rememberNavController()
    val startDestination = Route.LandingPage
    val backStackEntry by navController.currentBackStackEntryAsState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    )
    {
            innerPadding ->
        val contentModifier = Modifier.consumeWindowInsets(innerPadding).padding(innerPadding)
        NavHost(
            navController = navController,
            startDestination = startDestination
        ){

            activityListDestination(
                contentModifier,

                navController = navController,
                onNavigateToDetails = {
                    navController.navigateToActivityDetail(it)
                },
                onNavigateToCreation = {
                    navController.navigateToActivityCreation()
                },
                onNavigateToPlanList = {
                    navController.navigateToPlanList()
                },
                onNavigateToSetting = {
                    navController.navigateToSetting()
                },
                onNavigateToMap = {
                    navController.navigateToMap()
                }
            ) {
                navController.navigateToMap()
            }
            activityCreationDestination(contentModifier,
                onNavegationBack={
                    navController.navigateToActivityList()
                },
            )
            activityDetailDestination(contentModifier,
                onNavegationBack={
                    navController.navigateToActivityList()
                },
                onNavegateToUpdate={
                    navController.navigateToActivityUpdate(it)
                }
            )
            activityUpdateDestination(contentModifier,
                onNavigateToDetails={
                    navController.navigateToActivityDetail(it)

                }
            )
            planListDestination(
                contentModifier,
                onNavigateToDetails = {
                    navController.navigateToPlanDetail(it)
                },
                onNavigateToCreation = {
                    navController.navigateToPlanCreation()
                },
                onActivityList = {
                    navController.navigateToActivityList()
                },
                onNavigateToSetting = {
                    navController.navigateToSetting()
                },
                onNavigateToMap = {
                    navController.navigateToMap()
                },
                navController = navController
            )
            planDetailDestination(contentModifier,
                onNavegationBack={
                    navController.navigateToPlanList()
                },
                onNavegateToUpdate= {
                    navController.navigateToPlanUpdate(it)
                }
            )
            planCreationDestination(contentModifier,
                onNavegationBack={
                    navController.navigateToPlanList()
                }
            )
            planUpdateDestination(contentModifier,
                onNavigateToDetails= {
                    navController.navigateToPlanDetail(it)
                }
            )
            landingPageDestination(contentModifier,
                onNavigateToLogin={
                    navController.navigateToLogin()
                },
                onNavigateToRegister={
                    navController.navigateToRegister()
                },
                onNavigateToPlans = {
                    navController.navigateToPlanList()
                },
                onNavigateToActivities = {
                    navController.navigateToActivityList()
                },
                onNavigateToProfile = {
                    navController.navigateToProfile()
                }
            )

            loginPageDestination(navController)
            registerPageDestination(navController)

            mapDestination(
                navController = navController,
                onNavigateToPlanList = {
                    navController.navigateToPlanList()
                },
                onNavigateToProfile = {
                    navController.navigateToProfile()
                },
                onNavigateToActivities = {
                    navController.navigateToActivityList()
                },

                onNavigateToSetting = {
                    navController.navigateToSetting()
                }
            )
            profileDestination(
                navController,
                onNavigateToCamera = {
                    navController.navigateToCamera()
                }
            )




        }
    }

}

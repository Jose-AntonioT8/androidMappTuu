package com.example.mapptuu.ui.navigation



import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.mapptuu.ui.activityCreation.ActivityCreationScreen
import com.example.mapptuu.ui.activityDetail.ActivityDetailScreen
import com.example.mapptuu.ui.activityList.ActivityListScreen
import com.example.mapptuu.ui.activityUpdate.ActivityUpdateScreen
import com.example.mapptuu.ui.landingPage.LandingPageScreen
import com.example.mapptuu.ui.login.LoginScreen
import com.example.mapptuu.ui.planCreation.PlanCreationScreen
import com.example.mapptuu.ui.planDetail.PlanDetailScreen
import com.example.mapptuu.ui.planList.PlanListScreen
import com.example.mapptuu.ui.planUpdate.PlanUpdateScreen
import com.example.mapptuu.ui.signup.SignUpScreen


import kotlinx.serialization.Serializable
import kotlin.Unit

@Serializable
sealed class Route(val route:String) {
    @Serializable
    data object ActivityList:Route("activity_list")
    @Serializable
    data class ActivityDetail(val id:Long):Route(route = "activity_detail[$id]")

    @Serializable
    data object ActivityCreation:Route("activity_creation")

    @Serializable
    data class ActivityUpdate(val id:Long):Route(route = "activity_update[$id]")

    @Serializable
    data object PlanList:Route("plan_list")

    @Serializable
    data class PlanDetail(val id: Long):Route(route = "plan_detail[$id]")

    @Serializable
    data object PlanCreation:Route("plan_creation")

    @Serializable
    data class PlanUpdate(val id:Long):Route(route = "plan_update[$id]")

    @Serializable
    data object Login: Route("login")

    @Serializable
    data object Register: Route("register")

    @Serializable
    data object LandingPage:Route("landing_page")


}
fun NavController.navigateToPlanDetail(id:Long) {
    this.navigate(Route.PlanDetail(id))
}
fun NavController.navigateToPlanCreation(){
    this.navigate(Route.PlanCreation)
}
fun NavController.navigateToPlanUpdate(id: Long) {
    this.navigate(Route.PlanUpdate(id))
}
fun NavController.navigateToPlanList(){
    this.navigate(Route.PlanList)
}
fun NavController.navigateToActivityUpdate(id:Long) {
    this.navigate(Route.ActivityUpdate(id))
}
fun NavController.navigateToActivityDetail(id:Long) {
    this.navigate(Route.ActivityDetail(id))
}
fun NavController.navigateToActivityCreation(){
    this.navigate(Route.ActivityCreation)
}
fun NavController.navigateToActivityList(){
    this.navigate(Route.ActivityList)
}
fun NavController.navigateToLandingPage(){
    this.navigate(Route.LandingPage)
}
fun NavController.navigateToLogin(){
    this.navigate(Route.Login)
}
fun NavController.navigateToRegister(){
    this.navigate(Route.Register)
}

fun NavGraphBuilder.activityCreationDestination(
    modifier:Modifier = Modifier,
    onNavegationBack:()->Unit,

    ){
    composable<Route.ActivityCreation> {


            backStackEntry ->
        ActivityCreationScreen (
            modifier = modifier,
            onNavegationBack={
                onNavegationBack()
            })
    }
}
fun NavGraphBuilder.activityDetailDestination(
    modifier:Modifier = Modifier,
    onNavegationBack:()->Unit,
    onNavegateToUpdate:(Long)->Unit,


    ) {
    composable<Route.ActivityDetail> {


            backStackEntry ->
        ActivityDetailScreen (
            modifier = modifier,
            onNavegationBack={
                onNavegationBack()
            }, onUpdateActivity={
                    id ->
                onNavegateToUpdate(id)
            }
        )


    }
}

fun NavGraphBuilder.activityUpdateDestination(
    modifier:Modifier = Modifier,
    onNavigateToDetails:(Long)->Unit,
){
    composable<Route.ActivityUpdate>{
            backStackEntry ->
        ActivityUpdateScreen (
            modifier = modifier,
            onNavigateToDetails={
                    id ->
                onNavigateToDetails(id)
            }
        )
    }
}
fun NavGraphBuilder.activityListDestination(
    modifier: Modifier = Modifier,
    onNavigateToDetails: (Long) -> Unit,
    onNavigateToCreation: () -> Unit,
    onNavigateToPlanList: () -> Unit

) {
    composable<Route.ActivityList> {

        ActivityListScreen(
            modifier = modifier,
            onCreate = {
                onNavigateToCreation()
            },
            onPlanList = {
                onNavigateToPlanList()
            },
            onShowDetail = { id ->
                onNavigateToDetails(id)
            }
        )


    }
}


fun NavGraphBuilder.planCreationDestination(
    modifier:Modifier = Modifier,
    onNavegationBack:()->Unit,

    ){
    composable<Route.PlanCreation> {


            backStackEntry ->
        PlanCreationScreen(
            modifier = modifier,
            onNavegationBack={
                onNavegationBack()
            })
    }
}
fun NavGraphBuilder.planDetailDestination(
    modifier:Modifier = Modifier,
    onNavegationBack:()->Unit,
    onNavegateToUpdate:(Long)->Unit,


    ) {
    composable<Route.PlanDetail> {


            backStackEntry ->
        PlanDetailScreen (
            modifier = modifier,
            onNavegationBack = {
                onNavegationBack()
            }, onUpdatePlan = { id ->
                onNavegateToUpdate(id)
            },

            )


    }
}

fun NavGraphBuilder.planUpdateDestination(
    modifier:Modifier = Modifier,
    onNavigateToDetails:(Long)->Unit,
){
    composable<Route.PlanUpdate>{
            backStackEntry ->
        PlanUpdateScreen(
            modifier = modifier,
            onNavigateToDetails={
                    id ->
                onNavigateToDetails(id)
            }
        )
    }
}
fun NavGraphBuilder.planListDestination(
    modifier: Modifier = Modifier,
    onNavigateToDetails: (Long) -> Unit,
    onNavigateToCreation: () -> Unit,
    onActivityList: () -> Unit

) {
    composable<Route.PlanList> {

        PlanListScreen(
            modifier = modifier,
            onCreate = {
                onNavigateToCreation()
            },
            onActivityList = {
                onActivityList()
            },
            onShowDetail = { id ->
                onNavigateToDetails(id)
            }
        )


    }
}


fun NavGraphBuilder.landingPageDestination(
    modifier: Modifier = Modifier,
    onNavigateToLogin: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToActivities:() -> Unit,
    onNavigateToPlans:() -> Unit,

){
    composable<Route.LandingPage> {
        LandingPageScreen(
            modifier = modifier,
            onNavigateToLogin = {
                onNavigateToLogin()
            },
            onNavigateToRegister = {
                onNavigateToRegister()
            },
            onNavigateToActivities = {
                onNavigateToActivities()
            },
            onNavigateToPlans = {
                onNavigateToPlans()
            }

        )
    }
}

fun NavGraphBuilder.loginPageDestination(
    navController: NavController,

){
    composable<Route.Login>{
        LoginScreen(navController)

    }
}

fun NavGraphBuilder.registerPageDestination(
    navController: NavController
){
    composable<Route.Register>{
        SignUpScreen(navController)

    }
}
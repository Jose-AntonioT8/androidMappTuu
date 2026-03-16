package com.example.mapptuu.ui.navigation



import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.mapptuu.ui.activityCreation.ActivityCreationScreen
import com.example.mapptuu.ui.activityDetail.ActivityDetailScreen
import com.example.mapptuu.ui.activityList.ActivityListScreen
import com.example.mapptuu.ui.activityUpdate.ActivityUpdateScreen
import com.example.mapptuu.ui.camera.CameraScreen
import com.example.mapptuu.ui.landingPage.LandingPageScreen
import com.example.mapptuu.ui.login.LoginScreen
import com.example.mapptuu.ui.map.MapScreen
import com.example.mapptuu.ui.planCreation.PlanCreationScreen
import com.example.mapptuu.ui.planDetail.PlanDetailScreen
import com.example.mapptuu.ui.planList.PlanListScreen
import com.example.mapptuu.ui.planUpdate.PlanUpdateScreen
import com.example.mapptuu.ui.signup.SignUpScreen
import com.example.mapptuu.ui.profile.ProfileScreen


import kotlinx.serialization.Serializable
import kotlin.Unit

@Serializable
sealed class Route(val route:String) {
    @Serializable
    data object ActivityList:Route("activity_list")
    @Serializable
    data class ActivityDetail(val id:String):Route(route = "activity_detail[$id]")

    @Serializable
    data object ActivityCreation:Route("activity_creation")

    @Serializable
    data class ActivityUpdate(val id:String):Route(route = "activity_update[$id]")

    @Serializable
    data object PlanList:Route("plan_list")

    @Serializable
    data class PlanDetail(val id: String):Route(route = "plan_detail[$id]")

    @Serializable
    data object PlanCreation:Route("plan_creation")

    @Serializable
    data object Setting:Route("setting")

    @Serializable
    data class PlanUpdate(val id:String):Route(route = "plan_update[$id]")

    @Serializable
    data object Profile: Route("profile")

    @Serializable
    data object Map: Route("map")

    @Serializable
    data object Login: Route("login")

    @Serializable
    data object Register: Route("register")

    @Serializable
    data object LandingPage:Route("landing_page")

    @Serializable
    data object Camera:Route("camera")


}
fun NavController.navigateToPlanDetail(id:String) {
    this.navigate(Route.PlanDetail(id))
}
fun NavController.navigateToPlanCreation(){
    this.navigate(Route.PlanCreation)
}
fun NavController.navigateToPlanUpdate(id: String) {
    this.navigate(Route.PlanUpdate(id))
}
fun NavController.navigateToPlanList(){
    this.navigate(Route.PlanList)
}
fun NavController.navigateToActivityUpdate(id:String) {
    this.navigate(Route.ActivityUpdate(id))
}
fun NavController.navigateToActivityDetail(id:String) {
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

fun NavController.navigateToProfile(){
    this.navigate(Route.Profile)
}

fun NavController.navigateToMap(){
    this.navigate(Route.Map)
}



fun NavController.navigateToSetting(){
    this.navigate(Route.Setting)
}

fun NavController.navigateToLogin(){
    this.navigate(Route.Login)
}
fun NavController.navigateToRegister(){
    this.navigate(Route.Register)
}

fun NavController.navigateToCamera(){
    this.navigate(Route.Camera)
}



fun NavGraphBuilder.mapDestination(
    modifier: Modifier = Modifier,
    navController: NavController,
    onNavigateToPlanList: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToActivities: () -> Unit,
    onNavigateToSetting: () -> Unit,
    onNavigateToLanding: () -> Unit,
){
    composable<Route.Map>{
        MapScreen(
            modifier = modifier,
            navController = navController,
            onPlanList = onNavigateToPlanList,
            onNavigateToProfile = onNavigateToProfile,
            onNavigateToActivities = onNavigateToActivities,
            onNavigateToSetting = onNavigateToSetting,
            onNavigateToLanding= onNavigateToLanding,
        )
    }

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
    onNavegateToUpdate:(String)->Unit,


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
    onNavigateToDetails:(String)->Unit,
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
    navController: NavController,
    onNavigateToDetails: (String) -> Unit,
    onNavigateToCreation: () -> Unit,
    onNavigateToPlanList: () -> Unit,
    onNavigateToSetting: () -> Unit,
    onNavigateToMap: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToLanding: () -> Unit,

) {
    composable<Route.ActivityList> {

        ActivityListScreen(
            onCreate = {
                onNavigateToCreation()
            },
            onShowDetail = { id ->
                onNavigateToDetails(id)
            },
            onPlanList = {
                onNavigateToPlanList()
            },
            onNavigateToSetting = {
                onNavigateToSetting()
            },
            onNavigateToMap = {
                onNavigateToMap()
            },
            navController = navController,
            onNavigateToProfile = {
                onNavigateToProfile()
            },
            onNavigateToLanding = {
                onNavigateToLanding()
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
    onNavegateToUpdate:(String)->Unit,


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
    onNavigateToDetails:(String)->Unit,
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
    navController: NavController,
    onNavigateToDetails: (String) -> Unit,
    onNavigateToCreation: () -> Unit,
    onActivityList: () -> Unit,
    onNavigateToSetting: () -> Unit,
    onNavigateToMap: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToLanding: () -> Unit,
) {
    composable<Route.PlanList> {

        PlanListScreen(
            modifier = modifier,
            onCreate = {
                onNavigateToCreation()
            },
            onShowDetail = { id ->
                onNavigateToDetails(id)
            },
            onNavigateActivityList = {
                onActivityList()
            },
            onNavigateToSetting = {
                onNavigateToSetting()
            },
            onNavigateToMap = {
                onNavigateToMap()
            },
            onNavigateToProfile = {
                onNavigateToProfile()
            },
            onNavigateToLanding = {
                onNavigateToLanding()
            },
            navController = navController,
        )


    }
}


fun NavGraphBuilder.landingPageDestination(
    modifier: Modifier = Modifier,
    onNavigateToLogin: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToActivities:() -> Unit,
    onNavigateToPlans:() -> Unit,
    onNavigateToProfile: () -> Unit,

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
            },
            onNavigateToProfile = {
                onNavigateToProfile()
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

fun NavGraphBuilder.profileDestination(
    navController: NavController,
    onNavigateToCamera: () -> Unit,
    onNavigateToLanding: () -> Unit,
){
    composable<Route.Profile>{
        ProfileScreen(
            navController,
            onNavigateToLanding = {
                navController.navigateToLandingPage()
            },
        )

    }
    composable<Route.Camera>{
        CameraScreen(navController)

    }

}
fun NavGraphBuilder.cameraDestination(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    composable<Route.Camera> {
        CameraScreen(
            modifier = modifier,
            navController = navController
        )
    }
}

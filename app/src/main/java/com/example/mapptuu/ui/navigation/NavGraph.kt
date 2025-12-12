package com.example.mapptuu.ui.navigation



import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

import com.example.dragonballapiandroid.ui.creation.CharacterCreationScreen
import com.example.dragonballapiandroid.ui.detail.CharacterDetailScreen
import com.example.dragonballapiandroid.ui.list.CharacterListScreen
import com.example.dragonballapiandroid.ui.planetCreation.PlanetCreationScreen
import com.example.dragonballapiandroid.ui.planetDetail.PlanetDetailScreen
import com.example.dragonballapiandroid.ui.planetList.PlanetListScreen
import com.example.dragonballapiandroid.ui.planetUpdate.PlanetUpdateScreen
import com.example.dragonballapiandroid.ui.update.CharacterUpdateScreen
import kotlinx.serialization.Serializable
import kotlin.Unit

@Serializable
sealed class Route(val route:String) {
    @Serializable
    data object List:Route("character_list")
    @Serializable
    data class Detail(val id:Long):Route(route = "character_detail[$id]")

    @Serializable
    data object Creation:Route("creation")

    @Serializable
    data class Update(val id:Long):Route(route = "update[$id]")

    @Serializable
    data object PlanetList:Route("planet_list")

    @Serializable
    data class PlanetDetail(val id:Long):Route(route = "planet_detail[$id]")

    @Serializable
    data object PlanetCreation:Route("planet_creation")

    @Serializable
    data class PlanetUpdate(val id:Long):Route(route = "planet_update[$id]")


}
fun NavController.navigateToPlanetDetail(id:Long) {
    this.navigate(Route.PlanetDetail(id))
}
fun NavController.navigateToPlanetCreation(){
    this.navigate(Route.PlanetCreation)
}
fun NavController.navigateToPlanetUpdate(id:Long) {
    this.navigate(Route.PlanetUpdate(id))
}
fun NavController.navigateToPlanetList(){
    this.navigate(Route.PlanetList)
}
fun NavController.navigateToCharacterUpdate(id:Long) {
    this.navigate(Route.Update(id))
}
fun NavController.navigateToCharacterDetail(id:Long) {
    this.navigate(Route.Detail(id))
}
fun NavController.navigateToCreation(){
    this.navigate(Route.Creation)
}
fun NavController.navigateToList(){
    this.navigate(Route.List)
}

fun NavGraphBuilder.characterCreationDestination(
    modifier:Modifier = Modifier,
    onNavegationBack:()->Unit,

    ){
    composable<Route.Creation> {


            backStackEntry ->
        CharacterCreationScreen(
            modifier = modifier,
            onNavegationBack={
                onNavegationBack()
            })
    }
}
fun NavGraphBuilder.characterDetailDestination(
    modifier:Modifier = Modifier,
    onNavegationBack:()->Unit,
    onNavegateToUpdate:(Long)->Unit,


    ) {
    composable<Route.Detail> {


            backStackEntry ->
        CharacterDetailScreen(
            modifier = modifier,
            onNavegationBack={
                onNavegationBack()
            }, onUpdateCharacter={
                    id ->
                onNavegateToUpdate(id)
            }
        )


    }
}

fun NavGraphBuilder.characterUpdateDestination(
    modifier:Modifier = Modifier,
    onNavigateToDetails:(Long)->Unit,
){
    composable<Route.Update>{
            backStackEntry ->
        CharacterUpdateScreen(
            modifier = modifier,
            onNavigateToDetails={
                    id ->
                onNavigateToDetails(id)
            }
        )
    }
}
fun NavGraphBuilder.characterListDestination(
    modifier: Modifier = Modifier,
    onNavigateToDetails: (Long) -> Unit,
    onNavigateToCreation: () -> Unit,
    onNavigateToPlanetList: () -> Unit

) {
    composable<Route.List> {

        CharacterListScreen(
            modifier = modifier,
            onCreate = {
                onNavigateToCreation()
            },
            onPlanetList = {
                onNavigateToPlanetList()
            },
            onShowDetail = { id ->
                onNavigateToDetails(id)
            }
        )


    }
}


fun NavGraphBuilder.planetCreationDestination(
    modifier:Modifier = Modifier,
    onNavegationBack:()->Unit,

    ){
    composable<Route.PlanetCreation> {


            backStackEntry ->
        PlanetCreationScreen(
            modifier = modifier,
            onNavegationBack={
                onNavegationBack()
            })
    }
}
fun NavGraphBuilder.planetDetailDestination(
    modifier:Modifier = Modifier,
    onNavegationBack:()->Unit,
    onNavegateToUpdate:(Long)->Unit,


    ) {
    composable<Route.PlanetDetail> {


            backStackEntry ->
        PlanetDetailScreen(
            modifier = modifier,
            onNavegationBack = {
                onNavegationBack()
            }, onUpdatePlanet = { id ->
                onNavegateToUpdate(id)
            },

            )


    }
}

fun NavGraphBuilder.planetUpdateDestination(
    modifier:Modifier = Modifier,
    onNavigateToDetails:(Long)->Unit,
){
    composable<Route.PlanetUpdate>{
            backStackEntry ->
        PlanetUpdateScreen(
            modifier = modifier,
            onNavigateToDetails={
                    id ->
                onNavigateToDetails(id)
            }
        )
    }
}
fun NavGraphBuilder.planetListDestination(
    modifier: Modifier = Modifier,
    onNavigateToDetails: (Long) -> Unit,
    onNavigateToCreation: () -> Unit,
    onCharacterList: () -> Unit

) {
    composable<Route.PlanetList> {

        PlanetListScreen(
            modifier = modifier,
            onCreate = {
                onNavigateToCreation()
            },
            onCharacterList = {
                onCharacterList()
            },
            onShowDetail = { id ->
                onNavigateToDetails(id)
            }
        )


    }
}

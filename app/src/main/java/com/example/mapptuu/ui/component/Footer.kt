package com.example.mapptuu.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mapptuu.R
import com.example.mapptuu.ui.navigation.Route

data class DynamicNavItem(
    val label: String,
    val icon: Int,
    val route: Route,
    val isToggleBtn: Boolean = false
)


@Composable
fun Footer(
    activeRoute: String = "mapa",
    onNavigate: (String) -> Unit,
    navController: NavController
){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // 2. Lógica: ¿Qué botón muestro?
    // Usamos activeRoute ("mapa" o "lista") para saber dónde estamos y qué mostrar
    val toggleItem = if (activeRoute == "mapa") {
        DynamicNavItem("lista", R.drawable.lista, Route.ActivityList, isToggleBtn = true)
    } else {
        DynamicNavItem("mapa", R.drawable.map, Route.Map, isToggleBtn = true)
    }

    val toggleActivityPlan = if (activeRoute == "plans") {
        DynamicNavItem("lista", R.drawable.lista, Route.ActivityList, isToggleBtn = true)
    } else {
        DynamicNavItem("plans", R.drawable.plan, Route.Map, isToggleBtn = true)
    }

    NavigationBar(

    ) {
        val isLeftSelected = currentRoute == Route.PlanList.route || currentRoute == Route.ActivityList.route


        NavigationBarItem(

            icon = {
                Image(
                    painter = painterResource(id = toggleActivityPlan.icon),
                    contentDescription = stringResource(R.string.plans),,
                    Modifier.size(38.dp)
                )
            },
            selected = isLeftSelected,
            onClick = { onNavigate(toggleActivityPlan.label) }
        )

        // Primero calculamos qué debe mostrar este botón
        val isMapActive = currentRoute == Route.Map.route



        val isMiddleSelected = currentRoute == Route.Map.route || currentRoute == Route.ActivityList.route

        NavigationBarItem(
            icon = {
                Image(
                    painter = painterResource(id = toggleItem.icon),
                    contentDescription = stringResource(R.string.map_list),
                    Modifier.size(38.dp)
                )
            },
            selected = isMiddleSelected,
            onClick = {
                onNavigate(toggleItem.label)
            }
        )

        NavigationBarItem(
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.perso_colorida),
                    contentDescription = stringResource(R.string.user),
                    Modifier.size(38.dp)
                )
            },
            selected = activeRoute == "profile",
            onClick = { onNavigate("profile") }
        )
    }
}


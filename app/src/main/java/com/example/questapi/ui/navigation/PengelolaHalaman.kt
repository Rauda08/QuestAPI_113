package com.example.questapi.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.questapi.ui.view.DestinasiDetail
import com.example.questapi.ui.view.DestinasiEntry
import com.example.questapi.ui.view.DestinasiHome
import com.example.questapi.ui.view.DestinasiUpdate
import com.example.questapi.ui.view.DetailView
import com.example.questapi.ui.view.EntryMhsScreen
import com.example.questapi.ui.view.HomeScreen
import com.example.questapi.ui.view.UpdateMhsView


@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier
    ) {
        composable(
            route = DestinasiHome.route
        ){
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                onDetailClick = { nim ->
                    navController.navigate("${DestinasiDetail.route}/$nim")
                    println(nim)
                }
            )
        }
        composable(
            route = DestinasiEntry.route
        ){
            EntryMhsScreen(
                navigateBack = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHome.route) { inclusive = true }
                    }
                }
            )
        }
        composable(
            route = DestinasiDetail.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetail.nim){
                type = NavType.StringType
            })
        ){ backStackEntry ->
            val nim = backStackEntry.arguments?.getString(DestinasiDetail.nim)
            nim?.let {
                DetailView(
                    navigateBack = {
                        navController.navigateUp()
                    },
                    onEditClick = { nim ->
                        navController.navigate("${DestinasiUpdate.route}/$nim")
                        println(nim)
                    }
                )
            }
        }
        composable(
            route = DestinasiUpdate.routeWithArgs,
            arguments = listOf(navArgument(DestinasiUpdate.nim){
                type = NavType.StringType
            })
        ){
            UpdateMhsView(
                navigateBack = {
                    navController.popBackStack()
                },
                onNavigateUp = {
                    navController.navigate(
                        DestinasiUpdate.route
                    ){
                        popUpTo(DestinasiHome.route){
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

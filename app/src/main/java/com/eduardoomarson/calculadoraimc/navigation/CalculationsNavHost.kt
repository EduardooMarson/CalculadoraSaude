package com.eduardoomarson.calculadoraimc.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.eduardoomarson.calculadoraimc.ui.theme.feature.history.HistoryScreen
import com.eduardoomarson.calculadoraimc.ui.theme.feature.home.HomeScreen
import com.eduardoomarson.calculadoraimc.ui.theme.feature.imc.IMCScreen
import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

@Serializable
data class HistoryRoute(val id: Long?= null)

@Serializable
data class IMCRoute(val id: Long?= null)

@Composable
fun CalculationsNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = HomeRoute
    ) {

        composable<HomeRoute> {
            HomeScreen(
                onNavigateToIMC = { id ->
                    navController.navigate(IMCRoute(id = id))
                },
                onNavigateToHistory = { id ->
                    navController.navigate(HistoryRoute(id = id))
                }

                // Adicione as outras navegações conforme implementar TMB, etc.
            )
        }

        composable<IMCRoute>{ backStackEntry ->
            val imcRoute = backStackEntry.toRoute<IMCRoute>()
            IMCScreen(
                id = imcRoute.id,
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<HistoryRoute> { backStackEntry ->
            val historyRoute = backStackEntry.toRoute<HistoryRoute>()
            HistoryScreen(
                id = historyRoute.id,
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }

}
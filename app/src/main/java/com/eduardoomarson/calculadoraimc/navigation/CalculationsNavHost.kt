package com.eduardoomarson.calculadoraimc.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.eduardoomarson.calculadoraimc.ui.theme.feature.CalculationsHubScreen.CalculationsHubScreen
import com.eduardoomarson.calculadoraimc.ui.theme.feature.history.HistoryScreen
import com.eduardoomarson.calculadoraimc.ui.theme.feature.home.HomeScreen
import com.eduardoomarson.calculadoraimc.ui.theme.feature.imc.IMCScreen
import com.eduardoomarson.calculadoraimc.ui.theme.feature.tmb.TMBScreen
import kotlinx.serialization.Serializable

/* ---------- ROTAS ---------- */

@Serializable
object HomeRoute

@Serializable
data class HistoryRoute(val id: Long? = null)

@Serializable
data class IMCRoute(val id: Long? = null)

@Serializable
data class TMBRoute(val id: Long? = null)

@Serializable
object CalculationsHubRoute


/* ---------- NAV HOST ---------- */

@Composable
fun CalculationsNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HomeRoute
    ) {

        /* ---------- HOME ---------- */
        composable<HomeRoute> {
            HomeScreen(
                onNavigateToIMC = { id -> navController.navigate(IMCRoute(id)) },
                onNavigateToHistory = { id -> navController.navigate(HistoryRoute(id)) },
                onNavigateToCalculations = {
                    navController.navigate(CalculationsHubRoute)
                }
            )
        }


        /* ---------- IMC ---------- */
        composable<IMCRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<IMCRoute>()

            IMCScreen(
                id = route.id,
                navigateBack = {
                    navController.popBackStack()
                },
                navigateToTMB = {
                    navController.navigate(TMBRoute())
                },
                navigateHome = {
                    navController.navigate(HomeRoute) {
                        popUpTo(HomeRoute) { inclusive = true }
                    }
                }
            )
        }

        composable<CalculationsHubRoute> {
            CalculationsHubScreen(
                navigateToIMC = { navController.navigate(IMCRoute()) },
                navigateToTMB = { navController.navigate(TMBRoute()) },
                navigateBack = { navController.popBackStack() },
                navigateToHistory = { navController.navigate(HistoryRoute()) } // tirar o TODO()
            )
        }



        /* ---------- TMB ---------- */
        composable<TMBRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<TMBRoute>()

            TMBScreen(
                id = route.id,
                navigateBack = {
                    navController.popBackStack()
                },
                navigateHome = {
                    navController.navigate(HomeRoute) {
                        popUpTo(HomeRoute) { inclusive = true }
                    }
                }
            )
        }

        /* ---------- HISTÓRICO ---------- */
        composable<HistoryRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<HistoryRoute>()

            HistoryScreen(
                id = route.id,
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

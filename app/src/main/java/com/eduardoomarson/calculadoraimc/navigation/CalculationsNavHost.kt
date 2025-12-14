package com.eduardoomarson.calculadoraimc.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.eduardoomarson.calculadoraimc.data.HistoryRepository
import com.eduardoomarson.calculadoraimc.ui.theme.feature.Calculations.CalculatorScreen
import com.eduardoomarson.calculadoraimc.ui.theme.feature.history.HistoryScreen
import com.eduardoomarson.calculadoraimc.ui.theme.feature.home.HomeScreen

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
object CalculationsRoute


/* ---------- NAV HOST ---------- */

@Composable
fun CalculationsNavHost(
    repository: HistoryRepository // Injete o repository aqui
) {
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
                    navController.navigate(CalculationsRoute)
                }
            )
        }

        /* ---------- CALCULATIONS (Nova tela unificada) ---------- */
        composable<CalculationsRoute> {
            CalculatorScreen(
                navigateBack = {
                    navController.popBackStack()
                },
                navigateHome = {
                    navController.navigate(HomeRoute) {
                        popUpTo(HomeRoute) { inclusive = true }
                    }
                },
                onNavigateToIdealWeight = {
                },
                repository = repository
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
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

@Serializable
object HomeRoute

@Serializable
data class HistoryRoute(val id: Long? = null)

@Serializable
data class IMCRoute(val id: Long? = null)

@Serializable
data class TMBRoute(val id: Long? = null)

@Serializable
object CalculationsRoute // Sugestão da LLM usar CalculationsRoute como object


@Composable
fun CalculationsNavHost(
    repository: HistoryRepository
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HomeRoute
    ) {

        composable<HomeRoute> {
            HomeScreen(
                onNavigateToIMC = { id -> navController.navigate(IMCRoute(id)) },
                onNavigateToHistory = { id -> navController.navigate(HistoryRoute(id)) },
                onNavigateToCalculations = {
                    navController.navigate(CalculationsRoute)
                }
            )
        }

        /* ---------- Sugestão Claude ---------------*/
        /* Prompt: Poderia analisar o código atual de CalculationsNavHost e sugerir mudanças
                   de melhorias?
         */
        composable<CalculationsRoute> {
            CalculatorScreen(
                navigateBack = {
                    navController.popBackStack()
                },
                navigateHome = {
                    /* ---- trecho adaptado pela LLM utilizada -----*/
                    // PopBackStack iria retornar uma tela simplesmente
                    // Uso de popUpTo mais adequeado nesse cenário
                    // inclusive true para limpar estados antigos
                    navController.navigate(HomeRoute) {
                        popUpTo(HomeRoute) { inclusive = true }
                    }

                    /* --- fim do trecho adaptado pela LLM --------*/
                },
                onNavigateToIdealWeight = {
                },
                repository = repository
            )
        }

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
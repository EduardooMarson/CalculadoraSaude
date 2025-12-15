package com.eduardoomarson.calculadoraimc.ui.theme.feature.history

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eduardoomarson.calculadoraimc.UiEvent
import com.eduardoomarson.calculadoraimc.data.HistoryDatabaseProvider
import com.eduardoomarson.calculadoraimc.data.HistoryRepositoryImpl
import com.eduardoomarson.calculadoraimc.domain.History
import com.eduardoomarson.calculadoraimc.ui.theme.BlackPrimary
import com.eduardoomarson.calculadoraimc.ui.theme.White
import com.eduardoomarson.calculadoraimc.ui.theme.components.HistoryItemCard


@Composable
fun HistoryScreen(
    id: Long? = null,
    navigateBack: (() -> Unit)? = null
) {
    val context = LocalContext.current.applicationContext
    val database = HistoryDatabaseProvider.provide(context)
    val repository = HistoryRepositoryImpl(
        dao = database.historyDao
    )

    val viewModel = viewModel<HistoryViewModel> {
        HistoryViewModel(
            id = id,
            repository = repository
        )
    }

    val histories by viewModel.histories.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.Navigate<*> -> {}
                UiEvent.NavigateBack -> {
                    navigateBack?.invoke()
                }
                is UiEvent.ShowSnackbar -> {}
            }
        }
    }

    HistoryContent(
        histories = histories,
        onEvent = viewModel::onEvent,
        onNavigateBack = navigateBack
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryContent(
    histories: List<History>,
    onEvent: (HistoryEvent) -> Unit,
    onNavigateBack: (() -> Unit)? = null,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Histórico de Cálculos")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BlackPrimary,
                    titleContentColor = White
                )
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .consumeWindowInsets(paddingValues)
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp)
        ) {
            itemsIndexed(histories) { index, history ->
                HistoryItemCard(
                    history = history,
                    onItemClick = { },
                    onDeleteClick = {
                        onEvent(HistoryEvent.Delete(history.id))
                    }
                )

                if (index < histories.lastIndex) {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun HistoryPreview() {
    HistoryContent(
        histories = listOf(
            History(
                id = 1,
                date = "14/12/2024",
                hour = "15:30",
                gender = "Masculino",
                age = "25",
                height = "175",
                weight = "70",
                physicalActivities = "Moderado",
                imcDescription = "IMC: 22.9\nPeso normal",
                tmbDescription = "TMB: 1750 kcal/dia",
                pesoIdealDescription = null,
                caloriaDiariaDescription = "2713 kcal/dia"
            ),
            History(
                id = 2,
                date = "13/12/2024",
                hour = "10:15",
                gender = "Feminino",
                age = "30",
                height = "165",
                weight = null,
                physicalActivities = null,
                imcDescription = null,
                tmbDescription = null,
                pesoIdealDescription = "Peso Ideal (média): 58.5 kg\n\nFórmulas individuais:\n• Devine: 55.2 kg\n• Robinson: 57.8 kg\n• Miller: 62.5 kg",
                caloriaDiariaDescription = null
            )
        ),
        onEvent = {},
        onNavigateBack = {}
    )
}
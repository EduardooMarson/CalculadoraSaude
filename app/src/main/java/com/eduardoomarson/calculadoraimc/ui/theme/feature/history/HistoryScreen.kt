package com.eduardoomarson.calculadoraimc.ui.theme.feature.history

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
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
import com.eduardoomarson.calculadoraimc.domain.HistoryIMC
import com.eduardoomarson.calculadoraimc.domain.historyIMC1
import com.eduardoomarson.calculadoraimc.domain.historyIMC2
import com.eduardoomarson.calculadoraimc.ui.theme.Blue
import com.eduardoomarson.calculadoraimc.ui.theme.White
import com.eduardoomarson.calculadoraimc.ui.theme.components.HistoryItemIMC
import kotlin.compareTo


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
                is UiEvent.Navigate<*> -> TODO()
                UiEvent.NavigateBack -> TODO()
                is UiEvent.ShowSnackbar -> TODO()
            }
        }
    }

    HistoryContent(
        histories = histories,
        onEvent = viewModel::onEvent
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryContent(
    histories: List<HistoryIMC>,
    onEvent: (HistoryEvent) -> Unit,
    ) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Histórico")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Blue,
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
            itemsIndexed(histories){index, history ->
                HistoryItemIMC(
                    historyIMC = history,
                    onItemClick = { },
                    onDeleteClick = {
                        onEvent(HistoryEvent.Delete(history.id))
                    }
                )

                if(index < histories.lastIndex){
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}



@Preview
@Composable
fun HistoyPreview() {
    HistoryContent (
        histories = listOf(historyIMC1, historyIMC2),
        onEvent = { }
    )
}

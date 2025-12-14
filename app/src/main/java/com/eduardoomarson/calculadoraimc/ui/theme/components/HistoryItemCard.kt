package com.eduardoomarson.calculadoraimc.ui.theme.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eduardoomarson.calculadoraimc.domain.History
import com.eduardoomarson.calculadoraimc.ui.theme.BlackPrimary
import com.eduardoomarson.calculadoraimc.ui.theme.GraySurface
import com.eduardoomarson.calculadoraimc.ui.theme.OrangePrimary

@Composable
fun HistoryItemCard(
    history: History,
    onItemClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        onClick = onItemClick,
        colors = CardDefaults.cardColors(containerColor = GraySurface),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header: Data, Hora e Botão Delete
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = history.date,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = BlackPrimary
                    )
                    Text(
                        text = history.hour,
                        fontSize = 12.sp,
                        color = BlackPrimary.copy(alpha = 0.6f)
                    )
                }

                IconButton(
                    onClick = onDeleteClick,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Deletar",
                        tint = OrangePrimary
                    )
                }
            }

            // Dados Pessoais (se existirem)
            if (!history.gender.isNullOrEmpty() || !history.age.isNullOrEmpty() ||
                !history.weight.isNullOrEmpty() || !history.physicalActivities.isNullOrEmpty()) {

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = BlackPrimary.copy(alpha = 0.1f))
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Dados Pessoais",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = OrangePrimary
                )
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (!history.height.isNullOrEmpty()) {
                        InfoItem("Altura", "${history.height} cm")
                    }
                    if (!history.weight.isNullOrEmpty()) {
                        InfoItem("Peso", "${history.weight} kg")
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (!history.age.isNullOrEmpty()) {
                        InfoItem("Idade", "${history.age} anos")
                    }
                    if (!history.gender.isNullOrEmpty()) {
                        InfoItem("Sexo", history.gender)
                    }
                }

                if (!history.physicalActivities.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.height(6.dp))
                    InfoItem("Atividade", history.physicalActivities)
                }
            }

            // Resultados dos Cálculos
            var hasResults = false

            // IMC
            if (!history.imcDescription.isNullOrEmpty()) {
                if (!hasResults) {
                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(color = BlackPrimary.copy(alpha = 0.1f))
                    Spacer(modifier = Modifier.height(12.dp))
                    hasResults = true
                } else {
                    Spacer(modifier = Modifier.height(12.dp))
                }

                ResultSection(
                    title = "IMC",
                    result = history.imcDescription
                )
            }

            // TMB
            if (!history.tmbDescription.isNullOrEmpty()) {
                if (!hasResults) {
                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(color = BlackPrimary.copy(alpha = 0.1f))
                    Spacer(modifier = Modifier.height(12.dp))
                    hasResults = true
                } else {
                    Spacer(modifier = Modifier.height(12.dp))
                }

                ResultSection(
                    title = "TMB",
                    result = history.tmbDescription
                )
            }

            // Necessidade Calórica Diária
            if (!history.caloriaDiariaDescription.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                ResultSection(
                    title = "Necessidade Calórica",
                    result = history.caloriaDiariaDescription
                )
            }

            // Peso Ideal
            if (!history.pesoIdealDescription.isNullOrEmpty()) {
                if (!hasResults) {
                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(color = BlackPrimary.copy(alpha = 0.1f))
                    Spacer(modifier = Modifier.height(12.dp))
                } else {
                    Spacer(modifier = Modifier.height(12.dp))
                }

                ResultSection(
                    title = "Peso Ideal",
                    result = history.pesoIdealDescription
                )
            }
        }
    }
}

@Composable
private fun InfoItem(label: String, value: String) {
    Column {
        Text(
            text = label,
            fontSize = 10.sp,
            color = BlackPrimary.copy(alpha = 0.6f)
        )
        Text(
            text = value,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = BlackPrimary
        )
    }
}

@Composable
private fun ResultSection(title: String, result: String) {
    Column {
        Text(
            text = title,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = OrangePrimary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = result,
            fontSize = 13.sp,
            color = BlackPrimary,
            lineHeight = 18.sp
        )
    }
}
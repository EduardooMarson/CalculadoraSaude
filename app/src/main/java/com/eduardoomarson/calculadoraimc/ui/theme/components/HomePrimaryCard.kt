package com.eduardoomarson.calculadoraimc.ui.theme.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eduardoomarson.calculadoraimc.ui.theme.CalculadoraIMCTheme
import com.eduardoomarson.calculadoraimc.ui.theme.OrangePrimary
import com.eduardoomarson.calculadoraimc.ui.theme.WhitePrimary

@Composable
fun HomePrimaryCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: (() -> Unit)?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable { onClick?.invoke() },
        colors = CardDefaults.cardColors(
            containerColor = OrangePrimary
        ),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column {
                Text(
                    text = title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = WhitePrimary
                )
                Text(
                    text = subtitle,
                    fontSize = 14.sp,
                    color = WhitePrimary.copy(alpha = 0.9f)
                )
            }

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = WhitePrimary,
                modifier = Modifier.size(42.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePrimaryCardPreview() {
    CalculadoraIMCTheme {
        HomePrimaryCard(
            title = "Calcular",
            subtitle = "Calcular métricas de saúde",
            icon = Icons.Default.MonitorWeight,
            onClick = {}
        )
    }
}

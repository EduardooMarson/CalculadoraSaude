package com.eduardoomarson.calculadoraimc.ui.theme.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.eduardoomarson.calculadoraimc.ui.theme.BlackPrimary
import com.eduardoomarson.calculadoraimc.ui.theme.GraySurface
import com.eduardoomarson.calculadoraimc.ui.theme.OrangePrimary
import com.eduardoomarson.calculadoraimc.ui.theme.WhitePrimary

@Composable
fun ActivityButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) OrangePrimary else GraySurface,
            contentColor = if (selected) WhitePrimary else BlackPrimary
        ),
        //modifier = Modifier.weight(1f)
    ) {
        Text(text, fontSize = 12.sp)
    }
}


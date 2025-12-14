package com.eduardoomarson.calculadoraimc.ui.theme.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.eduardoomarson.calculadoraimc.ui.theme.BlackPrimary
import com.eduardoomarson.calculadoraimc.ui.theme.GraySurface
import com.eduardoomarson.calculadoraimc.ui.theme.OrangePrimary
import com.eduardoomarson.calculadoraimc.ui.theme.WhitePrimary

@Composable
fun GenderButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) OrangePrimary else GraySurface,
            contentColor = if (selected) WhitePrimary else BlackPrimary
        )
    ) {
        Text(text)
    }
}
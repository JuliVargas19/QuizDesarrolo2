package com.example.quiz.Items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.ui.Alignment


@Composable
@Preview (showBackground = true)
fun CalcularResistencia() {
    val colorDeBanda = listOf(
        "Negro" to 0,
        "Marrón" to 1,
        "Rojo" to 2,
        "Naranja" to 3,
        "Amarillo" to 4,
        "Verde" to 5,
        "Azul" to 6,
        "Violeta" to 7,
        "Gris" to 8,
        "Blanco" to 9
    )

    val multiplicadores = listOf(
        "Negro" to 1,
        "Marrón" to 10,
        "Rojo" to 100,
        "Naranja" to 1000,
        "Amarillo" to 10000
    )

    val tolerancias = listOf(
        "Dorado" to 5,
        "Plateado" to 10,
        "Ninguno" to 20
    )

    var banda1 by remember { mutableStateOf(colorDeBanda[0]) }
    var banda2 by remember { mutableStateOf(colorDeBanda[0]) }
    var bandaMultiplicadora by remember { mutableStateOf(multiplicadores[0]) }
    var bandaTolerancia by remember { mutableStateOf(tolerancias[0]) }
    var resultado by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DropdownMenuComponent("Banda 1", colorDeBanda, banda1) { banda1 = it }
        DropdownMenuComponent("Banda 2", colorDeBanda, banda2) { banda2 = it }
        DropdownMenuComponent("Multiplicador", multiplicadores, bandaMultiplicadora) { bandaMultiplicadora = it }
        DropdownMenuComponent("Tolerancia", tolerancias, bandaTolerancia) { bandaTolerancia = it }

        Button(onClick = {
            val valorBase = ((banda1.second * 10) + banda2.second) * bandaMultiplicadora.second
            resultado = "$valorBase Ω ±${bandaTolerancia.second}%"
        }) {
            Text("Calcular")
        }

        if (resultado.isNotEmpty()) {
            Text("Resultado: $resultado")
        }
    }
}

@Composable
fun DropdownMenuComponent(
    label: String,
    items: List<Pair<String, Int>>,
    selectedItem: Pair<String, Int>,
    onItemSelected: (Pair<String, Int>) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(text = label)
        Button(onClick = { expanded = true }) {
            Text(selectedItem.first)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item.first) },
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

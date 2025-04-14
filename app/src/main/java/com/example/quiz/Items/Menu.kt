package com.example.quiz.Items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
@Preview(showBackground = true)
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
        Resistencia(
            banda1 = banda1,
            banda2 = banda2,
            bandaMultiplicadora = bandaMultiplicadora,
            bandaTolerancia = bandaTolerancia
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DropdownMenuComponent("Banda 1", colorDeBanda, banda1) { banda1 = it }
            DropdownMenuComponent("Banda 2", colorDeBanda, banda2) { banda2 = it }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DropdownMenuComponent("Multiplicador", multiplicadores, bandaMultiplicadora) { bandaMultiplicadora = it }
            DropdownMenuComponent("Tolerancia", tolerancias, bandaTolerancia) { bandaTolerancia = it }
        }
        Button(
            onClick = {
                val valorBase = ((banda1.second * 10) + banda2.second) * bandaMultiplicadora.second
                resultado = "$valorBase Ω ±${bandaTolerancia.second}%"
            },
            modifier = Modifier.shadow(4.dp)
        ) {
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

    Column(horizontalAlignment = Alignment.Start) {
        Text(text = label)
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.width(160.dp)
        ) {
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

@Composable
fun Resistencia(
    banda1: Pair<String, Int>,
    banda2: Pair<String, Int>,
    bandaMultiplicadora: Pair<String, Int>,
    bandaTolerancia: Pair<String, Int>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .width(40.dp)
                .height(6.dp)
                .background(Color.DarkGray)
        )
        Box(
            modifier = Modifier
                .width(200.dp)
                .height(50.dp)
                .shadow(6.dp, RoundedCornerShape(25.dp))
                .background(Color(0xFFF5DEB3), RoundedCornerShape(25.dp)),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                listOf(banda1, banda2, bandaMultiplicadora, bandaTolerancia).forEach { banda ->
                    Box(
                        modifier = Modifier
                            .width(10.dp)
                            .height(40.dp)
                            .background(colorNombre(banda.first), RoundedCornerShape(2.dp))
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .width(40.dp)
                .height(6.dp)
                .background(Color.DarkGray)
        )
    }
}

fun colorNombre(nombre: String): Color {
    return when (nombre) {
        "Negro" -> Color.Black
        "Marrón" -> Color(0xFF8B4513)
        "Rojo" -> Color.Red
        "Naranja" -> Color(0xFFFFA500)
        "Amarillo" -> Color.Yellow
        "Verde" -> Color.Green
        "Azul" -> Color.Blue
        "Violeta" -> Color(0xFF8A2BE2)
        "Gris" -> Color.Gray
        "Blanco" -> Color.White
        "Dorado" -> Color(0xFFFFD700)
        "Plateado" -> Color.LightGray
        "Ninguno" -> Color.Transparent
        else -> Color.Transparent
    }
}
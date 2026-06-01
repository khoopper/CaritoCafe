package com.example.caritocafeapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductFormScreen(
    onBackClick: () -> Unit,
    onSaveClick: (String, Double, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf("") }
    var priceString by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Bebidas") }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Nuevo Producto",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF3E2723)
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás", tint = Color(0xFF5D4037))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFAF6F0))
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFAF6F0))
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it; errorMessage = "" },
                label = { Text("Nombre del Producto") },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF8D6E63),
                    unfocusedBorderColor = Color(0xFFD7CCC8),
                    focusedLabelColor = Color(0xFF5D4037)
                ),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = priceString,
                onValueChange = { priceString = it; errorMessage = "" },
                label = { Text("Precio ($)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF8D6E63),
                    unfocusedBorderColor = Color(0xFFD7CCC8),
                    focusedLabelColor = Color(0xFF5D4037)
                ),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = category,
                onValueChange = { category = it; errorMessage = "" },
                label = { Text("Categoría") },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF8D6E63),
                    unfocusedBorderColor = Color(0xFFD7CCC8),
                    focusedLabelColor = Color(0xFF5D4037)
                ),
                modifier = Modifier.fillMaxWidth()
            )

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val parsedPrice = priceString.toDoubleOrNull()
                    if (name.isBlank()) {
                        errorMessage = "El nombre es obligatorio"
                    } else if (parsedPrice == null || parsedPrice <= 0) {
                        errorMessage = "El precio debe ser mayor que cero"
                    } else {
                        onSaveClick(name.trim(), parsedPrice, category.trim())
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5D4037),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(Icons.Default.Check, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Guardar Producto", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}

package com.example.caritocafeapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caritocafeapp.data.model.OrderItem
import com.example.caritocafeapp.data.model.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewOrderScreen(
    availableProducts: List<Product>,
    onBackClick: () -> Unit,
    onSaveOrderClick: (List<OrderItem>) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val cartItems = remember { mutableStateListOf<OrderItem>() }
    val total = cartItems.sumOf { it.subtotal }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Crear Nueva Orden",
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
                .padding(20.dp)
        ) {
            Text(
                text = "Seleccione un producto para agregar:",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF5D4037)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Box(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { expanded = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEFEBE9),
                        contentColor = Color(0xFF4E342E)
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Seleccionar Producto ▼", fontWeight = FontWeight.Bold)
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .background(Color.White)
                ) {
                    availableProducts.forEach { product ->
                        DropdownMenuItem(
                            text = { Text("${product.name} - $${String.format("%.2f", product.price)}") },
                            onClick = {
                                expanded = false
                                val existingItemIndex = cartItems.indexOfFirst { it.product.id == product.id }
                                if (existingItemIndex != -1) {
                                    val existingItem = cartItems[existingItemIndex]
                                    cartItems[existingItemIndex] = existingItem.copy(quantity = existingItem.quantity + 1)
                                } else {
                                    cartItems.add(OrderItem(product, 1))
                                }
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Detalle de Orden Activa",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3E2723)
            )
            Spacer(modifier = Modifier.height(12.dp))

            if (cartItems.isEmpty()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No has agregado productos al pedido",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF8D6E63)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(cartItems) { item ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = item.product.name,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF3E2723)
                                    )
                                    Text(
                                        text = "Subtotal: $${String.format("%.2f", item.subtotal)}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color(0xFF8D6E63)
                                    )
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    IconButton(
                                        onClick = {
                                            val idx = cartItems.indexOf(item)
                                            if (item.quantity > 1) {
                                                cartItems[idx] = item.copy(quantity = item.quantity - 1)
                                            } else {
                                                cartItems.removeAt(idx)
                                            }
                                        }
                                    ) {
                                        Icon(Icons.Default.Remove, contentDescription = "Menos", tint = Color(0xFF8D6E63))
                                    }

                                    Text(
                                        text = "${item.quantity}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = Color(0xFF3E2723)
                                    )

                                    IconButton(
                                        onClick = {
                                            val idx = cartItems.indexOf(item)
                                            cartItems[idx] = item.copy(quantity = item.quantity + 1)
                                        }
                                    ) {
                                        Icon(Icons.Default.Add, contentDescription = "Más", tint = Color(0xFF8D6E63))
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Divider(modifier = Modifier.padding(vertical = 16.dp), color = Color(0xFFEFEBE9))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total Final:",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3E2723)
                )
                Text(
                    text = "$${String.format("%.2f", total)}",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF8D6E63)
                )
            }

            Button(
                onClick = { onSaveOrderClick(cartItems.toList()) },
                enabled = cartItems.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5D4037),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(Icons.Default.ShoppingCart, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Guardar Orden Abierta", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}

package com.example.caritocafeapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caritocafeapp.data.model.Order
import com.example.caritocafeapp.ui.components.OrderCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    orders: List<Order>,
    onNavigateToProducts: () -> Unit,
    onNavigateToNewOrder: () -> Unit,
    onNavigateToOrderDetail: (Int) -> Unit,
    onNavigateToReports: () -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Carito Café & Bakery",
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF3E2723)
                    ) 
                },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Cerrar Sesión",
                            tint = Color(0xFF5D4037)
                        )
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
                text = "Panel de Control",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF5D4037)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Navigation grid-like buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Products
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(100.dp)
                        .clickable { onNavigateToProducts() },
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF8D6E63))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Default.Coffee, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Productos", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 14.sp)
                    }
                }

                // New Order
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(100.dp)
                        .clickable { onNavigateToNewOrder() },
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF5D4037))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Nueva Orden", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 14.sp)
                    }
                }

                // Reports
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(100.dp)
                        .clickable { onNavigateToReports() },
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF4E342E))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Default.Assessment, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Reportes", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 14.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Orders list header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.ReceiptLong, contentDescription = null, tint = Color(0xFF5D4037), modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Órdenes Registradas",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3E2723)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

            if (orders.isEmpty()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay órdenes creadas todavía",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF8D6E63)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(orders) { order ->
                        OrderCard(
                            order = order,
                            onClick = { onNavigateToOrderDetail(order.id) }
                        )
                    }
                }
            }
        }
    }
}

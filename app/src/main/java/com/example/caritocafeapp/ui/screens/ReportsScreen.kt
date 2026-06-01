package com.example.caritocafeapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.PointOfSale
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
fun ReportsScreen(
    orders: List<Order>,
    totalRevenue: Double,
    closedOrdersCount: Int,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val closedOrders = orders.filter { it.status == "cerrada" }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Resumen de Ventas",
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
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Métricas del Día",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF5D4037)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Icon(
                            imageVector = Icons.Default.AttachMoney,
                            contentDescription = null,
                            tint = Color(0xFF2E7D32)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Total Vendido",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF2E7D32)
                        )
                        Text(
                            text = "$${String.format("%.2f", totalRevenue)}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF1B5E20)
                        )
                    }
                }

                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFECEFF1))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Icon(
                            imageVector = Icons.Default.PointOfSale,
                            contentDescription = null,
                            tint = Color(0xFF37474F)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Órdenes Pagadas",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF37474F)
                        )
                        Text(
                            text = "$closedOrdersCount",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF212121)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Detalle de Ventas Cerradas",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3E2723)
            )

            if (closedOrders.isEmpty()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No se registran ventas cobradas el día de hoy",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF8D6E63)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(closedOrders) { order ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(14.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "Orden #${order.id}",
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF3E2723)
                                    )
                                    Text(
                                        text = "${order.items.sumOf { it.quantity }} productos vendidos",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color(0xFF8D6E63)
                                    )
                                }
                                Text(
                                    text = "$${String.format("%.2f", order.total)}",
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFF2E7D32),
                                    fontSize = 18.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

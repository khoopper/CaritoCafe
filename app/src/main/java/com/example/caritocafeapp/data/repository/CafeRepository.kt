package com.example.caritocafeapp.data.repository

import androidx.compose.runtime.mutableStateListOf
import com.example.caritocafeapp.data.model.Order
import com.example.caritocafeapp.data.model.OrderItem
import com.example.caritocafeapp.data.model.Product
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object CafeRepository {
    // In-memory list of products using Compose reactive state list
    val products = mutableStateListOf<Product>(
        Product(1, "Café Americano", 2.50, "Bebidas"),
        Product(2, "Capuchino", 3.50, "Bebidas"),
        Product(3, "Croissant", 2.00, "Pastelería"),
        Product(4, "Tarta de Queso", 4.50, "Pastelería")
    )

    // In-memory list of orders using Compose reactive state list
    val orders = mutableStateListOf<Order>()

    private var nextProductId = 5
    private var nextOrderId = 1

    fun addProduct(name: String, price: Double, category: String) {
        val newProduct = Product(
            id = nextProductId++,
            name = name,
            price = price,
            category = category
        )
        products.add(newProduct)
    }

    fun getProducts(): List<Product> {
        return products
    }

    fun createOrder(items: List<OrderItem>): Order {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val newOrder = Order(
            id = nextOrderId++,
            items = items,
            status = "abierta",
            createdAt = dateFormat.format(Date())
        )
        orders.add(newOrder)
        return newOrder
    }

    fun getOrders(): List<Order> {
        return orders
    }

    fun getOrderById(id: Int): Order? {
        return orders.find { it.id == id }
    }

    fun cerrarOrder(id: Int) {
        val index = orders.indexOfFirst { it.id == id }
        if (index != -1) {
            val order = orders[index]
            orders[index] = order.copy(status = "cerrada")
        }
    }

    fun obtenerTotalVendidoDia(): Double {
        return orders
            .filter { it.status == "cerrada" }
            .sumOf { it.total }
    }

    fun contarOrdenesCerradas(): Int {
        return orders.count { it.status == "cerrada" }
    }
}

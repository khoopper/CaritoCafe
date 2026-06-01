package com.example.caritocafeapp.data.model

data class Order(
    val id: Int,
    val items: List<OrderItem>,
    val status: String,
    val createdAt: String = ""
) {
    val total: Double
        get() = items.sumOf { it.subtotal }
}

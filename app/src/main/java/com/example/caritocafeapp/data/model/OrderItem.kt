package com.example.caritocafeapp.data.model

data class OrderItem(
    val product: Product,
    val quantity: Int
) {
    val subtotal: Double
        get() = product.price * quantity
}

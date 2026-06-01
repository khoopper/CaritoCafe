package com.example.caritocafeapp.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("home")
    object ProductList : Screen("product_list")
    object ProductForm : Screen("product_form")
    object NewOrder : Screen("new_order")
    object OrderDetail : Screen("order_detail/{orderId}") {
        fun createRoute(orderId: Int) = "order_detail/$orderId"
    }
    object Reports : Screen("reports")
}

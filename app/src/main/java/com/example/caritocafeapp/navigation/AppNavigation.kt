package com.example.caritocafeapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.caritocafeapp.data.repository.CafeRepository
import com.example.caritocafeapp.ui.screens.*

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
        modifier = modifier
    ) {
        // 1. Login Screen
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        // 1b. Register Screen
        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                    }
                },
                onBackToLoginClick = {
                    navController.navigateUp()
                }
            )
        }

        // 2. Home Screen
        composable(Screen.Home.route) {
            HomeScreen(
                orders = CafeRepository.orders,
                onNavigateToProducts = { navController.navigate(Screen.ProductList.route) },
                onNavigateToNewOrder = { navController.navigate(Screen.NewOrder.route) },
                onNavigateToOrderDetail = { orderId ->
                    navController.navigate(Screen.OrderDetail.createRoute(orderId))
                },
                onNavigateToReports = { navController.navigate(Screen.Reports.route) },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }

        // 3. Product List Screen
        composable(Screen.ProductList.route) {
            ProductListScreen(
                products = CafeRepository.products,
                onBackClick = { navController.navigateUp() },
                onAddProductClick = { navController.navigate(Screen.ProductForm.route) }
            )
        }

        // 4. Product Form Screen
        composable(Screen.ProductForm.route) {
            ProductFormScreen(
                onBackClick = { navController.navigateUp() },
                onSaveClick = { name, price, category ->
                    CafeRepository.addProduct(name, price, category)
                    navController.navigateUp()
                }
            )
        }

        // 5. New Order Screen
        composable(Screen.NewOrder.route) {
            NewOrderScreen(
                availableProducts = CafeRepository.products,
                onBackClick = { navController.navigateUp() },
                onSaveOrderClick = { items ->
                    CafeRepository.createOrder(items)
                    navController.navigateUp()
                }
            )
        }

        // 6. Order Detail Screen
        composable(
            route = Screen.OrderDetail.route,
            arguments = listOf(navArgument("orderId") { type = NavType.IntType })
        ) { backStackEntry ->
            val orderId = backStackEntry.arguments?.getInt("orderId") ?: 0
            val order = CafeRepository.getOrderById(orderId)

            OrderDetailScreen(
                order = order,
                onBackClick = { navController.navigateUp() },
                onCloseOrderClick = {
                    CafeRepository.cerrarOrder(orderId)
                    navController.navigateUp()
                }
            )
        }

        // 7. Reports Screen
        composable(Screen.Reports.route) {
            ReportsScreen(
                orders = CafeRepository.orders,
                totalRevenue = CafeRepository.obtenerTotalVendidoDia(),
                closedOrdersCount = CafeRepository.contarOrdenesCerradas(),
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}

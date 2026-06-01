package com.example.caritocafeapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var username by remember { mutableStateOf("") }
    var pin by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val lightGrayBg = Color(0xFFF1F5F9)
    val darkBlack = Color(0xFF0F172A)
    val textGray = Color(0xFF64748B)
    val skyBlueAccent = Color(0xFF0284C7)
    val borderLightGray = Color(0xFFCBD5E1)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(lightGrayBg),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Carito Café & Bakery",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Black,
                color = darkBlack,
                fontSize = 32.sp
            )
            Text(
                text = "Técnicas de Producción Industrial de Software",
                style = MaterialTheme.typography.bodySmall,
                color = textGray,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Acceso Administrativo",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = darkBlack,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    OutlinedTextField(
                        value = username,
                        onValueChange = {
                            username = it
                            errorMessage = ""
                        },
                        label = { Text("Usuario / Email") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = skyBlueAccent) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = skyBlueAccent,
                            unfocusedBorderColor = borderLightGray,
                            focusedLabelColor = skyBlueAccent
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = pin,
                        onValueChange = {
                            pin = it
                            errorMessage = ""
                        },
                        label = { Text("PIN / Contraseña") },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = skyBlueAccent) },
                        trailingIcon = {
                            val image = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                                Icon(imageVector = image, contentDescription = null, tint = skyBlueAccent)
                            }
                        },
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = skyBlueAccent,
                            unfocusedBorderColor = borderLightGray,
                            focusedLabelColor = skyBlueAccent
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (errorMessage.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.align(Alignment.Start)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            if (username.isBlank() || pin.isBlank()) {
                                errorMessage = "Los campos no deben estar vacíos"
                            } else {
                                onLoginSuccess()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = skyBlueAccent,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(
                            text = "Ingresar",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    TextButton(onClick = onRegisterClick) {
                        Text(
                            text = "¿No tienes cuenta? Regístrate",
                            color = skyBlueAccent,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

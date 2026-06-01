package com.example.caritocafeapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onBackToLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var fullName by remember { mutableStateOf("") }
    var identification by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    // Validation checks
    val isIdValid = identification.length >= 8
    val isPasswordValid = password.length >= 6
    val isFormValid = fullName.isNotBlank() && isIdValid && isPasswordValid

    // Style colors
    val accentColor = Color(0xFF0284C7)
    val darkBlueColor = Color(0xFF0F172A)
    val lightBgColor = Color(0xFFF1F5F9)
    val grayHelperColor = Color(0xFF94A3B8)
    val greenSuccessColor = Color(0xFF2E7D32)

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            title = { 
                Text(
                    text = "¡Registro Exitoso!",
                    fontWeight = FontWeight.Bold,
                    color = darkBlueColor
                ) 
            },
            text = { 
                Text(
                    text = "Los datos de $fullName han sido validados correctamente.",
                    color = darkBlueColor
                ) 
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showSuccessDialog = false
                        onRegisterSuccess()
                    }
                ) {
                    Text("Aceptar", fontWeight = FontWeight.Bold, color = accentColor)
                }
            },
            containerColor = Color.White
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(lightBgColor),
        contentAlignment = Alignment.Center
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(28.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(vertical = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 32.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Circular avatar representation
                Box(
                    modifier = Modifier
                        .size(86.dp)
                        .background(accentColor, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(52.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Brandon Ramírez",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Black,
                    color = darkBlueColor,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Registro de Usuario",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = darkBlueColor,
                    modifier = Modifier.padding(top = 4.dp),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Completa los datos para habilitar el registro",
                    style = MaterialTheme.typography.bodyMedium,
                    color = grayHelperColor,
                    modifier = Modifier.padding(top = 4.dp, bottom = 24.dp),
                    textAlign = TextAlign.Center
                )

                // 1. Full name field
                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    placeholder = { Text("Nombre completo") },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = accentColor,
                        unfocusedBorderColor = Color(0xFFE2E8F0),
                        placeholderColor = grayHelperColor
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 2. Identification field
                Column(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = identification,
                        onValueChange = { identification = it },
                        placeholder = { Text("Identificación") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = accentColor,
                            unfocusedBorderColor = Color(0xFFE2E8F0),
                            placeholderColor = grayHelperColor
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = if (isIdValid) "Identificación válida" else "Debe contener al menos 8 dígitos",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (isIdValid) greenSuccessColor else grayHelperColor,
                        modifier = Modifier.padding(start = 12.dp, top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 3. Password field
                Column(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = { Text("Contraseña") },
                        trailingIcon = {
                            val image = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                                Icon(imageVector = image, contentDescription = null, tint = accentColor)
                            }
                        },
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = accentColor,
                            unfocusedBorderColor = Color(0xFFE2E8F0),
                            placeholderColor = grayHelperColor
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = if (isPasswordValid) "Contraseña válida" else "La contraseña debe tener al menos 6 caracteres",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (isPasswordValid) greenSuccessColor else grayHelperColor,
                        modifier = Modifier.padding(start = 12.dp, top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Submit button
                Button(
                    onClick = {
                        if (isFormValid) {
                            showSuccessDialog = true
                        }
                    },
                    enabled = isFormValid,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = accentColor,
                        contentColor = Color.White,
                        disabledContainerColor = Color(0xFFE2E8F0),
                        disabledContentColor = Color(0xFF94A3B8)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "Finalizar Registro",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = onBackToLoginClick) {
                    Text(
                        text = "¿Ya tienes cuenta? Inicia Sesión",
                        color = accentColor,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

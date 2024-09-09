package com.example.excerise_6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginPage(navController) }
        composable("dashboard") { StockMarketDashboard() }
    }
}

@Composable
fun LoginPage(navController: NavHostController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login", fontSize = 24.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (username.isEmpty() || password.isEmpty()) {
                    errorMessage = "Please fill in all fields"
                } else {
                    if (username == "user" && password == "password") {
                        errorMessage = ""
                        navController.navigate("dashboard")
                    } else {
                        errorMessage = "Invalid credentials"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Login")
        }
        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = errorMessage, color = Color.Red)
        }
    }
}

@Composable
fun StockMarketDashboard() {
    // List of stock information
    val stockData = listOf(
        Stock("AAPL - Apple Inc.", currentPrice = 150.00, purchasePrice = 120.00),
        Stock("GOOGL - Alphabet Inc.", currentPrice = 2800.00, purchasePrice = 2500.00),
        Stock("AMZN - Amazon.com Inc.", currentPrice = 3400.00, purchasePrice = 3200.00),
        Stock("TSLA - Tesla Inc.", currentPrice = 720.00, purchasePrice = 800.00),
        Stock("MSFT - Microsoft Corp.", currentPrice = 295.00, purchasePrice = 300.00)
    )

    Scaffold(
        topBar = {
            Text(
                text = "Stock Market Dashboard",
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier.padding(16.dp)
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            items(stockData) { stock ->
                StockCard(stock = stock)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun StockCard(stock: Stock) {
    // Calculate profit or loss
    val profitOrLoss = stock.currentPrice - stock.purchasePrice
    val profitOrLossColor = if (profitOrLoss >= 0) Color.Green else Color.Red

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black) // Set the background color
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(text = stock.name, fontSize = 20.sp, color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Current Price: \$${stock.currentPrice}", fontSize = 16.sp, color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Purchase Price: \$${stock.purchasePrice}", fontSize = 16.sp, color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Profit/Loss: \$${"%.2f".format(profitOrLoss)}",
                fontSize = 16.sp,
                color = profitOrLossColor
            )
        }
    }
}

data class Stock(
    val name: String,
    val currentPrice: Double,
    val purchasePrice: Double
)

@Preview(showBackground = true)
@Composable
fun PreviewLoginPage() {
    MyApp()
}

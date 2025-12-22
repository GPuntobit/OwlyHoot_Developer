package com.owlyhoot.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.owlyhoot.ui.theme.OwlyHootTheme
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Home Feed",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Placeholder per i filtri
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            FilterChip(
                selected = true,
                onClick = { /* Handle filter selection */ },
                label = { Text("Trending") }
            )
            
            FilterChip(
                selected = false,
                onClick = { /* Handle filter selection */ },
                label = { Text("Amici") }
            )
            
            FilterChip(
                selected = false,
                onClick = { /* Handle filter selection */ },
                label = { Text("Recenti") }
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Placeholder per il feed
        LazyColumn {
            items(10) { index ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Utente $index",
                            style = MaterialTheme.typography.titleMedium
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "Questo è un post di esempio numero $index. In una vera applicazione, questi sarebbero i contenuti condivisi dagli utenti.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Row {
                            IconButton(onClick = { /* Handle like */ }) {
                                Icon(Icons.Default.Favorite, contentDescription = "Like")
                            }
                            
                            Text("${(index * 3)}")
                            
                            IconButton(onClick = { /* Handle comment */ }) {
                                Icon(Icons.Default.Comment, contentDescription = "Commenta")
                            }
                            
                            Text("${index + 1}")
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    OwlyHootTheme {
        HomeScreen(rememberNavController())
    }
}
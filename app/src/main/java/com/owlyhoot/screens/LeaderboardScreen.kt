package com.owlyhoot.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.owlyhoot.ui.theme.OwlyHootTheme

@Composable
fun LeaderboardScreen(navController: NavController) {
    var selectedPeriod by remember { mutableStateOf("Settimanale") }
    
    val periods = listOf("Settimanale", "Mensile")
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Classifica",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Selettore periodo
        TabRow(selectedTabIndex = if(selectedPeriod == "Settimanale") 0 else 1) {
            periods.forEachIndexed { index, period ->
                Tab(
                    selected = selectedPeriod == period,
                    onClick = { selectedPeriod = period },
                    text = { Text(period) }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Placeholder per la classifica
        LazyColumn {
            items(10) { index ->
                val medal = when(index) {
                    0 -> "🥇"
                    1 -> "🥈"
                    2 -> "🥉"
                    else -> "${index + 1}."
                }
                
                val isCurrentUser = index == 3 // Simula posizione utente corrente
                
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = if(isCurrentUser) {
                        CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    } else {
                        CardDefaults.cardColors()
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = medal,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.secondary)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if(isCurrentUser) "Tu" else "Utente ${index + 1}",
                                fontWeight = if(isCurrentUser) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                        
                        Text(
                            text = "${2500 - (index * 100)} pts",
                            fontWeight = if(isCurrentUser) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LeaderboardScreenPreview() {
    OwlyHootTheme {
        LeaderboardScreen(rememberNavController())
    }
}
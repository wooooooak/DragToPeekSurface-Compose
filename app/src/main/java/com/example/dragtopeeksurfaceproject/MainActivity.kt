package com.example.dragtopeeksurfaceproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.dragtopeeksurfaceproject.ui.DragToPeekSurfaceProjectTheme
import com.example.dragtopeeksurfaceproject.ui.yellow_principle

class MainActivity : AppCompatActivity() {

    private val cardModifier = Modifier
        .padding(30.dp)
        .clip(RoundedCornerShape(16.dp))
        .fillMaxWidth()
        .height(160.dp)
        .background(yellow_principle)
        .padding(20.dp)

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DragToPeekSurfaceProjectTheme {
                Surface {
                    Column(modifier = cardModifier) {
                        Description()
                        DragToPeekSurface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 36.dp, end = 36.dp, top = 30.dp)
                                .height(40.dp),
                            backgroundContent = { BackgroundContent() },
                            coverContent = { CoverContent() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Description() {
    androidx.compose.material.Text("Drag to see the amount", fontSize = TextUnit.Sp(13), color = Color.Black)
}

@Composable
private fun BackgroundContent() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        androidx.compose.material.Text(
            text = "1,234,567$",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = TextUnit.Sp(22)
        )
    }
}

@Composable
private fun CoverContent() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        androidx.compose.material.Text(
            text = "See Amount",
            color = Color.Gray,
            textAlign = TextAlign.Center,
            fontSize = TextUnit.Sp(16)
        )
    }
}
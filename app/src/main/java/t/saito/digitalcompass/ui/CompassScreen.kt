package t.saito.digitalcompass.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import t.saito.digitalcompass.ui.theme.DigitalCompassTheme
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CompassScreen(
    angle: Float = 0f,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Angle display
        Text(
            text = "${angle.toInt()}° ${getCardinalDirection(angle)}",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        
        // Compass with needle and directions
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(300.dp)
        ) {
            // Compass circle with cardinal directions
            CompassCircle()
            
            // Compass needle
            CompassNeedle(
                rotation = angle,
                modifier = Modifier.size(200.dp)
            )
        }
        
        // Cardinal direction explanation
        Text(
            text = "北が赤、南が青",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 24.dp)
        )
    }
}

@Composable
fun CompassCircle(
    modifier: Modifier = Modifier
) {
    val onSurfaceColor = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant
    
    Canvas(
        modifier = modifier.size(300.dp)
    ) {
        val center = Offset(size.width / 2f, size.height / 2f)
        val radius = size.minDimension / 2f * 0.9f
        
        // Draw outer circle
        drawCircle(
            color = onSurfaceVariant,
            radius = radius,
            center = center,
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 3.dp.toPx())
        )
        
        // Draw cardinal directions
        drawCardinalDirections(
            center = center,
            radius = radius,
            color = onSurfaceColor
        )
        
        // Draw degree markings
        drawDegreeMarkings(
            center = center,
            radius = radius,
            color = onSurfaceVariant
        )
    }
}

private fun DrawScope.drawCardinalDirections(
    center: Offset,
    radius: Float,
    color: androidx.compose.ui.graphics.Color
) {
    val textSize = 24.sp.toPx()
    val directions = listOf(
        0f to "N",
        90f to "E", 
        180f to "S",
        270f to "W"
    )
    
    directions.forEach { (angle, text) ->
        val radian = Math.toRadians(angle.toDouble())
        val textRadius = radius - 30.dp.toPx()
        
        val x = center.x + cos(radian - Math.PI / 2).toFloat() * textRadius
        val y = center.y + sin(radian - Math.PI / 2).toFloat() * textRadius
        
        drawContext.canvas.nativeCanvas.drawText(
            text,
            x,
            y + textSize / 3,
            android.graphics.Paint().apply {
                this.color = color.value.toInt()
                this.textSize = textSize
                this.textAlign = android.graphics.Paint.Align.CENTER
                this.isAntiAlias = true
                this.typeface = android.graphics.Typeface.DEFAULT_BOLD
            }
        )
    }
}

private fun DrawScope.drawDegreeMarkings(
    center: Offset,
    radius: Float,
    color: androidx.compose.ui.graphics.Color
) {
    // Draw major markings every 30 degrees
    for (i in 0 until 12) {
        val angle = i * 30f
        val radian = Math.toRadians(angle.toDouble())
        
        val startRadius = radius - 20.dp.toPx()
        val endRadius = radius - 5.dp.toPx()
        
        val startX = center.x + cos(radian - Math.PI / 2).toFloat() * startRadius
        val startY = center.y + sin(radian - Math.PI / 2).toFloat() * startRadius
        val endX = center.x + cos(radian - Math.PI / 2).toFloat() * endRadius
        val endY = center.y + sin(radian - Math.PI / 2).toFloat() * endRadius
        
        drawLine(
            color = color,
            start = Offset(startX, startY),
            end = Offset(endX, endY),
            strokeWidth = 2.dp.toPx()
        )
    }
}

private fun getCardinalDirection(angle: Float): String {
    val normalizedAngle = ((angle % 360) + 360) % 360
    return when {
        normalizedAngle < 22.5 || normalizedAngle >= 337.5 -> "N"
        normalizedAngle < 67.5 -> "NE"
        normalizedAngle < 112.5 -> "E"
        normalizedAngle < 157.5 -> "SE"
        normalizedAngle < 202.5 -> "S"
        normalizedAngle < 247.5 -> "SW"
        normalizedAngle < 292.5 -> "W"
        normalizedAngle < 337.5 -> "NW"
        else -> "N"
    }
}

@Preview(showBackground = true)
@Composable
fun CompassScreenPreview() {
    DigitalCompassTheme {
        CompassScreen(angle = 0f)
    }
}

@Preview(showBackground = true)
@Composable
fun CompassScreenRotatedPreview() {
    DigitalCompassTheme {
        CompassScreen(angle = 75f)
    }
}

@Preview(showBackground = true)
@Composable
fun CompassScreenSouthPreview() {
    DigitalCompassTheme {
        CompassScreen(angle = 180f)
    }
}
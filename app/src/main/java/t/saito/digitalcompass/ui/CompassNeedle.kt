package t.saito.digitalcompass.ui

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import t.saito.digitalcompass.ui.theme.DigitalCompassTheme
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CompassNeedle(
    rotation: Float = 0f,
    modifier: Modifier = Modifier
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val errorColor = MaterialTheme.colorScheme.error
    
    // Store the previous rotation to calculate shortest path
    var previousRotation by remember { mutableFloatStateOf(rotation) }
    
    // Calculate the target rotation using shortest path logic
    val targetRotation = remember(rotation) {
        calculateShortestRotation(previousRotation, rotation).also {
            previousRotation = it
        }
    }
    
    // Animate the rotation with spring animation
    val animatedRotation by animateFloatAsState(
        targetValue = targetRotation,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "compass_needle_rotation"
    )
    
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.size(200.dp)
        ) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val radius = size.minDimension / 2f * 0.8f
            
            rotate(degrees = animatedRotation, pivot = center) {
                // Draw compass needle
                drawCompassNeedle(
                    center = center,
                    radius = radius,
                    northColor = errorColor,
                    southColor = primaryColor
                )
            }
            
            // Draw center dot
            drawCircle(
                color = primaryColor,
                radius = 8.dp.toPx(),
                center = center
            )
        }
    }
}

private fun DrawScope.drawCompassNeedle(
    center: Offset,
    radius: Float,
    northColor: Color,
    southColor: Color
) {
    val needleWidth = 6.dp.toPx()
    val northLength = radius * 0.7f
    val southLength = radius * 0.5f
    
    // North pointer (red)
    val northEnd = Offset(
        center.x,
        center.y - northLength
    )
    val northLeft = Offset(
        center.x - needleWidth / 2,
        center.y
    )
    val northRight = Offset(
        center.x + needleWidth / 2,
        center.y
    )
    
    drawTriangle(
        point1 = northEnd,
        point2 = northLeft,
        point3 = northRight,
        color = northColor
    )
    
    // South pointer (blue/primary)
    val southEnd = Offset(
        center.x,
        center.y + southLength
    )
    val southLeft = Offset(
        center.x - needleWidth / 2,
        center.y
    )
    val southRight = Offset(
        center.x + needleWidth / 2,
        center.y
    )
    
    drawTriangle(
        point1 = southEnd,
        point2 = southLeft,
        point3 = southRight,
        color = southColor
    )
}

private fun DrawScope.drawTriangle(
    point1: Offset,
    point2: Offset,
    point3: Offset,
    color: Color
) {
    val path = androidx.compose.ui.graphics.Path().apply {
        moveTo(point1.x, point1.y)
        lineTo(point2.x, point2.y)
        lineTo(point3.x, point3.y)
        close()
    }
    drawPath(path = path, color = color)
}

@Preview(showBackground = true)
@Composable
fun CompassNeedlePreview() {
    DigitalCompassTheme {
        CompassNeedle(rotation = 0f)
    }
}

@Preview(showBackground = true)
@Composable
fun CompassNeedleRotatedPreview() {
    DigitalCompassTheme {
        CompassNeedle(rotation = 45f)
    }
}

/**
 * Calculate the shortest rotation path between two angles.
 * This ensures that the needle rotates in the most natural direction
 * and handles the 360-degree boundary correctly.
 *
 * @param from Current rotation angle
 * @param to Target rotation angle
 * @return The target rotation angle adjusted for shortest path
 */
private fun calculateShortestRotation(from: Float, to: Float): Float {
    // Normalize both angles to 0-360 range
    val normalizedFrom = ((from % 360) + 360) % 360
    val normalizedTo = ((to % 360) + 360) % 360
    
    // Calculate the difference
    var diff = normalizedTo - normalizedFrom
    
    // If the difference is more than 180 degrees, go the other way
    if (diff > 180) {
        diff -= 360
    } else if (diff < -180) {
        diff += 360
    }
    
    // Return the adjusted target rotation
    return from + diff
}
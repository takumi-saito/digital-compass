package t.saito.digitalcompass

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import t.saito.digitalcompass.ui.CompassScreen
import t.saito.digitalcompass.ui.theme.DigitalCompassTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DigitalCompassTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CompassScreen(
                        angle = 75f, // Demo angle for now
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CompassPreview() {
    DigitalCompassTheme {
        CompassScreen(angle = 75f)
    }
}
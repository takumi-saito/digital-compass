package t.saito.digitalcompass

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import t.saito.digitalcompass.ui.CompassScreen
import t.saito.digitalcompass.ui.theme.DigitalCompassTheme
import t.saito.digitalcompass.viewmodel.CompassViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DigitalCompassTheme {
                CompassApp()
            }
        }
    }
}

@Composable
fun CompassApp(
    compassViewModel: CompassViewModel = viewModel()
) {
    val context = LocalContext.current
    val azimuth by compassViewModel.azimuth.collectAsState()
    
    // Initialize sensor and manage lifecycle
    DisposableEffect(context) {
        compassViewModel.initializeSensor(context)
        compassViewModel.startListening()
        
        onDispose {
            compassViewModel.stopListening()
        }
    }
    
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        CompassScreen(
            angle = azimuth,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CompassPreview() {
    DigitalCompassTheme {
        CompassScreen(angle = 75f)
    }
}
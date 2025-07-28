package t.saito.digitalcompass.viewmodel

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.atan2

class CompassViewModel : ViewModel(), SensorEventListener {
    
    private var sensorManager: SensorManager? = null
    private var rotationVectorSensor: Sensor? = null
    private var isListening = false
    
    private val _azimuth = MutableStateFlow(0f)
    val azimuth: StateFlow<Float> = _azimuth.asStateFlow()
    
    private val _isSensorAvailable = MutableStateFlow(false)
    val isSensorAvailable: StateFlow<Boolean> = _isSensorAvailable.asStateFlow()
    
    private val rotationMatrix = FloatArray(9)
    private val orientationAngles = FloatArray(3)
    
    fun initializeSensor(context: Context) {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        rotationVectorSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
        
        _isSensorAvailable.value = rotationVectorSensor != null
    }
    
    fun startListening() {
        if (!isListening && rotationVectorSensor != null) {
            sensorManager?.registerListener(
                this,
                rotationVectorSensor,
                SensorManager.SENSOR_DELAY_UI
            )
            isListening = true
        }
    }
    
    fun stopListening() {
        if (isListening) {
            sensorManager?.unregisterListener(this)
            isListening = false
        }
    }
    
    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ROTATION_VECTOR) {
            // Convert rotation vector to rotation matrix
            SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
            
            // Get orientation from rotation matrix
            SensorManager.getOrientation(rotationMatrix, orientationAngles)
            
            // Convert azimuth from radians to degrees
            var azimuthDegrees = Math.toDegrees(orientationAngles[0].toDouble()).toFloat()
            
            // Normalize to 0-359 degrees
            if (azimuthDegrees < 0) {
                azimuthDegrees += 360f
            }
            
            _azimuth.value = azimuthDegrees
        }
    }
    
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Handle accuracy changes if needed
        // For now, we don't need to do anything special
    }
    
    override fun onCleared() {
        super.onCleared()
        stopListening()
    }
}
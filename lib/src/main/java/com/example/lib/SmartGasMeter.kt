package com.example.lib
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
class SmartGasMeter(
    val gasConsumption: Double,//m3
    val flowRate: Double,//m3/h , hitrost s katero plin prehaja števec
    val relativeReading: Double,//poraba od zadnjega odčitavanja  ->m3
    val absoluteReading: Double,//m3
    val serialNumber: String,
    val meterType: String,//"mechanical", "smart"
    val manufacturer: String,
    val operatingTime: Int,
    val batteryStatus: Int,
    val remoteReadingEnabled: Boolean,
    val location: String,
    val coordinates: Pair<Double, Double>,
    var id:String = UUID.randomUUID().toString()
): Parcelable {
    override fun toString(): String {
        return """
            SmartMeter(
                ID: $id
                Serial Number: $serialNumber
                Manufacturer: $manufacturer
                Type: $meterType
                Operating Time: $operatingTime hours
                Battery Status: $batteryStatus%
                Remote Reading Enabled: $remoteReadingEnabled
                
                Location: $location
                Coordinates: Latitude ${coordinates.first}, Longitude ${coordinates.second}
                
                Gas Consumption: $gasConsumption m³
                Flow Rate: $flowRate m³/h
                Relative Reading: $relativeReading m³
                Absolute Reading: $absoluteReading m³
                
            )
        """.trimIndent()
    }
}
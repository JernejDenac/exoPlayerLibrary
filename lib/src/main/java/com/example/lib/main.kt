package com.example.lib

fun main() {
    val smartMeter = SmartGasMeter(
        gasConsumption = 125.4,          // Skupna poraba plina (m³)
        flowRate = 3.2,                  // Trenutni pretok plina (m³/h)
        relativeReading = 10.8,          // Poraba od zadnjega odčitavanja (m³)
        absoluteReading = 1200.5,
        serialNumber = "SM123456789",    // Serijska številka
        meterType = "smart",             // Tip števca
        manufacturer = "GasTech Inc.",   // Proizvajalec
        operatingTime = 8760,            // Obratovalni čas (v urah)
        batteryStatus = 85,              // Stanje baterije (v %)
        remoteReadingEnabled = true,      // Ali omogoča daljinsko branje
        location = "Downtown City Center",
        coordinates = Pair(46.0569, 14.5058)
    )
    println(smartMeter)

}

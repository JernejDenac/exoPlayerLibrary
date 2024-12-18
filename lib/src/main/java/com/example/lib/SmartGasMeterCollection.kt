package com.example.lib

import java.util.UUID

class SmartGasMeterCollection(
    val collection: MutableList<SmartGasMeter>,
    var id:String = UUID.randomUUID().toString().replace("-", "")
) {
}
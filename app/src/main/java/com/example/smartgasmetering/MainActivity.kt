package com.example.smartgasmetering

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
import android.graphics.Typeface
import android.location.GpsStatus
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lib.SmartGasMeter
import com.example.lib.SmartGasMeterCollection
import com.example.smartgasmetering.databinding.ActivityMainBinding
import org.osmdroid.api.IMapController
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import kotlin.random.Random
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import android.widget.Button
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import org.osmdroid.views.overlay.Marker
import java.util.Locale


class MainActivity : AppCompatActivity(), MapListener  {
    private lateinit var binding: ActivityMainBinding
    private val smartGasMeterCollection = SmartGasMeterCollection(mutableListOf())
    private lateinit var adapter: CustomAdapter
    lateinit var mMap: MapView
    lateinit var controller: IMapController;
    lateinit var mMyLocationOverlay: MyLocationNewOverlay;

    private val scrollHandler = Handler(Looper.getMainLooper())
    private val zoomHandler = Handler(Looper.getMainLooper())

    private var scrollRunnable: Runnable? = null
    private var zoomRunnable: Runnable? = null



    private lateinit var drawerLayout: DrawerLayout
    private lateinit var infoTitle: TextView
    private lateinit var infoContent: TextView
    private lateinit var closeDrawerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        for (i in 1..100) {
            smartGasMeterCollection.collection.add(generateRandomSmartMeter())
        }
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                1 // Koda za zahtevo dovoljenja
            )
        } else {
            // Lokacija je že dovoljena -> inicializiraj zemljevid
            initializeMap()
        }

        Configuration.getInstance().load(
            applicationContext,
            getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
        )

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        drawerLayout = binding.drawerLayout
        infoTitle = binding.meterInfoTitle

        closeDrawerButton = binding.closeDrawerButton

        closeDrawerButton.setOnClickListener {
            drawerLayout.closeDrawer(binding.infoDrawer)
        }


        binding.buttonAddMeter.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            //addActivityLauncher.launch(intent)
        }

        binding.drawerRefreshDataButton.setOnClickListener{// TO DO
            refreshData()
        }



        val toolbar: androidx.appcompat.widget.Toolbar = binding.toolbarMainActivity
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""






    }

    private fun refreshData() {
        TODO("Not yet implemented")
    }

    private fun initializeMap() {
        mMap = binding.osmmap
        mMap.setTileSource(TileSourceFactory.MAPNIK)
        mMap.setMultiTouchControls(true)
        mMap.getLocalVisibleRect(Rect())

        controller = mMap.controller

        // Privzeta lokacija na center Slovenije
        val sloveniaCenter = GeoPoint(46.1512, 14.9955)
        controller.setCenter(sloveniaCenter)
        controller.setZoom(9.0)

        mMyLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(this), mMap)
        mMyLocationOverlay.enableMyLocation()
        mMyLocationOverlay.enableFollowLocation()
        mMyLocationOverlay.isDrawAccuracyEnabled = true

        mMyLocationOverlay.runOnFirstFix {
            runOnUiThread {
                val currentLocation = mMyLocationOverlay.myLocation
                if (currentLocation != null) {
                    // GPS lokacija
                    controller.setCenter(currentLocation)
                    controller.animateTo(currentLocation)
                } else {
                    Log.e("GPS", "GPS lokacija ni na voljo, prikazujem center Slovenije.")
                }
            }
        }

        mMap.overlays.add(mMyLocationOverlay)
        mMap.addMapListener(this)

        addSmartMeterMarkers()
    }

    override fun onScroll(event: ScrollEvent?): Boolean {
        // Debounce za onScroll
        scrollRunnable?.let { scrollHandler.removeCallbacks(it) }
        scrollRunnable = Runnable {
            val latitude = event?.source?.mapCenter?.latitude
            val longitude = event?.source?.mapCenter?.longitude
            Log.e("TAG", "onScroll - Lat: $latitude, Lon: $longitude")
        }
        scrollHandler.postDelayed(scrollRunnable!!, 300) // 300 ms debounce

        return true
    }

    override fun onZoom(event: ZoomEvent?): Boolean {
        // Debounce za onZoom
        zoomRunnable?.let { zoomHandler.removeCallbacks(it) }
        zoomRunnable = Runnable {
            val zoomLevel = event?.zoomLevel
            Log.e("TAG", "onZoom - Zoom Level: $zoomLevel")
        }
        zoomHandler.postDelayed(zoomRunnable!!, 300) // 300 ms debounce

        return false
    }



    fun generateRandomSmartMeter(): SmartGasMeter {
        return SmartGasMeter(
            gasConsumption = Random.nextDouble(0.0, 5000.0), // Skupna poraba med 0 in 5000 m³
            flowRate = Random.nextDouble(0.1, 50.0),         // Pretok plina med 0.1 in 50 m³/h
            relativeReading = Random.nextDouble(0.0, 100.0), // Poraba od zadnjega odčitavanja med 0 in 100 m³
            absoluteReading = Random.nextDouble(0.0, 5000.0), // Absolutna poraba med 0 in 5000 m³
            serialNumber = "SM" + Random.nextInt(100000, 999999), // Naključna serijska številka
            meterType = if (Random.nextBoolean()) "smart" else "mechanical", // Smart ali mehanski števec
            manufacturer = listOf("GasTech Inc.", "PlinoTech", "SmartGas Co.").random(), // Naključni proizvajalec
            operatingTime = Random.nextInt(0, 100000),       // Obratovalni čas med 0 in 100000 ur
            batteryStatus = Random.nextInt(0, 100),          // Stanje baterije med 0% in 100%
            remoteReadingEnabled = Random.nextBoolean(),     // Ali omogoča daljinsko branje
            location = listOf("Downtown City Center", "Residential Area", "Industrial Zone", "Rural Farm").random(), // Naključna lokacija
            coordinates = Pair(
                Random.nextDouble(45.42, 46.88),  // Latitude omejen na Slovenijo
                Random.nextDouble(13.38, 16.60)   // Longitude omejen na Slovenijo
            )

        )
    }


    private fun addSmartMeterMarkers() {
        for (meter in smartGasMeterCollection.collection) {
            val marker = Marker(mMap)
            val (latitude, longitude) = meter.coordinates
            val position = GeoPoint(latitude, longitude)
            marker.position = position




            marker.icon = ContextCompat.getDrawable(this, R.drawable.meter)
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

            marker.setOnMarkerClickListener { _, _ ->
                binding.meterInfoTitle.text = getString(R.string.info_title)
                binding.meterInfoValueTitle.text = meter.serialNumber

                binding.infoLabelManufacturer.text = getString(R.string.info_manufacturer)
                binding.infoValueManufacturer.text = meter.manufacturer

                binding.infoLabelType.text = getString(R.string.info_type)
                binding.infoValueType.text = meter.meterType

                binding.infoLabelGasConsumption.text = getString(R.string.info_gas_consumption)
                binding.infoValueGasConsumption.text = String.format(Locale.US,"%.2f m³", meter.gasConsumption)

                binding.infoLabelFlowRate.text = getString(R.string.info_flow_rate)
                binding.infoValueFlowRate.text = String.format(Locale.US,"%.2f m³/h", meter.flowRate)

                binding.infoLabelRelativeReading.text = getString(R.string.info_relative_reading)
                binding.infoValueRelativeReading.text = String.format(Locale.US,"%.2f m³", meter.relativeReading)

                binding.infoLabelAbsoluteReading.text = getString(R.string.info_absolute_reading)
                binding.infoValueAbsoluteReading.text = String.format(Locale.US,"%.2f m³", meter.absoluteReading)

                binding.infoLabelOperatingTime.text = getString(R.string.info_operating_time)
                binding.infoValueOperatingTime.text = String.format(Locale.US, "%d ur", meter.operatingTime)

                binding.infoLabelBatteryStatus.text = getString(R.string.battery_status)
                binding.infoValueBatteryStatus.text = String.format(Locale.US, "%d%%", meter.batteryStatus)

                binding.infoLabelRemoteReading.text = getString(R.string.info_remote_reading_enabled)
                binding.infoValueRemoteReading.text = if (meter.remoteReadingEnabled) {
                    getString(R.string.info_remote_reading_enabled_value)
                } else {
                    getString(R.string.info_remote_reading_disabled_value)
                }

                binding.infoLabelLocation.text = getString(R.string.info_location)
                binding.infoValueLocation.text = meter.location

                binding.infoLabelCoordinates.text = getString(R.string.info_coordinates)
                val formattedCoordinates = String.format(Locale.US, "%.4f, %.4f", meter.coordinates.first, meter.coordinates.second)
                binding.infoValueCoordinates.text = formattedCoordinates


                binding.drawerLayout.openDrawer(binding.infoDrawer)
                true
            }
            mMap.overlays.add(marker)
        }
        mMap.invalidate() // Osveži zemljevid, da prikaže markerje
    }

}
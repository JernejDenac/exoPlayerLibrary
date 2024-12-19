package com.example.smartgasmetering

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lib.SmartGasMeter
import com.example.smartgasmetering.databinding.ActivitySmartGasMeterListBinding

class SmartGasMeterListActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySmartGasMeterListBinding
    private lateinit var adapter: CustomAdapter
    private lateinit var smartGasMeters: MutableList<SmartGasMeter>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySmartGasMeterListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Toolbar
        val toolbar: androidx.appcompat.widget.Toolbar = binding.toolbarGasMeterListActivity
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        //Prejem podatkov
        smartGasMeters =
            (intent.extras?.getParcelableArrayList<SmartGasMeter>("SMART_GAS_METERS")
                ?: mutableListOf()).toMutableList()

        if (smartGasMeters.isNotEmpty()) {
            println("Prejeti plinomeri: $smartGasMeters")
        } else {
            Toast.makeText(this, "Ni bilo mogoče pridobiti podatkov!", Toast.LENGTH_SHORT).show()
        }


        val recyclerView = binding.itemList
        recyclerView.layoutManager = LinearLayoutManager(this)


        //POsodabljanje ali brisanje
        adapter = CustomAdapter(smartGasMeters, onItemClicked = { smartGasMeter, position ->
            val intent = Intent(this, AddActivity::class.java)
            intent.putExtra("serialNumber", smartGasMeter.serialNumber)
            intent.putExtra("manufacturer", smartGasMeter.manufacturer)
            intent.putExtra("relativeReading", smartGasMeter.relativeReading)
            intent.putExtra("absoluteReading", smartGasMeter.absoluteReading)
            intent.putExtra("battery", smartGasMeter.batteryStatus)
            intent.putExtra("location", smartGasMeter.location)
            intent.putExtra("position", position)
            addActivityResult.launch(intent)
        },
            onItemLongClicked = { smartGasMeter, position ->

                DeleteConfirmationDialogFragment {
                    smartGasMeters.removeAt(position)
                    adapter.notifyItemRemoved(position)
                    Toast.makeText(this, "Delivery deleted", Toast.LENGTH_SHORT).show()
                }.apply {
                    arguments = Bundle().apply {//Serial number se prenese v dialog
                        putString("serialNumber", smartGasMeter.serialNumber)
                    }
                }.show(supportFragmentManager, "DELETE_CONFIRMATION_DIALOG")
            })

        recyclerView.adapter = adapter


    }//on create end


    private val addActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val bundle = result.data?.extras
                val serialNumber = bundle?.getString("serialNumber") ?: ""
                val manufacturer = bundle?.getString("manufacturer") ?: ""
                val relativeReading = bundle?.getDouble("relativeReading", 0.0) ?: 0.0
                val absoluteReading = bundle?.getDouble("absoluteReading", 0.0) ?: 0.0
                val battery = bundle?.getInt("battery", 0) ?: 0
                val location = bundle?.getString("location") ?: ""
                val position = bundle?.getInt("position", -1) ?: -1


                if (position >= 0) {
                    // Posodobi obstoječi element
                    smartGasMeters[position] = SmartGasMeter(
                        gasConsumption = smartGasMeters[position].gasConsumption,
                        flowRate = smartGasMeters[position].flowRate,
                        relativeReading = relativeReading,
                        absoluteReading = absoluteReading,
                        serialNumber = serialNumber,
                        meterType = smartGasMeters[position].meterType,
                        manufacturer = manufacturer,
                        operatingTime = smartGasMeters[position].operatingTime,
                        batteryStatus = battery,
                        remoteReadingEnabled = smartGasMeters[position].remoteReadingEnabled,
                        location = location,
                        coordinates = smartGasMeters[position].coordinates
                    )
                    adapter.notifyItemChanged(position)
                    Toast.makeText(this, "Delivery updated", Toast.LENGTH_SHORT).show()
                } else {//Dodajane elementa na začetek

                    val newMeter = SmartGasMeter(
                        gasConsumption = 0.0,
                        flowRate = 0.0,
                        relativeReading = relativeReading,
                        absoluteReading = absoluteReading,
                        serialNumber = serialNumber,
                        meterType = "smart",
                        manufacturer = manufacturer,
                        operatingTime = 0,
                        batteryStatus = battery,
                        remoteReadingEnabled = true,
                        location = location,
                        coordinates = Pair(46.1512, 14.9955)
                    )
                    smartGasMeters.add(0, newMeter)
                    adapter.notifyItemInserted(0)
                    Toast.makeText(this, "New meter added", Toast.LENGTH_SHORT).show()

                }
            }
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val resultIntent = Intent()
                resultIntent.putParcelableArrayListExtra(
                    "UPDATED_METERS",
                    ArrayList(smartGasMeters)
                )
                setResult(Activity.RESULT_OK, resultIntent)

                onBackPressedDispatcher.onBackPressed()//klic na finish() izvede ob kliku na gumb za nazaj, kar zapre aktivnost.
                true
            }

            R.id.action_add -> {
                val intent = Intent(this, AddActivity::class.java)
                addActivityResult.launch(intent)
                true
            }

            else ->
                super.onOptionsItemSelected(item)

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {//Pretvori XML datoteko menija v  UI elemente na toolbaru
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


}
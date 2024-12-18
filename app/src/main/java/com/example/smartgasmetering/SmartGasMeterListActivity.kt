package com.example.smartgasmetering

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lib.SmartGasMeter
import com.example.smartgasmetering.databinding.ActivitySmartGasMeterListBinding

class SmartGasMeterListActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySmartGasMeterListBinding
    private lateinit var adapter: CustomAdapter


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
        val smartGasMeters: MutableList<SmartGasMeter> =
            (intent.extras?.getParcelableArrayList<SmartGasMeter>("SMART_GAS_METERS")
                ?: mutableListOf()).toMutableList()

        if (smartGasMeters.isNotEmpty()) {
            println("Prejeti plinomeri: $smartGasMeters")
        } else {
            Toast.makeText(this, "Ni bilo mogoče pridobiti podatkov!", Toast.LENGTH_SHORT).show()
        }


        val recyclerView = binding.itemList
        recyclerView.layoutManager = LinearLayoutManager(this)


        adapter = CustomAdapter(smartGasMeters, onItemClicked = { smartGasMeter, position ->
            // Dodaj logiko za klik na element
            Toast.makeText(this, "Kliknjen: ${smartGasMeter.serialNumber}", Toast.LENGTH_SHORT)
                .show()
        },
            onItemLongClicked = { smartGasMeter, position ->
                // Dodaj logiko za dolg klik na element
                Toast.makeText(
                    this,
                    "Dolg klik na: ${smartGasMeter.serialNumber}",
                    Toast.LENGTH_SHORT
                ).show()
            })

        recyclerView.adapter = adapter

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()//klic na finish() izvede ob kliku na gumb za nazaj, kar zapre aktivnost.
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}
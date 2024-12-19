package com.example.smartgasmetering

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.smartgasmetering.databinding.ActivityAddBinding
import java.util.Locale

class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val toolbar: androidx.appcompat.widget.Toolbar = binding.toolbarAddActivity
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""



        val position = intent.getIntExtra("position", -1)

        // Če position enak -1 , gre za dodajanje, drugače za posodabljanjee
        if (position != -1) {
            val serialNumber = intent.getStringExtra("serialNumber") ?: ""
            val manufacturer = intent.getStringExtra("manufacturer") ?: ""
            val relativeReading = intent.getDoubleExtra("relativeReading", 0.0)
            val absoluteReading = intent.getDoubleExtra("absoluteReading", 0.0)
            val battery = intent.getIntExtra("battery", 0)
            val location = intent.getStringExtra("location") ?: ""

            binding.serialNumberInput.setText(serialNumber)
            binding.manufacturerInput.setText(manufacturer)
            binding.relativeReadingInput.setText(String.format(Locale.US, "%.2f", relativeReading))
            binding.absoluteReadingInput.setText(String.format(Locale.US, "%.2f", absoluteReading))
            binding.batteryStatusInput.setText(String.format(Locale.US, "%d", battery))
            binding.locationInput.setText(location)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        menu?.findItem(R.id.action_check)?.isVisible = true
        menu?.findItem(R.id.action_add)?.isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()//klic na finish() izvede ob kliku na gumb za nazaj, kar zapre aktivnost.
                true
            }

            R.id.action_check -> {
                val serialNumber = binding.serialNumberInput.text.toString()
                val relative = binding.relativeReadingInput.text.toString()
                val absolute = binding.absoluteReadingInput.text.toString()
                val location = binding.locationInput.text.toString()
                val manufacturer = binding.manufacturerInput.text.toString()
                val battery = binding.batteryStatusInput.text.toString()


                if (serialNumber.isEmpty() || relative.isEmpty() || absolute.isEmpty() ||
                    location.isEmpty() || manufacturer.isEmpty() || battery.isEmpty()
                ) {
                    if (serialNumber.isEmpty()) {
                        Toast.makeText(
                            this,
                            "Serial number field is empty",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                    if (relative.isEmpty()) {
                        Toast.makeText(
                            this,
                            "Relative reading field is empty",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                    if (absolute.isEmpty()) {
                        Toast.makeText(
                            this,
                            "Absolute reading field is empty",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                    if (location.isEmpty()) {
                        Toast.makeText(
                            this,
                            "Location field is empty",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                    if (manufacturer.isEmpty()) {
                        Toast.makeText(
                            this,
                            "Manufacturer field is empty",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                    if (battery.isEmpty()) {
                        Toast.makeText(
                            this,
                            "Battery field is empty",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }

                } else {
                    val bundle = Bundle()

                    val position = intent.getIntExtra("position", -1)

                    bundle.putInt("position", position)
                    bundle.putString("serialNumber", serialNumber)
                    bundle.putString("manufacturer", manufacturer)
                    bundle.putDouble("relativeReading", relative.toDouble())
                    bundle.putDouble("absoluteReading", absolute.toDouble())
                    bundle.putInt("battery", battery.toInt())
                    bundle.putString("location", location)

                    val resultIntent = intent
                    resultIntent.putExtras(bundle)
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                }

                true
            }

            else ->
                super.onOptionsItemSelected(item)

        }
    }
}
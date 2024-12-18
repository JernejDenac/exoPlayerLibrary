package com.example.smartgasmetering

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.bold
import androidx.recyclerview.widget.RecyclerView

import com.example.lib.SmartGasMeter
import com.example.smartgasmetering.databinding.TextRowItemBinding
import java.util.Locale

class CustomAdapter(
    private val smartGasMeterList: MutableList<SmartGasMeter>,
    private val onItemClicked: (SmartGasMeter, Int) -> Unit,
    private val onItemLongClicked: (SmartGasMeter, Int) -> Unit
) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {


    class ViewHolder(binding: TextRowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {//vsak element v seznamu ima svoj ViewHolder

        val serialNumber = binding.textViewSerialNumber
        val manufacturer = binding.textViewManufacturer
        val relativeReading = binding.textViewRelativeReading
        val absoluteReading = binding.textViewAbsoluteReading
        val batteryStatus = binding.textViewBatteryStatus
        val location = binding.textViewLocation

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {//ustvari nov pogled

        val binding =
            TextRowItemBinding.inflate(//TextRowItemBinding.inflate->pretvorba xml v dejanski pogled
                LayoutInflater.from(parent.context),//from(parent.context)->LayoutInflater, ki je povezan z RecyclerView
                parent,
                false
            )
        return ViewHolder(binding)
    }//ViewHolder(binding)->ustvarim ViewHolder, ki bo imel dostop do vseh el. v text_row_item.xml, zaradi binding


    override fun onBindViewHolder(//kliče se samodejno vsakič, ko se mora prikazati nov element
        holder: ViewHolder,
        position: Int
    ) {
        val list = smartGasMeterList[position]


        val serialNumberText = SpannableStringBuilder()
            .bold { append(holder.itemView.context.getString(R.string.meter_serial_number)) }
            .append(" ")
            .append(String.format(Locale.US, "%s", list.serialNumber))

        holder.serialNumber.text = serialNumberText


        val manufacturerText = SpannableStringBuilder()
            .bold { append(holder.itemView.context.getString(R.string.manufacturer)) }
            .append(" ")
            .append(String.format(Locale.US, "%s", list.manufacturer))

        holder.manufacturer.text = manufacturerText



        val relativeReadingText = SpannableStringBuilder()
            .bold { append(holder.itemView.context.getString(R.string.relative_reading)) }
            .append(" ")
            .append(String.format(Locale.US, "%.2f m³", list.absoluteReading))

        holder.relativeReading.text = relativeReadingText


        val absoluteReadingText = SpannableStringBuilder()
            .bold { append(holder.itemView.context.getString(R.string.absolute_reading)) }
            .append(" ")
            .append(String.format(Locale.US, "%.2f m³", list.absoluteReading))

        holder.absoluteReading.text = absoluteReadingText


        val batteryStatusText = SpannableStringBuilder()
            .bold { append(holder.itemView.context.getString(R.string.battery_status)) }
            .append(" ")
            .append(String.format(Locale.US, "%d%%", list.batteryStatus))

        holder.batteryStatus.text = batteryStatusText


        val locationText = SpannableStringBuilder()
            .bold { append(holder.itemView.context.getString(R.string.info_location)) }
            .append(" ")
            .append(list.location)
        holder.location.text = locationText



        holder.itemView.setOnClickListener {
            onItemClicked(list, position)
        }
        holder.itemView.setOnLongClickListener {
            onItemLongClicked(list, position)
            true
        }
    }//Tukaj napolnimo ViewHolder z podatki, position RecyclerView sam pridobi



    override fun getItemCount() = smartGasMeterList.size
}
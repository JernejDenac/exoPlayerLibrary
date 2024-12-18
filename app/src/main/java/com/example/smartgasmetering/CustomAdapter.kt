package com.example.smartgasmetering

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.lib.SmartGasMeter
import com.example.smartgasmetering.databinding.TextRowItemBinding

class CustomAdapter(private val shipmentCollection: MutableList<SmartGasMeter>, private val onItemClicked: (SmartGasMeter, Int) -> Unit, private val onItemLongClicked: (SmartGasMeter, Int) -> Unit ) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {


    class ViewHolder(binding: TextRowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {//vsak element v seznamu ima svoj ViewHolder

//        val textViewDeliveryNumber = binding.textViewDeliveryNumber
//        val textViewType = binding.textViewType
//        val textViewOrigin = binding.textViewOrigin
//        val textViewFlavor = binding.textViewFlavor
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {//ustvari nov pogled

        val binding = TextRowItemBinding.inflate(//TextRowItemBinding.inflate->pretvorba xml v dejanski pogled
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
        val shipment = shipmentCollection[position]
//        holder.textViewDeliveryNumber.text = "Delivery Number: ${shipment.deliveryNumber}"
//        holder.textViewType.text = "Type: ${shipment.type}"
//        holder.textViewOrigin.text = "Origin: ${shipment.origin}"
//        holder.textViewFlavor.text = "Flavor: ${shipment.flavor}"

        holder.itemView.setOnClickListener {
            onItemClicked(shipment, position)
        }
        holder.itemView.setOnLongClickListener {
            onItemLongClicked(shipment, position)
            true
        }
    }//Tukaj napolnimo ViewHolder z podatki, position RecyclerView sam pridobi


    override fun getItemCount() = shipmentCollection.size
}
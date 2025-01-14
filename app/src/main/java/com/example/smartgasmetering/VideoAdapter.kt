import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smartgasmetering.databinding.ItemVideoBinding

class VideoAdapter(
    private val videoList: MutableList<Uri>,
    private val onItemClicked: (Uri) -> Unit
) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    // ViewHolder z uporabo View Binding
    class VideoViewHolder(private val binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(videoUri: Uri, position: Int, onItemClicked: (Uri) -> Unit) {
            val videoNameWithIndex = "${position + 1}. ${videoUri.lastPathSegment ?: "Unknown"}"
            binding.videoName.text = videoNameWithIndex
            binding.root.setOnClickListener {
                onItemClicked(videoUri)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = ItemVideoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val videoUri = videoList[position]
        holder.bind(videoUri, position, onItemClicked)
    }

    override fun getItemCount(): Int = videoList.size
}

package by.ssrlab.drukvkl.rv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.ssrlab.drukvkl.MainActivity
import by.ssrlab.drukvkl.R
import by.ssrlab.drukvkl.helpers.RV_EMPTY
import by.ssrlab.drukvkl.helpers.RV_MAIN
import by.ssrlab.drukvkl.helpers.RV_TITLE
import coil.load

class PlacesListAdapter(
    private val activity: MainActivity,
    private val action: () -> Unit
): RecyclerView.Adapter<PlacesListAdapter.PlacesHolder>() {

    private val city = activity.getVM().getCity()
    private val list = activity.getVM().getPlaces()

    inner class PlacesHolder(item: View): RecyclerView.ViewHolder(item)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            when (viewType) {
                RV_TITLE -> R.layout.rv_common_title
                RV_MAIN -> R.layout.rv_common_main
                RV_EMPTY -> R.layout.rv_common_empty
                else -> R.layout.rv_common_empty
            },
            parent,
            false
        )

        return PlacesHolder(itemView)
    }

    override fun onBindViewHolder(holder: PlacesHolder, position: Int) {
        val itemView = holder.itemView

        if (position == RV_TITLE) {

            val title = itemView.findViewById<TextView>(R.id.rv_title)
            title.text = city.name
        } else if (position < list.size + 1) {

            val place = list[position - 1]

            val title = itemView.findViewById<TextView>(R.id.rv_common_title)
            val item = itemView.findViewById<ImageButton>(R.id.rv_common_ripple)
            val image = itemView.findViewById<ImageView>(R.id.rv_common_logo)

            title.text = place.name
            item.setOnClickListener {
                activity.getVM().apply {
                    setPlace(place)

                    this.loadImagesList {
                        place.images = it
                        setPlace(place)

                        action()
                    }
                }
            }

            activity.getVM().getImageAddress("places/${city.id}", place.id.toInt()) {
                image.load(it) {
                    crossfade(true)
                    placeholder(R.drawable.background_img)
                }
            }
        }
    }

    override fun getItemCount(): Int = list.size + 2

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> RV_TITLE
            list.size + 1 -> RV_EMPTY
            else -> RV_MAIN
        }
    }
}
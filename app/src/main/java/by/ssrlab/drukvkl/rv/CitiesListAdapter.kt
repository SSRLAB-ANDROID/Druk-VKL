package by.ssrlab.drukvkl.rv

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.ssrlab.drukvkl.R
import by.ssrlab.drukvkl.client.FireClient
import by.ssrlab.drukvkl.db.City
import by.ssrlab.drukvkl.helpers.RV_EMPTY
import by.ssrlab.drukvkl.helpers.RV_MAIN
import by.ssrlab.drukvkl.helpers.RV_TITLE
import coil.load
import coil.size.Precision
import coil.size.Scale
import coil.transform.RoundedCornersTransformation

class CitiesListAdapter(
    private val list: ArrayList<City>,
    private val action: (Int) -> Unit
): RecyclerView.Adapter<CitiesListAdapter.CitiesHolder>() {

    inner class CitiesHolder(item: View): RecyclerView.ViewHolder(item)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            (when (viewType) {
                RV_TITLE -> R.layout.rv_common_title
                RV_MAIN -> R.layout.rv_common_main
                RV_EMPTY -> R.layout.rv_common_empty
                else -> R.layout.rv_common_empty
            }),
            parent,
            false
        )

        return CitiesHolder(itemView)
    }

    override fun getItemCount(): Int = list.size + 2

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CitiesHolder, position: Int) {
        val itemView = holder.itemView

        if (position == RV_TITLE) {

            val title = itemView.findViewById<TextView>(R.id.rv_title)
            title.text = "Cities"
        } else if (position < list.size + 1) {

            val city = list[position - 1]

            val title = itemView.findViewById<TextView>(R.id.rv_common_title)
            val image = itemView.findViewById<ImageView>(R.id.rv_common_logo)
            val item = itemView.findViewById<ImageButton>(R.id.rv_common_ripple)

            title.text = city.name
            item.setOnClickListener { action(city.id.toInt()) }

            FireClient().getImageAddress("cities", city.id.toInt()) {
                image.load(it) {
                    crossfade(true)
                    placeholder(R.drawable.background_img)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> RV_TITLE
            list.size + 1 -> RV_EMPTY
            else -> RV_MAIN
        }
    }
}
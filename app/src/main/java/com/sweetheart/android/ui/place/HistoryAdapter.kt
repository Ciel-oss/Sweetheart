package com.sweetheart.android.ui.place

import android.app.AlertDialog
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.sweetheart.android.MainActivity
import com.sweetheart.android.R
import com.sweetheart.android.logic.dao.LocalPlace
import com.sweetheart.android.logic.model.Place
import com.sweetheart.android.ui.weather.WeatherActivity
import kotlinx.android.synthetic.main.activity_weather.*

class HistoryAdapter(private val fragment: PlaceFragment, private val placeList: List<LocalPlace>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val placeName: TextView = view.findViewById(R.id.placeName)
        val placeAddress: TextView = view.findViewById(R.id.placeAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        val holder = ViewHolder(view)
        //给history_item.xml的最外层布局注册一个长按事件监听器
        holder.itemView.setOnLongClickListener {
            if (placeList.size <= 1) {
                Toast.makeText(it.context, "只有一个城市不能删除", Toast.LENGTH_LONG).show()
            } else {
                val position = holder.adapterPosition
                val place = placeList[position]
                val activity = fragment.activity
                val dialog = AlertDialog.Builder(it.context)
                    .setTitle("提示")
                    .setMessage("是否删除" + place.name + "?")
                    .setNegativeButton(
                        "取消", null
                    )
                    .setPositiveButton(
                        "删除"
                    ) { dialog, _ ->
                        fragment.viewModel.deletePlace(place)
                        val places = fragment.viewModel.getSavedPlace()
                        if (position == 0) {
                            if (activity is WeatherActivity) {
                                activity.viewModel.locationLng = places[0].lng!!
                                activity.viewModel.locationLat = places[0].lat!!
                                activity.viewModel.placeName = places[0].name!!
                                activity.refreshWeather()
                            }
                        }
                        dialog.dismiss()
                        fragment.updateList()

                    }
                    .create()
                dialog.show()
            }

            false
        }

        //给history_item.xml的最外层布局注册一个点击事件监听器
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val place = placeList[position]
            val activity = fragment.activity
            if (activity is WeatherActivity) {
                activity.drawerLayout.closeDrawers()
                activity.viewModel.locationLng = place.lng!!
                activity.viewModel.locationLat = place.lat!!
                activity.viewModel.placeName = place.name!!
                activity.refreshWeather()
            } else if (activity is MainActivity) {
                val intent = Intent(parent.context, WeatherActivity::class.java).apply {
                    putExtra("location_lng", place.lng)
                    putExtra("location_lat", place.lat)
                    putExtra("place_name", place.name)
                }
                fragment.startActivity(intent)
                activity.finish()
            }
            fragment.viewModel.savePlace(place)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = placeList[position]
        holder.placeName.text = place.name
        holder.placeAddress.text = place.address
    }

    override fun getItemCount(): Int {
        return placeList.size
    }

}
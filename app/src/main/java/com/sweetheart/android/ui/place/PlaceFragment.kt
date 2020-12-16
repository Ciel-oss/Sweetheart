package com.sweetheart.android.ui.place

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sweetheart.android.MainActivity
import com.sweetheart.android.R
import com.sweetheart.android.ui.weather.WeatherActivity
import kotlinx.android.synthetic.main.fragment_place.*

class PlaceFragment : Fragment() {

    val viewModel by lazy { ViewModelProvider(this).get(PlaceViewModel::class.java) }

    private lateinit var adapter: PlaceAdapter
    private lateinit var historyAdapter: HistoryAdapter
    private val TAG = "jcy-PlaceFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val places = viewModel.getSavedPlace()
        if (activity is MainActivity && places.size > 0) {

            Log.d(TAG, "onActivityCreated  size: "+places.size)
            for (localPlace in places) {
                Log.d(TAG, "onActivityCreated: $localPlace")
            }
            val intent = Intent(context, WeatherActivity::class.java).apply {
                putExtra("location_lng", places[0].lng)
                putExtra("location_lat", places[0].lat)
                putExtra("place_name", places[0].name)
            }
            startActivity(intent)
            activity?.finish()
            return
        }
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        adapter = PlaceAdapter(this, viewModel.placeList)
        recyclerView.adapter = adapter

        val historyLayoutManager = LinearLayoutManager(activity)
        rv_history.layoutManager = historyLayoutManager
        historyAdapter = HistoryAdapter(this, viewModel.localPlaceList)
        rv_history.adapter = historyAdapter
        showHistory()
        searchPlaceEdit.addTextChangedListener { editable ->
            val content = editable.toString()
            if (content.isNotEmpty()) {
                viewModel.searchPlaces(content)
            } else {
                showHistory()
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }
        viewModel.placeLiveData.observe(viewLifecycleOwner, Observer { result ->
            val places = result.getOrNull()
            if (places != null) {
                recyclerView.visibility = View.VISIBLE
                rv_history.visibility = View.GONE
                bgImageView.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                adapter.notifyDataSetChanged()
                viewModel.localPlaceList.clear()
                historyAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, "未能查询到任何地点", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
                showHistory()
            }
        })
    }

    fun updateList(){
        if(searchPlaceEdit.text.toString().isEmpty()){
            showHistory()
        }
    }
    //加载本地数据
    private fun showHistory(){

        var list =viewModel.getSavedPlace()
        if(list.size>0){
            Log.d(TAG, "showHistory: list "+list.size)
            recyclerView.visibility = View.GONE
            rv_history.visibility = View.VISIBLE
            viewModel.localPlaceList.clear()
            viewModel.localPlaceList.addAll(list)
            historyAdapter.notifyDataSetChanged()
        }
    }

}
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

    //使用lazy函数这种懒加载技术来获取PlaceViewModel的实例，允许在整个类中随时使用viewModel变量
    val viewModel by lazy { ViewModelProvider(this).get(PlaceViewModel::class.java) }

    private lateinit var adapter: PlaceAdapter
    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //加载fragment_place布局
        return inflater.inflate(R.layout.fragment_place, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val places = viewModel.getSavedPlace()
        if (activity is MainActivity && places.size > 0) {

            val intent = Intent(context, WeatherActivity::class.java).apply {
                putExtra("location_lng", places[0].lng)
                putExtra("location_lat", places[0].lat)
                putExtra("place_name", places[0].name)
            }
            startActivity(intent)
            activity?.finish()
            return
        }
        //给RecyclerView设置LayoutManager和适配器
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        //使用PlaceViewModel中的placeList作为数据源
        adapter = PlaceAdapter(this, viewModel.placeList)
        recyclerView.adapter = adapter

        val historyLayoutManager = LinearLayoutManager(activity)
        rv_history.layoutManager = historyLayoutManager
        historyAdapter = HistoryAdapter(this, viewModel.localPlaceList)
        rv_history.adapter = historyAdapter

        showHistory()

        //监听搜索框内容的变化
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

        //对PlaceViewModel中的placeLiveData对象进行观察，当有任何数据变化时，就会回调到传入的Observer接口实现中
        viewModel.placeLiveData.observe(viewLifecycleOwner, Observer { result ->
            val places = result.getOrNull()
            //对回调的数据进行判断
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
        val list =viewModel.getSavedPlace()
        if(list.size>0){
            recyclerView.visibility = View.GONE
            rv_history.visibility = View.VISIBLE
            viewModel.localPlaceList.clear()
            viewModel.localPlaceList.addAll(list)
            historyAdapter.notifyDataSetChanged()
        }
    }

}
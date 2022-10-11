package com.example.waltz

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.waltz.databinding.FragmentMainMenuBinding
import java.text.SimpleDateFormat
import com.example.waltz.adapter.UnitRoomsAdapter
import com.example.waltz.data.UnitRoom
import java.util.*
import kotlin.collections.ArrayList


class MainMenuFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("TAG", "Hello, Main Menu")

    }

    private var _binding: FragmentMainMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set datetime textview
        val calender = Calendar.getInstance().time
        val formatter = SimpleDateFormat("EEE, dd MMM YYYY")
        val formattedDate = formatter.format(calender)

        binding.dateText.text = formattedDate

        val unitRoomList = ArrayList<UnitRoom>()

        binding.rvUnitRooms.layoutManager = GridLayoutManager(context, 2)

        val unitRoomAdapter = UnitRoomsAdapter(unitRoomList, requireContext())
        binding.rvUnitRooms.adapter = unitRoomAdapter

        unitRoomList.add(UnitRoom("1.4", false, 10f, 2))
        unitRoomList.add(UnitRoom("Lab. Robotika", true, 50f, 2))
        unitRoomList.add(UnitRoom("Lab. Informatika Terpadu 3.2", false, 10f, 3))
        unitRoomList.add(UnitRoom("Lab. Instrumentasi dan Kendali", true, 40f, 2))
        unitRoomList.add(UnitRoom("Lab. Instrumentasi dan Kendali", true, 40f, 2))

        // Set switch toggle change
        binding.switchAllBtn.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Log.d("Switch Val", "ON")
            } else {
                Log.d("Switch Val", "OFF")
            }
        }
        unitRoomAdapter.notifyDataSetChanged()
    }
}
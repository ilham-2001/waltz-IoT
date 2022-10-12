package com.example.waltz

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.example.waltz.databinding.FragmentMonitoringBinding
import org.eclipse.paho.client.mqttv3.*
import com.example.waltzLib.MQTTClient

class MonitoringFragment : Fragment(), View.OnClickListener {
    private val serverURI = "tcp://broker.hivemq.com:1883"
    private val clientID: String = MqttClient.generateClientId()
    private lateinit var mqtt: MQTTClient

    companion object {
        const val TAG = "AndroidMqttClient"
        lateinit var frame: ImageView
        lateinit var lightIndicator: CardView
    }

    // Life Cycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("TAG", "Hello, Monitoring")

    }

    private var _binding: FragmentMonitoringBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMonitoringBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Connect to MQTT when everything is set up
        mqtt = MQTTClient(context, serverURI, this.javaClass.name, clientID)

        frame = binding.ivScreen
        lightIndicator = binding.connectIndicator

        binding.leftControlAnalog.setOnClickListener(this)
        binding.rightControlAnalog.setOnClickListener(this)

        mqtt.connect()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.leftControlAnalog -> mqtt.publish("waltz/servoControl", "-5")
            R.id.rightControlAnalog -> mqtt.publish("waltz/servoControl", "5")

        }
    }
}
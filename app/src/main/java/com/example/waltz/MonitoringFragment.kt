package com.example.waltz

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.waltz.databinding.FragmentMonitoringBinding
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import com.example.waltzLib.MQTTClient
import  kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class MonitoringFragment : Fragment() {
    private lateinit var mqttClient: MqttAndroidClient
    val serverURI = "tcp://broker.hivemq.com:1883"
    val clientID = MqttClient.generateClientId()

    companion object {
        const val TAG = "AndroidMqttClient"
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
        val mqtt = MQTTClient(context, serverURI, clientID)
        // mqtt.connect()
        mqtt.subscribe("DIIBS/gambar_masker", 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun connectToServer(mqtt: MQTTClient) = runBlocking {
        async { mqtt.connect() }
    }


    private fun connect(context: Context?, clientID: String) {
        mqttClient = MqttAndroidClient(context, serverURI, clientID)

        try {
            val token = mqttClient.connect()

            token.actionCallback = object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Toast.makeText(context, "Connected", Toast.LENGTH_SHORT)
                        .show()
                    subscribe("DIIBS/gambar_masker", 0, mqttClient)
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d(TAG, "Error")
                }

            }
        } catch (e: MqttException) {
            Log.d(TAG, "Error when trying to connect")
        }
    }

    private fun subscribe(topic: String, qos: Int, client: MqttAndroidClient) {
        try {
            client.subscribe(topic, qos)
            client.setCallback(object : MqttCallback {
                override fun connectionLost(cause: Throwable?) {
                    // NOT IMPLEMENTED
                }

                override fun messageArrived(topic: String?, message: MqttMessage?) {
                    if (topic == "DIIBS/gambar_masker") {
                        val imageBytes = Base64.decode(message.toString(), Base64.DEFAULT)
                        val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

                        binding.ivScreen.setImageBitmap(bitmap)
                    }
                }

                override fun deliveryComplete(token: IMqttDeliveryToken?) {
                    // NOT IMPLEMENTED
                }

            })
        } catch (e: MqttException) {
            Log.e(TAG, "Error on retrieving message")
        }
    }

}
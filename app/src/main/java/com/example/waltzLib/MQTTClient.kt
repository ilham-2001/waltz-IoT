package com.example.waltzLib

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.waltz.MainMenuFragment
import com.example.waltz.MonitoringFragment
import com.example.waltz.R
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

class MQTTClient(
    context: Context?,
    serverUri: String,
    namespace: String,
    clientID: String = ""
) {
    private val mqttClient = MqttAndroidClient(context, serverUri, clientID)

    private val defaultCbConnect = object : IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken?) {

            val origin = namespace.split('.')[3]

            if (origin == "MonitoringFragment") {
                MonitoringFragment.lightIndicator.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context!!,
                        R.color.connected
                    )
                )
                subscribe("waltz/camFrame", 0)
            } else if (origin == "MainMenuFragment") {
                MainMenuFragment.lightIndicator.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context!!,
                        R.color.connected
                    )
                )
            }
        }

        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private val defaultCbClient = object : MqttCallback {
        override fun connectionLost(cause: Throwable?) {
            Toast.makeText(context, "Lost Connection", Toast.LENGTH_SHORT).show()
        }

        override fun messageArrived(topic: String?, message: MqttMessage?) {
            if (topic == "waltz/camFrame") {
                val imageBytes = Base64.decode(message.toString(), Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

                MonitoringFragment.frame.setImageBitmap(bitmap)
            }
            Log.d(this.javaClass.name, "Message is arrived")
        }

        override fun deliveryComplete(token: IMqttDeliveryToken?) {
            Log.d(this.javaClass.name, "Message is sent")
        }

    }

    fun connect(
        username: String = "",
        password: String = "",
        cbConnect: IMqttActionListener = defaultCbConnect,
        cbClient: MqttCallback = defaultCbClient
    ) {
        mqttClient.setCallback(cbClient)
        val options = MqttConnectOptions()
        options.userName = username
        options.password = password.toCharArray()

        try {
            mqttClient.connect(options, null, cbConnect)
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun subscribe(
        topic: String,
        qos: Int,
    ) {
        try {
            mqttClient.subscribe(topic, qos)
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun publish(
        topic: String,
        message: String
    ) {
        try {
            val payload = message.toByteArray()
            mqttClient.publish(topic, payload, 1, false)
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }
}
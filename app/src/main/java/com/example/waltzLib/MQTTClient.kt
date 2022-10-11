package com.example.waltzLib

import android.content.Context
import android.util.Log
import android.widget.Toast
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

class MQTTClient(
    context: Context?,
    serverUri: String,
    clientID: String = ""
) {
    private var mqttClient = MqttAndroidClient(context, serverUri, clientID)

    private val defaultCbConnect = object : IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken?) {
            Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show()
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
            Log.d(this.javaClass.name, "Message is arrived")
        }

        override fun deliveryComplete(token: IMqttDeliveryToken?) {
            Log.d(this.javaClass.name, "Message is sent")
        }

    }

    private val defaultSubscribe = object : IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken?) {
            Log.d("Subs", "Message is success to catch")
        }

        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            Log.d("Subs", "Message is failed to catch")
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
        cbSubscribe: IMqttActionListener = defaultSubscribe
    ) {
        try {
            mqttClient.subscribe(topic, qos, null, cbSubscribe)
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }
}
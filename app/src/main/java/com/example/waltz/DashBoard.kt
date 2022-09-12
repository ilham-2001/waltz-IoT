package com.example.waltz

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

class DashBoard : AppCompatActivity() {

    private lateinit var mqttClient: MqttAndroidClient

    companion object {
        const val TAG = "AndroidMqttClient"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

        val actionBar = supportActionBar
        actionBar!!.hide()

        val clientID = MqttClient.generateClientId()
        connect(this, clientID)
    }

    private fun connect(context: Context, clientID: String) {
        val serverURI = "tcp://broker.hivemq.com:1883"
        Log.d(TAG, context.toString())
        mqttClient = MqttAndroidClient(context, serverURI, clientID)

        Log.d(TAG, "trying to connect")

        try {
            val token = mqttClient.connect()

            token.actionCallback = object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Toast.makeText(this@DashBoard, "Connected", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d(TAG, "Error")
                }

            }
        } catch (e: MqttException) {
            Log.d(TAG, "Error when trying to connect")
        }
    }
}
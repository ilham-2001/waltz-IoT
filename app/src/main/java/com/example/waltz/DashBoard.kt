package com.example.waltz

import android.content.Context
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class DashBoard : AppCompatActivity() {

    private lateinit var mqttClient: MqttAndroidClient
    private lateinit var kwhChart: LineChart

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

        // dummy test data
        kwhChart = findViewById(R.id.kwh_chart)
        setLineChart()
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

    private fun setLineChart() {
        val linevalues = ArrayList<Entry>()
        linevalues.add(Entry(1f, 120_000F))
        linevalues.add(Entry(2f, 100_000F))
        linevalues.add(Entry(3f, 400_000F))
        linevalues.add(Entry(4f, 375_000F))
        linevalues.add(Entry(4f, 230_000F))
        linevalues.add(Entry(4f, 143_500F))
        linevalues.add(Entry(4f, 105_7500F))
        linevalues.add(Entry(5f, 278_4560F))
        linevalues.add(Entry(6f, 122_500F))
        linevalues.add(Entry(7f, 225_900F))
        linevalues.add(Entry(8f, 195_000F))
        linevalues.add(Entry(9f, 165_980F))
        linevalues.add(Entry(10f, 215_000F))
        linevalues.add(Entry(11f, 267_500F))
        linevalues.add(Entry(12f, 333_000f))

        val linedataset = LineDataSet(linevalues, "First")
        //We add features to our chart
        linedataset.color = resources.getColor(R.color.black)
//        linedataset.color = resources.getColor()

        linedataset.circleRadius = 2f
        linedataset.setDrawFilled(true)
        linedataset.valueTextSize = 10f
        linedataset.fillColor = resources.getColor(R.color.main)
        linedataset.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        val data = LineData(linedataset)
        kwhChart.data = data
        kwhChart.setBackgroundColor(resources.getColor(R.color.white))
        kwhChart.animateXY(2000, 2000, Easing.EaseInCubic)

        val xAxis = kwhChart.xAxis
        xAxis.granularity = 1f
        xAxis.valueFormatter = (object : ValueFormatter() {
            private val pattern = "dd MMM yy"
            private val mFormat = SimpleDateFormat(pattern)
            private val inputFormat = SimpleDateFormat("yyyy-MM-dd")

            private fun formatValue(value: Float): String {
                val month = when (value) {
                    1f -> "Jan"
                    2f -> "Feb"
                    3f -> "Mar"
                    4f -> "Apr"
                    5f -> "May"
                    6f -> "Jun"
                    7f -> "Jul"
                    8f -> "Aug"
                    9f -> "Sep"
                    10f -> "Oct"
                    11f -> "Nov"
                    12f -> "Dec"
                    else -> ""
                }

                return month
            }

            override fun getFormattedValue(value: Float): String {
//                val millis = TimeUnit.HOURS.toMillis(value.toLong())
//                return mFormat.format(inputFormat.parse(xLabel[value.toInt()]))
//                val date = Date(value.toLong())

//                Log.d(TAG, value.toLong().toString())
                Log.d(TAG, value.toInt().toString())

                return formatValue(value)
            }
        })
    }
}
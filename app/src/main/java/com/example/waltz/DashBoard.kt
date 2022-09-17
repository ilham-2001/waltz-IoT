package com.example.waltz

import android.content.Context
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.*
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
    private lateinit var monthlyCost: BarChart

    companion object {
        const val TAG = "AndroidMqttClient"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

//        val actionBar = supportActionBar
//        actionBar!!.hide()

        val clientID = MqttClient.generateClientId()
        connect(this, clientID)

        // dummy test data
        kwhChart = findViewById(R.id.kwh_chart)
        monthlyCost = findViewById(R.id.cost_monthly_chart)
        setLineChart()
        setBarChart()
    }

    private fun connect(context: Context, clientID: String) {
        val serverURI = "tcp://broker.hivemq.com:1883"
        Log.d(TAG, context.toString())
        mqttClient = MqttAndroidClient(context, serverURI, clientID)

//        Log.d(TAG, "trying to connect")

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

    private fun formatValueMonth(value: Float): String {
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

    private fun formatValueHour(value: Float): String {
        val month = when (value) {
            1f -> "00"
            2f -> "01"
            3f -> "02"
            4f -> "03"
            5f -> "04"
            6f -> "05"
            7f -> "06"
            8f -> "07"
            9f -> "08"
            10f -> "09"
            11f -> "10"
            12f -> "11"
            else -> ""
        }

        return month    }

    private fun setLineChart() {
        val linevalues = ArrayList<Entry>()
        linevalues.add(Entry(1f, 580F))
        linevalues.add(Entry(2f, 560F))
        linevalues.add(Entry(3f, 650F))
        linevalues.add(Entry(4f, 700F))
        linevalues.add(Entry(5f, 740F))
        linevalues.add(Entry(6f, 790F))
        linevalues.add(Entry(7f, 840F))
        linevalues.add(Entry(8f, 880F))
        linevalues.add(Entry(9f, 930F))
        linevalues.add(Entry(10f, 950F))
        linevalues.add(Entry(11f, 1_012F))

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

            override fun getFormattedValue(value: Float): String {
//                val millis = TimeUnit.HOURS.toMillis(value.toLong())
//                return mFormat.format(inputFormat.parse(xLabel[value.toInt()]))
//                val date = Date(value.toLong())

//                Log.d(TAG, value.toLong().toString())
//                Log.d(TAG, value.toInt().toString())

                return formatValueHour(value)
            }
        })
    }

    private fun setBarChart() {
        val barValues = ArrayList<BarEntry>()
        barValues.add(BarEntry(1f, 320_000f))
        barValues.add(BarEntry(2f, 220_000f))
        barValues.add(BarEntry(3f, 190_000f))
        barValues.add(BarEntry(4f, 110_000f))
        barValues.add(BarEntry(5f, 475_000f))
        barValues.add(BarEntry(6f, 325_000f))
        barValues.add(BarEntry(7f, 225_000f))
        barValues.add(BarEntry(8f, 212_500f))
        barValues.add(BarEntry(9f, 234_560f))
        barValues.add(BarEntry(10f, 110_5000f))
        barValues.add(BarEntry(11f, 123_456f))
        barValues.add(BarEntry(12f, 122_500f))

        val barDataSet = BarDataSet(barValues, "Monthly Cost")
        val barData = BarData(barDataSet)
        monthlyCost.data = barData

        barDataSet.valueTextColor = R.color.black
        barDataSet.setColor(resources.getColor(R.color.purple_200))
        barDataSet.valueTextSize = 12f
        monthlyCost.description.isEnabled = false

        val xAxis = monthlyCost.xAxis
        xAxis.valueFormatter = (object: ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return formatValueMonth(value)
            }
        })
    }
}
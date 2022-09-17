package com.example.waltz

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import java.text.SimpleDateFormat
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
        val realCostValues = ArrayList<Entry>()
        realCostValues.add(Entry(1f, 580F))
        realCostValues.add(Entry(2f, 560F))
        realCostValues.add(Entry(3f, 650F))
        realCostValues.add(Entry(4f, 700F))
        realCostValues.add(Entry(5f, 740F))
        realCostValues.add(Entry(6f, 790F))
        realCostValues.add(Entry(7f, 840F))
        realCostValues.add(Entry(8f, 880F))
        realCostValues.add(Entry(9f, 1_230F))
        realCostValues.add(Entry(10f, 1_105F))
        realCostValues.add(Entry(11f, 1_012F))
        realCostValues.add(Entry(12f, 912F))

        val predictedCostValues = ArrayList<Entry>()
        predictedCostValues.add(Entry(1f, 580F))
        predictedCostValues.add(Entry(2f, 560F))
        predictedCostValues.add(Entry(3f, 650F))
        predictedCostValues.add(Entry(4f, 700F))
        predictedCostValues.add(Entry(5f, 740F))
        predictedCostValues.add(Entry(6f, 790F))
        predictedCostValues.add(Entry(7f, 840F))
        predictedCostValues.add(Entry(8f, 880F))
        predictedCostValues.add(Entry(9f, 930F))
        predictedCostValues.add(Entry(10f, 950F))
        predictedCostValues.add(Entry(11f, 1_012F))
        predictedCostValues.add(Entry(12f, 1_412F))

        val lineRCV = LineDataSet(realCostValues, "Real Cost")
        val linePCV = LineDataSet(predictedCostValues, "Predicted Cost")
        //We add features to our chart
        lineRCV.color = resources.getColor(R.color.main)
        linePCV.color = resources.getColor(R.color.purple_200)

        // Real Cost Values
        lineRCV.circleRadius = 2f
        lineRCV.setDrawFilled(true)
        lineRCV.valueTextSize = 10f
        lineRCV.fillColor = resources.getColor(R.color.main)
        lineRCV.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        // Predicted Cost Values
        linePCV.circleRadius = 2f
        linePCV.setDrawFilled(true)
        linePCV.valueTextSize = 10f
        linePCV.fillColor = resources.getColor(R.color.purple_200)
        linePCV.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        val data = ArrayList<LineDataSet>()
        data.add(lineRCV)
        data.add(linePCV)

//        val RCVData = LineData(lineRCV)
//        val PCVData = LineData(linePCV)
        val lineData = LineData(data as List <ILineDataSet>?)

//        kwhChart.data = RCVData
//        kwhChart.data = PCVData
        kwhChart.data = lineData
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
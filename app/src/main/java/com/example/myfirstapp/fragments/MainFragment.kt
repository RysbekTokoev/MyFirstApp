package com.example.myfirstapp.fragments
import HoursFragment
import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myfirstapp.DialogManager
import com.example.myfirstapp.MainViewModel
import com.example.myfirstapp.R
import com.example.myfirstapp.adapters.VpAdapter
import com.example.myfirstapp.data.WeatherModel
import com.example.myfirstapp.databinding.FragmentMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import okhttp3.Request


const val API_KEY = "988e861aaa864e25abe84204231805"

//class MainFragment : Fragment() {
//    private lateinit var fLocationClient: FusedLocationProviderClient
//    private val fList = listOf(
//        HoursFragment.newInstance(),
//        DaysFragment.newInstance(),
//        ExpenseListFragment.newInstance(),
//    )
//    private val tList = listOf(
//        "Hours",
//        "Days",
//        "Expenses"
//    )
//    private lateinit var pLauncher: ActivityResultLauncher<String>
//    private lateinit var binding: FragmentMainBinding
//    private lateinit var carAnimation: Annotation
//    private val model: MainViewModel by activityViewModels()
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = FragmentMainBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        checkPermission()
//        init()
//        updateCurrentCard()
//        displayCarWashRecommendation()
//
//        val button = view.findViewById<Button>(R.id.ibWashesMap)
//        button.setOnClickListener {
//            val mapsFragment = MapsFragment()
//
//            val transaction = parentFragmentManager.beginTransaction()
//            transaction.replace(R.id.fragment_container, mapsFragment)
//            transaction.addToBackStack(null)
//            transaction.commit()
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//        checkLocation()
//    }
//
//
//    private fun init() = with(binding) {
//        progressBar.visibility = View.VISIBLE
//        fLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
//        val adapter = VpAdapter(activity as FragmentActivity, fList)
//        vp.adapter = adapter
//        vp.isUserInputEnabled = false
//        TabLayoutMediator(tabLayout, vp){
//                tab, pos -> tab.text = tList[pos]
//        }.attach()
//        ibSync.setOnClickListener {
//            tabLayout.selectTab(tabLayout.getTabAt(0))
//            checkLocation()
//        }
//        ibSearch.setOnClickListener{
//            DialogManager.searchByCityDialog(requireContext(), object : DialogManager.Listener{
//                override fun onClick(name: String?) {
//                    if (name != null) {
//                        requestWeatherData(name)
//                    }
//
//                }
//
//            })
//        }
//    }
//
//    private fun checkLocation(){
//        if (isLocationEnabled()){
//            getLocation()
//        } else {
//            DialogManager.locationSettingsDialog(requireContext(), object : DialogManager.Listener{
//                override fun onClick(name: String?) {
//                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
//                }
//
//            })
//        }
//    }
//
//    private fun isLocationEnabled(): Boolean{
//        val lm = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
//    }
//
//    private fun getLocation(){
//        val ct = CancellationTokenSource()
//        if (ActivityCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            return
//        }
//        fLocationClient
//            .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, ct.token)
//            .addOnCompleteListener{
//                requestWeatherData("${it.result.latitude},${it.result.longitude}")
//            }
//    }
//
//    private fun updateCurrentCard() = with(binding) {
//        model.liveDataCurrent.observe(viewLifecycleOwner) { item ->
//            val maxMinTemp = "${item.maxTemp}°C/${item.minTemp}°C"
//            tvData.text = item.time
//            tvCity.text = item.city
//            tvCurrenttemp.text = item.currentTemp.ifEmpty { maxMinTemp }
//            tvCondition.text = item.condition
//            tvMaxMin.text = if (item.currentTemp.isEmpty()) "" else maxMinTemp
//            Picasso.get().load("https:" + item.imageUrl).into(imWeather)
//            progressBar.visibility = View.GONE
//        }
//
//        Handler(Looper.getMainLooper()).post {
//            model.liveDataCurrent.value?.let {
//                model.liveDataCurrent.value = it
//            }
//        }
//    }
//    private fun permissionListener(){
//        pLauncher = registerForActivityResult(
//            ActivityResultContracts.RequestPermission()){
//            Toast.makeText(activity, "Permission is $it", Toast.LENGTH_LONG).show()
//        }
//    }
//
//    private fun checkPermission() {
//        if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
//            permissionListener()
//            pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
//
//
//        }
//    }
//
//    private fun requestWeatherData(city: String) {
//        val url = "https://api.weatherapi.com/v1/forecast.json?key=" +
//                API_KEY +
//                "&q=" +
//                city +
//                "&days=" +
//                "7" +
//                "&aqi=no&alerts=no"
//
//        val client = OkHttpClient()
//        val request = Request.Builder()
//            .url(url)
//            .build()
//
//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                Log.d("MyLog", "Error: $e")
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                val result = response.body?.string()
//                result?.let {
//                    parseWeatherData(it)
//                }
//            }
//        })
//    }
//
//    private fun displayCarWashRecommendation() {
//        model.liveDataCarWashRecommendation.observe(viewLifecycleOwner) { recommendation ->
//            binding.tvCarWashRecommendation.text = recommendation
//        }
//
//        Handler(Looper.getMainLooper()).post {
//            model.liveDataCarWashRecommendation.value = model.liveDataCarWashRecommendation.value
//        }
//    }
//
//    private fun parseWeatherData(result: String) {
//        val mainObject = JSONObject(result)
//        val list = parseDays(mainObject)
//        parseCurrentData(mainObject, list[0])
//        model.generateCarWashRecommendation(list)
//
//    }
//
//    private fun parseDays(mainObject: JSONObject): List<WeatherModel>{
//        val list =  ArrayList<WeatherModel>()
//        val daysArray = mainObject.getJSONObject("forecast")
//            .getJSONArray("forecastday")
//        val name = mainObject.getJSONObject("location").getString("name")
//        for (i in 0 until daysArray.length()){
//            val day = daysArray[i] as JSONObject
//            val item = WeatherModel(
//                name,
//                day.getString("date"),
//                day.getJSONObject("day").getJSONObject("condition")
//                    .getString("text"),
//                "",
//                day.getJSONObject("day").getString("maxtemp_c").toFloat().toInt().toString(),
//                day.getJSONObject("day").getString("mintemp_c").toFloat().toInt().toString(),
//                day.getJSONObject("day").getJSONObject("condition")
//                    .getString("icon"),
//                day.getJSONArray("hour").toString(),
//                day.getJSONObject("day").getString("daily_chance_of_rain"),
//                day.getJSONObject("day").getString("daily_chance_of_snow")
//            )
//            list.add(item)
//        }
//        model.liveDataList.postValue(list)
//        return list
//    }
//
//    private fun parseCurrentData(mainObject:JSONObject, weatherItem:WeatherModel){
//        val item = WeatherModel(
//            mainObject.getJSONObject("location").getString("name"),
//            mainObject.getJSONObject("current").getString("last_updated"),
//            mainObject.getJSONObject("current")
//                .getJSONObject("condition").getString("text"),
//            mainObject.getJSONObject("current").getString("temp_c"),
//            weatherItem.maxTemp,
//            weatherItem.minTemp,
//            mainObject.getJSONObject("current")
//                .getJSONObject("condition").getString("icon"),
//            weatherItem.hours,
//            weatherItem.chance_of_rain,
//            weatherItem.chance_of_snow
//        )
//        model.liveDataCurrent.postValue(item)
//
//    }
//
//
//
//    companion object {
//
//        @JvmStatic
//        fun newInstance() = MainFragment()
//
//
//    }
//}

class MainFragment : Fragment() {
    private lateinit var fLocationClient: FusedLocationProviderClient
    private val fList = listOf(
        HoursFragment.newInstance(),
        DaysFragment.newInstance(),
        ExpenseListFragment.newInstance(),
    )
    private val tList = listOf(
        "Hours",
        "Days",
        "Expenses"
    )
    private lateinit var pLauncher: ActivityResultLauncher<String>
    private lateinit var binding: FragmentMainBinding
    private lateinit var carAnimation: Annotation
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        // Создание канала уведомлений
        // Создание канала уведомлений
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "car_wash_channel",
                "Car Wash Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.enableVibration(true)
            channel.enableLights(true)

            val notificationManager =
                requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // Запрос разрешения на отправку уведомлений
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = notificationManager.getNotificationChannel("car_wash_channel")
            if (channel?.importance == NotificationManager.IMPORTANCE_NONE) {
                val intent = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS)
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, requireActivity().packageName)
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel.id)
                startActivity(intent)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        init()
        updateCurrentCard()
        displayCarWashRecommendation()

        val button = view.findViewById<Button>(R.id.ibWashesMap)
        button.setOnClickListener {
            val mapsFragment = MapsFragment()

            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, mapsFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    override fun onResume() {
        super.onResume()
        checkLocation()
    }


    private fun init() = with(binding) {
        progressBar.visibility = View.VISIBLE
        fLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        val adapter = VpAdapter(activity as FragmentActivity, fList)
        vp.adapter = adapter
        vp.isUserInputEnabled = false
        TabLayoutMediator(tabLayout, vp){
                tab, pos -> tab.text = tList[pos]
        }.attach()
        ibSync.setOnClickListener {
            tabLayout.selectTab(tabLayout.getTabAt(0))
            checkLocation()
        }
        ibSearch.setOnClickListener{
            DialogManager.searchByCityDialog(requireContext(), object : DialogManager.Listener{
                override fun onClick(name: String?) {
                    if (name != null) {
                        requestWeatherData(name)
                    }

                }

            })
        }
    }

    private fun checkLocation(){
        if (isLocationEnabled()){
            getLocation()
        } else {
            DialogManager.locationSettingsDialog(requireContext(), object : DialogManager.Listener{
                override fun onClick(name: String?) {
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }

            })
        }
    }

    private fun isLocationEnabled(): Boolean{
        val lm = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun getLocation(){
        val ct = CancellationTokenSource()
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fLocationClient
            .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, ct.token)
            .addOnCompleteListener{
                requestWeatherData("${it.result.latitude},${it.result.longitude}")
            }
    }

    private fun updateCurrentCard() = with(binding) {
        model.liveDataCurrent.observe(viewLifecycleOwner) { item ->
            val maxMinTemp = "${item.maxTemp}°C/${item.minTemp}°C"
            tvData.text = item.time
            tvCity.text = item.city
            tvCurrenttemp.text = item.currentTemp.ifEmpty { maxMinTemp }
            tvCondition.text = item.condition
            tvMaxMin.text = if (item.currentTemp.isEmpty()) "" else maxMinTemp
            Picasso.get().load("https:" + item.imageUrl).into(imWeather)
            progressBar.visibility = View.GONE
        }

        Handler(Looper.getMainLooper()).post {
            model.liveDataCurrent.value?.let {
                model.liveDataCurrent.value = it
            }
        }
    }
    private fun permissionListener(){
        pLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()){
            Toast.makeText(activity, "Permission is $it", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkPermission() {
        if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionListener()
            pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)


        }
    }

    private fun requestWeatherData(city: String) {
        val url = "https://api.weatherapi.com/v1/forecast.json?key=" +
                API_KEY +
                "&q=" +
                city +
                "&days=" +
                "7" +
                "&aqi=no&alerts=no"

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("MyLog", "Error: $e")
            }

            override fun onResponse(call: Call, response: Response) {
                val result = response.body?.string()
                result?.let {
                    parseWeatherData(it)
                }
            }
        })
    }

    private fun displayCarWashRecommendation() {
        model.liveDataCarWashRecommendation.observe(viewLifecycleOwner) { recommendation ->
            binding.tvCarWashRecommendation.text = recommendation

            // Send notification
            val notificationManager =
                requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val notification = NotificationCompat.Builder(requireContext(), "car_wash_channel")
                .setContentTitle("Рекомендация по мойке автомобиля")
                .setContentText(recommendation)
                .setSmallIcon(R.drawable.car_logo)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .build()

            notificationManager.notify(1, notification)
        }

        Handler(Looper.getMainLooper()).post {
            model.liveDataCarWashRecommendation.value = model.liveDataCarWashRecommendation.value
        }
    }


    private fun parseWeatherData(result: String) {
        val mainObject = JSONObject(result)
        val list = parseDays(mainObject)
        parseCurrentData(mainObject, list[0])
        model.generateCarWashRecommendation(list)

    }

    private fun parseDays(mainObject: JSONObject): List<WeatherModel>{
        val list =  ArrayList<WeatherModel>()
        val daysArray = mainObject.getJSONObject("forecast")
            .getJSONArray("forecastday")
        val name = mainObject.getJSONObject("location").getString("name")
        for (i in 0 until daysArray.length()){
            val day = daysArray[i] as JSONObject
            val item = WeatherModel(
                name,
                day.getString("date"),
                day.getJSONObject("day").getJSONObject("condition")
                    .getString("text"),
                "",
                day.getJSONObject("day").getString("maxtemp_c").toFloat().toInt().toString(),
                day.getJSONObject("day").getString("mintemp_c").toFloat().toInt().toString(),
                day.getJSONObject("day").getJSONObject("condition")
                    .getString("icon"),
                day.getJSONArray("hour").toString(),
                day.getJSONObject("day").getString("daily_chance_of_rain"),
                day.getJSONObject("day").getString("daily_chance_of_snow")
            )
            list.add(item)
        }
        model.liveDataList.postValue(list)
        return list
    }

    private fun parseCurrentData(mainObject:JSONObject, weatherItem:WeatherModel){
        val item = WeatherModel(
            mainObject.getJSONObject("location").getString("name"),
            mainObject.getJSONObject("current").getString("last_updated"),
            mainObject.getJSONObject("current")
                .getJSONObject("condition").getString("text"),
            mainObject.getJSONObject("current").getString("temp_c"),
            weatherItem.maxTemp,
            weatherItem.minTemp,
            mainObject.getJSONObject("current")
                .getJSONObject("condition").getString("icon"),
            weatherItem.hours,
            weatherItem.chance_of_rain,
            weatherItem.chance_of_snow
        )
        model.liveDataCurrent.postValue(item)

    }



    companion object {

        @JvmStatic
        fun newInstance() = MainFragment()


    }
}
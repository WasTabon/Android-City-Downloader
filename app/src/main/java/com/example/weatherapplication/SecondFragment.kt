package com.example.weatherapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.weatherapplication.databinding.FragmentSecondBinding
import com.example.weatherapplication.viewmodel.MainViewModel
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.weatherapplication.model.CurrentWeatherResponse
import java.lang.StringBuilder
import com.example.weatherapplication.R

class SecondFragment : Fragment() {
    private lateinit var binding: FragmentSecondBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var imgCondition: ImageView
    private lateinit var tvResult: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()

        binding.imgCondition.visibility.toString()
        binding.tvResult.text.toString()

    }

private fun subscribe() {
    mainViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
        // Set the result text to Loading
        if (isLoading) binding.tvResult.text = resources.getString(R.string.loading)
    }

    mainViewModel.isError.observe(viewLifecycleOwner) { isError ->
        // Hide display image and set the result text to the error message
        if (isError) {
            binding.imgCondition.visibility = View.GONE
            binding.tvResult.text = mainViewModel.errorMessage
        }
    }

    mainViewModel.weatherData.observe(viewLifecycleOwner) { weatherData ->
        // Display weather data to the UI
        if (weatherData != null) {
            setResultText(weatherData)
        }
    }
}

private fun setResultText(weatherData: CurrentWeatherResponse) {
    val resultText = StringBuilder("Result:\n")

    weatherData.location.let { location ->
        resultText.append("Name: ${location?.name}\n")
        resultText.append("Region: ${location?.region}\n")
        resultText.append("Country: ${location?.country}\n")
        resultText.append("Timezone ID: ${location?.tzId}\n")
        resultText.append("Local Time: ${location?.localtime}\n")
    }

    weatherData.current.let { current ->
        current?.condition.let { condition ->
            resultText.append("Condition: ${condition?.text}\n")
            setResultImage(condition?.icon)
        }
        resultText.append("Celcius: ${current?.tempC}\n")
        resultText.append("Fahrenheit: ${current?.tempF}\n")
    }

    binding.tvResult.text = resultText
}

private fun setResultImage(imageUrl: String?) {
    // Display image when image url is available
    imageUrl.let { url ->
        Glide.with(requireContext())
            .load("https:$url")
            .into(binding.imgCondition)

        binding.imgCondition.visibility = View.VISIBLE
        return
    }

    // Hide image when image url is null
    binding.imgCondition.visibility = View.GONE
}
}

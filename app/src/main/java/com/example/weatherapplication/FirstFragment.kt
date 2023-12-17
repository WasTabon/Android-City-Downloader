package com.example.weatherapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.weatherapplication.databinding.FragmentFirstBinding
import com.example.weatherapplication.model.CurrentWeatherResponse
import com.example.weatherapplication.viewmodel.MainViewModel
import java.lang.StringBuilder
import com.example.weatherapplication.R


class FirstFragment : Fragment() {
    private lateinit var binding: FragmentFirstBinding
    private lateinit var mainViewModel: MainViewModel

    private lateinit var etCityName: EditText
    private lateinit var btnSend: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etCityName.text.toString()

        binding.btnSendRequest.text.toString()


        // Add on click button to the send button
        binding.btnSendRequest.setOnClickListener {

            // Text field validation
            if (binding.etCityName.text.isNullOrEmpty() or binding.etCityName.text.isNullOrBlank()) {
                binding.etCityName.error = "Field can't be null"
            } else {
                findNavController()
                .navigate(
                    FirstFragmentDirections.actionFirstFragmentToSecondFragment()
                )
                // Get weather data
                mainViewModel.getWeatherData(binding.etCityName.text.toString())
            }
        }
    }
}

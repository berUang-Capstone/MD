package com.example.subs_inter.ui.notification

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.subs_inter.InputTargetActivity
import com.example.subs_inter.databinding.FragmentNotificationBinding
import com.example.subs_inter.ui.home.HomeViewModel
import java.text.NumberFormat
import java.util.*

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Load the saved price and format it as Rupiah
        val sharedPref = activity?.getSharedPreferences("AppPreferences", AppCompatActivity.MODE_PRIVATE)
        val targetPrice = sharedPref?.getFloat("targetPrice", 0.0f) ?: 0.0f
        binding.target.text = formatAsRupiah(targetPrice.toDouble())

        binding.btnSetTarget.setOnClickListener{
            val intent = Intent(requireContext(), InputTargetActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        val sharedPref = activity?.getSharedPreferences("AppPreferences", AppCompatActivity.MODE_PRIVATE)
        val targetPrice = sharedPref?.getFloat("targetPrice", 0.0f) ?: 0.0f
        binding.target.text = formatAsRupiah(targetPrice.toDouble())
        val mybalance = sharedPref?.getFloat("mybalance", 0.0f) ?: 0.0f
        if (mybalance > targetPrice && targetPrice.toInt() != 0) {
            binding.btnSetTarget.text = "Target Reached"
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Target Tercapai")
            builder.setMessage("Selamat! Tabungan Anda telah mencapai target yang ditetapkan.")
            builder.setPositiveButton("OK") { dialog, which ->
                // Hanya menutup dialog
                dialog.dismiss()
            }
            builder.show()
        }
        if (targetPrice.toInt() != 0){
            binding.btnSetTarget.text = "Edit Target"
        }
        else{
            binding.btnSetTarget.text = "Set Target"

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun formatAsRupiah(number: Double): String {
        val localeID = Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        return numberFormat.format(number)
    }
}


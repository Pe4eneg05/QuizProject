package com.pechenegmobilecompanyltd.quizproject.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.WallpaperManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.pechenegmobilecompanyltd.quizproject.R
import com.pechenegmobilecompanyltd.quizproject.data.DataClass
import com.pechenegmobilecompanyltd.quizproject.databinding.FragmentShopBinding
import com.pechenegmobilecompanyltd.quizproject.entity.PhotoItem
import com.pechenegmobilecompanyltd.quizproject.recyclerview.AdapterRecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

class ShopFragment : Fragment() {

    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = DataClass()
        var balance = getPointCount()
        val wallpaperManager: WallpaperManager = WallpaperManager.getInstance(activity)

        fun onClickItemRecyclerView(item: PhotoItem, price: Int) {

            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Внимание!")
            builder.setMessage("Вы уверены, что хотите изменить обои на своём телефоне?")
            builder.setPositiveButton("Да") { _, _ ->
                if (balance >= price) {
                    balance -= price
                    binding.balance.text = "Баланс: $balance"
                    lifecycleScope.launch(Dispatchers.IO) {
                        val inputStream = URL(item.resource).openStream()
                        wallpaperManager.setStream(inputStream)
                    }
                    Toast.makeText(activity, "Обои успешно измененны!", Toast.LENGTH_SHORT)
                        .show()
                    savePointsPrefData(balance)
                } else Toast.makeText(activity, "У вас недостаточно очков!", Toast.LENGTH_SHORT)
                    .show()
            }
            builder.setNegativeButton("Нет") { _, _ -> }
            builder.show()
        }

        binding.recycler.layoutManager =
            GridLayoutManager(context, 2)
        val adapter = AdapterRecyclerView { onClickItemRecyclerView(it, it.price) }
        adapter.setData(data.listPhotos)
        binding.recycler.adapter = adapter

        binding.balance.text = "Баланс: $balance"

        binding.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_ShopFragment_to_StartFragment)
            savePointsPrefData(balance)
        }


    }

    private fun savePointsPrefData(points: Int) {
        sharedPreferences = context?.getSharedPreferences("point", Context.MODE_PRIVATE)
        val editor = sharedPreferences!!.edit()
        editor.putInt("points", points)
        editor.apply()
    }

    private fun getPointCount(): Int {
        sharedPreferences = context?.getSharedPreferences("point", Context.MODE_PRIVATE)
        return sharedPreferences!!.getInt("points", 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
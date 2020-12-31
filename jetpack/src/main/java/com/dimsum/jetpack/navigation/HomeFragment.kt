package com.dimsum.jetpack.navigation

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.dimsum.jetpack.R
import com.dimsum.jetpack.databinding.HomeFragmentBinding
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var detailViewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.home_fragment, container, false)
        val binding = DataBindingUtil.inflate<HomeFragmentBinding>(
            inflater,
            R.layout.home_fragment,
            container,
            false
        )
        activity?.let {
            detailViewModel = ViewModelProvider(it).get(DetailViewModel::class.java)
            binding.data = detailViewModel
            binding.lifecycleOwner = it
        }
        binding.seekBar.apply {
            setProgress(detailViewModel.getNumber()?.value!!)
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    detailViewModel.getNumber()?.postValue(progress)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar) {
                }
            })
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
        button_home.setOnClickListener { v ->
            val controller = Navigation.findNavController(v)
            controller.navigate(R.id.action_homeFragment_to_detailFragment)
        }
    }

}
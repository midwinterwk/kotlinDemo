package com.dimsum.jetpack.navigation

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.dimsum.jetpack.R
import com.dimsum.jetpack.databinding.DetailFragmentBinding
import com.dimsum.jetpack.databinding.HomeFragmentBinding
import kotlinx.android.synthetic.main.detail_fragment.*

class DetailFragment : Fragment() {

    companion object {
        fun newInstance() = DetailFragment()
    }

    private lateinit var detailViewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<DetailFragmentBinding>(
            inflater,
            R.layout.detail_fragment,
            container,
            false
        )
        activity?.let {
            detailViewModel = ViewModelProvider(it).get(DetailViewModel::class.java)
            binding.data = detailViewModel
            binding.lifecycleOwner = it
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
        button_detail.setOnClickListener {v ->
            Navigation.findNavController(v).navigate(R.id.action_detailFragment_to_homeFragment)
//            Navigation.createNavigateOnClickListener(R.id.action_detailFragment_to_homeFragment)
        }
    }

}
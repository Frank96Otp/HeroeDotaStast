package com.example.dotaherostats.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dotaherostats.R
import com.example.dotaherostats.databinding.FragmentHeroeBinding
import com.example.dotaherostats.view.RVExploraListAdapter
import com.example.dotaherostats.view.fragments.ListViewModel


class HeroeFragment : Fragment() {
    private lateinit var binding: FragmentHeroeBinding
    private lateinit var viewModel: ListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ListViewModel::class.java)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentHeroeBinding.inflate(inflater,container,false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = RVExploraListAdapter(listOf())
        binding.rvExploraList.adapter = adapter
        binding.rvExploraList.layoutManager = GridLayoutManager(requireContext(),1,
            RecyclerView.VERTICAL,false )
        viewModel.heroesList.observe(requireActivity()){
            if (it != null) {
                adapter.heroes=it
            }
            adapter.notifyDataSetChanged()
        }
        viewModel.getHeroesFromService()
    }
}
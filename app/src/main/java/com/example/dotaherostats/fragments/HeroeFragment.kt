package com.example.dotaherostats.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dotaherostats.R
import com.example.dotaherostats.databinding.FragmentHeroeBinding
import com.example.dotaherostats.model.Heroe
import com.example.dotaherostats.view.RVExploraListAdapter
import com.example.dotaherostats.view.fragments.ListViewModel
import com.squareup.picasso.Picasso


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
        val adapter = RVExploraListAdapter(listOf()) { heroe ->
            showHeroDetailsDialog(heroe)
        }
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

    private fun showHeroDetailsDialog(heroe: Heroe) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_hero_details, null)

        dialogView.findViewById<TextView>(R.id.txtHeroName).text = heroe.localizedName
        dialogView.findViewById<TextView>(R.id.txtPrimaryAttr).text = "Atributo: ${heroe.primaryAttr}"
        dialogView.findViewById<TextView>(R.id.txtAttackType).text = "Tipo de Ataque: ${heroe.attackType}"
        dialogView.findViewById<TextView>(R.id.txtRoles).text = "Roles: ${heroe.roles.joinToString(", ")}"
        dialogView.findViewById<TextView>(R.id.txtBaseHealth).text = "Salud Base: ${heroe.baseHealth}"

        // Agrega más TextViews para otros campos del modelo Heroe
        dialogView.findViewById<TextView>(R.id.txtId).text = "ID: ${heroe.id}"
        val imgIcon = dialogView.findViewById<ImageView>(R.id.imgIcon)
        val imgHeroe = dialogView.findViewById<ImageView>(R.id.imgHeroe)

        val completedIconUrl = "https://api.opendota.com${heroe.icon}"
        val completedImgUrl = "https://api.opendota.com${heroe.img}"

        if (!completedIconUrl.isNullOrEmpty()) {
            Picasso.get().load(completedIconUrl).into(imgIcon)
        } else {
            imgIcon.setImageResource(R.drawable.default_dota)
        }

        if (!completedImgUrl.isNullOrEmpty()) {
            Picasso.get().load(completedImgUrl).into(imgHeroe)
        } else {
            imgHeroe.setImageResource(R.drawable.default_dota)
        }

        // Agrega más TextViews para otros campos del modelo Heroe

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }
}
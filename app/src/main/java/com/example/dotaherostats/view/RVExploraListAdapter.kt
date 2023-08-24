package com.example.dotaherostats.view
import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView;
import com.example.dotaherostats.R
import com.example.dotaherostats.databinding.ItemHerolistBinding
import com.example.dotaherostats.model.Heroe
import com.squareup.picasso.Picasso



class RVExploraListAdapter(
    var heroes: List<Heroe>,
    private val onItemClick: (Heroe) -> Unit
) : RecyclerView.Adapter<RVExploraListAdapter.ExploraVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploraVH {
        val binding = ItemHerolistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExploraVH(binding)
    }

    override fun getItemCount(): Int = heroes.size

    override fun onBindViewHolder(holder: ExploraVH, position: Int) {
        holder.bind(heroes[position])
    }

    fun updateHeroes(newHeroes: List<Heroe>) {
        heroes = newHeroes
        notifyDataSetChanged()
    }

    inner class ExploraVH(private val binding: ItemHerolistBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.imageHeroe.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(heroes[position])
                }
            }
        }

        fun bind(heroe: Heroe) {
            val imageUrl = "https://api.opendota.com" + heroe.img
            if (!imageUrl.isNullOrEmpty()) {
                println("cargo imagen ")
                Picasso.get().load(imageUrl).into(binding.imageHeroe)
            } else {
                println("no cargo imagen ")
                // Cargar una imagen por defecto o hacer otra acción en caso de que imageUrl esté vacío.
                binding.imageHeroe.setImageResource(R.drawable.heroe_dota2)
            }

            binding.txtNombre.text = heroe.localizedName
            binding.txtPuntuacion.text = heroe.id.toString()

            binding.txtDescripcion.text = when (heroe.primaryAttr) {
                "str" -> "Fuerza"
                "agi" -> "Agilidad"
                "int" -> "Inteligencia"
                else -> "Descripción no disponible"
            }
        }
    }
}
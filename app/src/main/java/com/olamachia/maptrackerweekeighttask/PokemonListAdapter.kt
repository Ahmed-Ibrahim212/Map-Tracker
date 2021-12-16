package com.olamachia.maptrackerweekeighttask

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.olamachia.maptrackerweekeighttask.databinding.PokemanListItemBinding
import com.squareup.picasso.Picasso


class PokemonListAdapter(
    private val pokemons: List<ResultsData>,
    var listener: OnPokemonListClick
) : RecyclerView.Adapter<PokemonListAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val binding = PokemanListItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindView(pokemons[position], listener)
    }

    override fun getItemCount(): Int {
       return pokemons.size
    }


    inner class MyViewHolder(binding: PokemanListItemBinding):RecyclerView.ViewHolder(binding.root){
        val pokemonText: TextView = binding.pokemonText
        val pokemonImage: ImageView = binding.pokemonImage

        fun bindView(item:ResultsData, action: OnPokemonListClick){
            pokemonText.text = item.name

            Picasso.get()
                .load(getImageUrl(item.url))
                .into(pokemonImage)

            itemView.setOnClickListener {
                action.onItemCLick(item,adapterPosition)
            }
        }
    }

    //This method gets the Url for the image
    private fun getImageUrl(url: String): String {
        var init = url.split( '/')
        var number = init[init.size - 2]
        var baseURL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
        var newUrl = "${baseURL}$number.png"

        return newUrl
    }


}

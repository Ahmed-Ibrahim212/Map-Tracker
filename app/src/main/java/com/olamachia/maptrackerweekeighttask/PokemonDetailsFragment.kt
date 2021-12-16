package com.olamachia.maptrackerweekeighttask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso

import com.olamachia.maptrackerweekeighttask.databinding.FragmentPokemonDetailsBinding


class PokemonDetailsFragment : Fragment() {
    private var _binding: FragmentPokemonDetailsBinding? = null
    private val binding get() = _binding!!
    private val args : PokemonDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPokemonDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pokemonId = args.number


        // Download and transform Pokemon images
        Picasso.get()
            .load("${Constants.POKEMON_URL}$pokemonId.png")
            .into(binding.pokemonImg)

        val pokemonDetailsViewModel =
            ViewModelProvider(this).get(PokemonDetailsViewModel::class.java)

        // get pokemon details
        pokemonDetailsViewModel.getPokemonIndividualDetails(pokemonId)
        /**
         * Observes obtained pokemon details
         */
        pokemonDetailsViewModel.getPokemonIndividualDataDetails().observe(
            viewLifecycleOwner, {
                with(binding) {
                    name.text = it.species.name
                    height.text = "${it.height} cm"
                    weight.text = "${it.weight} lbs"
                    baseExperience.text = "${it.base_experience}"
                    order.text = "${it.order}"
                    move1.text = "${it.moves[0].move.name}"
                    move2.text = "${it.moves[1].move.name}"
                    move3.text = "${it.moves[2].move.name}"
                }
            }
        )
    }


}

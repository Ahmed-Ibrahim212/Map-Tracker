package com.olamachia.maptrackerweekeighttask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.olamachia.maptrackerweekeighttask.databinding.FragmentPokemanListBinding

class PokemonListFragment: Fragment(), OnPokemonListClick {

    private var _binding: FragmentPokemanListBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPokemanListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this).get(PokemonViewModel::class.java)

        val recyclerPokemon = binding.PokemonRecyclerView

        binding.buttonSetLimit.setOnClickListener {
            if (binding.getPokeLimit.text.isNotEmpty()){
                val limit = binding.getPokeLimit.text.toString().toInt()
                viewModel.getPokemon(limit, 0)
        //To hide the on-screen keyboard after button click
        binding.getPokeLimit.onEditorAction(EditorInfo.IME_ACTION_DONE)
            }else{
                binding.getPokeLimit.error = "Invalid Input"
            }
        }
        viewModel.getPokemon(100, 0)

        recyclerPokemon.layoutManager= GridLayoutManager(requireContext(),2)
        viewModel.pokemonData.observe(
            viewLifecycleOwner,
            Observer {
                recyclerPokemon.adapter = PokemonListAdapter(it.results, this)

            }
        )
    }

    override fun onItemCLick(item: ResultsData, position: Int) {
        val num = getNumber(item.url)
        val action = PokemonListFragmentDirections.actionPokemonListFragmentToPokemonDetailsFragment(num)
        findNavController().navigate(action)
    }

    /**
     * Get the number of selected pokemon
     */
    fun getNumber(url: String): String {
        var init = url.split('/')
        var number = init[init.size - 2]
        return number
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}


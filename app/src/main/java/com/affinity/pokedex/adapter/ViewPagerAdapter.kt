package com.affinity.pokedex.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.affinity.pokedex.fragments.AboutFragment
import com.affinity.pokedex.fragments.EvolutionFragment
import com.affinity.pokedex.fragments.StatsFragment

class ViewPagerAdapter(fragmentManager : FragmentManager, lifecycle : Lifecycle)
    : FragmentStateAdapter(fragmentManager, lifecycle){
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> AboutFragment()
            1 -> StatsFragment()
            2 -> EvolutionFragment()
            else -> AboutFragment()
        }
    }
}
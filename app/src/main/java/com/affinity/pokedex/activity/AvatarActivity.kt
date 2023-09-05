package com.affinity.pokedex.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.GridLayoutManager
import com.affinity.pokedex.adapter.AvatarAdapter
import com.affinity.pokedex.databinding.ActivityAvatarBinding
import com.affinity.pokedex.repository.MyPreferences
import com.affinity.pokedex.utility.AvatarUtils

class AvatarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAvatarBinding

    private lateinit var avatarAdapter : AvatarAdapter

    private var currentAvatarPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAvatarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setCurrentAvatar()


        setTransition()

        implementRecyclerView()

        onItemClick()

        onDoneClick()

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setCurrentAvatar() {
        currentAvatarPosition = MyPreferences(this).currentAvatar
        binding.ivProfilePic.setImageResource(AvatarUtils.avatars[currentAvatarPosition])
    }

    private fun onDoneClick() {
        binding.ivDone.setOnClickListener(){
            MyPreferences(this).currentAvatar = currentAvatarPosition
            onBackPressed()
        }
    }

    private fun setTransition() {
        supportStartPostponedEnterTransition()
        binding.cvProfilePic.doOnPreDraw {
            supportStartPostponedEnterTransition()
        }

        val transition = TransitionInflater.from(this).inflateTransition(android.R.transition.move)
        window.sharedElementReturnTransition = transition
    }

    private fun onItemClick() {
        avatarAdapter.onItemClick = { avatarPosition ->
            binding.ivDone.visibility = View.VISIBLE
            binding.ivProfilePic.setImageResource(AvatarUtils.avatars[avatarPosition])
            currentAvatarPosition = avatarPosition
        }
    }

    private fun implementRecyclerView() {
        avatarAdapter = AvatarAdapter(MyPreferences((this)))
        binding.rvAvatar.apply {
            adapter = avatarAdapter
            layoutManager = GridLayoutManager(this@AvatarActivity, 4, GridLayoutManager.VERTICAL, false)
        }
        avatarAdapter.differ.submitList(AvatarUtils.avatars)
    }


}
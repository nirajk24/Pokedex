package com.example.pokedex.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import com.example.pokedex.R
import com.example.pokedex.databinding.ActivityAvatarBinding
import com.example.pokedex.databinding.ActivityProfileBinding
import com.example.pokedex.databinding.TempBinding
import com.example.pokedex.repository.MyPreferences
import com.example.pokedex.utility.AvatarUtils

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding : TempBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TempBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initialLayout()
        setName()

        setAvatar()
        setGridSize()
        setTheme()
    }

    private fun setAvatar() {
        binding.layoutAvatar.setOnClickListener {
            intent = Intent(this,AvatarActivity::class.java)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                binding.cvProfilePic,
                ViewCompat.getTransitionName(binding.cvProfilePic)!!
            )
            startActivity(intent, options.toBundle())
        }
    }

    private fun initialLayout() {
        // Username
        binding.tvUsername.text = MyPreferences(this).username

        // Avatar
        val currentAvatar = MyPreferences(this).currentAvatar
        binding.ivProfilePic.setImageResource(AvatarUtils.avatars[currentAvatar])

        // Number of Pokemons
        val pokemonsCount = MyPreferences(this).collectedPokemonCount
        binding.pbPokemonCollected.progress = pokemonsCount
        val progressValue = "$pokemonsCount/151"
        binding.tvCollectedProgress.text = progressValue


        val animate = AnimationUtils.loadAnimation(this, R.anim.fill_animation)
        binding.pbPokemonCollected.startAnimation(animate)

        // Grid Value
        val gridValue = MyPreferences(this).isLinear
        if(gridValue) binding.tvGridValue.text = "List"
        else binding.tvGridValue.text = "Block"

        // Theme
        when(MyPreferences(this).darkMode){
            0 -> binding.tvThemeValue.text = "Light"
            1 -> binding.tvThemeValue.text = "Dark"
            2 -> binding.tvThemeValue.text = "System"
        }

        // Set on Back Listener
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setName() {
        binding.tvUsername.setOnClickListener {
            showUsernameChangeDialog()
        }
    }

    private fun setTheme() {
        binding.layoutTheme.setOnClickListener {
            showThemeChangeDialog()
            MyPreferences(this).isChanged = true
        }
    }
    private fun setGridSize(){
        binding.layoutGrid.setOnClickListener {
            showGridSizeChangeDialog()
            MyPreferences(this).isChanged = true
        }
    }

    private fun showUsernameChangeDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_change_username, null)
        val editText = dialogView.findViewById<EditText>(R.id.etUsername)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Change Username")
            .setMessage("Enter a new username")
            .setView(dialogView)
            .setPositiveButton("Done") { _, _ ->
                val newUsername = editText.text.toString().take(20)
                binding.tvUsername.text = newUsername
                MyPreferences(this).username = newUsername
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .create()


        dialog.show()
    }


    private fun showGridSizeChangeDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_change_gridsize, null)
        val btnOption1 = dialogView.findViewById<RadioButton>(R.id.btnOption1)
        val btnOption2 = dialogView.findViewById<RadioButton>(R.id.btnOption2)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        when(MyPreferences(this).isLinear){
            true -> btnOption1.toggle()
            false -> btnOption2.toggle()
        }

        btnOption1.setOnClickListener {
            MyPreferences(this).isLinear = true
            binding.tvGridValue.text = "List"
            dialog.dismiss()
        }

        btnOption2.setOnClickListener {
            MyPreferences(this).isLinear = false
            binding.tvGridValue.text = "Block"
            dialog.dismiss()
        }

        dialog.show()
    }


    private fun showThemeChangeDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_change_theme, null)
        val btnOption1 = dialogView.findViewById<RadioButton>(R.id.btnOption1)
        val btnOption2 = dialogView.findViewById<RadioButton>(R.id.btnOption2)
        val btnOption3 = dialogView.findViewById<RadioButton>(R.id.btnOption3)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        when(MyPreferences(this).darkMode){
            0 -> btnOption1.toggle()
            1 -> btnOption2.toggle()
            2 -> btnOption3.toggle()

        }

        btnOption1.setOnClickListener {
            MyPreferences(this).darkMode = 0
            binding.tvThemeValue.text = "Light"
            dialog.dismiss()
        }

        btnOption2.setOnClickListener {
            MyPreferences(this).darkMode = 1
            binding.tvThemeValue.text = "Dark"
            dialog.dismiss()
        }

        btnOption3.setOnClickListener {
            MyPreferences(this).darkMode = 2
            binding.tvThemeValue.text = "System"
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onBackPressed() {

        if(MyPreferences(this).isChanged){
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            MyPreferences(this).isChanged = false
            startActivity(intent)
            finish()
        } else {
            super.onBackPressed()

        }
    }
}
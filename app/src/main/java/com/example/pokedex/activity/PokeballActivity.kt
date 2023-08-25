package com.example.pokedex.activity

import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.ScaleAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.example.pokedex.R
import com.example.pokedex.databinding.ActivityPokeballBinding
import com.example.pokedex.model.PokemonSmall
import com.example.pokedex.repository.Repository
import com.example.pokedex.utility.CircularFillDrawable
import com.example.pokedex.utility.ColorUtils
import com.example.pokedex.utility.TypeUtils
import com.example.pokedex.viewmodel.MainViewModel
import com.example.pokedex.viewmodel.MainViewModelFactory
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class PokeballActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPokeballBinding

    private lateinit var pokemon : PokemonSmall


    private val mainMvvm: MainViewModel by lazy {
        val repository = Repository()
        val mainViewModelFactory = MainViewModelFactory(repository, application)
        ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokeballBinding.inflate(layoutInflater)
        setContentView(binding.root)
        WindowCompat.setDecorFitsSystemWindows(window, false)

//        window.navigationBarColor = resources.getColor(R.color.transparent)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.transparent)


        val base64 = intent.extras?.getString("BASE64")
        val data = intent.extras?.getString("POKEMON_SMALL_LIST")

        mainMvvm.observePokemonListLiveData().value = Json.decodeFromString(data!!)


        mainMvvm.fetchPokemonFromApi(base64!!)

        mainMvvm.onApiResult = { pokemonSmall, prob ->

            pokemon = pokemonSmall
            // Pokemon Caught Confirm
            if(prob > 0.1) {
                Glide.with(this)
                    .load(pokemonSmall.imageurl)
                    .into(binding.ivPokemonImage)


//                binding.ivPokemonImage.animate()
                setCaughtView(pokemonSmall)
                // Go to Pokemon Activity

                binding.btnHome.setOnClickListener {
                    onBackPressed()
                }
                binding.ivBack.setOnClickListener {
                    onBackPressed()
                }

                binding.pokemonCaught.visibility = View.VISIBLE


                binding.btnCollect.setOnClickListener {
                    val intent = Intent(this, PokemonActivity::class.java)
                    val pokemonList = mainMvvm.getPokemonEvolutionList(pokemon)

                    val pokemonData = Json.encodeToString(pokemonList)
                    intent.apply {
                        putExtra("TRANSITION_NAME", pokemon.name)
                        putExtra("POKEMON", pokemonData)
                        putExtra("SOURCE", "RV")
                    }

                    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this,
                        binding.ivPokemonImage,
                        pokemon.name
                    )
                    startActivity(intent, options.toBundle())
                }
//                    startActivity(intent)
            } else {
                Glide.with(this)
                    .load(R.drawable.no_pokemon)
                    .downsample(DownsampleStrategy.AT_MOST)
                    .into(binding.ivPokemonImage)

                binding.btnHome.setOnClickListener {
                    onBackPressed()
                }
            }
        }



    }

    private fun setCaughtView(pokemonSmall : PokemonSmall) {
        binding.apply {

            tvPokemonName.text = pokemonSmall.name

            val pokemonType1 = pokemon.typeofpokemon[0]

            val color = ColorUtils.getColorForString(pokemonType1)


            pokemonId.text = pokemonSmall.id
            // Setting 1st Type Card
            tvPokemonType1.text = pokemonType1
            val pokemonType1Icon = TypeUtils.typeMap[pokemonType1]
            cvPokemonType1.background.setTint(Color.parseColor("#CC$color"))
            Glide.with(this@PokeballActivity)
                .load(pokemonType1Icon)
                .into(ivPokemonType1)

            // Type 2
            var color2 = ""
            if(pokemonSmall.typeofpokemon.size >= 2){
                cvPokemonType2.visibility = View.VISIBLE

                val pokemonType2 = pokemon.typeofpokemon[1]
                tvPokemonType2.text = pokemonType2

                color2 = ColorUtils.getColorForString(pokemonType2)
                cvPokemonType2.background.setTint(Color.parseColor("#CC$color2"))

                val pokemonType2Icon = TypeUtils.typeMap[pokemonType2]
                Glide.with(this@PokeballActivity)
                    .load(pokemonType2Icon)
                    .into(ivPokemonType2)
            }

            btnHome.background.setTint(Color.parseColor("#88$color"))
            btnCollect.background.setTint(Color.parseColor("#AA$color"))

            btnHome.visibility = View.VISIBLE
            btnCollect.visibility = View.VISIBLE

            color2 = color2.ifEmpty { "00000000" }
            animateBackgroundColor(color, color2)

            val scaleAnimation = ScaleAnimation(
                0.3f, 1f,  // Start and end scale X
                0.3f, 1f,  // Start and end scale Y
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,  // Pivot point X
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f   // Pivot point Y
            )
            scaleAnimation.duration = 400 // Animation duration in milliseconds

            ivPokemonImage.startAnimation(scaleAnimation)


        }
    }


    private fun animateBackgroundColor(color: String, color2 : String) {
        val startColor = Color.parseColor("#$color2") // Replace with your starting color string
        val endColor = Color.parseColor("#FF$color") // Replace with your target color string
        val circularFillDrawable = CircularFillDrawable(startColor, endColor)
        binding.view.background = circularFillDrawable


        val radiusAnimator = ValueAnimator.ofFloat(.2f, Math.max(binding.view.width, binding.view.height).toFloat())
        radiusAnimator.duration = 1500 // Duration of the animation in milliseconds
        radiusAnimator.interpolator = AccelerateDecelerateInterpolator()

        radiusAnimator.addUpdateListener { animator ->
            val radius = animator.animatedValue as Float
            circularFillDrawable.setRadius(radius)
        }

        radiusAnimator.start()

    }

    override fun onStop() {
        super.onStop()
        finish()


    }
}
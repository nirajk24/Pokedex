package com.example.pokedex.activity

import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
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
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonSmall
import com.example.pokedex.repository.MyPreferences
import com.example.pokedex.repository.Repository
import com.example.pokedex.utility.CircularFillDrawable
import com.example.pokedex.utility.ColorUtils
import com.example.pokedex.utility.TypeUtils
import com.example.pokedex.viewmodel.MainViewModel
import com.example.pokedex.viewmodel.MainViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class PokeballActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPokeballBinding

    private lateinit var pokemonCaught : Pokemon


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



        Glide.with(this)
            .asGif()
            .load(R.drawable.pokeball_loading_new)
            .into(binding.ivPokemonLoading)

//        window.navigationBarColor = resources.getColor(R.color.transparent)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.transparent)



        val base64 = intent.extras?.getString("BASE64")
//        val data = intent.extras?.getString("POKEMON_SMALL_LIST")


//        mainMvvm.observePokemonListLiveData().value = Json.decodeFromString(data!!)

        initializeData()


        mainMvvm.fetchPokemonFromApi(base64!!)

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }


        onApiResult()



    }

    private fun initializeData() {
        val inputStream = resources.openRawResource(R.raw.pokemon)
        mainMvvm.initializePokemon(inputStream)
        inputStream.close()
    }

    private fun onApiResult() {
        mainMvvm.onApiResult = { pokemon, prob ->

            Handler(Looper.getMainLooper()).postDelayed({


            binding.ivPokemonLoading.visibility = View.GONE
            pokemonCaught = pokemon
            // Pokemon Caught Confirm
            if(prob > 0.1) {
                Glide.with(this)
                    .load(pokemon.imageurl)
                    .into(binding.ivPokemonImage)

//                binding.ivPokemonImage.animate()
                setCaughtView(pokemonCaught)
                // Go to Pokemon Activity

                binding.btnHome.setOnClickListener {
                    onBackPressed()
                }

                binding.pokemonCaught.visibility = View.VISIBLE


                binding.btnCollect.setOnClickListener {
                    intentToPokemonActivity()
                }
            } else {
                binding.ivNoPokemonImage.visibility = View.VISIBLE
                binding.tvNoPokemonFound.visibility = View.VISIBLE
                Glide.with(this)
                    .load(R.drawable.no_pokemon)
                    .downsample(DownsampleStrategy.AT_MOST)
                    .into(binding.ivNoPokemonImage)



                binding.btnHome.setOnClickListener {
                    onBackPressed()
                }
            }
            }, 2000)

        }
    }

    private fun intentToPokemonActivity() {
        CoroutineScope(Dispatchers.Default).launch {
            val intent = Intent(this@PokeballActivity, PokemonActivity::class.java)
            val pokemonList = mainMvvm.getPokemonEvolutionList(pokemonCaught)

            MyPreferences(this@PokeballActivity).addCollectedPokemon(mainMvvm.getIdFromString(pokemonList[0].id))

            val pokemonData = Json.encodeToString(pokemonList)
            intent.apply {
                putExtra("TRANSITION_NAME", pokemonCaught.name)
                putExtra("POKEMON", pokemonData)
                putExtra("SOURCE", "NOT_RV")
            }

            withContext(Dispatchers.Main) {
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this@PokeballActivity,
                    binding.ivPokemonImage,
                    pokemonCaught.name
                )
                startActivity(intent, options.toBundle())
            }
        }
    }

    private fun setCaughtView(pokemon: Pokemon) {
        binding.apply {

            tvPokemonName.text = pokemon.name

            val pokemonType1 = pokemon.typeofpokemon[0]

            val color = ColorUtils.getColorForString(pokemonType1)


            pokemonId.text = pokemon.id
            // Setting 1st Type Card
            cvPokemonType1.visibility = View.VISIBLE
            tvPokemonType1.text = pokemonType1
            val pokemonType1Icon = TypeUtils.typeMap[pokemonType1]
            cvPokemonType1.background.setTint(Color.parseColor("#CC$color"))
            Glide.with(this@PokeballActivity)
                .load(pokemonType1Icon)
                .into(ivPokemonType1)

            // Type 2
            var color2 = ""
            if(pokemon.typeofpokemon.size >= 2){
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

//            btnHome.visibility = View.VISIBLE
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
        val startColor = Color.parseColor("#000000") // Replace with your starting color string
        val endColor = Color.parseColor("#FF$color") // Replace with your target color string
        val circularFillDrawable = CircularFillDrawable(startColor, endColor)
        binding.view.background = circularFillDrawable


        val radiusAnimator = ValueAnimator.ofFloat(.01f, Math.max(binding.view.width, binding.view.height).toFloat())
        radiusAnimator.duration = 1000 // Duration of the animation in milliseconds
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
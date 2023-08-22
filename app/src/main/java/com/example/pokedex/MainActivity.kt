package com.example.pokedex

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Rect
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.adapter.PokemonAdapter
import com.example.pokedex.databinding.ActivityMainBinding
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonSmall
import com.example.pokedex.model.readCsvLineByIndex
import com.example.pokedex.repository.Repository
import com.example.pokedex.viewmodel.MainViewModel
import com.example.pokedex.viewmodel.MainViewModelFactory
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.IOException


// Constants for request codes
private const val CAMERA_PERMISSION_REQUEST = 101
private const val STORAGE_PERMISSION_REQUEST = 13

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var selectedImageBitmap: Bitmap? = null

    // Adapter
    private lateinit var pokemonAdapter : PokemonAdapter

    private val mainMvvm : MainViewModel by lazy{
        val repository = Repository()
        val mainViewModelFactory = MainViewModelFactory(repository, application)
        ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        WindowCompat.setDecorFitsSystemWindows(window, false)

//        val screenHeight = Resources.getSystem().displayMetrics.heightPixels
//        val navigationBarHeight = getNavigationBarHeight(this)
//        val availableScreenHeight = screenHeight - navigationBarHeight
//
//        val fab: FloatingActionButton = findViewById(R.id.fabPokeball) // Replace with your FAB reference
//
//        val layoutParams = fab.layoutParams as RelativeLayout.LayoutParams
//        layoutParams.bottomMargin = availableScreenHeight / 4 // Adjust this value as needed
//        fab.layoutParams = layoutParams

//
        binding.btnCamera.setOnClickListener {
            requestCameraPermission()
            hideFabButtons()
        }

        binding.btnStorage.setOnClickListener {
            requestStoragePermission()
            hideFabButtons()
        }

        initializeData()

        intentToProfile()

        prepareRecyclerView()
        initializeRecyclerView()


        onPokemonItemClick()

        implementSearchView()



        setUpButton()

        setFabButton()


    }

    private fun setFabButton() {
        binding.apply {
            fabPokeball.setOnClickListener {
                if(btnCamera.isVisible or btnStorage.isVisible){
                    hideFabButtons()
                } else {
                   showFabButtons()
                }
            }
        }
    }

    private fun showFabButtons() {
        binding.apply {
            btnCamera.visibility = View.VISIBLE
            btnStorage.visibility = View.VISIBLE
            fabPokeball.setImageResource(R.drawable.ic_close)
        }
    }

    private fun hideFabButtons() {
        binding.apply {
            btnCamera.visibility = View.GONE
            btnStorage.visibility = View.GONE
            fabPokeball.setImageResource(R.drawable.pokeball)
        }
    }


    private fun implementSearchView() {
        val searchView = binding.searchView
        val searchIcon = searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_button)
        val closeIcon = searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
        val searchPlate = searchView.findViewById<View>(androidx.appcompat.R.id.search_plate)

        searchIcon.setOnClickListener {
            // Expand animation
            searchView.isIconified = false
            searchPlate.alpha = 0f
            searchPlate.animate().alpha(1f).setDuration(300).start()
        }

        closeIcon.setOnClickListener {
            // Collapse animation
            searchPlate.animate().alpha(0f).setDuration(300).withEndAction {
                searchView.isIconified = true
                searchPlate.alpha = 1f
            }.start()
        }

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle query submission
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Animate the search plate based on query text
                val filteredPokemons = mainMvvm.filterPokemons(newText.orEmpty())

                pokemonAdapter.differ.submitList(filteredPokemons)
                return true
            }
        })

        setUpHint()
    }

    private fun setUpHint() {

        val hints = listOf("Search Charmander", "Search Fire", "Search #004",
            "Search Chikorita", "Search Grass", "Search #152",
            "Search Palkia", "Search Water", "Search #484") // List of hints to cycle through
        var currentHintIndex = 0

        val handler = Handler()
        val color1 = Color.parseColor("#CCEE8130") // Fire Color
        val color2 =  Color.parseColor("#CC7AC74C") // Grass color
        val color3 =  Color.parseColor("#CC6390F0") // Water color

        val initialColor = Color.parseColor("#CCEE8130") // Initial color

        binding.searchView.queryHint = SpannableString(hints[currentHintIndex]).apply {
            setSpan(ForegroundColorSpan(initialColor), 6, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        handler.post(object : Runnable {
            override fun run() {
                currentHintIndex = (currentHintIndex + 1) % hints.size

                val currentColor = when (currentHintIndex) {
                    0, 1, 2 -> color1
                    3, 4, 5 -> color2
                    else -> color1
                }

                binding.searchView.queryHint = SpannableString(hints[currentHintIndex]).apply {
                    setSpan(ForegroundColorSpan(currentColor), 6, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//                  coloredHint.setSpan(StyleSpan(Typeface.BOLD), 6, hint.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                }

                handler.postDelayed(this, 4000) // Repeat every 4 seconds
            }
        })

        // Stop the handler when you're done
        binding.searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                handler.removeCallbacksAndMessages(null)
            }
        }
    }


    private fun setUpButton() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvPokemon.layoutManager = layoutManager

        binding.rvPokemon.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // Get the first visible item position
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                // Toggle the visibility of the button based on the scroll position
                binding.btnGoToTop.visibility = if (firstVisibleItemPosition > 0) View.VISIBLE else View.GONE
            }
        })

        binding.btnGoToTop.setOnClickListener {
            binding.rvPokemon.smoothScrollToPosition(0)
//            binding.rvPokemon.scrollToPosition(0)
            binding.btnGoToTop.visibility = View.GONE
        }

        binding.btnGoToTop.setOnLongClickListener {
            binding.rvPokemon.scrollToPosition(0)
            binding.btnGoToTop.visibility = View.GONE
            true
        }


    }

    // Loading Pokemon data from Json
    private fun initializeData() {
        val inputStream = resources.openRawResource(R.raw.pokemon_small)
        mainMvvm.initializePokemon(inputStream)
    }

    // Profile section
    private fun intentToProfile() {
        binding.rlProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }

    // Preparing Pokemon Recycler View
    private fun prepareRecyclerView() {
        pokemonAdapter = PokemonAdapter()
        binding.rvPokemon.apply {
//            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = pokemonAdapter
            setHasFixedSize(true)
            setItemViewCacheSize(40)
        }
    }

    // Observing data and submitting the list
    private fun initializeRecyclerView() {
        mainMvvm.observePokemonListLiveData().observe(this, Observer {pokemonList ->
            pokemonAdapter.differ.submitList(pokemonList)
        })
    }



    // Set om click listener on Pokemon Item
    private fun onPokemonItemClick(){
        pokemonAdapter.onItemClick = { pokemon, transitionName, sharedImageView ->
            val intent = Intent(this, PokemonActivity::class.java)
            val pokemonList = mainMvvm.getPokemonEvolutionList(pokemon)
            Log.d("CHECK", pokemonList.toString())
            val data = Json.encodeToString(pokemonList)
            intent.apply {
                putExtra("TRANSITION_NAME", ViewCompat.getTransitionName(sharedImageView))
                putExtra("POKEMON", data)
            }

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                sharedImageView,
                ViewCompat.getTransitionName(sharedImageView)!!
            )
            startActivity(intent, options.toBundle())
        }
    }
    // <-- Permission and Camera Section -->

    // Launching Camera
    private val takePictureLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageBitmap = result.data?.extras?.get("data") as Bitmap
                selectedImageBitmap = Bitmap.createScaledBitmap(imageBitmap, 224, 224, true)

                Log.d("SIZE",
                    (selectedImageBitmap!!.width.toString() + " "  + selectedImageBitmap!!.height).toString()
                )

                // Handle the captured image
                val base64 = mainMvvm.convertToByte64(selectedImageBitmap!!)
                mainMvvm.getPokemonNameByImage(base64)  // Sets the Current Pokemon

                TODO("Dialog Page Appears")
            }
        }

    // Launching Storage
    private val choosePictureLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri = result.data?.data
                try {
                    imageUri?.let {
                        val imageStream = contentResolver.openInputStream(it)
                        val imageBitmap = BitmapFactory.decodeStream(imageStream)
                        selectedImageBitmap = Bitmap.createScaledBitmap(imageBitmap, 224, 224, true)
                        Log.d("SIZE",
                            (selectedImageBitmap!!.width.toString() + " "  + selectedImageBitmap!!.height).toString()
                        )

                        // Handle the selected image
                        val base64 = mainMvvm.convertToByte64(selectedImageBitmap!!)
                        mainMvvm.getPokemonNameByImage(base64) // Sets the Current Pokemon

                        TODO("Dialog Page Appears")
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }






    // Requesting Permission - Camera & Storage
    private fun requestCameraPermission() {
        val hasCameraPermission = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        if (!hasCameraPermission) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST
            )
        } else {
            dispatchTakePictureIntent()
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun requestStoragePermission(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    13
                )
            }else {
                dispatchChoosePictureIntent()
            }
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_MEDIA_IMAGES
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES),
                    13
                )
            }else {
                dispatchChoosePictureIntent()
            }
        }

        return true
    }

    // Launching Intent to select image and camera
    private fun dispatchChoosePictureIntent() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        choosePictureLauncher.launch(intent)
    }
    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePictureLauncher.launch(takePictureIntent)
    }


    // Launching Camera or Storage intent after permission Granted for first time
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent()
            } else {
                // Handle case when camera permission is denied
            }
        } else if (requestCode == STORAGE_PERMISSION_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchChoosePictureIntent()
            } else {
                // Handle case when storage permission is denied
            }
        }
    }

}
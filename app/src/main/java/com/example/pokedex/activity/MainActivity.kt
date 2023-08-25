package com.example.pokedex.activity

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.example.pokedex.R
import com.example.pokedex.adapter.PokemonAdapter
import com.example.pokedex.adapter.PokemonGridAdapter
import com.example.pokedex.databinding.ActivityMainBinding
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

    private lateinit var dialog: Dialog

    private var isLinearLayout = true
    // Adapter
    private lateinit var pokemonAdapter: PokemonAdapter

    private lateinit var pokemonGridAdapter: PokemonGridAdapter

    private val mainMvvm: MainViewModel by lazy {
        val repository = Repository()
        val mainViewModelFactory = MainViewModelFactory(repository, application)
        ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.S)
            installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        window.setDecorFitsSystemWindows(false)


        dialog = Dialog(this, R.style.FullScreenDialogStyle)
//        showFullscreenDialog()

//        binding.shimmerLayoutHome.startShimmer()
//
//        Handler(Looper.getMainLooper()).postDelayed({
//            //Do something after 100ms
//            // Hide shimmer effect
//            binding.shimmerLayoutHome.stopShimmer()
//            binding.shimmerLayoutHome.visibility = View.GONE
//            binding.shimmerCardView.visibility = View.GONE
//            // Make the view visible again
//        }, 2000)

        binding.shimmerLayoutHome.visibility = View.GONE
        binding.shimmerCardView.visibility = View.GONE

        prepareRecyclerView()
        initializeRecyclerView()


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




        onPokemonItemClick()

        implementSearchView()

        setUpButton()

        setFabButton()

//        observeCurrentPokemon()

        setUpDialogBox()


    }

    private fun setUpDialogBox() {

        mainMvvm.onApiResult = { pokemonSmall, prob ->

            // Pokemon Caught Confirm
            if(prob > 0.5) {
                Glide.with(this)
                    .load(pokemonSmall.imageurl)
                    .into(dialog.findViewById(R.id.ivPokemonImage))

                dialog.apply {
                    findViewById<ConstraintLayout>(R.id.pokemonCaught).visibility = View.VISIBLE
                }

//            ViewCompat.setTransitionName(
//                dialog.findViewById(R.id.imgCurrentPokemon),
//                pokemonSmall.name
//            );

//            ViewCompat.setTransitionName(holder.binding.ivPokemonThumb, pokemon.name);


                // Go to Pokemon Activity
                dialog.findViewById<Button>(R.id.btnCollect).setOnClickListener {
//                dialog.setContentView(0)


                    val intent = Intent(this, PokemonActivity::class.java)
                    val pokemonList = mainMvvm.getPokemonEvolutionList(pokemonSmall)
                    val sharedImageView = dialog.findViewById<ImageView>(R.id.ivPokemonImage)

                    val data = Json.encodeToString(pokemonList)
                    intent.apply {
                        putExtra("TRANSITION_NAME", pokemonSmall.name)
                        putExtra("POKEMON", data)
                        putExtra("SOURCE", "DIALOG")
                    }

//                    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                        this,
//                        sharedImageView,
//                        pokemonSmall.name
//                    )

//                    startActivity(intent, options.toBundle())
                    startActivity(intent)

                }
            } else {
                Glide.with(this)
                    .load(R.drawable.no_pokemon)
                    .downsample(DownsampleStrategy.AT_MOST)
                    .into(dialog.findViewById(R.id.ivPokemonImage))
            }
        }
    }


    private fun setFabButton() {
        binding.apply {
            fabPokeball.setOnClickListener {
                if (btnCamera.isVisible or btnStorage.isVisible) {
                    hideFabButtons()
                } else {
                    showFabButtons()
                }
            }
        }
    }

    private fun showFabButtons() {

        val animateCamera = TranslateAnimation(100f, 0f, 150f, 0f)
        animateCamera.duration = 100;

        val animateStorage = TranslateAnimation(100f, 0f, 100f, 0f)
        animateStorage.duration = 150

        binding.apply {
            btnCamera.startAnimation(animateCamera);
            btnCamera.visibility = View.VISIBLE

            btnStorage.startAnimation(animateStorage);
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

        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle query submission
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Animate the search plate based on query text
                val filteredPokemons = mainMvvm.filterPokemons(newText.orEmpty())

                if (isLinearLayout) {
                    pokemonAdapter.differ.submitList(filteredPokemons)
                } else {
                    pokemonGridAdapter.differ.submitList(filteredPokemons)
                }
                if (newText.isNullOrEmpty() ) {
                    mainMvvm.observePokemonListLiveData().observe(this@MainActivity, Observer {pokemonList ->
                        if(isLinearLayout) pokemonAdapter.differ.submitList(pokemonList)
                        else pokemonGridAdapter.differ.submitList(pokemonList)
                    })
                }
                return true
            }
        })

        setUpHint()
    }

    private fun setUpHint() {

        val hints = listOf(
            "Search Charmander", "Search Fire", "Search #004",
            "Search Chikorita", "Search Grass", "Search #152",
            "Search Gastly", "Search Ghost", "Search #091"
        ) // List of hints to cycle through
        var currentHintIndex = 0

        val handler = Handler()
        val color1 = Color.parseColor("#CCEE8130") // Fire Color
        val color2 = Color.parseColor("#CC7AC74C") // Grass color
        val color3 = Color.parseColor("#DDA98FF3") // Water color

        val initialColor = Color.parseColor("#CCEE8130") // Initial color

        binding.searchView.queryHint = SpannableString(hints[currentHintIndex]).apply {
            setSpan(
                ForegroundColorSpan(initialColor),
                6,
                length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        handler.post(object : Runnable {
            override fun run() {
                currentHintIndex = (currentHintIndex + 1) % hints.size

                val currentColor = when (currentHintIndex) {
                    0, 1, 2 -> color1
                    3, 4, 5 -> color2
                    else -> color3
                }

                binding.searchView.queryHint = SpannableString(hints[currentHintIndex]).apply {
                    setSpan(
                        ForegroundColorSpan(currentColor),
                        6,
                        length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
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

        if (isLinearLayout) {
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

        } else {
            val layoutManager = GridLayoutManager(this, 2)
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
        }

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

    private fun showFullscreenDialog() {
        // Customize the dialog content here
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.Flying));
//        }

        dialog.setContentView(R.layout.fullscreen_dialog)
        dialog.findViewById<Button>(R.id.btnHome).setOnClickListener {
            hideFullScreenDialog()
        }
        dialog.findViewById<ImageView>(R.id.ivBack).setOnClickListener {
            hideFullScreenDialog()
        }
        Log.d("CHECK", window.decorView.toString())


//        WindowCompat.setDecorFitsSystemWindows(window, false)

        dialog.show()
    }

    private fun hideFullScreenDialog(){
        if(dialog.isShowing){
            dialog.hide()
        }
        WindowCompat.setDecorFitsSystemWindows(window, true)
    }

    // Loading Pokemon data from Json
    private fun initializeData() {
        val inputStream = resources.openRawResource(R.raw.pokemon_small)
        mainMvvm.initializePokemon(inputStream)
        inputStream.close()
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
        val assetManager: AssetManager? = assets

        pokemonAdapter = PokemonAdapter(assetManager!!)
        pokemonGridAdapter = PokemonGridAdapter()

        binding.rvPokemon.apply {
            if (isLinearLayout) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = pokemonAdapter

            } else {
                setMargins(6, 8, 10, 8)
                layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                adapter = pokemonGridAdapter
            }
            setHasFixedSize(true)
            setItemViewCacheSize(20)
        }
    }

    fun View.setMargins(
        leftMarginDp: Int? = null,
        topMarginDp: Int? = null,
        rightMarginDp: Int? = null,
        bottomMarginDp: Int? = null
    ) {
        if (layoutParams is ViewGroup.MarginLayoutParams) {
            val params = layoutParams as ViewGroup.MarginLayoutParams
            leftMarginDp?.run { params.leftMargin = this.dpToPx(context) }
            topMarginDp?.run { params.topMargin = this.dpToPx(context) }
            rightMarginDp?.run { params.rightMargin = this.dpToPx(context) }
            bottomMarginDp?.run { params.bottomMargin = this.dpToPx(context) }
            requestLayout()
        }
    }

    fun Int.dpToPx(context: Context): Int {
        val metrics = context.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), metrics).toInt()
    }

    // Observing data and submitting the list
    private fun initializeRecyclerView() {
        mainMvvm.observePokemonListLiveData().observe(this, Observer {pokemonList ->
            if(isLinearLayout) pokemonAdapter.differ.submitList(pokemonList)
            else pokemonGridAdapter.differ.submitList(pokemonList)

        })
    }



    // Set om click listener on Pokemon Item
    private fun onPokemonItemClick(){
        pokemonAdapter.onItemClick = { pokemon, transitionName, sharedImageView ->
            val intent = Intent(this, PokemonActivity::class.java)
            val pokemonList = mainMvvm.getPokemonEvolutionList(pokemon)

            val data = Json.encodeToString(pokemonList)
            intent.apply {
                putExtra("TRANSITION_NAME", ViewCompat.getTransitionName(sharedImageView))
                putExtra("POKEMON", data)
                putExtra("SOURCE", "RV")
            }

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                sharedImageView,
                ViewCompat.getTransitionName(sharedImageView)!!
            )
            startActivity(intent, options.toBundle())
        }

        pokemonGridAdapter.onItemClick = { pokemon, transitionName, sharedImageView ->
            val intent = Intent(this, PokemonActivity::class.java)
            val pokemonList = mainMvvm.getPokemonEvolutionList(pokemon)

            val data = Json.encodeToString(pokemonList)
            intent.apply {
                putExtra("TRANSITION_NAME", ViewCompat.getTransitionName(sharedImageView))
                putExtra("POKEMON", data)
                putExtra("SOURCE", "RV")
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
//                mainMvvm.getPokemonNameByImage(base64)  // Sets the Current Pokemon



//                dialog = Dialog(this, R.style.FullScreenDialogStyle)
//                showFullscreenDialog()
                intentToPokeballActivity(base64)

//                TODO("Dialog Page Appears")
            }


        }

    fun intentToPokeballActivity(base64 : String){
        val intent = Intent(this, PokeballActivity::class.java)
        intent.putExtra("BASE64", base64)
        var pokemonSmallList = ""
        mainMvvm.observePokemonListLiveData().observe(this, Observer {
            pokemonSmallList = Json.encodeToString(it)
        })
        Log.d("EXTRA", pokemonSmallList)
        intent.putExtra("POKEMON_SMALL_LIST", pokemonSmallList)
        startActivity(intent)
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

                        // Handle the selected image
                        val base64 = mainMvvm.convertToByte64(selectedImageBitmap!!)
//                        mainMvvm.getPokemonNameByImage(base64) // Sets the Current Pokemon

//                        showFullscreenDialog()

                        intentToPokeballActivity(base64)
//                        TODO("Dialog Page Appears")
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

    override fun onStop() {
        super.onStop()
        hideFullScreenDialog()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        hideFullScreenDialog()
    }

}
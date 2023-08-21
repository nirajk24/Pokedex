package com.example.pokedex

import android.app.ActionBar
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.adapter.PokemonAdapter
import com.example.pokedex.databinding.ActivityMainBinding
import com.example.pokedex.model.Pokemon
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
//        binding.btnCamera.setOnClickListener {
//            requestCameraPermission()
//        }
//
//        binding.btnStorage.setOnClickListener {
//            requestStoragePermission()
//        }

        initializeData()

        intentToProfile()

        prepareRecyclerView()
        initializeRecyclerView()


        onPokemonItemClick()


    }

    private fun onPokemonItemClick(){
        pokemonAdapter.onItemClick = { pokemon, transitionName, sharedImageView ->
            val intent = Intent(this, PokemonActivity::class.java)
            val pokemonList = getPokemonEvolutionList(pokemon)
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

    private fun getPokemonEvolutionList(pokemon: Pokemon): List<Pokemon> {
        val pokemonList = mutableListOf<Pokemon>()
        pokemonList.add(pokemon)
        for(id in pokemon.evolutions){
            val idInt = getIdFromString(id)
            mainMvvm.observePokemonListLiveData().observe(this, Observer {
                pokemonList.add(it[idInt - 1])
            })
        }

        return pokemonList
    }

    private fun getIdFromString(id: String): Int {
        var idInt = 0
        val length = id.length
        for(i in 1 until length){
            idInt = (idInt * 10) + id[i].toString().toInt()
        }

        return idInt
    }

    private fun intentToProfile() {
        binding.rlProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }





    private fun initializeRecyclerView() {
        mainMvvm.observePokemonListLiveData().observe(this, Observer {pokemonList ->
            pokemonAdapter.differ.submitList(pokemonList)
        })
    }


    // Loading Pokemon data from Json
    private fun initializeData() {
        val inputStream = resources.openRawResource(R.raw.pokemon)
        mainMvvm.initializePokemon(inputStream)
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
package com.example.tinderfordogs

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.tinderfordogs.UI.likedDogs.FavouriteDogsActivity
import com.example.tinderfordogs.adapter.DogTinderAdapter
import com.example.tinderfordogs.databinding.ActivityMainBinding
import com.example.tinderfordogs.model.TinderCardModel
import com.example.tinderfordogs.viewmodel.MainActivityVIewModel
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity  : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    lateinit var manager:CardStackLayoutManager
    private val viewModel: MainActivityVIewModel by viewModels()

    lateinit var listOfImges:List<TinderCardModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        binding.ivLike.setOnClickListener{
            startActivity(Intent(this,FavouriteDogsActivity::class.java))
            finish()
        }

        viewModel.loading.observe(this, Observer { it->
            if(it){
                binding.pbMain.isVisible=true
            }else {
                binding.pbMain.isVisible=false
                listOfImges=ArrayList<TinderCardModel>()
                val adapter= DogTinderAdapter(this,listOfImges)
                binding.cardStackView.layoutManager=manager
                binding.cardStackView.itemAnimator= DefaultItemAnimator()
                viewModel.dogImages.observe(this, Observer { images ->
                    listOfImges = images
                    adapter.updateData(images)
                })
                binding.cardStackView.adapter=adapter
            }
        })




    }

    init {
        manager= CardStackLayoutManager(this,object :CardStackListener{
            override fun onCardDragging(direction: Direction?, ratio: Float) {
                if(direction?.equals(Direction.Left) == true){
                    binding.tvRejected.isVisible=true
                }
                else{
                    binding.tvRejected.isVisible=false
                }

                if (direction?.equals(Direction.Right) == true) {
                    binding.tvAccepted.isVisible = true
                    lifecycleScope.launch(Dispatchers.IO) {
                        saveDog(manager.topPosition )
                    }


                } else {
                    binding.tvAccepted.isVisible = false
                }


            }

            override fun onCardSwiped(direction: Direction?) {
                if(manager.topPosition==listOfImges.size){
                    Toast.makeText(this@MainActivity, "All Cards Swiped", Toast.LENGTH_SHORT).show()
                    viewModel.fetchImages()
                }
            }

            override fun onCardRewound() {
            }

            override fun onCardCanceled() {
            }

            override fun onCardAppeared(view: View?, position: Int) {
            }

            override fun onCardDisappeared(view: View?, position: Int) {
                binding.tvAccepted.isVisible=false
                binding.tvRejected.isVisible=false
            }

        })

        manager.setVisibleCount(5)
        manager.setTranslationInterval(0.6f)
        manager.setScaleInterval(0.8f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.HORIZONTAL)
    }

    private suspend fun saveDog(i: Int) {
        viewModel.addDog(listOfImges[i])
    }
}
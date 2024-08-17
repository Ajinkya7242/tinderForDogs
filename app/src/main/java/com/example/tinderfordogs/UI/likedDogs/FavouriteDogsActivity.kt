package com.example.tinderfordogs.UI.likedDogs

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tinderfordogs.MainActivity
import com.example.tinderfordogs.R
import com.example.tinderfordogs.UI.intro.LoginOptionsActivity
import com.example.tinderfordogs.adapter.FavDogsAdapter
import com.example.tinderfordogs.databinding.ActivityFavouriteDogsBinding
import com.example.tinderfordogs.model.TinderCardModel
import com.example.tinderfordogs.repository.FavoriteDogsRepository
import com.example.tinderfordogs.viewmodel.FavoriteDogsViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
class FavouriteDogsActivity : AppCompatActivity() {

    lateinit var binding:ActivityFavouriteDogsBinding

     var dogListNew:ArrayList<TinderCardModel> =ArrayList<TinderCardModel>()
    lateinit var adapter:FavDogsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityFavouriteDogsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val viewModel:FavoriteDogsViewModel by viewModels()

        adapter=FavDogsAdapter(this@FavouriteDogsActivity,dogListNew)


        binding.rvFavoriteDog.layoutManager = LinearLayoutManager(this)
        binding.rvFavoriteDog.adapter = adapter

        binding.ivSwap.setOnClickListener{
            onBackPressed()
        }

        lifecycleScope.launch {
            viewModel.dogList.collect { dogList ->
                Timber.d(dogList.toString())
                if (dogList.isNotEmpty()) {
                    dogListNew.clear()
                    dogListNew.addAll(dogList)
                    adapter.notifyDataSetChanged()
                }
            }
        }


        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedDog: TinderCardModel =
                    dogListNew.get(viewHolder.adapterPosition)
                val position = viewHolder.adapterPosition
                viewModel.removeDog(deletedDog)
                dogListNew.removeAt(viewHolder.adapterPosition)

                // below line is to notify our item is removed from adapter.
                adapter.notifyItemRemoved(viewHolder.adapterPosition)

            }
        }).attachToRecyclerView(binding.rvFavoriteDog)


        binding.tvLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            showLogoutConfirmationDialog()

        }



    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }



    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Logout Confirmation")
        builder.setMessage("Do you really want to Log Out?")

        builder.setPositiveButton("Yes") { dialog, _ ->
            dialog.dismiss()
            startActivity(Intent(this,LoginOptionsActivity::class.java))
            finish()
        }

        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        // Create and show the dialog
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}
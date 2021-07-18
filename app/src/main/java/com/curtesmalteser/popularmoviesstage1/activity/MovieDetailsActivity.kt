package com.curtesmalteser.popularmoviesstage1.activity

import android.content.ContentValues
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import com.curtesmalteser.popularmoviesstage1.R
import com.curtesmalteser.popularmoviesstage1.adapter.SimpleFragmentPagerAdapter
import com.curtesmalteser.popularmoviesstage1.databinding.ActivityMovieDetailsBinding
import com.curtesmalteser.popularmoviesstage1.db.MoviesContract.MoviesEntry
import com.curtesmalteser.popularmoviesstage1.utils.MoviesModel
import com.curtesmalteser.popularmoviesstage1.utils.NetworkUtils
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class MovieDetailsActivity : AppCompatActivity() {

    private var state = false

    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.background.setColorFilter(Color.argb(220, 0, 0, 0))

        if (intent.hasExtra(resources.getString(R.string.string_extra))) {

            intent.getParcelableExtra<MoviesModel>(resources.getString(R.string.string_extra))
                ?.let { model ->
                    getPoster(posterPath = model.posterPath, backdropPath = model.backdropPath)
                    binding.tvTitle.text = model.title
                    binding.voteAverage.text =
                        String.format(getString(R.string.string_vote_average), model.voteAverage)
                    binding.releaseDate.text = model.releaseDate
                    val adapter = SimpleFragmentPagerAdapter(this, supportFragmentManager)


                    binding.scrollOverviewAndTrailers.let {
                        it.viewPager.adapter = adapter
                        it.tabLayout.setupWithViewPager(it.viewPager)
                    }


                    binding.likeButton.setOnClickListener {
                        if (!state) {
                            val movieSaved = addFavoriteMovie(model).toLong()
                            if (movieSaved > 0) {
                                binding.likeButton.setImageResource(R.drawable.ic_heart_red)
                                binding.tvAddRemove.setText(R.string.string_remove_fav)
                                Toast.makeText(
                                    this@MovieDetailsActivity,
                                    "You like this movie!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                state = true
                            } else {
                                Toast.makeText(
                                    this@MovieDetailsActivity,
                                    R.string.string_error_add_fav,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            val deleted = removeFavoriteMovie(model)
                            if (deleted > 0) {
                                binding.likeButton.setImageResource(R.drawable.ic_heart_white)
                                binding.tvAddRemove.setText(R.string.string_add_fav)
                                Toast.makeText(
                                    this@MovieDetailsActivity,
                                    "You don't like this movie!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                state = false
                            }
                        }
                    }
                    val cursor = checkFavoriteMovie(model)
                    state = if (cursor!!.count > 0) {
                        binding.tvAddRemove.setText(R.string.string_remove_fav)
                        binding.likeButton.setImageResource(R.drawable.ic_heart_red)
                        true
                    } else {
                        binding.tvAddRemove.setText(R.string.string_add_fav)
                        binding.likeButton.setImageResource(R.drawable.ic_heart_white)
                        false
                    }

                    cursor.close()
                }
        }
    }

    private fun getPoster(posterPath: String, backdropPath: String) {
        Picasso.get().let {
            it.loadImage(
                uri = NetworkUtils.getPosterUrl(
                    resources.getString(R.string.poster_width_segment),
                    posterPath
                ),
                imageView = binding.posterInDetailsActivity,
                success = {
                    getBackground(
                        NetworkUtils.getPosterUrl(
                            resources.getString(R.string.poster_width_segment),
                            backdropPath
                        )
                    )
                },
                error = {
                    it.loadImage(uri = NetworkUtils.getPosterUrl(
                        resources.getString(R.string.poster_width_segment),
                        posterPath
                    ),
                        imageView = binding.posterInDetailsActivity,
                        errorResId = R.drawable.ic_heart_white,
                        success = {},
                        error = { Log.e("Picasso", "Could not fetch image") }
                    )
                }
            )
        }
    }

    private fun getBackground(backdropPath: Uri) {
        Picasso.get().let {
            it.loadImage(
                uri = backdropPath,
                imageView = binding.background,
                success = {},
                error = {
                    it.loadImage(uri = backdropPath,
                        imageView = binding.background,
                        success = {},
                        error = { Log.e("Picasso", "Could not fetch image") }
                    )
                }
            )
        }
    }

    private fun Picasso.loadImage(
        uri: Uri,
        imageView: ImageView,
        success: () -> Unit,
        error: () -> Unit,
        @DrawableRes errorResId: Int = R.drawable.drawable_background
    ) = load(uri)
        .error(errorResId)
        .into(imageView, object : Callback {
            override fun onSuccess(): Unit = success()
            override fun onError(e: Exception?): Unit = error()
        })


    private fun addFavoriteMovie(model: MoviesModel): Int {
        val cv = ContentValues()
        cv.put(MoviesEntry.COLUMN_NAME_ID, model.id)
        cv.put(MoviesEntry.COLUMN_NAME_VOTE_AVERAGE, model.voteAverage)
        cv.put(MoviesEntry.COLUMN_NAME_TITLE, model.title)
        cv.put(MoviesEntry.COLUMN_NAME_POSTER_PATH, model.posterPath)
        cv.put(MoviesEntry.COLUMN_NAME_BACKDROP_PATH, model.backdropPath)
        cv.put(MoviesEntry.COLUMN_NAME_OVERVIEW, model.overview)
        cv.put(MoviesEntry.COLUMN_NAME_RELEASE_DATE, model.releaseDate)
        val uri = contentResolver.insert(MoviesEntry.CONTENT_URI, cv)
        return if (uri != null) {
            1
        } else 0
    }

    private fun removeFavoriteMovie(model: MoviesModel): Int = MoviesEntry.CONTENT_URI
        .buildUpon()
        .appendPath(model.id.toString())
        .build().run {
            contentResolver.delete(this, null, null)
        }

    private fun checkFavoriteMovie(model: MoviesModel): Cursor? = contentResolver.query(
        MoviesEntry.CONTENT_URI,
        null,
        MoviesEntry.COLUMN_NAME_ID + " = ? ",
        arrayOf(model.id.toString()),
        null
    )

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
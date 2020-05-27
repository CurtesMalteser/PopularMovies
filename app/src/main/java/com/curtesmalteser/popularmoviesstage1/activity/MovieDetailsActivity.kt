package com.curtesmalteser.popularmoviesstage1.activity

import android.content.ContentValues
import android.database.Cursor
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import com.curtesmalteser.popularmoviesstage1.R
import com.curtesmalteser.popularmoviesstage1.adapter.SimpleFragmentPagerAdapter
import com.curtesmalteser.popularmoviesstage1.db.MoviesContract.MoviesEntry
import com.curtesmalteser.popularmoviesstage1.utils.MoviesModel
import com.curtesmalteser.popularmoviesstage1.utils.NetworkUtils
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.details_tablayout.*
import kotlinx.android.synthetic.main.single_card_view.*

class MovieDetailsActivity : AppCompatActivity() {

    private var state = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        background.setColorFilter(Color.argb(220, 0, 0, 0))

        if (intent.hasExtra(resources.getString(R.string.string_extra))) {

            intent.getParcelableExtra<MoviesModel>(resources.getString(R.string.string_extra))?.let { model ->
                getPoster(posterPath = model.posterPath, backdropPath = model.backdropPath)
                tvTitle.text = model.title
                voteAverage.text = String.format(getString(R.string.string_vote_average), model.voteAverage)
                releaseDate.text = model.releaseDate
                val adapter = SimpleFragmentPagerAdapter(this, supportFragmentManager)
                viewPager.adapter = adapter
                tabLayout.setupWithViewPager(viewPager)


                likeButton.setOnClickListener {
                    if (!state) {
                        val movieSaved = addFavoriteMovie(model).toLong()
                        if (movieSaved > 0) {
                            likeButton.setImageResource(R.drawable.ic_heart_red)
                            tvAddRemove.setText(R.string.string_remove_fav)
                            Toast.makeText(this@MovieDetailsActivity, "You like this movie!", Toast.LENGTH_SHORT).show()
                            state = true
                        } else {
                            Toast.makeText(this@MovieDetailsActivity, R.string.string_error_add_fav, Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        val deleted = removeFavoriteMovie(model)
                        if (deleted > 0) {
                            likeButton.setImageResource(R.drawable.ic_heart_white)
                            tvAddRemove.setText(R.string.string_add_fav)
                            Toast.makeText(this@MovieDetailsActivity, "You don't like this movie!", Toast.LENGTH_SHORT).show()
                            state = false
                        }
                    }
                }
                val cursor = checkFavoriteMovie(model)
                state = if (cursor!!.count > 0) {
                    tvAddRemove.setText(R.string.string_remove_fav)
                    likeButton.setImageResource(R.drawable.ic_heart_red)
                    true
                } else {
                    tvAddRemove.setText(R.string.string_add_fav)
                    likeButton.setImageResource(R.drawable.ic_heart_white)
                    false
                }

                cursor.close()
            }
        }
    }

    private fun getPoster(posterPath: String, backdropPath: String) {
        Picasso.get()
                .load(NetworkUtils.getPosterUrl(resources.getString(R.string.poster_width_segment), posterPath))
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(posterInDetailsActivity, object : Callback {
                    override fun onSuccess() {
                        getBackground(backdropPath)
                    }

                    override fun onError(e: Exception) {
                        //Try again online if cache failed
                        Picasso.get()
                                .load(NetworkUtils.getPosterUrl(resources.getString(R.string.poster_width_segment), posterPath))
                                .error(R.drawable.ic_heart_white)
                                .into(poster, object : Callback {
                                    override fun onSuccess() {}
                                    override fun onError(e: Exception) {
                                        Log.v("Picasso", "Could not fetch image")
                                    }
                                })
                    }
                })
    }

    private fun getBackground(backdropPath: String) {
        Picasso.get()
                .load(NetworkUtils.getPosterUrl(resources.getString(R.string.poster_width_segment), backdropPath))
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(background, object : Callback {
                    override fun onSuccess() {}
                    override fun onError(e: Exception) {
                        //Try again online if cache failed
                        Picasso.get()
                                .load(NetworkUtils.getPosterUrl(resources.getString(R.string.poster_width_segment), backdropPath))
                                .error(R.drawable.drawable_background)
                                .into(background, object : Callback {
                                    override fun onSuccess() {}
                                    override fun onError(e: Exception) {
                                        Log.v("Picasso", "Could not fetch image")
                                    }
                                })
                    }
                })
    }

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
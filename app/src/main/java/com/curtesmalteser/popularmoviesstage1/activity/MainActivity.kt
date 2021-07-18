package com.curtesmalteser.popularmoviesstage1.activity

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.curtesmalteser.popularmoviesstage1.R
import com.curtesmalteser.popularmoviesstage1.fragment.FavoriteMoviesFragment
import com.curtesmalteser.popularmoviesstage1.fragment.PopularMoviesFragment
import com.curtesmalteser.popularmoviesstage1.fragment.TopRatedMoviesFragment
import com.facebook.stetho.Stetho
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val selectPopularMoviesFragment by lazy { PopularMoviesFragment.newInstance() }
    private val selectTopRatedMoviesFragment by lazy { TopRatedMoviesFragment.newInstance() }
    private val selectFavoriteMoviesFragment by lazy { FavoriteMoviesFragment.newInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        Stetho.initializeWithDefaults(this)

        val bottomNavigationMenu = findViewById<BottomNavigationView>(R.id.bottomNavigationMenu).apply {
            setOnNavigationItemSelectedListener { item: MenuItem -> setFragment(item.itemId) }
        }

        if (savedInstanceState == null) {
            readPreferences().let {
                setFragment(it)
                bottomNavigationMenu.selectedItemId = it
            }
        }

    }

    private fun setFragment(@IdRes id: Int): Boolean {
        val selectedFragment: Fragment? = when (id) {
            R.id.action_popular_movies -> selectPopularMoviesFragment
            R.id.action_top_rated_movies -> selectTopRatedMoviesFragment
            R.id.action_favorite_movies -> selectFavoriteMoviesFragment
            else -> null
        }

        selectedFragment?.let {

            savePreferences(id)
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frameLayout, it)
                    .commit()
        }

        return true
    }

    private fun savePreferences(@IdRes selection: Int) = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
            .edit()
            .putInt(SELECTION, selection)
            .apply()

    private fun readPreferences(): Int = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
            .getInt(SELECTION, R.id.action_popular_movies)

    companion object {
        private const val PREFERENCES_NAME = "movies_preferences"
        private const val SELECTION = "selected_fragment"

    }
}
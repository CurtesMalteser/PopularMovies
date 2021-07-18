package com.curtesmalteser.popularmoviesstage1.ext

import android.app.Activity
import androidx.viewbinding.ViewBinding

fun <T : ViewBinding> Activity.setContentBinding(action: () -> T): T {
    return action().apply binding@{
        this@setContentBinding.setContentView(this@binding.root)
    }
}

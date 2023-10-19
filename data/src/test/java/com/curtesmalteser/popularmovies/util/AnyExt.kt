package com.curtesmalteser.popularmoviesstage1.util

import java.io.File

/**
 * Created by António Bastião on 29.09.21
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */

// based on https://stackoverflow.com/questions/29341744/android-studio-unit-testing-read-data-input-file
fun Any.loadFileAsStringOrNull(fileName: String): String? {
    return runCatching {
        javaClass.classLoader!!.run {
            val resource = getResource(fileName).toURI()
            File(resource).readText()
        }
    }.getOrNull()
}
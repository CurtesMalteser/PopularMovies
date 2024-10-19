package com.curtesmalteser.presentation.details

import com.curtesmalteser.popularmovies.data.MovieData
import com.curtesmalteser.presentation.mock.repository.MovieDetailsRepositoryMock
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

/**
 * Created by António Bastião on 19.10.2024
 * Refer to [CurtesMalteser github](https://github.com/CurtesMalteser)
 */
class FavoriteMovieUseCaseTest {

    private val movieDetailsRepositoryMock = MovieDetailsRepositoryMock()
    private val sut = ToggleFavoriteMovieUseCase(movieDetailsRepositoryMock)

    @Test
    fun toggleFavorite() = runTest {

        val movieDetails = MovieData(
            id = 1,
            title = "title",
            posterPath = "poster",
            backdropPath = "backdrop",
            overview = "overview",
            releaseDate = "releaseDate",
            voteAverage = "voteAverage",
        )

        sut.toggleFavorite(movieDetails)

        movieDetailsRepositoryMock.toggleFavoriteCalled shouldBe true
    }
}
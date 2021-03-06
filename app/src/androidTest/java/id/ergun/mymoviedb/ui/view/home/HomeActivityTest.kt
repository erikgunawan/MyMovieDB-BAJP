package id.ergun.mymoviedb.ui.view.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import id.ergun.mymoviedb.R
import id.ergun.mymoviedb.util.testing.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class HomeActivityTest {

    @get:Rule
    val rule = activityScenarioRule<HomeActivity>()

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource())
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource())
    }

    @Test
    fun loadMovies() {
        onView(withId(R.id.rv_movie))
            .check(matches(isDisplayed()))
    }

    @Test
    fun loadDetailMovie() {
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_tag_line)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_rating)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_overview)).check(matches(isDisplayed()))
    }

    @Test
    fun addFavoriteMovie() {
        onView(withId(R.id.rv_movie))
            .check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.fab_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_favorite)).perform(click())
        onView(withText(R.string.message_add_to_favorite))
            .check(matches(isDisplayed()))
    }

    @Test
    fun loadTvShows() {
        onView(withId(R.id.action_tv_shows)).perform(click())
        onView(withId(R.id.rv_tv_show))
            .check(matches(isDisplayed()))
    }

    @Test
    fun loadDetailTvShow() {
        onView(withId(R.id.action_tv_shows)).perform(click())
        onView(withId(R.id.rv_tv_show)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_tag_line)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_rating)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_overview)).check(matches(isDisplayed()))
    }

    @Test
    fun addFavoriteTvShow() {
        onView(withId(R.id.action_tv_shows)).perform(click())
        onView(withId(R.id.rv_tv_show))
            .check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_show)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.fab_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_favorite)).perform(click())
        onView(withText(R.string.message_add_to_favorite))
            .check(matches(isDisplayed()))
    }

    @Test
    fun loadFavorites() {
        onView(withId(R.id.action_favorites)).perform(click())
        onView(withText(R.string.tv_show))
            .check(matches(isDisplayed()))
        onView(withText(R.string.movie))
            .check(matches(isDisplayed()))
    }

    @Test
    fun removeFavoriteMovie() {
        onView(withId(R.id.action_favorites)).perform(click())
        onView(withText(R.string.movie))
            .check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie))
            .check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.fab_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_favorite)).perform(click())
        onView(withText(R.string.message_remove_from_favorite))
            .check(matches(isDisplayed()))
    }

    @Test
    fun removeFavoriteTvShow() {
        onView(withId(R.id.action_favorites)).perform(click())
        onView(withText(R.string.tv_show))
            .check(matches(isDisplayed()))
        onView(withText(R.string.tv_show)).perform(click())
        onView(withId(R.id.rv_tv_show))
            .check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_show)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.fab_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_favorite)).perform(click())
        onView(withText(R.string.message_remove_from_favorite))
            .check(matches(isDisplayed()))
    }
}
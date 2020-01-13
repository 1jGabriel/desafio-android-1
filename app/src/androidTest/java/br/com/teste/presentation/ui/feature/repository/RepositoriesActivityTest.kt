package br.com.teste.presentation.ui.feature.repository

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import br.com.teste.R
import br.com.teste.common.util.EspressoIdlingResource
import br.com.teste.presentation.ui.feature.repository.adapter.RepositoryAdapter
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class RepositoriesActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(RepositoriesActivity::class.java)

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun unregisterIdlingResourece() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun testActivity_inView() {
        onView(withId(R.id.container)).check(matches(isDisplayed()))

    }

    @Test
    fun testActivity_recyclerView() {
        onView(withId(R.id.repositoryList)).check(matches(isDisplayed()))
    }


    @Test
    fun navToPullRequestsActivityWhenClickOnRecyclerViewItem() {

        onView(withId(R.id.repositoryList)).check(matches(isDisplayed()))

        onView(withId(R.id.repositoryList)).perform(
            actionOnItemAtPosition<RepositoryAdapter.RepositoryViewHolder>(
                4,
                click()
            )
        )

        onView(withId(R.id.containerSecondScreen)).check(matches(isDisplayed()))

    }
}
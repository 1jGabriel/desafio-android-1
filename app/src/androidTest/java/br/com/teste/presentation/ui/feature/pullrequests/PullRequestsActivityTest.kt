package br.com.teste.presentation.ui.feature.pullrequests

import android.content.Intent
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import br.com.teste.R
import br.com.teste.common.util.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class PullRequestsActivityTest {

    @get:Rule
    val activityRule =
        object : ActivityTestRule<PullRequestsActivity>(PullRequestsActivity::class.java) {
            override fun getActivityIntent(): Intent {
                return Intent(
                    InstrumentationRegistry.getInstrumentation().targetContext,
                    PullRequestsActivity::class.java
                ).apply {
                    putExtra(PullRequestsActivity.CREATOR, "elastic")
                    putExtra(PullRequestsActivity.REPOSITORY, "elasticsearch")
                }
            }
        }

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
        Espresso.onView(ViewMatchers.withId(R.id.pullRequestsContainer)).check(
            ViewAssertions.matches(
                ViewMatchers
                    .isDisplayed()
            )
        )
    }
}
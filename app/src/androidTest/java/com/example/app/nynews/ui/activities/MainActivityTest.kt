package com.example.app.nynews.ui.activities

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.runner.AndroidJUnit4
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest{
    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun testActivityCreation() {
        scenario.onActivity { activity ->
            assertNotNull(activity)
        }
    }


    @Test
    fun testSearchArticleClick() {
        scenario.onActivity { activity ->
            activity.binding.llSearchArticle.performClick()
            val expectedIntent = Intent(activity, SearchArticlesActivity::class.java)
            expectedIntent.putExtra("SEARCH", "search")
            intended(IntentMatchers.hasComponent(SearchArticlesActivity::class.java.name))
            intended(IntentMatchers.hasExtra("SEARCH", "search"))
        }
    }


    @Test
    fun testMostViewedClick() {
        scenario.onActivity { activity ->
            activity.binding.llMostViewed.performClick()
            val expectedIntent = Intent(activity, SearchArticlesActivity::class.java)
            expectedIntent.putExtra("POPULAR_VIEWED", "viewed")
            intended(IntentMatchers.hasComponent(SearchArticlesActivity::class.java.name))
            intended(IntentMatchers.hasExtra("POPULAR_VIEWED", "viewed"))
        }
    }

    @Test
    fun testMostEmailedClick() {
        scenario.onActivity { activity ->
            activity.binding.llMostEmailed.performClick()
            val expectedIntent = Intent(activity, SearchArticlesActivity::class.java)
            expectedIntent.putExtra("POPULAR_EMAILED", "emailed")
            intended(IntentMatchers.hasComponent(SearchArticlesActivity::class.java.name))
            intended(IntentMatchers.hasExtra("POPULAR_EMAILED", "emailed"))
        }
    }

    @Test
    fun testMostSharedClick() {
        scenario.onActivity { activity ->
            activity.binding.llMostShared.performClick()
            val expectedIntent = Intent(activity, SearchArticlesActivity::class.java)
            expectedIntent.putExtra("POPULAR_SHARED", "shared")
            intended(IntentMatchers.hasComponent(SearchArticlesActivity::class.java.name))
            intended(IntentMatchers.hasExtra("POPULAR_SHARED", "shared"))
        }
    }
}
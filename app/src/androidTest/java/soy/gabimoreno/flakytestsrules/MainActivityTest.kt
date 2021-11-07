package soy.gabimoreno.flakytestsrules

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    companion object {
        private const val EXPECTED_TEXT = "Hello World!"
        private const val UNEXPECTED_TEXT = "foo"

    }

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    // Comment / Uncomment RetryFlakyTestUntilSuccessRule or RetryFlakyTestUntilFailureRule depending on the case
    @Rule
    @JvmField
    val retryFlakyTestUntilSuccessRule = RetryFlakyTestUntilSuccessRule()

//    @Rule
//    @JvmField
//    val retryFlakyTestUntilFailureRule = RetryFlakyTestUntilFailureRule()

    private lateinit var maybeExpectedText: String

    @Before
    fun setUp() {
        val suffix = if ((0..1).random() == 0) "" else "."
        maybeExpectedText = EXPECTED_TEXT + suffix
    }

    @Test
    fun checkShowsExpectedText() {
        onView(withId(R.id.tvText))
            .check(matches(withText(EXPECTED_TEXT)))
    }

    @Test
    fun checkDoesNotShowUnexpectedText() {
        onView(withId(R.id.tvText))
            .check(matches(not(withText(UNEXPECTED_TEXT))))
    }

    @Test
    fun checkShowsMaybeExpectedText() {
        onView(withId(R.id.tvText))
            .check(matches(withText(maybeExpectedText)))
    }
}

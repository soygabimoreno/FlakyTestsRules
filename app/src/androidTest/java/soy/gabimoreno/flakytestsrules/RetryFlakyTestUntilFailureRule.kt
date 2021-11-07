package soy.gabimoreno.flakytestsrules

import android.util.Log
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class RetryFlakyTestUntilFailureRule(val count: Int = 10) : TestRule {

    companion object {
        private const val TAG = "RetryFlakyTestUntilFailureRule"
    }

    override fun apply(base: Statement, description: Description): Statement = statement(base, description)

    private fun statement(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                val displayName = description.displayName
                for (i in 1 until count + 1) {
                    try {
                        base.evaluate()
                        Log.e(TAG, "$displayName: Run $i succeeded")
                    } catch (throwable: Throwable) {
                        Log.i(TAG, "$displayName: Run $i failed: ${throwable.message}")
                        return
                    }
                }
                Log.e(TAG, "$displayName: Giving up after run $count successes.")
            }
        }
    }
}

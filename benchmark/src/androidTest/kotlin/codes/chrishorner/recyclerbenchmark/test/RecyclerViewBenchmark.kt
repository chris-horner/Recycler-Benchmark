package codes.chrishorner.recyclerbenchmark.test

import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecyclerViewBenchmark {

  /*
  @get:Rule
  val benchmarkRule = BenchmarkRule()
   */

  @get:Rule
  val activityRule = ActivityTestRule(codes.chrishorner.recyclerbenchmark.ui.MainActivity::class.java)

  @UiThreadTest
  @Test
  fun scroll() {
    assertEquals(true, true)
  }
}

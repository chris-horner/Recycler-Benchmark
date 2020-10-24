package codes.chrishorner.recyclerbenchmark.test

import android.widget.CompoundButton
import android.widget.Spinner
import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.recyclerview.widget.RecyclerView
import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import codes.chrishorner.recyclerbenchmark.ui.MainActivity
import codes.chrishorner.recyclerbenchmark.ui.Mode
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecyclerViewBenchmark {

  private lateinit var recyclerView: RecyclerView
  private lateinit var typeSpinner: Spinner
  private lateinit var preformatNamesSwitch: CompoundButton
  private lateinit var preformatDatesSwitch: CompoundButton

  private var scrollDown = true

  @get:Rule
  val benchmarkRule = BenchmarkRule()

  @get:Rule
  val activityRule = ActivityTestRule(MainActivity::class.java)

  @Before
  fun setup() {
    Thread.sleep(300)
    scrollDown = true

    activityRule.runOnUiThread {
      recyclerView = activityRule.activity.recycler
      typeSpinner = activityRule.activity.typeSpinner
      preformatNamesSwitch = activityRule.activity.preformatNamesSwitch
      preformatDatesSwitch = activityRule.activity.preformatDatesSwitch

      typeSpinner.setSelection(Mode.CONSTRAINT_LAYOUT.ordinal)
      preformatNamesSwitch.isChecked = false
      preformatDatesSwitch.isChecked = false
      Thread.sleep(100)
      assertTrue(recyclerView.childCount > 0)
      assertTrue(recyclerView.height > 0)
    }
  }

  private fun getScrollAmount(): Int {
    if (scrollDown && !recyclerView.canScrollVertically(1)) {
      scrollDown = false
    } else if (!scrollDown && !recyclerView.canScrollVertically(-1)) {
      scrollDown = true
    }

    val itemHeight = recyclerView.getChildAt(recyclerView.childCount - 1).height

    return if (scrollDown) itemHeight else -itemHeight
  }

  @UiThreadTest
  @Test
  fun constraintLayout_noPreformatting() {
    typeSpinner.setSelection(Mode.CONSTRAINT_LAYOUT.ordinal)

    benchmarkRule.measureRepeated {
      recyclerView.scrollBy(0, getScrollAmount())
    }
  }

  @UiThreadTest
  @Test
  fun constraintLayout_preformatting() {
    typeSpinner.setSelection(Mode.CONSTRAINT_LAYOUT.ordinal)
    preformatNamesSwitch.isChecked = true
    preformatDatesSwitch.isChecked = true

    benchmarkRule.measureRepeated {
      recyclerView.scrollBy(0, getScrollAmount())
    }
  }

  @UiThreadTest
  @Test
  fun relativeLayout_noPreformatting() {
    typeSpinner.setSelection(Mode.RELATIVE_LAYOUT.ordinal)

    benchmarkRule.measureRepeated {
      recyclerView.scrollBy(0, getScrollAmount())
    }
  }

  @UiThreadTest
  @Test
  fun relativeLayout_preformatting() {
    typeSpinner.setSelection(Mode.RELATIVE_LAYOUT.ordinal)
    preformatNamesSwitch.isChecked = true
    preformatDatesSwitch.isChecked = true

    benchmarkRule.measureRepeated {
      recyclerView.scrollBy(0, getScrollAmount())
    }
  }

  @UiThreadTest
  @Test
  fun nestedLinears_noPreformatting() {
    typeSpinner.setSelection(Mode.NESTED_LINEARS.ordinal)

    benchmarkRule.measureRepeated {
      recyclerView.scrollBy(0, getScrollAmount())
    }
  }

  @UiThreadTest
  @Test
  fun nestedLinears_preformatting() {
    typeSpinner.setSelection(Mode.NESTED_LINEARS.ordinal)
    preformatNamesSwitch.isChecked = true
    preformatDatesSwitch.isChecked = true

    benchmarkRule.measureRepeated {
      recyclerView.scrollBy(0, getScrollAmount())
    }
  }

  @UiThreadTest
  @Test
  fun customLayout_noPreformatting() {
    typeSpinner.setSelection(Mode.CUSTOM_VIEWGROUP.ordinal)

    benchmarkRule.measureRepeated {
      recyclerView.scrollBy(0, getScrollAmount())
    }
  }

  @UiThreadTest
  @Test
  fun customLayout_preformatting() {
    typeSpinner.setSelection(Mode.CUSTOM_VIEWGROUP.ordinal)
    preformatNamesSwitch.isChecked = true
    preformatDatesSwitch.isChecked = true

    benchmarkRule.measureRepeated {
      recyclerView.scrollBy(0, getScrollAmount())
    }
  }
}

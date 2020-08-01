package codes.chrishorner.recyclerbenchmark

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import timber.log.Timber

class MainActivity : AppCompatActivity(R.layout.activity_main) {

  private val scope = MainScope()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    initTimber()

    // Render under the status and navigation bars.
    window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or
        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE

    scope.launch {
      val people = getTestData(this@MainActivity)
      Timber.d("Number of people = ${people.size}")
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    scope.cancel()
  }
}

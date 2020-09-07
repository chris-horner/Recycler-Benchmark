package codes.chrishorner.recyclerbenchmark

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import codes.chrishorner.recyclerbenchmark.PeopleAdapter.Mode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(R.layout.activity_main) {

  private val scope = CoroutineScope(Job() + Dispatchers.Main.immediate)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    initTimber()

    // Render under the status and navigation bars.
    window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or
        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE

    val adapter = PeopleAdapter()

    val recycler: RecyclerView = findViewById(R.id.recycler)
    recycler.updatePaddingWithInsets(left = true, top = true, right = true, bottom = true)
    recycler.layoutManager = LinearLayoutManager(this)
    recycler.adapter = adapter

    scope.launch {
      val people = getTestData(this@MainActivity)
      adapter.display(people, Mode.CONSTRAINT_LAYOUT)
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    scope.cancel()
  }
}

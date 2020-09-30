package codes.chrishorner.recyclerbenchmark.ui

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(R.layout.activity_main) {

  private val scope = CoroutineScope(Job() + Dispatchers.Main.immediate)

  lateinit var recycler: RecyclerView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    initTimber()

    // Render under the status and navigation bars.
    window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or
        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE

    val toolbar: View = findViewById(R.id.toolbar)
    toolbar.updatePaddingWithInsets(left = true, top = true, right = true)

    val adapter = PeopleAdapter()
    recycler = findViewById(R.id.recycler)
    recycler.updatePaddingWithInsets(left = true, right = true, bottom = true)
    recycler.layoutManager = LinearLayoutManager(this)
    recycler.adapter = adapter
    scope.launch { adapter.people = getTestData(this@MainActivity) }

    val spinner: Spinner = findViewById(R.id.modeSelector)
    spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, Mode.values()).apply {
      setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }
    spinner.onItemSelected { position -> adapter.mode = Mode.values()[position] }

    val preformatNames: CompoundButton = findViewById(R.id.preformatNames)
    preformatNames.setOnCheckedChangeListener { _, isChecked -> adapter.preformatNames = isChecked }
    val preformatDates: CompoundButton = findViewById(R.id.preformatDates)
    preformatDates.setOnCheckedChangeListener { _, isChecked -> adapter.preformatDates = isChecked }
  }

  override fun onDestroy() {
    super.onDestroy()
    scope.cancel()
  }
}

package codes.chrishorner.recyclerbenchmark.ui

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(R.layout.activity_main) {

  lateinit var recycler: RecyclerView
  lateinit var typeSpinner: Spinner
  lateinit var preformatNamesSwitch: CompoundButton
  lateinit var preformatDatesSwitch: CompoundButton

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
    adapter.people = getTestData(this@MainActivity)

    typeSpinner = findViewById(R.id.modeSelector)
    typeSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, Mode.values()).apply {
      setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }
    typeSpinner.onItemSelected { position -> adapter.mode = Mode.values()[position] }

    preformatNamesSwitch = findViewById(R.id.preformatNames)
    preformatNamesSwitch.setOnCheckedChangeListener { _, isChecked -> adapter.preformatNames = isChecked }
    preformatDatesSwitch = findViewById(R.id.preformatDates)
    preformatDatesSwitch.setOnCheckedChangeListener { _, isChecked -> adapter.preformatDates = isChecked }
  }
}

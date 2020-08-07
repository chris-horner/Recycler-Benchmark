package codes.chrishorner.recyclerbenchmark

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class PeopleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  private var people: List<Person> = emptyList()

  enum class Mode {
    NESTED_LAYOUTS, CONSTRAINT_LAYOUT, CUSTOM_VIEWGROUP
  }

  fun display(people: List<Person>, mode: Mode) {
    this.people = people
    notifyDataSetChanged()
  }

  override fun getItemCount() = people.size

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    TODO("Not implemented.")
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    TODO("Not implemented.")
  }
}

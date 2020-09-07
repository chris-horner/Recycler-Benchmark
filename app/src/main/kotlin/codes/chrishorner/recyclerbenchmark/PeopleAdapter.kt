package codes.chrishorner.recyclerbenchmark

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.time.format.DateTimeFormatter

class PeopleAdapter : RecyclerView.Adapter<ViewHolder>() {

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
    val view: View = parent.inflate(R.layout.item_constraint_layout)
    return PersonViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    when (holder) {
      is PersonViewHolder -> holder.display(people[position])
    }
  }

  class PersonViewHolder(view: View) : ViewHolder(view) {

    private val resources = view.context.resources
    private val nameView: TextView = view.findViewById(R.id.name)
    private val emailView: TextView = view.findViewById(R.id.email)
    private val jobView: TextView = view.findViewById(R.id.job)
    private val cityView: TextView = view.findViewById(R.id.city)
    private val dobView: TextView = view.findViewById(R.id.dateOfBirth)

    fun display(person: Person) {
      nameView.text = resources.getString(R.string.nameTemplate, person.firstName, person.lastName)
      emailView.text = person.email
      jobView.text = person.job
      cityView.text = person.city
      dobView.text = DateTimeFormatter.ofPattern("MMM dd").format(person.dateOfBirth)
    }
  }
}

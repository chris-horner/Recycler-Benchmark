package codes.chrishorner.recyclerbenchmark

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import codes.chrishorner.recyclerbenchmark.Mode.CONSTRAINT_LAYOUT
import codes.chrishorner.recyclerbenchmark.Mode.CUSTOM_VIEWGROUP
import codes.chrishorner.recyclerbenchmark.Mode.NESTED_LINEARS
import codes.chrishorner.recyclerbenchmark.Mode.RELATIVE_LAYOUT
import codes.chrishorner.recyclerbenchmark.PeopleAdapter.ViewHolder
import java.time.format.DateTimeFormatter

class PeopleAdapter : RecyclerView.Adapter<ViewHolder>() {

  private val dateFormat = DateTimeFormatter.ofPattern("MMM dd")

  private var preformattedDates: List<String> = emptyList()

  var people: List<Person> = emptyList()
    set(value) {
      field = value
      preformattedDates = people.map { dateFormat.format(it.dateOfBirth) }
      notifyDataSetChanged()
    }

  var mode = CONSTRAINT_LAYOUT
    set(value) {
      field = value
      notifyDataSetChanged()
    }

  var preformatDates: Boolean = false
    set(value) {
      field = value
      notifyDataSetChanged()
    }

  var preformatNames: Boolean = false
    set(value) {
      field = value
      notifyDataSetChanged()
    }

  override fun getItemViewType(position: Int) = when (mode) {
    CONSTRAINT_LAYOUT -> R.layout.item_constraint_layout
    RELATIVE_LAYOUT -> R.layout.item_relative_layout
    NESTED_LINEARS -> R.layout.item_nested_linears
    CUSTOM_VIEWGROUP -> TODO()
  }

  override fun getItemCount() = people.size

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view: View = parent.inflate(viewType)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.display(people[position], position)
  }

  inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val resources = view.context.resources
    private val nameView: TextView = view.findViewById(R.id.name)
    private val emailView: TextView = view.findViewById(R.id.email)
    private val jobView: TextView = view.findViewById(R.id.job)
    private val cityView: TextView = view.findViewById(R.id.city)
    private val dobView: TextView = view.findViewById(R.id.dateOfBirth)

    fun display(person: Person, position: Int) {
      nameView.text =
          if (preformatNames) person.fullName
          else resources.getString(R.string.nameTemplate, person.firstName, person.lastName)

      emailView.text = person.email
      jobView.text = person.job
      cityView.text = person.city

      dobView.text =
          if (preformatDates) preformattedDates[position]
          else dateFormat.format(person.dateOfBirth)
    }
  }
}

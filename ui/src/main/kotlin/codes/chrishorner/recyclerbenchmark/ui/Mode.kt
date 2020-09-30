package codes.chrishorner.recyclerbenchmark.ui

enum class Mode(private val label: String) {
  CONSTRAINT_LAYOUT("ConstraintLayout"),
  RELATIVE_LAYOUT("RelativeLayout"),
  NESTED_LINEARS("Nested linears"),
  CUSTOM_VIEWGROUP("Custom ViewGroup");

  override fun toString() = label
}

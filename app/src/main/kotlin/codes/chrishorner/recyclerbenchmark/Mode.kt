package codes.chrishorner.recyclerbenchmark

enum class Mode(private val label: String) {
  CONSTRAINT_LAYOUT("ConstraintLayout"),
  RELATIVE_LAYOUT("RelativeLayout"),
  NESTED_LAYOUTS("Nested layouts"),
  CUSTOM_VIEWGROUP("Custom ViewGroup");

  override fun toString() = label
}

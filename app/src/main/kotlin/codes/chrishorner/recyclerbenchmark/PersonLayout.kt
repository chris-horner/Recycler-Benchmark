package codes.chrishorner.recyclerbenchmark

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.View.MeasureSpec.AT_MOST
import android.view.View.MeasureSpec.EXACTLY
import android.view.View.MeasureSpec.makeMeasureSpec
import android.view.ViewGroup

class PersonLayout(context: Context, attrs: AttributeSet) : ViewGroup(context, attrs) {

  private lateinit var avatar: View
  private lateinit var name: View
  private lateinit var email: View
  private lateinit var dateOfBirth: View
  private lateinit var job: View
  private lateinit var separatorDot: View
  private lateinit var city: View

  private val horizontalSpacing = dpToPx(16)
  private val verticalSpacing = dpToPx(8)

  override fun onFinishInflate() {
    super.onFinishInflate()
    avatar = findViewById(R.id.avatar)
    name = findViewById(R.id.name)
    email = findViewById(R.id.email)
    dateOfBirth = findViewById(R.id.dateOfBirth)
    job = findViewById(R.id.job)
    separatorDot = findViewById(R.id.separatorDot)
    city = findViewById(R.id.city)
  }

  override fun onMeasure(widthSpec: Int, heightSpec: Int) {
    // Start by measuring the simplest views that're just wrap_content by wrap_content.
    measureChild(avatar, widthSpec, heightSpec)
    measureChild(dateOfBirth, widthSpec, heightSpec)
    measureChild(job, widthSpec, heightSpec)
    measureChild(separatorDot, widthSpec, heightSpec)

    // Work out how much width we have after padding.
    val width = MeasureSpec.getSize(widthSpec)
    val availableWidth = width - paddingLeft - paddingRight

    // Measure the name and email views.
    val nameAndEmailWidth =
        availableWidth - avatar.measuredWidth - dateOfBirth.measuredWidth - (horizontalSpacing * 2)
    val nameAndEmailMaxHeight = avatar.measuredHeight / 2
    val nameAndEmailWidthSpec = makeMeasureSpec(nameAndEmailWidth, EXACTLY)
    val nameAndEmailHeightSpec = makeMeasureSpec(nameAndEmailMaxHeight, AT_MOST)
    name.measure(nameAndEmailWidthSpec, nameAndEmailHeightSpec)
    email.measure(nameAndEmailWidthSpec, nameAndEmailHeightSpec)

    // Measure the city view.
    val cityWidth =
        availableWidth - avatar.measuredWidth - horizontalSpacing - job.measuredWidth - separatorDot.measuredWidth
    val cityWidthSpec = makeMeasureSpec(cityWidth, EXACTLY)
    val cityHeightSpec = makeMeasureSpec(job.measuredHeight, EXACTLY)
    city.measure(cityWidthSpec, cityHeightSpec)

    // Calculate final height based off of children and set measured dimension.
    val height =
        paddingTop + paddingBottom + name.measuredHeight + email.measuredHeight + verticalSpacing + job.measuredHeight
    setMeasuredDimension(width, height)
  }

  override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
    avatar.layout(
        paddingLeft,
        paddingTop,
        paddingLeft + avatar.measuredWidth,
        paddingTop + avatar.measuredHeight
    )

    val textLeft = avatar.right + horizontalSpacing

    name.layout(
        textLeft,
        paddingTop,
        textLeft + name.measuredWidth,
        paddingTop + name.measuredHeight
    )

    email.layout(
        textLeft,
        name.bottom,
        textLeft + email.measuredWidth,
        name.bottom + email.measuredHeight
    )

    val dateOfBirthLeft = name.right + horizontalSpacing
    val dateOfBirthTop = paddingTop + ((name.measuredHeight + email.measuredHeight - dateOfBirth.measuredHeight ) / 2)

    dateOfBirth.layout(
        dateOfBirthLeft,
        dateOfBirthTop,
        dateOfBirthLeft + dateOfBirth.measuredWidth,
        dateOfBirthTop + dateOfBirth.measuredHeight
    )

    val jobTop = email.bottom + verticalSpacing

    job.layout(
        textLeft,
        jobTop,
        textLeft + job.measuredWidth,
        jobTop + job.measuredHeight
    )

    val separatorDotTop = job.top + ((job.measuredHeight - separatorDot.measuredHeight) / 2)

    separatorDot.layout(
        job.right,
        separatorDotTop,
        job.right + separatorDot.measuredWidth,
        separatorDotTop + separatorDot.measuredHeight
    )

    city.layout(
        separatorDot.right,
        job.top,
        separatorDot.right + city.measuredWidth,
        job.top + city.measuredHeight
    )
  }
}

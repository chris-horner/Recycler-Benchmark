package codes.chrishorner.recyclerbenchmark.ui

import android.content.Context
import androidx.annotation.MainThread
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okio.buffer
import okio.source
import java.time.LocalDate

data class Person(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val fullName: String,
    val email: String,
    val avatar: String,
    val dateOfBirth: LocalDate,
    val city: String,
    val job: String
)

private var cache: List<Person> = emptyList()

/**
 * Returns what's in an in memory cache, or synchronously reads, caches, and returns
 * test data. (Keeps things simple for the sake of benchmarking).
 */
@MainThread
fun getTestData(context: Context): List<Person> {

  if (cache.isNotEmpty()) return cache

  val listType = Types.newParameterizedType(List::class.java, Person::class.java)
  val adapter: JsonAdapter<List<Person>> = moshi.adapter(listType)
  val source = context.resources.openRawResource(R.raw.people).source().buffer()
  val people = adapter.fromJson(source)!!
  cache = people
  return cache
}

private val moshi = Moshi.Builder()
    .add(LocalDateAdapter)
    .add(KotlinJsonAdapterFactory())
    .build()

private object LocalDateAdapter {
  @ToJson fun toJson(value: LocalDate) = value.toString()
  @FromJson fun fromJson(input: String) = LocalDate.parse(input)
}

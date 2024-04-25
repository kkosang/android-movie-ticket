package woowacourse.movie.view

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.data.MovieDao
import woowacourse.movie.model.Payment
import woowacourse.movie.model.Ticket
import woowacourse.movie.presentation.reservation.result.ReservationResultActivity
import woowacourse.movie.presentation.screen.detail.MovieDetailActivity

@RunWith(AndroidJUnit4::class)
class ReservationResultActivityTest {
    private val movie = MovieDao().find(0)
    private val ticket = Ticket()
    private val payment = Payment()

    private val intent =
        Intent(
            ApplicationProvider.getApplicationContext(),
            ReservationResultActivity::class.java,
        ).putExtra("movie", movie)
            .putExtra("ticket", ticket)
            .putExtra("payment", payment)

    @get:Rule
    val activityRule = ActivityScenarioRule<MovieDetailActivity>(intent)

    @Test
    fun 액티비티가_시작하면_제목이_보인다() {
        onView(withId(R.id.result_title_textview))
            .check(matches(withText(movie.title)))
    }

    @Test
    fun 액티비티가_시작하면_상영일이_보인다() {
        onView(withId(R.id.result_screen_date_textview))
            .check(matches(withText(movie.screenDateToString())))
    }

    @Test
    fun 액티비티가_시작하면_count가_보인다() {
        onView(withId(R.id.result_count_textview))
            .check(matches(withText(ticket.count().toString())))
    }

    @Test
    fun 액티비티가_시작하면_지불금액이_보인다() {
        onView(withId(R.id.result_price_textview))
            .check(matches(withText("13,000")))
    }
}

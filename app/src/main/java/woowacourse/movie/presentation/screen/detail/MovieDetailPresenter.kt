package woowacourse.movie.presentation.screen.detail

import android.content.Intent
import woowacourse.movie.data.MovieDao
import woowacourse.movie.model.Movie
import woowacourse.movie.model.Payment
import woowacourse.movie.model.Theater
import woowacourse.movie.model.Ticket
import java.time.LocalDate
import java.time.LocalTime

class MovieDetailPresenter(
    private val view: MovieDetailContract.View,
    private val dao: MovieDao,
) :
    MovieDetailContract.Presenter {
    var ticket: Ticket = Ticket()
        private set
    private val payment: Payment = Payment()
    private lateinit var movie: Movie
    private val theater: Theater by lazy {
        Theater(movie)
    }
    private lateinit var screenDate: LocalDate
    private lateinit var screenTime: LocalTime

    override fun fetchScreenInfo(movieId: Int) {
        movie = dao.find(movieId)
        movie.let {
            view.setUpView(
                movie.img,
                movie.title,
                movie.screenDateToString(),
                movie.runningTime,
                movie.description,
            )
            view.setUpSpinner(
                theater.screenDates(),
                theater.screenTimes(movie.screenDate[0]),
            )
            view.updateTicketCount(ticket.count())
        }
    }


    override fun subTicketCount() {
        ticket.subCount()
        view.updateTicketCount(ticket.count())
    }

    override fun addTicketCount() {
        ticket.addCount()
        view.updateTicketCount(ticket.count())
    }

    override fun onClickedSelectSeatButton(intent: Intent) {
        intent.putExtra("movieId", movie.id)
        intent.putExtra("ticket", ticket)
        intent.putExtra("payment", payment)
    }

    override fun ticketCount(): Int = ticket.count()

    override fun restoreTicketCount(count: Int) {
        ticket.restoreCount(count)
    }

    override fun onSelectedDateTime(date: LocalDate) {
        val times = theater.screenTimes(date)
        view.updateTimeSpinner(times)
    }

    override fun createTicket() {
        ticket = Ticket(ticket.count, screenDate to screenTime)
    }

    override fun registerScreenDate(date: LocalDate) {
        screenDate = date
    }

    override fun registerScreenTime(time: LocalTime) {
        screenTime = time
    }
}

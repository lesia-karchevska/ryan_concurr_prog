package tournament_n_process_mutex

import java.time.LocalDateTime

import common.MutexExerciser

import scala.util.Random

object RunTournamentMutex extends App {

  MutexExerciser.testMutex(() => new TournamentMutex(4), 1, 17)
}

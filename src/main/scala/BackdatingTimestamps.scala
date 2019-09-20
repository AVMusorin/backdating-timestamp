import com.github.nscala_time.time.Imports._

import scala.language.implicitConversions

object BackdatingTimestamps {
  type Row = (Int, DateTime)
  private val dateTimePattern = DateTimeFormat.forPattern("yyyy.MM.dd")

  implicit def toDateTime(arr: Seq[(Int, String)]): Seq[Row] = {
    arr.map { case (a, b) => (a, DateTime.parse(b, dateTimePattern)) }
  }

  def findBackdatingTimestamps(table: Seq[Row]): Seq[Int] = {
    val (ids, _) = table.foldLeft(Seq.empty[Int], Option.empty[DateTime]) {
      case (a, (id, timestamp)) =>
        val (ints, maxValue) = a
        maxValue match {
          case Some(m) if m > timestamp => (ints :+ id, maxValue)
          case _ => (ints, Some(timestamp))
        }
    }
    ids
  }
}

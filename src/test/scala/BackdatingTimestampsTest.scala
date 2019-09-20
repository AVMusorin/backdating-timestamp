import BackdatingTimestamps._
import org.scalatest.FunSpec
import org.scalatest.Matchers

class BackdatingTimestampsTest extends FunSpec with Matchers {

  describe("BackdatingTimestamps") {
    it("should work with some backdating timestamps") {
      val table: Seq[(Int, String)] = Seq(
        (1, "2016.09.11"),
        (2, "2016.09.12"),
        (3, "2016.09.13"),
        (4, "2016.09.14"),
        (5, "2016.09.09"),
        (6, "2016.09.08"),
        (7, "2016.09.15")
      )

      BackdatingTimestamps.findBackdatingTimestamps(table) should be(Seq(5, 6))
    }

    it("should work without backdating timestamp") {
      val table = Seq(
        (1, "2019.09.01"),
        (2, "2019.09.02"),
        (3, "2019.09.03"),
        (4, "2019.09.04"),
        (5, "2019.09.05")
      )

      BackdatingTimestamps.findBackdatingTimestamps(table) should be(Seq.empty[Int])
    }

    it("should work when backdating timestamp is in the last position") {
      val table = Seq(
        (1, "2019.09.01"),
        (2, "2019.09.03"),
        (3, "2019.09.04"),
        (4, "2019.09.02"),
      )

      BackdatingTimestamps.findBackdatingTimestamps(table) should be(Seq(4))
    }

    it("should work when backdating timestamps are split by common timestamps") {
      val table = Seq(
        (1, "2019.09.03"),
        (2, "2019.09.05"),
        (3, "2019.09.04"),
        (4, "2019.09.02"),
        (5, "2019.09.06"),
        (6, "2019.09.09"),
        (7, "2019.09.08")
      )

      BackdatingTimestamps.findBackdatingTimestamps(table) should be (Seq(3, 4, 7))
    }

    it("should work with empty table") {
      BackdatingTimestamps.findBackdatingTimestamps(Seq.empty[Row]) should be (Seq.empty[Int])
    }
  }
}

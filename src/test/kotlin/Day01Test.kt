import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day01Test {
    @Test
    fun testPartOne() {
        val data = HelperTest.getTestResource("/day-01-part-01")
        val day = Day01()

        val result = day.partOne(data)

        assertEquals(142, result)
    }

    @Test
    fun testPartTwo() {
        val data = HelperTest.getTestResource("/day-01-part-02")
        val day = Day01()

        val result = day.partTwo(data)

        assertEquals(281, result)
    }
}

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day03Test {
    @Test
    fun testPartOne() {
        val data = HelperTest.getTestResource("/day-03")
        val day = Day03()

        val result = day.partOne(data)

        assertEquals(4361, result)
    }

    @Test
    fun testPartTwo() {
        val data = HelperTest.getTestResource("/day-03")
        val day = Day03()

        val result = day.partTwo(data)

        assertEquals(467835, result)
    }
}

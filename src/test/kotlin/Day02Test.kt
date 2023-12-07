import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day02Test {
    @Test
    fun testPartOne() {
        val data = HelperTest.getTestResource("/day-02")
        val day = Day02()

        val result = day.partOne(data)

        assertEquals(8, result)
    }

    @Test
    fun testPartTwo() {
        val data = HelperTest.getTestResource("/day-02")
        val day = Day02()

        val result = day.partTwo(data)

        assertEquals(2286, result)
    }
}

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class MainTest {
    @Test
    fun testMain() {
        var result: List<String>
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream)
        System.setOut(printStream)

        main(arrayOf(0.toString()))
        result = outputStream.toString().lines()

        assertEquals("Hello Advent of Code 2023!", result[0])
        assertEquals("", result[1])
        assertEquals("", result[2])
        assertEquals("An unexpected error occurred!", result[3])

        outputStream.reset()

        main(arrayOf(1.toString()))
        result = outputStream.toString().lines()

        assertEquals("Hello Advent of Code 2023!", result[0])
        assertEquals("", result[1])
        assertEquals("", result[2])
        assertEquals("Result:", result[3])
    }
}

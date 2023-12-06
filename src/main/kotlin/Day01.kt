class Day01 : Day {
    override fun main(data: String): String {
        val resultOne = partOne(data)
        val resultTwo = partTwo(data)
        return "${resultOne}\n${resultTwo}"
    }

    fun partOne(data: String): Int {
        val result = data
            .lines()
            .filter { it.isNotEmpty() }
            .map { line ->
                val firstDigit = line.find { it.isDigit() }?.digitToIntOrNull()
                val lastDigit = line.findLast { it.isDigit() }?.digitToIntOrNull()
                firstDigit to lastDigit
            }
            .filter { it.first != null && it.second != null }
            .mapNotNull { "${it.first}${it.second}".toIntOrNull() }
            .sum()
        return result
    }

    fun partTwo(data: String): Int {
        val result = data
            .lines()
            .filter { it.isNotEmpty() }
            .map { line ->
                val digitsStringToInt = Digits.entries.map {
                    it.name.lowercase() to it.ordinal + 1
                }

                val firstDigitIntIndexed = line
                    .indexOfFirst { it.isDigit() }
                    .takeIf { it >= 0 }
                    ?.let {
                        val digit = line[it].digitToIntOrNull()
                        if (digit == null) null
                        else it to digit
                    }
                val lastDigitIntIndexed = line
                    .indexOfLast { it.isDigit() }
                    .takeIf { it >= 0 }
                    ?.let {
                        val digit = line[it].digitToIntOrNull()
                        if (digit == null) null
                        else it to digit
                    }

                val firstDigitStringIndexed = digitsStringToInt
                    .map { line.indexOf(it.first) to it.second }
                    .filter { it.first >= 0 }
                    .minByOrNull { it.first }
                val lastDigitStringIndexed = digitsStringToInt
                    .map { line.lastIndexOf(it.first) to it.second }
                    .filter { it.first >= 0 }
                    .maxByOrNull { it.first }

                val firstDigitIndexed = listOfNotNull(firstDigitIntIndexed, firstDigitStringIndexed)
                    .minByOrNull { it.first }
                    ?.second
                val lastDigitIndexed = listOfNotNull(lastDigitIntIndexed, lastDigitStringIndexed)
                    .maxByOrNull { it.first }
                    ?.second

                firstDigitIndexed to lastDigitIndexed
            }
            .filter { it.first != null && it.second != null }
            .mapNotNull { "${it.first}${it.second}".toIntOrNull() }
            .sum()
        return result
    }

    enum class Digits {
        One, Two, Three, Four, Five, Six, Seven, Eight, Nine
    }
}

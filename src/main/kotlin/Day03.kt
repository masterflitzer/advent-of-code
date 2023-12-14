import kotlin.math.max
import kotlin.math.min

class Day03 : Day {
    override fun main(data: String): String {
        val resultOne = partOne(data)
        val resultTwo = partTwo(data)
        return "${resultOne}\n${resultTwo}"
    }

    fun partOne(data: String): Int {
        val lines = data
            .lines()
            .filter { it.isNotEmpty() }

        val result = lines
            .flatMapIndexed { i, line ->
                rxDigits
                    .findAll(line)
                    .toList()
                    .filter { match ->
                        val rangeStart = match.range.first - 1
                        val rangeEnd = match.range.last + 1
                        val rangeStartMax = max(rangeStart, 0)
                        val rangeEndMin = min(rangeEnd, lines[i].length)
                        val surroundingList = listOfNotNull(
                            lines.getOrNull(i - 1)?.substringOrNull(rangeStartMax..rangeEndMin),
                            lines.getOrNull(i)?.getOrNull(rangeStart)?.toString(),
                            lines.getOrNull(i)?.getOrNull(rangeEnd)?.toString(),
                            lines.getOrNull(i + 1)?.substringOrNull(rangeStartMax..rangeEndMin)
                        )
                        surroundingList.any {
                            rxSymbol.containsMatchIn(it)
                        }
                    }
            }
            .mapNotNull { it.value.toIntOrNull() }
            .sum()
        return result
    }

    fun partTwo(data: String): Int {
        val lines = data
            .lines()
            .filter { it.isNotEmpty() }

        val digitToAsteriskList = lines
            .flatMapIndexed { i, line ->
                rxDigits
                    .findAll(line)
                    .toList()
                    .map { Match.of(it, i) }
                    .map { digitMatch ->
                        val rangeStart = digitMatch.range.first - 1
                        val rangeEnd = digitMatch.range.last + 1
                        val rangeStartMax = max(rangeStart, 0)
                        val rangeEndMin = min(rangeEnd, lines[i].length)
                        val surroundingLineList = listOf(
                            lines.getOrNull(i - 1) to i - 1,
                            lines.getOrNull(i) to i,
                            lines.getOrNull(i + 1) to i + 1
                        )
                        val asteriskMatchList = surroundingLineList
                            .filter { it.first != null }
                            .mapNotNull { (lineString, lineIndex) ->
                                lineString?.let {
                                    rxAsterisk.findAll(lineString)
                                        .toList()
                                        .map {
                                            Match.of(it, lineIndex)
                                        }
                                }
                            }
                            .flatten()
                            .mapNotNull { asteriskMatch ->
                                when (asteriskMatch.lineIndex) {
                                    i - 1, i + 1 -> asteriskMatch.takeIf {
                                        it.range.first >= rangeStartMax && it.range.last <= rangeEndMin
                                    }

                                    i -> asteriskMatch.takeIf {
                                        it.range.first == rangeStart && it.range.last == rangeStart || it.range.first == rangeEnd && it.range.last == rangeEnd
                                    }

                                    else -> null
                                }
                            }

                        digitMatch to asteriskMatchList
                    }
                    .filter { it.second.isNotEmpty() }
            }

        val asteriskToDigitList = digitToAsteriskList
            .flatMap { (_, asteriskMatchList) ->
                asteriskMatchList
            }
            .distinct()
            .map { asteriskMatch ->
                val digitMatchList = digitToAsteriskList
                    .filter { (_, asteriskMatchList) ->
                        asteriskMatchList.any {
                            it == asteriskMatch
                        }
                    }
                    .map { it.first }
                asteriskMatch to digitMatchList
            }

        val result = asteriskToDigitList
            .mapNotNull { (_, digitMatchList) ->
                digitMatchList
                    .takeIf { it.size == 2 }
                    ?.mapNotNull { match ->
                        match.value.toIntOrNull()
                    }
                    ?.reduce { accumulator, element -> accumulator * element }
            }
            .sum()

        return result
    }

    companion object {
        private val rxAsterisk = Regex("""[*]""")
        private val rxDigits = Regex("""\d+""")
        private val rxSymbol = Regex("""[^\d.]""")

        private fun String.substringOrNull(range: IntRange): String? = try {
            substring(range)
        } catch (e: IndexOutOfBoundsException) {
            null
        }

        data class Match(val value: String, val range: IntRange, val lineIndex: Int) {
            companion object {
                fun of(match: MatchResult, lineIndex: Int) = Match(match.value, match.range, lineIndex)
            }
        }
    }
}

class Day02 : Day {
    override fun main(data: String): String {
        val resultOne = partOne(data)
        val resultTwo = partTwo(data)
        return "${resultOne}\n${resultTwo}"
    }

    fun partOne(data: String): Int {
        val contains = CubeSet(red = 12, green = 13, blue = 14)
        val result = data
            .lines()
            .filter { it.isNotEmpty() }
            .map { Game.parse(it) }
            .filter { game -> game.sets.all { it.red <= contains.red } }
            .filter { game -> game.sets.all { it.green <= contains.green } }
            .filter { game -> game.sets.all { it.blue <= contains.blue } }
            .sumOf { it.id }
        return result
    }

    fun partTwo(data: String): Int {
        val result = data
            .lines()
            .filter { it.isNotEmpty() }
            .map { Game.parse(it) }
            .map {
                val red = it.sets.maxOf { x -> x.red }
                val green = it.sets.maxOf { x -> x.green }
                val blue = it.sets.maxOf { x -> x.blue }
                CubeSet(red = red, green = green, blue = blue)
            }
            .sumOf { it.red * it.green * it.blue }
        return result
    }

    data class CubeSet(val red: Int, val green: Int, val blue: Int)

    data class Game(val id: Int, val sets: List<CubeSet>) {
        enum class CubeColor {
            Red, Green, Blue
        }

        companion object {
            private val rxDigits = Regex("""\d+""")

            fun parse(s: String): Game {
                require(s.lines().size == 1) { "Expected a single line!" }

                val (game, set) = s
                    .split(':')
                    .map(String::trim)
                val gameId = rxDigits
                    .find(game)
                    ?.value
                    ?.toIntOrNull()
                require(gameId != null) { "Expected an Int!" }

                val cubeSetList = set
                    .split(';')
                    .map(String::trim)
                    .map { cubeSet ->
                        val cubeList = cubeSet
                            .split(',')
                            .map(String::trim)
                            .map { cube ->
                                cube
                                    .split(' ')
                                    .map(String::trim)
                                    .let { (countString, colorString, _) ->
                                        val count = countString.toIntOrNull()
                                        val color = CubeColor.entries.find { it.name.lowercase() == colorString }
                                        require(count != null) { "Expected an Int!" }
                                        require(color != null) { "Expected a color of ${CubeColor.entries}!" }
                                        count to color
                                    }
                            }
                        require(cubeList.size in 1..3) { "Expected a size between 1 and 3!" }
                        val red = cubeList.find { it.second == CubeColor.Red }?.first ?: 0
                        val green = cubeList.find { it.second == CubeColor.Green }?.first ?: 0
                        val blue = cubeList.find { it.second == CubeColor.Blue }?.first ?: 0
                        CubeSet(red = red, green = green, blue = blue)
                    }
                return Game(gameId, cubeSetList)
            }
        }
    }
}

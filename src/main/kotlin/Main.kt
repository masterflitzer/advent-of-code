fun main(args: Array<String>) {
    println("Hello Advent of Code 2023!\n")

    val minDay = 1
    val maxDay = 24

    val day = args.firstOrNull()?.toIntOrNull() ?: run {
        var input: Int?
        do {
            print("Enter day (Int between $minDay and $maxDay): ")
            input = readlnOrNull()?.toIntOrNull()
        } while (input == null || input !in minDay..maxDay)
        input
    }

    println()

    val dayString = "%02d".format(day)
    val dayClassName = "${Day::class.qualifiedName}${dayString}"
    val dayClass = try {
        Class.forName(dayClassName)
    } catch (e: ClassNotFoundException) {
        null
    }

    if (dayClass == null || !Day::class.java.isAssignableFrom(dayClass)) {
        println("An unexpected error occurred!")
        return
    }

    val data = Helper.getMainResource("/day-${dayString}")
    val dayInstance = dayClass.getConstructor().newInstance() as Day
    val result = dayInstance.main(data)
    println("Result:\n${result}")
}

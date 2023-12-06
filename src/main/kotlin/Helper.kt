class Helper {
    companion object {
        fun getMainResource(path: String) = this::class.java.getResourceAsStream(path)!!.reader().readText()
    }
}

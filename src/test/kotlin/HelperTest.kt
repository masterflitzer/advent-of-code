class HelperTest {
    companion object {
        fun getTestResource(path: String) = this::class.java.getResourceAsStream(path)!!.reader().readText()
    }
}

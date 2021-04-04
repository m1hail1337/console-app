package test.kotlin

import main.kotlin.main
import main.kotlin.stringTail
import main.kotlin.symbolTail
import main.kotlin.tail
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

class TailTest {
    private fun assertFileContent(name: String, expectedContent: String) {
        val file = File(name)
        val content = file.readLines().joinToString("\n")
        assertEquals(expectedContent, content)
    }

    @Test
    fun tailTest () {
        main("tail -o output1.txt input/1.txt".split(" ").toTypedArray())
        assertFileContent("output1.txt","""Без божества, без вдохновенья,
Без слез, без жизни, без любви.
Душе настало пробужденье:
И вот опять явилась ты,
Как мимолетное виденье,
Как гений чистой красоты.
И сердце бьется в упоенье,
И для него воскресли вновь
И божество, и вдохновенье,
И жизнь, и слезы, и любовь.""")
        File("output1.txt").delete()

        main("tail -c 20 -o output2.txt input/3.txt".split(" ").toTypedArray())
        assertFileContent("output2.txt","""ни весны, ни солнца.""")
        File("output2.txt").delete()


        main("tail -c 35 input/3.txt".split(" ").toTypedArray())
        assertFileContent("deleted.txt","адавшего вчера чуть не в бешенство.")
        File("deleted.txt").delete()


        main("tail -c 20 -o output4.txt input/1.txt input/2.txt input/3.txt".split(" ").toTypedArray())
        assertFileContent("output4.txt","""===>input/1.txt<===
, и слезы, и любовь.
===>input/2.txt<===
чуть не в бешенство.
===>input/3.txt<===
ни весны, ни солнца.""".trimIndent())
        File("output4.txt").delete()


        main("tail -n 2 input/1.txt input/2.txt input/3.txt".split(" ").toTypedArray())
        tail(2, null, "deleted.txt", listOf("input/1.txt", "input/2.txt", "input/3.txt"))
        assertFileContent("deleted.txt","""===>input/1.txt<===
И божество, и вдохновенье,
И жизнь, и слезы, и любовь.
===>input/2.txt<===
бередило ее; но в то же время он и подивился отчасти сегодняшнему умению владеть собой и скрывать свои чувства вчерашнего мономана,
из-за малейшего слова впадавшего вчера чуть не в бешенство.
===>input/3.txt<===
он старым, сердитым и презрительным уродом стоял между улыбающимися березами. Только он один не хотел подчиняться обаянию
весны и не хотел видеть ни весны, ни солнца.""".trimIndent())
        File("deleted.txt").delete()

    }

    @Test
    fun stringTailTest() {
        assertEquals(stringTail("input/1.txt", 5, false ),
                """Как гений чистой красоты.
И сердце бьется в упоенье,
И для него воскресли вновь
И божество, и вдохновенье,
И жизнь, и слезы, и любовь.""")
        assertEquals(stringTail("input/1.txt", 3, true ),
                """===>input/1.txt<===
И для него воскресли вновь
И божество, и вдохновенье,
И жизнь, и слезы, и любовь.""")
    }

    @Test
    fun symbolTailTest() {
        assertEquals(symbolTail("input/2.txt", 10, false ),
                """бешенство.""")
        assertEquals(symbolTail("input/2.txt", 15, true ),
                """===>input/2.txt<===
не в бешенство.""".trimIndent())
    }
}
package test.kotlin

import Launcher.main
import main.kotlin.stringTail
import main.kotlin.symbolTail
import main.kotlin.tail
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.File

private fun createFile(filename: String, content: String) {
    File(filename).writeText(content)
}

private fun assertFileContent(name: String, expectedContent: String) {
    val file = File(name)
    val content = file.readLines().joinToString("\n")
    assertEquals(expectedContent, content)
}

private fun deleteFile(filename: String) {
    File(filename).delete()
}

class TailTest {
    companion object {
        @JvmStatic
        @BeforeAll
        fun init() {
            createFile("input1.txt", first)
            createFile("input2.txt", second)
            createFile("input3.txt", third)
        }

        @JvmStatic
        @AfterAll
        fun clear() {
            deleteFile("input1.txt")
            deleteFile("input2.txt")
            deleteFile("input3.txt")

        }
    }

    @Test
    fun tailTest () {
        main("-o output1.txt input1.txt".split(" ").toTypedArray())
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
        deleteFile("output1.txt")

        main("-c 20 -o output3.txt input3.txt".split(" ").toTypedArray())
        assertFileContent("output3.txt","""ни весны, ни солнца.""")
        deleteFile("output3.txt")

        main("-c 35 -o output2.txt input2.txt".split(" ").toTypedArray())
        assertFileContent("output2.txt","адавшего вчера чуть не в бешенство.")
        deleteFile("output2.txt")

        main("-c 20 -o output4.txt input1.txt input2.txt input3.txt".split(" ").toTypedArray())
        assertFileContent("output4.txt","""===>input1.txt<===
, и слезы, и любовь.
===>input2.txt<===
чуть не в бешенство.
===>input3.txt<===
ни весны, ни солнца.""".trimIndent())
        deleteFile("output4.txt")

        val deletedOut = File("deleted.txt").bufferedWriter()
        val ins = listOf(File("input1.txt").bufferedReader() to "input1.txt", File("input2.txt").bufferedReader() to "input2.txt", File("input3.txt").bufferedReader() to "input3.txt")
        tail(
            2,
            null,
            deletedOut,
            ins
        )

        assertFileContent("deleted.txt", """===>input1.txt<===
И божество, и вдохновенье,
И жизнь, и слезы, и любовь.
===>input2.txt<===
бередило ее; но в то же время он и подивился отчасти сегодняшнему умению владеть собой и скрывать свои чувства вчерашнего мономана,
из-за малейшего слова впадавшего вчера чуть не в бешенство.
===>input3.txt<===
он старым, сердитым и презрительным уродом стоял между улыбающимися березами. Только он один не хотел подчиняться обаянию
весны и не хотел видеть ни весны, ни солнца.""".trimIndent())

        deletedOut.close()
        ins.forEach { it.first.close() }
        deleteFile("deleted.txt")
    }


    @Test
    fun stringTailTest() {
        assertEquals(stringTail(File("input1.txt").bufferedReader(), 5),
                """Как гений чистой красоты.
И сердце бьется в упоенье,
И для него воскресли вновь
И божество, и вдохновенье,
И жизнь, и слезы, и любовь.""")
        assertEquals(stringTail(File("input1.txt").bufferedReader(), 3, "input1.txt"),
                """===>input1.txt<===
И для него воскресли вновь
И божество, и вдохновенье,
И жизнь, и слезы, и любовь.""")
    }

    @Test
    fun symbolTailTest() {
        assertEquals(symbolTail(File("input2.txt").bufferedReader(), 10),
                """бешенство.""")
        assertEquals(symbolTail(File("input2.txt").bufferedReader(), 15, "input2.txt"),
                """===>input2.txt<===
не в бешенство.""".trimIndent())
    }

}

private const val first = "Я помню чудное мгновенье:\n" +
        "Передо мной явилась ты,\n" +
        "Как мимолетное виденье,\n" +
        "Как гений чистой красоты.\n" +
        "В томленьях грусти безнадежной,\n" +
        "В тревогах шумной суеты,\n" +
        "Звучал мне долго голос нежный\n" +
        "И снились милые черты.\n" +
        "Шли годы. Бурь порыв мятежный\n" +
        "Рассеял прежние мечты,\n" +
        "И я забыл твой голос нежный,\n" +
        "Твои небесные черты.\n" +
        "В глуши, во мраке заточенья\n" +
        "Тянулись тихо дни мои\n" +
        "Без божества, без вдохновенья,\n" +
        "Без слез, без жизни, без любви.\n" +
        "Душе настало пробужденье:\n" +
        "И вот опять явилась ты,\n" +
        "Как мимолетное виденье,\n" +
        "Как гений чистой красоты.\n" +
        "И сердце бьется в упоенье,\n" +
        "И для него воскресли вновь\n" +
        "И божество, и вдохновенье,\n" +
        "И жизнь, и слезы, и любовь."

private const val second = "— Здоров, здоров! — весело крикнул навстречу входящим Зосимов. Он уже минут с десять как пришел и сидел во\n" +
        "вчерашнем своем углу на диване. Раскольников сидел в углу напротив, совсем одетый и даже тщательно вымытый\n" +
        "и причесанный, чего уже давно с ним не случалось. Комната разом наполнилась, но Настасья все-таки\n" +
        "успела пройти вслед за посетителями и стала слушать.\n" +
        "\n" +
        "Действительно, Раскольников был почти здоров, особенно в сравнении со вчерашним, только был очень бледен,\n" +
        "рассеян и угрюм. Снаружи он походил как бы на раненого человека или вытерпливающего какую-нибудь сильную физическую боль:\n" +
        "брови его были сдвинуты, губы сжаты, взгляд воспаленный. Говорил он мало и неохотно, как бы через силу или исполняя\n" +
        "обязанность, и какое-то беспокойство изредка появлялось в его движениях.\n" +
        "\n" +
        "Недоставало какой-нибудь повязки на руке или чехла из тафты на пальце для полного сходства с человеком, у которого,\n" +
        "например, очень больно нарывает палец, или ушиблена рука, или что-нибудь в этом роде.\n" +
        "\n" +
        "Впрочем, и это бледное и угрюмое лицо озарилось на мгновение как бы светом,\n" +
        "когда вошли мать и сестра, но это прибавило только к выражению его, вместо прежней тоскливой рассеянности,\n" +
        "как бы более сосредоточенной муки. Свет померк скоро, но мука осталась, и Зосимов, наблюдавший и изучавший\n" +
        "своего пациента со всем молодым жаром только что начинающего полечивать доктора, с удивлением заметил в нем,\n" +
        "с приходом родных, вместо радости, как бы тяжелую скрытую решимость перенесть час-другой пытки, которой нельзя уж избегнуть.\n" +
        "Он видел потом, как почти каждое слово последовавшего разговора точно прикасалось к какой-нибудь ране его пациента и\n" +
        "бередило ее; но в то же время он и подивился отчасти сегодняшнему умению владеть собой и скрывать свои чувства вчерашнего мономана,\n" +
        "из-за малейшего слова впадавшего вчера чуть не в бешенство."

private const val third = "На краю дороги стоял дуб. Вероятно, в десять раз старше берез, составлявших лес, он был в десять раз толще,\n" +
        "и в два раза выше каждой березы. Это был огромный, в два обхвата дуб, с обломанными, давно, видно, суками и с обломанной\n" +
        "корой, заросшей старыми болячками. С огромными своими неуклюже, несимметрично растопыренными корявыми руками и пальцами,\n" +
        "он старым, сердитым и презрительным уродом стоял между улыбающимися березами. Только он один не хотел подчиняться обаянию\n" +
        "весны и не хотел видеть ни весны, ни солнца."
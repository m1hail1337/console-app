package main.kotlin

import java.io.BufferedReader
import java.io.BufferedWriter


//Вариант 9 -- tail
//Выделение из текстового файла его конца некоторого размера:
//● fileN задаёт имя входного файла. Если параметр отсутствует, следует
//считывать входные данные с консольного ввода. Если параметров несколько,
//то перед выводом для каждого файла следует вывести его имя в отдельной
//строке.
//● Флаг -o ofile задаёт имя выходного файла (в данном случае ofile). Если
//параметр отсутствует, следует выводить результат на консольный вывод.
//● Флаг -с num, где num это целое число, говорит о том, что из файла нужно
//извлечь последние num символов.
//● Флаг -n num, где num это целое число, говорит о том, что из файла нужно
//извлечь последние num строк.
//Command line: tail [-c num|-n num] [-o ofile] file0 file1 file2 …
//В случае, когда какое-нибудь из имён файлов неверно или указаны одновременно
//флаги -c и -n, следует выдать ошибку. Если ни один из этих флагов не указан, следует
//вывести последние 10 строк.
//Кроме самой программы, следует написать автоматические тесты к ней


fun stringTail(input: BufferedReader, cutLines: Int, title: String? = null): String {
    val inputLines = input.readLines()
    return buildString {
        title?.let { append("===>$title<===\n") }

        for (i in kotlin.math.max(0, inputLines.size - cutLines) .. inputLines.lastIndex) {
            appendLine(inputLines[i])
        }
    }.trimMargin()
}

fun symbolTail(input: BufferedReader, cutSymbols: Int, title: String? = null): String {
    val inputText = input.readLines().joinToString(separator = System.lineSeparator())
    return buildString {
        title?.let { append("===>$title<===\n") }
        append(inputText.substring( inputText.length - cutSymbols))
    }.trimMargin()
}

fun tail(strings: Int?, symbols: Int?, out: BufferedWriter, inputs: List<Pair<BufferedReader, String?>>) {
    val result = buildString {
        if (symbols != null)
            for (i in inputs) append("${symbolTail(i.first, symbols, i.second)}\n")
        else
            for (i in inputs) append("${stringTail(i.first, strings!!, i.second)}\n")
    }

    out.append(result)
    out.flush()
}


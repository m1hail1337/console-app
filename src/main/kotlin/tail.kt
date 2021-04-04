package main.kotlin

import main.java.Launcher
import java.io.File


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


fun main(args: Array<String>) {
    Launcher.main(args)
}

fun stringTail(inputFile: String, arg: Int, title: Boolean): String {
    val inputStrings = File(inputFile).readLines()
    return buildString {
        if (title) append("===>$inputFile<===\n")
        if (arg < inputStrings.size)
            for (i in inputStrings.size - arg until inputStrings.size) append("${inputStrings[i]}\n")
        else
            for (i in inputStrings) append("$inputStrings\n")
    }.trimMargin()
}

fun symbolTail(inputFile: String, arg: Int, title: Boolean): String {
    val inputStrings = File(inputFile).readLines()
    val lastString = inputStrings.last()
    return buildString {
        if (title) append("===>$inputFile<===\n")
        for (i in lastString.length - arg until lastString.length) append("${lastString[i]}")
    }.trimMargin()
}

fun tail(strings: Int?, symbols: Int?, outFile: String?, inputFiles: List<String>) {
    val result = buildString {
        if (symbols != null)
            for (i in inputFiles) append("${symbolTail(i, symbols, inputFiles.size > 1)}\n")
        else
            for (i in inputFiles) append("${stringTail(i, strings!!, inputFiles.size > 1)}\n")
    }
    if (outFile != null)
        File(outFile).writeText(result)
    else println(result)
}


fun tail4CmdInput(symbols: Int?) {
    val input = readLine()!!
    if (symbols == null) println(input)
    else {
        println(buildString {
            for (i in input.length - symbols until input.length - 1) append(input[i])
        })
    }
}

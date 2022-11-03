package Compiler

import java.io.File

class InputReader {
    // TODO: Move files to resources
    fun getSourceFromFile(fileName: String): String {
        return File(fileName).readText()
    }

    fun getSourceFromStringHard(): String {
        return """
            s = 0 + 1 + 2
            b = 3
            c_ = 45

            if ( s > b ) {
                print ( s )
            } elif ( s > 4 ) {
                print ( 5 )
            } else {
                print ( 6 )
            }

            while ( s < b ) {
                if b > s {
                    s = 7
                }
                s = s + 8
            }
        """.trimIndent()
    }

    fun getSourceFromStringSimple(): String {
        return """
            s = 0 + 1
        """.trimIndent()
    }

    fun getSourceFromStringSimpleAllCases(): String {
        return """
            a = 0 + 1
            b = 2 - 3
            c = 4 * 5
            d = 6 / 7
            
            a = 0 + 1
            b = 2 - a
            c = b * 5
            d = 6 / c
        """.trimIndent()
    }

    fun getSourceFromStringSimpleWrong(): String {
        return """
            a = b + 1
        """.trimIndent()
    }

    fun getSourceFromStringHarderWrong(): String {
        return """
            a = 3 * 8
            b = a - 8
            c = b + a
            d = e + c
            e = 1 - 3
        """.trimIndent()
    }

    // Known bug with larger expressions
    fun getSourceFromStringManyCases(): String {
        return """
            var = 0 + 1
            vars = 40 - 3 + 45
            letters_ = 20 - 109 * 4
            letters_more = 20 * 109 - 6
            abc = ( 7201 + 1039 ) - 7
            xyz = ( 2402 + ( 1109 * 4382 ) ) - 8
            abc_xyz = 9 / ( 2402 + ( 1109 * 4382 ) ) - 8
            y = - 1034
        """.trimIndent()
    }
}
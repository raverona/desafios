package text.formatter

class TextFormatter {
    fun wrap(text: String, maximumLineSize: Int = 40, justified: Boolean = false): String {
        val textLines = text.splitLines().map { line -> line.splitWords() }
        val formattedLines = mutableListOf<String>()

        var formattedLine = StringBuilder()

        for (line in textLines) {
            for (word in line) {

                if (formattedLine.isEmpty()) {
                    formattedLine.append(word)
                }
                else if (word.fitsInto(formattedLine, maximumLineSize)) {
                    formattedLine.append(" $word")
                }
                else {
                    formattedLines.insertFormattedLine(formattedLine, justified, maximumLineSize)
                    formattedLine = StringBuilder(word)
                }
            }

            formattedLines.insertFormattedLine(formattedLine, justified, maximumLineSize)
            formattedLine = formattedLine.clear()
        }

        return formattedLines.reduce { line1, line2 ->
            "$line1\n$line2"
        }
    }

    private fun MutableList<String>.insertFormattedLine(line: StringBuilder, justified: Boolean = false, maximumLineSize: Int) {
        if (justified)
            this.add(justify(line.toString(), maximumLineSize))
        else
            this.add(line.toString())
    }

    private fun justify(line: String, maximumLineSize: Int): String {
        val words = line.splitWords()
        val wordsCount = words.count()
        val wordsLength = words.reduce { word1, word2 -> "$word1$word2" }.length
        val spacesNeeded = maximumLineSize - wordsLength
        val justifiedLine = StringBuilder()

        if (wordsCount > 1) {
            val spacesPerWord = spacesNeeded / (wordsCount - 1)
            var spacesReminder = spacesNeeded % (wordsCount - 1)

            for (word in words) {
                justifiedLine.append(word)
                justifiedLine.appendSpace(spacesPerWord)

                if (spacesReminder > 0) {
                    justifiedLine.appendSpace()
                    spacesReminder--
                }
            }
        }

        return justifiedLine.toString().trimEnd()
    }

    private fun String.splitLines(): MutableList<String> {
        return this.split("\n").toMutableList()
    }

    private fun String.splitWords(): MutableList<String> {
        return this.trimEnd().split(" ").toMutableList()
    }

    private fun String.fitsInto(line: StringBuilder, maximumLineSize: Int): Boolean {
        return line.length + this.length + 1 <= maximumLineSize
    }

    private fun StringBuilder.appendSpace(): StringBuilder {
        return this.append(" ")
    }

    private fun StringBuilder.appendSpace(numberOfSpaces: Int): StringBuilder {
        for (spaces in 1..numberOfSpaces)
            this.appendSpace()
        return this
    }
}

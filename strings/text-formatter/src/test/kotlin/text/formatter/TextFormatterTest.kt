package text.formatter

import io.kotlintest.shouldBe
import io.kotlintest.specs.FreeSpec

class TextFormatterTest: FreeSpec() {
    init {
        "Given a text formatter" - {
            val textFormatter = TextFormatter()

            "And any text" - {
                val text = """
                    In the beginning God created the heavens and the earth.
                    Now the earth was formless and empty, darkness was over the surface
                    of the deep, and the Spirit of God was hovering over the waters.



                    And God said, "Let there be light," and there was light. God saw that the light was good, and he separated the light from the darkness. God called the light "day," and the darkness he called "night." And there was evening, and there was morning - the first day.
                """.trimIndent()

                "When the wrap function is called" - {
                    val formattedText = textFormatter.wrap(text)

                    "Then the result should be the text wrapped containing at maximum 40 characters per line" {
                        formattedText shouldBe """
                                                 |In the beginning God created the heavens
                                                 |and the earth.
                                                 |Now the earth was formless and empty,
                                                 |darkness was over the surface
                                                 |of the deep, and the Spirit of God was
                                                 |hovering over the waters.
                                                 |
                                                 |
                                                 |
                                                 |And God said, "Let there be light," and
                                                 |there was light. God saw that the light
                                                 |was good, and he separated the light
                                                 |from the darkness. God called the light
                                                 |"day," and the darkness he called
                                                 |"night." And there was evening, and
                                                 |there was morning - the first day.""".trimMargin()
                    }
                }

                "When the wrap function is called with the parameter 'justified' set to 'true'" - {
                    val formattedText = textFormatter.wrap(text, justified = true)

                    "Then the result should be the text wrapped containing at maximum 40 characters per line and the lines should be justified" {
                        formattedText shouldBe """
                                                 |In the beginning God created the heavens
                                                 |and              the              earth.
                                                 |Now  the  earth  was formless and empty,
                                                 |darkness    was    over    the   surface
                                                 |of  the  deep, and the Spirit of God was
                                                 |hovering      over      the      waters.
                                                 |
                                                 |
                                                 |
                                                 |And  God said, "Let there be light," and
                                                 |there  was light. God saw that the light
                                                 |was  good,  and  he  separated the light
                                                 |from  the darkness. God called the light
                                                 |"day,"   and   the  darkness  he  called
                                                 |"night."  And  there  was  evening,  and
                                                 |there  was  morning  -  the  first  day.""".trimMargin()
                    }
                }
            }
        }
    }
}
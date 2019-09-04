package co.idwall.redditscrapper.telegram.command

import co.idwall.redditscrapper.reddit.RedditScrapper
import org.springframework.stereotype.Component
import reddit.humanReadable
import telegram.command.AbstractCommand

@Component
class NothingToDoCommand(
        private val redditScrapper: RedditScrapper
): AbstractCommand() {
    override fun answer(commandMessage: String): String {
        if (commandMessage.isEmpty())
            return "Specify one or more subreddit names separated by semicolon"

        val subredditNames = commandMessage.split(";").toTypedArray()

        val trendingThreads = redditScrapper.getTrendingThreads(*subredditNames)

        return """The following threads are trending right now: 
            |
            |${trendingThreads.humanReadable()}
        """.trimMargin()
    }
}

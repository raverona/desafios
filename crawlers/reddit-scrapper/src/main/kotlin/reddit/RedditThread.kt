package reddit

import co.idwall.redditscrapper.reddit.Reddit
import org.jsoup.nodes.Element

data class RedditThread(private val title: String,
                        private val subreddit: String,
                        private val points: Int,
                        private val threadLink: String,
                        private val commentsLink: String) {
    fun isTrending(): Boolean {
        return this.points >= 5000
    }

    override fun toString(): String {
        return """
            title = $title
            subreddit = $subreddit
            points = $points
            threadLink = $threadLink
            commentsLink = $commentsLink
        """.trimIndent()
    }

    companion object {
        fun from(redditThreadElement: Element): RedditThread {
            val title = redditThreadElement.select("a.title").text()
            val subreddit = redditThreadElement.attr("data-subreddit")
            val points = redditThreadElement.select("div.score.unvoted").text()
            val domain = redditThreadElement.select("span.domain > a").attr("href")
            val threadLink = redditThreadElement.select("a.title").attr("href")
            val commentsLink = redditThreadElement.select("a.comments").attr("href")

            return RedditThread(
                    title = title,
                    subreddit = subreddit,
                    points = points.toIntOrZero(),
                    threadLink = threadLink.getFullLink(domain, subreddit),
                    commentsLink = commentsLink
            )
        }
    }
}

private fun String.toIntOrZero(): Int {
    return this.toIntOrNull() ?: 0
}

private fun String.getFullLink(domain: String, subreddit: String): String {
    return if (domain.isSubredditDomain(subreddit))
               "${Reddit.urlBase}$this"
           else
               this
}

private fun String.isSubredditDomain(subreddit: String): Boolean {
    return "${Reddit.subredditPath}$subreddit/" == this
}

fun List<RedditThread>.humanReadable(): String {
    return this.map { thread1 -> thread1.toString() }.reduce { thread1, thread2 -> "$thread1\n\n$thread2" }
}
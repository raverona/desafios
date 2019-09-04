package co.idwall.redditscrapper.reddit

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.springframework.stereotype.Component
import reddit.RedditThread

@Component
class RedditScrapper {
    fun getTrendingThreads(vararg subreddits: String): List<RedditThread> {
        val trendingThreads = mutableListOf<RedditThread>()

        for (subreddit in subreddits) {
            trendingThreads.addAll(getTrendingThreads(subreddit))
        }

        return trendingThreads
    }

    private fun getTrendingThreads(subreddit: String): List<RedditThread> {
        val threads = getThreads(subreddit)

        return threads.filter { thread -> thread.isTrending() }
    }

    private fun getThreads(subreddit: String): List<RedditThread> {
        val subredditPage = getSubredditPage(subreddit)
        val subredditThreads = mutableListOf<RedditThread>()

        val threads = subredditPage.getSubredditThreadsExcludingAdvertisements()
        for (thread in threads) {
            subredditThreads.addThread(thread)
        }

        return subredditThreads
    }

    private fun getSubredditPage(subreddit: String): Document {
        return Jsoup.connect(Reddit.urlBase + Reddit.subredditPath + subreddit).get()
    }

    private fun Document.getSubredditThreadsExcludingAdvertisements(): Elements {
        return this.body().select("div.thing:not(.promoted)")
    }

    private fun MutableList<RedditThread>.addThread(thread: Element): Boolean {
        try {
            return this.add(RedditThread.from(thread))
        } catch (exception: Exception) {
            return false
        }
    }
}
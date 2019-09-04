package co.idwall.redditscrapper.reddit

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
object Reddit {
    var urlBase: String = ""
    @Value("\${reddit.url.base}")
        set

    var subredditPath: String = ""
    @Value("\${reddit.url.path.subreddit}")
        set
}
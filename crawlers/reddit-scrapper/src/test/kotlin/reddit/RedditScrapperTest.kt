package co.idwall.redditscrapper.reddit

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*
import io.kotlintest.Spec
import io.kotlintest.matchers.collections.shouldContainInOrder
import io.kotlintest.specs.FreeSpec
import io.kotlintest.spring.SpringListener
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkAll
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import reddit.RedditThread

@ActiveProfiles("test")
@RunWith(SpringRunner::class)
@SpringBootTest
class RedditScrapperTest: FreeSpec() {

    override fun listeners() = listOf(SpringListener)

    private val redditScrapper = RedditScrapper()

    private val wireMockServer = WireMockServer()

    override fun beforeSpec(spec: Spec) {
        mockReddit()
        mockRedditResponse()
    }

    init {
        "When the scrapper is called to retrieve the trending threads of some subreddit" - {
            val trendingThreads = redditScrapper.getTrendingThreads("leagueoflegends")

            "Then it should return all threads trending on the first page of that subreddit" {
                trendingThreads shouldContainInOrder getExpectedThreads("leagueoflegends")
            }
        }
    }

    private fun mockReddit() {
        mockkObject(Reddit)
        every { Reddit.urlBase } returns "https://old.reddit.com"
        every { Reddit.subredditPath } returns "/r/"
    }

    private fun mockRedditResponse() {
        wireMockServer.start()
        stubFor(get(urlMatching("/r/leagueoflegends"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/html")
                        .withBodyFile("leagueoflegends.html")))
    }

    private fun getExpectedThreads(subredditName: String): List<RedditThread> {
        val expectedThreadsJson = RedditScrapperTest::class.java.getClassLoader().getResourceAsStream("expectedTrendingThreadsFor$subredditName.json")
        val objectMapper = ObjectMapper().registerModule(KotlinModule())
        return objectMapper.readValue<List<RedditThread>>(expectedThreadsJson, objectMapper.getTypeFactory().constructCollectionType(List::class.java, RedditThread::class.java))
    }

    override fun afterSpec(spec: Spec) {
        unmockkAll()
        wireMockServer.stop()
    }
}
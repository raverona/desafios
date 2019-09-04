package co.idwall.redditscrapper

import co.idwall.redditscrapper.telegram.RedditScrapperBot
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Profile
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException

@Profile("default")
@SpringBootApplication
class RedditScrapperApplication(
		private val redditScrapperBot: RedditScrapperBot
): CommandLineRunner {
	override fun run(vararg args: String?) {
		val telegramBotApi = TelegramBotsApi()
		try {
			telegramBotApi.registerBot(redditScrapperBot)
		} catch (e: TelegramApiRequestException) {
			e.printStackTrace()
		}
	}
}

fun main(args: Array<String>) {
	ApiContextInitializer.init()
	runApplication<RedditScrapperApplication>(*args)
}

package co.idwall.redditscrapper.telegram

import co.idwall.redditscrapper.telegram.command.CommandFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.meta.api.methods.send.SendMessage


@Component
class RedditScrapperBot(
    @Value("\${telegram.bot.username}")
    private val redditScrapperBotUsername: String,

    @Value("\${telegram.bot.token}")
    private val redditScrapperBotToken: String,

    private val commandFactory: CommandFactory
): TelegramLongPollingBot() {

    override fun getBotToken(): String {
        return this.redditScrapperBotToken
    }

    override fun getBotUsername(): String {
        return this.redditScrapperBotUsername
    }

    override fun onUpdateReceived(update: Update) {
        if (update.hasMessage() && update.message.hasText()) {
            val userInputtedMessage = update.message.text
            val (commandName, commandMessage) = userInputtedMessage.getCommandNameAndMessage()

            val commandResponse = commandFactory.build(commandName).answer(commandMessage)

            val outputMessage = buildOutputMessage(update.message.chatId!!, commandResponse)

            send(outputMessage)
        }
    }

    private fun String.getCommandNameAndMessage(): Pair<String, String> {
        val commandNameAndMessage = this.split(" ", limit = 2)
        if (commandNameAndMessage.size == 1)
            return commandNameAndMessage.first() to ""
        return commandNameAndMessage.first() to commandNameAndMessage.last()
    }

    private fun buildOutputMessage(chatId: Long, messageText: String): SendMessage {
        return SendMessage()
                .setChatId(chatId)
                .setText(messageText)
    }

    private fun send(message: SendMessage) {
        try {
            execute(message)
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }
    }
}
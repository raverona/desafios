package co.idwall.redditscrapper.telegram.command

import org.springframework.stereotype.Component
import telegram.command.AbstractCommand

@Component
class CommandFactory(
        private val nothingToDoCommand: NothingToDoCommand
) {
    fun build(commandName: String): AbstractCommand {
        return when (commandName) {
            "/NothingToDo" -> nothingToDoCommand
            else -> InvalidCommand()
        }
    }
}

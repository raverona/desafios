package co.idwall.redditscrapper.telegram.command

import telegram.command.AbstractCommand

class InvalidCommand : AbstractCommand() {
    override fun answer(commandMessage: String): String {
        return "Command not found"
    }

}

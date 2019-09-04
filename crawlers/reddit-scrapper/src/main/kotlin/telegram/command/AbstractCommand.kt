package telegram.command

abstract class AbstractCommand {
    abstract fun answer(commandMessage: String): String
}

package scalatron.botwar.botPlugin

import pl.lzola.scalatron.bots.BotImpl
import pl.lzola.scalatron.commons.CommandParser

// -------------------------------------------------------------------------------------------------
// Framework
// -------------------------------------------------------------------------------------------------

class ControlFunctionFactory {
    def create = (input: String) => {
        val (opcode, params) = CommandParser(input)
        opcode match {
            case "React" =>
                val bot = new BotImpl(params)
                if( bot.generation == 0 ) {
                    ControlFunction.forMaster(bot)
                } else {
                    ControlFunction.forSlave(bot)
                }
                bot.toString
            case _ => "" // OK
        }
    }
}
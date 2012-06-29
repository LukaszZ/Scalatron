package scalatron.botwar.botPlugin

import pl.lzola.scalatron.bots.Bot
import pl.lzola.scalatron.bots.MiniBot
import pl.lzola.scalatron.commons.XY


object ControlFunction
{
    val RotationDelay = 16  // wait this many simulation steps before turning

    def forMaster(bot: Bot) {
        val heading = bot.inputAsXYOrElse("heading", XY(0,1))
        bot.move(heading)

        // rotate the heading by 45 degrees counter-clockwise every few steps
        val lastRotationTime = bot.inputAsIntOrElse("lastRotationTime", 0)
        if((bot.time-lastRotationTime) > RotationDelay) {
            val newHeading = heading.rotateCounterClockwise45
            bot.set("heading" -> newHeading, "lastRotationTime" -> bot.time)
        }

        // can we spawn a mini-bot? We don't do it more often than every 10 cycles.
        val lastSpawnTime = bot.inputAsIntOrElse("lastSpawnTime", 0)
        if((bot.time - lastSpawnTime) > RotationDelay ) {
            // yes, we can (try to) spawn a mini-bot
            if(bot.energy > 100) {
                bot
                .spawn(heading.rotateClockwise45, "offset" -> heading * 10)
                .set("lastSpawnTime" -> bot.time)
                .say("Off you go!")
                .status("Circling...")
            } else {
                bot.status("Low Energy")
            }
        } else {
            bot.status("Waiting...")
        }
    }

    def forSlave(bot: MiniBot) {
        val actualOffset = bot.offsetToMaster.negate                    // as seen from master
        val desiredOffset = bot.inputAsXYOrElse("offset", XY(10,10))    // as seen from master
        bot.move((desiredOffset - actualOffset).signum)
    }
}





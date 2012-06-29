package pl.lzola.scalatron.bots

import pl.lzola.scalatron.commons.XY


trait MiniBot extends Bot {
    // inputs
    def offsetToMaster: XY

    // outputs
    def explode(blastRadius: Int) : Bot
}

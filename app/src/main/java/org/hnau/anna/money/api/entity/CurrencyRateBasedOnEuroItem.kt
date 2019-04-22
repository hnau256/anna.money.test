package org.hnau.anna.money.api.entity

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root


@Root(name = "Cube", strict = false)
class CurrencyRateBasedOnEuroItem {

    @set:Attribute(name = "currency", required = false)
    @get:Attribute(name = "currency", required = false)
    var currency: String = ""

    @set:Attribute(name = "rate", required = false)
    @get:Attribute(name = "rate", required = false)
    var rate: Float = 0f

}
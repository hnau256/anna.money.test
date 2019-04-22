package org.hnau.anna.money.api.entity

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root
import org.simpleframework.xml.Path


@Root(name = "gesmes:Envelope")
class CurrencyRateBasedOnEuro {

    @set:Element(name = "Cube")
    @get:Element(name = "Cube")
    @Path("Cube")
    var items: CurrencyRateBasedOnEuroItems? = null

}
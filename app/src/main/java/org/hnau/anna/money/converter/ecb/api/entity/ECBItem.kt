package org.hnau.anna.money.converter.ecb.api.entity

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root
import java.math.BigDecimal


@Root(name = "Cube", strict = false)
class ECBItem {

    @set:Attribute(name = "currency", required = false)
    @get:Attribute(name = "currency", required = false)
    var currency: String = ""

    @set:Attribute(name = "rate", required = false)
    @get:Attribute(name = "rate", required = false)
    var rate: BigDecimal = BigDecimal.ZERO

}
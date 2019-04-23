package org.hnau.anna.money.converter.ecb.api.entity

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root
import org.simpleframework.xml.Path


@Root(name = "gesmes:Envelope")
class ECBData {

    @set:Element(name = "Cube")
    @get:Element(name = "Cube")
    @Path("Cube")
    var items: ECBItems? = null

}
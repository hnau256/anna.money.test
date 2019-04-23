package org.hnau.anna.money.converter.ecb.api.entity

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Path
import org.simpleframework.xml.Root


@Root(name = "Cube")
class ECBItems {

    @set:ElementList(name = "Cube")
    @get:ElementList(name = "Cube")
    @Path("Cube")
    var items: MutableList<ECBItem> = ArrayList()

}
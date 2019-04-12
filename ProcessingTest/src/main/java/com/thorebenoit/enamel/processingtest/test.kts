import com.thorebenoit.enamel.geometry.layout.dsl.*
import com.thorebenoit.enamel.geometry.alignement.*
import com.thorebenoit.enamel.geometry.alignement.EAlignment.*
import com.thorebenoit.enamel.geometry.alignement.ERectEdge.*
import com.thorebenoit.enamel.geometry.layout.playground.sendToPlayground

val layout1 = "layout1".layoutTag
val layout2 = "layout2".layoutTag

layout1.arranged(topLeft)
    .sendToPlayground()
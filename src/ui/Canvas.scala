package ui

import java.awt.{Color, Graphics2D}

import processing.Cell

import scala.swing._

class Canvas (val nodeGrid: Array[Array[Cell]], val width: Int, val height: Int, maxCells: Int) extends Component {
  private val backgroundColor: Color = new Color(38, 38, 37)
  private val floorColor: Color = new Color(189, 184, 174)


  override def paintComponent(g: Graphics2D) {
    val dim: Dimension = size
    g.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
      java.awt.RenderingHints.VALUE_ANTIALIAS_ON)
    g.setColor(backgroundColor)
    g.fillRect(0, 0, dim.width, dim.height)

    for (x <- 0 until width) {
      for (y <- 0 until height) {
        var c: Color = backgroundColor
        val node: Cell = nodeGrid(x)(y)
        if (node.isFloor()) {
          val distance: Float = node.distanceFromRoot
          val colorVal: Float = distance / (maxCells / 255)
          c = new Color(0.4f, colorVal, colorVal)
//          if (node.animating) {
//            val frame: Int = node.animate()
//            c = new Color(230 / 255f, 126 / 255f, 34 / 255f, 1 - (frame / 80f))
//          } else {
//            c = floorColor
//          }
        }
        g.setColor(c)
        g.fillRect(node.left, node.top, node.width, node.height)
      }
    }
  }
}

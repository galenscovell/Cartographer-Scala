package ui

import java.awt.{Color, Graphics2D}

import processing.Cell

import scala.swing._


class Canvas (val nodeGrid: Array[Array[Cell]], val width: Int, val height: Int) extends Component {
  private val backgroundColor: Color = new Color(52, 73, 94)
  private val floorColor: Color = new Color(236, 240, 241)


  override def paintComponent(g: Graphics2D) {
    val dim: Dimension = size
    g.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
      java.awt.RenderingHints.VALUE_ANTIALIAS_ON)
    g.setColor(backgroundColor)
    g.fillRect(0, 0, dim.width, dim.height)

    for (x <- 0 until width) {
      for (y <- 0 until height) {
        val node: Cell = nodeGrid(x)(y)
        if (node.isFloor()) {
          g.setColor(floorColor)
        } else {
          g.setColor(backgroundColor)
        }
        if (node.animating) {
          node.animate()
          g.setColor(new Color(0.18f, 0.8f, 0.44f, node.frame / 60f))
        }
        g.fillRect(node.left, node.top, node.width, node.height)
      }
    }
  }
}

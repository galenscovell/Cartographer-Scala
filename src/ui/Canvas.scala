package ui

import java.awt.{Color, Graphics2D}

import processing.Node

import scala.swing._

class Canvas (val nodeGrid: Array[Array[Node]], val width: Int, val height: Int) extends Component {

  override def paintComponent(g: Graphics2D) {
    val dim: Dimension = size
    g.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
      java.awt.RenderingHints.VALUE_ANTIALIAS_ON)
    g.setColor(Color.BLACK)
    g.fillRect(0, 0, dim.width, dim.height)

    for (x <- 0 until width) {
      for (y <- 0 until height) {
        val node: Node = nodeGrid(x)(y)
        g.setColor(node.color)
        g.fillRect(node.exactX, node.exactY, node.size, node.size)
      }
    }
  }
}

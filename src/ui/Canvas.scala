package ui

import processing.Node

import java.awt.{Graphics2D, Color}
import scala.swing._

class Canvas(val nodeGrid: Array[Array[Node]], val width: Int, val height: Int) extends Component {
  private val grid: Array[Array[Node]] = nodeGrid

  override def paintComponent(g: Graphics2D) {
    val d = size
    g.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
      java.awt.RenderingHints.VALUE_ANTIALIAS_ON)
    g.setColor(Color.BLACK)
    g.fillRect(0, 0, d.width, d.height)

    for (x <- 0 until width) {
      for (y <- 0 until height) {
        var node: Node = grid(x)(y)
        if (node != null) {
          g.setColor(node.color)
          g.fillRect(node.exactX, node.exactY, node.size, node.size)
        }
      }
    }
  }
}

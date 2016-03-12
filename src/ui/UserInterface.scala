package ui

import processing.Grid
import java.awt.{Color}
import scala.swing._

class UserInterface extends MainFrame {
  private val nodeSize: Int = 10
  private val width: Int = 800
  private val height: Int = 480

  title = "Visualizing Prim's"
  preferredSize = new Dimension(width + (2 * nodeSize), height + (2 * nodeSize))

  val grid = new Grid(width, height, nodeSize)
  val canvas = new Canvas(grid.getGrid(), width / nodeSize, height / nodeSize)

  contents = new BorderPanel {
    border = Swing.MatteBorder(nodeSize, nodeSize, nodeSize, nodeSize, new swing.Color(52, 73, 94))
    background = Color.BLACK
    add(canvas, BorderPanel.Position.Center)
  }
}

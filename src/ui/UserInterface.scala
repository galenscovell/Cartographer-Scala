package ui

import processing.Grid
import java.awt.{Color}
import scala.swing._

class UserInterface extends MainFrame {
  title = "Visualizing Prim's"
  preferredSize = new Dimension(800, 480)

  val grid = new Grid(800, 480, 10)
  val canvas = new Canvas(grid.getGrid(), 800, 480)

  contents = new BorderPanel {
    border = Swing.MatteBorder(10, 10, 10, 10, new swing.Color(52, 73, 94))
    background = Color.BLACK
    add(canvas, BorderPanel.Position.Center)
  }
}

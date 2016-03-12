package processing

import java.awt.Color

class Node(val xPos: Int, val yPos: Int, val dimensions: Int) {
  val x = xPos
  val y = yPos
  val size = dimensions
  var color = Color.WHITE
}

package processing

import java.awt.Color

class Node(val xPos: Int, val yPos: Int, val nodeSize: Int) {
  val x: Int = xPos
  val y: Int = yPos
  val exactX: Int = xPos * nodeSize
  val exactY: Int = yPos * nodeSize
  val size: Int = nodeSize
  var color: Color = Color.WHITE
}

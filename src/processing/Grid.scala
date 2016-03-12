package processing

import java.awt.Color

class Grid(val width: Int, val height: Int, val nodeSize: Int) {
  private val gridWidth = width / nodeSize
  private val gridHeight = height / nodeSize
  private val nodeGrid = Array.ofDim[Node](gridWidth, gridHeight)

  for (x <- 0 until gridWidth) {
    for (y <- 0 until gridHeight) {
      val newNode = new Node(x, y, nodeSize)
      newNode.color = new Color(100, x * 2, y * 2)
      nodeGrid(x)(y) = newNode
    }
  }
  println("%d %d".format(nodeGrid.length, nodeGrid(0).length))

  def getGrid(): Array[Array[Node]] = nodeGrid
}

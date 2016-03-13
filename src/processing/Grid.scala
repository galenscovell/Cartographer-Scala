package processing

import java.awt.Color

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class Grid (val width: Int, val height: Int, val nodeSize: Int) {
  private val gridWidth: Int = width / nodeSize
  private val gridHeight: Int = height / nodeSize
  private val nodes: Array[Array[Node]] = Array.ofDim[Node](gridWidth, gridHeight)
  private var frontier: ArrayBuffer[Node] = ArrayBuffer()
  private val random: Random = Random

  // Generate grid full of walls
  for (x <- 0 until gridWidth) {
    for (y <- 0 until gridHeight) {
      val newNode: Node = new Node(x, y, nodeSize)
      // newNode.color = new Color(100, x * 2, y * 2)
      nodes(x)(y) = newNode
    }
  }

  // Randomly pick first maze node
  val startX: Int = random.nextInt(gridWidth)
  val startY: Int = random.nextInt(gridHeight)
  mark(startX, startY)


  private def mark(x: Int, y: Int) = {
    nodes(x)(y).color = Color.WHITE

    for (dx <- -1 to 1 by 2) {
      val newX: Int = x + dx
      if (!isOutOfBounds(newX, y)) {
        val neighborNode: Node = nodes(newX)(y)
        if (neighborNode.isWall()) {
          frontier += neighborNode
        }
      }
    }

    for (dy <- -1 to 1 by 2) {
      val newY: Int = y + dy
      if (!isOutOfBounds(x, newY)) {
        val neighborNode: Node = nodes(x)(newY)
        if (neighborNode.isWall()) {
          frontier += neighborNode
        }
      }
    }
  }


  def expandFrontier() = {
    if (frontier.length > 0) {
      val randomIndex: Int = random.nextInt(frontier.length)
      val neighborNode: Node = frontier(randomIndex)
      frontier -= neighborNode
      mark(neighborNode.x, neighborNode.y)
      true
    } else {
      false
    }
  }


  private def isOutOfBounds(x: Int, y: Int) = {
    x < 0 || x >= gridWidth || y < 0 || y >= gridHeight
  }


  def getGrid(): Array[Array[Node]] = {
    nodes
  }
}

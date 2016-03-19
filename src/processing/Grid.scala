package processing

import scala.collection.mutable.ArrayBuffer
import scala.util.Random


class Grid (val width: Int, val height: Int, val cellSize: Int, val cellSpacing: Int) {
  private val gridWidth: Int = (width - cellSize) / (cellSize + cellSpacing)
  private val gridHeight: Int = (height - cellSize) / (cellSize + cellSpacing)

  private var cells: Array[Array[Cell]] = Array.ofDim[Cell](gridWidth, gridHeight)
  private var frontier: ArrayBuffer[Cell] = ArrayBuffer()

  private val random: Random = Random
  private var nextCell: Cell = null
  private var currentCell: Cell = null

  build()


  def build(): Unit = {
    frontier.clear()
    nextCell = null
    currentCell = null
    // Generate grid full of walls
    for (x <- 0 until gridWidth) {
      for (y <- 0 until gridHeight) {
        val newCell: Cell = new Cell(x, y, cellSize, cellSpacing)
        cells(x)(y) = newCell
      }
    }

    // Set cell neighbors
    for (x <- 0 until gridWidth) {
      for (y <- 0 until gridHeight) {
        setNeighbors(cells(x)(y))
      }
    }
  }

  def start(): Unit = {
    // Start in bottom left corner
    mark(0, gridHeight - 1)
  }

  private def mark(x: Int, y: Int): Unit = {
    val targetNode: Cell = cells(x)(y)
    targetNode.setFloor()

    for (n <- 0 to 3) {
      if (targetNode.neighbors(n) != null) {
        val neighborNode: Cell = targetNode.neighbors(n)
        if (neighborNode.isEmpty()) {
          frontier += neighborNode
        }
      }
    }
  }

  private def isOutOfBounds(x: Int, y: Int): Boolean = {
    x < 0 || x >= gridWidth || y < 0 || y >= gridHeight
  }

  private def setNeighbors(cell: Cell) = {
    if (!isOutOfBounds(cell.x+1, cell.y)) {
      cell.neighbors(0) = cells(cell.x+1)(cell.y)
    }
    if (!isOutOfBounds(cell.x-1, cell.y)) {
      cell.neighbors(1) = cells(cell.x-1)(cell.y)
    }
    if (!isOutOfBounds(cell.x, cell.y+1)) {
      cell.neighbors(2) = cells(cell.x)(cell.y+1)
    }
    if (!isOutOfBounds(cell.x, cell.y-1)) {
      cell.neighbors(3) = cells(cell.x)(cell.y-1)
    }
  }

  private def getDirection(fx: Int, fy: Int, nx: Int, ny: Int): Int = {
    // N=1, E=2, S=4, W=8
    if (fx < nx) {
      2
    } else if (fx > nx) {
      8
    } else if (fy < ny) {
      4
    } else {
      1
    }
  }

  def getCells(): Array[Array[Cell]] = {
    cells
  }

  def expand(): Unit = {
    if (nextCell == null) {
      if (frontier.length > 0) {
        nextCell = frontier(random.nextInt(frontier.length))
        nextCell.explore()
        var connectingCell: Cell = null

        while (connectingCell == null) {
          var randomIndex: Int = 0
          val weight: Int = random.nextInt(10)
          if (weight > 3) {
            randomIndex = random.nextInt(2)
          } else {
            randomIndex = random.nextInt(4)
          }
          if (nextCell.neighbors(randomIndex) != null && nextCell.neighbors(randomIndex).isFloor()) {
            connectingCell = nextCell.neighbors(randomIndex)
          }
        }
        currentCell = connectingCell
        val direction: Int = getDirection(currentCell.x, currentCell.y, nextCell.x, nextCell.y)
        val oppositeDirection: Int = getDirection(nextCell.x, nextCell.y, currentCell.x, currentCell.y)
        currentCell.connect(direction)
        nextCell.connect(oppositeDirection)
        nextCell.setParent(currentCell)
      }
    } else {
      mark(nextCell.x, nextCell.y)
      currentCell = nextCell
      while (frontier.contains(currentCell)) {
        frontier -= currentCell
      }
      nextCell = null
    }
  }
}

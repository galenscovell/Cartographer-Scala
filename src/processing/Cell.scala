package processing


class Cell(val xPos: Int, val yPos: Int, val cellSize: Int, val cellSpacing: Int) {
  val x: Int = xPos
  val y: Int = yPos
  var left: Int = xPos * cellSize + (xPos + 1) * cellSpacing
  var top: Int = yPos * cellSize + (yPos + 1) * cellSpacing
  var width: Int = cellSize
  var height: Int = cellSize
  var cellType = CellType.EMPTY
  val neighbors: Array[Cell] = Array.ofDim[Cell](4)
  var animating: Boolean = false
  var frame: Int = 0
  var parent: Cell = null
  var distanceFromRoot: Float = 0f


  def setParent(p: Cell) = {
    parent = p
    distanceFromRoot = p.distanceFromRoot + 1
  }

  def animate() = {
    if (frame > 0) {
      frame -= 1
    } else {
      animating = false
    }
    frame
  }

  def isFloor() = {
    cellType == CellType.FLOOR
  }

  def setFloor() = {
    cellType = CellType.FLOOR
  }

  def isWall() = {
    cellType == CellType.WALL
  }

  def setWall() = {
    cellType = CellType.WALL
  }

  def explore() = {
    cellType = CellType.FLOOR
    animating = true
    frame = 80
  }

  def isEmpty() = {
    cellType == CellType.EMPTY
  }
  
  def connect(cardinal: Int) = {
    // N=1, E=2, S=4, W=8
    if (cardinal == 1) {
      top -= cellSpacing
      height += cellSpacing
    } else if (cardinal == 2) {
      width += cellSpacing
    } else if (cardinal == 4) {
      height += cellSpacing
    } else if (cardinal == 8) {
      left -= cellSpacing
      width += cellSpacing
    }
  }
}

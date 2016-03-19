package processing


class Cell(val xPos: Int, val yPos: Int, val cellSize: Int, val cellSpacing: Int) {
  var x: Int = xPos
  var y: Int = yPos
  var left: Int = xPos * cellSize + (xPos + 1) * cellSpacing
  var top: Int = yPos * cellSize + (yPos + 1) * cellSpacing
  var width: Int = cellSize
  var height: Int = cellSize

  val neighbors: Array[Cell] = Array.ofDim[Cell](4)
  var animating: Boolean = false
  var distanceFromRoot: Float = 0f

  private var cellType = CellType.EMPTY
  private var frame: Int = 0
  private var parent: Cell = null
  private var connectedDirs: Array[Boolean] = new Array[Boolean](4)
  private var connections: Int = 0


  def setParent(p: Cell): Unit = {
    parent = p
    distanceFromRoot = p.distanceFromRoot + 1
  }

  def getParent(): Cell = {
    parent
  }

  def animate(): Int = {
    if (frame > 0) {
      frame -= 1
    } else {
      animating = false
    }
    frame
  }

  def isFloor(): Boolean = {
    cellType == CellType.FLOOR
  }

  def setFloor(): Unit = {
    cellType = CellType.FLOOR
  }

  def isWall(): Boolean = {
    cellType == CellType.WALL
  }

  def setWall(): Unit = {
    cellType = CellType.WALL
  }

  def explore(): Unit = {
    cellType = CellType.FLOOR
    animating = true
    frame = 90
  }

  def isEmpty(): Boolean = {
    cellType == CellType.EMPTY
  }
  
  def connect(cardinal: Int): Unit = {
    // N=1, E=2, S=4, W=8
    connections += cardinal
  }

  def getConnections(): Int = {
    // Connections representation value
    //    1
    //  8 * 2
    //    4
    // Example: Connected top and bottom = 5
    // Example: Connected top and left = 9
    connections
  }
}

package ui

import java.awt.image.BufferedImage
import java.awt.{BasicStroke, Color, Graphics2D}

import gfx.SpriteSheet
import processing.Cell

import scala.swing._
import scala.collection.mutable.ArrayBuffer


class Canvas (val cellGrid: Array[Array[Cell]], val width: Int, val height: Int, maxCells: Int, cellSize: Int) extends Component {
  private val backgroundColor: Color = new Color(38, 38, 37)
  private val floorColor: Color = new Color(189, 184, 174)
  private val pathColor: Color = new Color(52, 152, 219)
  private val targetColor: Color = new Color(52, 152, 219)
  private var mode: Boolean = false
  private var trace: Boolean = false
  private val tracePath: ArrayBuffer[Cell] = new ArrayBuffer[Cell]()
  private val spriteSheet: SpriteSheet = new SpriteSheet("res/tilesheet.png", 24, 24)


  def switchMode(): Unit = {
    mode = !mode
  }

  def switchTrace(): Unit = {
    if (trace) {
      clearTrace()
    } else {
      generatePath(cellGrid(width-1)(0))
    }
  }

  def clearTrace(): Unit = {
    tracePath.clear()
    trace = false
  }

  private def generatePath(endCell: Cell): Unit = {
    var currentCell: Cell = endCell
    while (currentCell != null) {
      tracePath.append(currentCell)
      currentCell = currentCell.getParent()
    }
    trace = true
  }

  override def paintComponent(gfx: Graphics2D): Unit = {
    val dim: Dimension = size
    gfx.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
      java.awt.RenderingHints.VALUE_ANTIALIAS_ON)
    gfx.setColor(backgroundColor)
    gfx.fillRect(0, 0, dim.width, dim.height)

    for (x <- 0 until width) {
      for (y <- 0 until height) {
        val cell: Cell = cellGrid(x)(y)
        if (!mode) {
          val cellConnections: Int = cell.getConnections()
          val sprite: BufferedImage = spriteSheet.getSprite(cellConnections)
          gfx.drawImage(sprite, cell.left, cell.top, cell.width, cell.height, null)
        } else {
          var c: Color = backgroundColor
          if (cell.isFloor()) {
            val frame: Int = cell.animate()
            if (mode) {
              val distance: Float = cell.distanceFromRoot
              val colorVal: Float = distance / (maxCells / 255)
              c = new Color(0.5f, colorVal, colorVal)
            }
          }
          gfx.setColor(c)
          gfx.fillRect(cell.left, cell.top, cell.width, cell.height)
        }
        if (x == 0 && y == height - 1 || x == width - 1 && y == 0) {
          gfx.setColor(targetColor)
          gfx.fillOval(cell.left + (cellSize / 4), cell.top + (cellSize / 4), cell.width / 2, cell.height / 2)
        }
      }
    }
    if (trace) {
      gfx.setStroke(new BasicStroke(2))
      for (c <- 1 until tracePath.length) {
        val nextCell: Cell = tracePath(c)
        val currentCell: Cell = tracePath(c-1)
        gfx.setColor(pathColor)
        gfx.drawLine(currentCell.left + (cellSize / 2), currentCell.top + (cellSize / 2),
          nextCell.left + (cellSize / 2), nextCell.top + (cellSize / 2))
      }
    }
  }
}

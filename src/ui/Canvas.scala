package ui

import java.awt.image.BufferedImage
import java.awt.{Color, Graphics2D}

import gfx.SpriteSheet
import processing.Cell

import scala.swing._

class Canvas (val cellGrid: Array[Array[Cell]], val width: Int, val height: Int, maxCells: Int) extends Component {
  private val backgroundColor: Color = new Color(38, 38, 37)
  private val floorColor: Color = new Color(189, 184, 174)
  private var mode: Boolean = false
  private val spriteSheet: SpriteSheet = new SpriteSheet("res/tilesheet.png", 16, 16)


  def switchMode() = {
    mode = !mode
  }

  override def paintComponent(gfx: Graphics2D) {
    val dim: Dimension = size
    gfx.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
      java.awt.RenderingHints.VALUE_ANTIALIAS_ON)
    gfx.setColor(backgroundColor)
    gfx.fillRect(0, 0, dim.width, dim.height)

    for (x <- 0 until width) {
      for (y <- 0 until height) {
        val cell: Cell = cellGrid(x)(y)
        val cellConnections: Int = cell.getConnections()
        var sprite: BufferedImage = spriteSheet.getSprite(cellConnections)
        gfx.drawImage(sprite, cell.left, cell.top, cell.width, cell.height, null)
//        var c: Color = backgroundColor
//        if (cell.isFloor()) {
//          val frame: Int = cell.animate()
//          if (mode) {
//            val distance: Float = cell.distanceFromRoot
//            val colorVal: Float = distance / (maxCells / 255)
//            c = new Color(0.4f, colorVal, colorVal)
//          } else {
//            if (frame == 0) {
//              c = floorColor
//            } else {
//              c = new Color(230 / 255f, 126 / 255f, 34 / 255f, 1 - (frame / 80f))
//            }
//          }
//        }
//        g.setColor(c)
//        g.fillRect(cell.left, cell.top, cell.width, cell.height)
      }
    }
  }
}

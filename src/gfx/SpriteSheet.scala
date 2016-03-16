package gfx

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO


class SpriteSheet (val pathStr: String, val xDim: Int, val yDim: Int) {
  private val path: String = pathStr
  private val spriteWidth: Int = xDim
  private val spriteHeight: Int = yDim
  private var subSprites: Array[BufferedImage] = null

  load()


  def getSprite(pos: Int): BufferedImage = {
    subSprites(pos)
  }

  private def load() = {
    val sheet: BufferedImage = ImageIO.read(new File(path))
    val sheetWidth: Int = sheet.getWidth()
    val sheetHeight: Int = sheet.getHeight()
    loadSubSprites(sheet, sheetWidth, sheetHeight)
  }

  private def loadSubSprites(img: BufferedImage, width: Int, height: Int) = {
    val partsX: Int = width / spriteWidth
    val partsY: Int = height / spriteHeight
    subSprites = Array.ofDim[BufferedImage](partsX * partsY)

    for (y <- 0 until partsY) {
      for (x <- 0 until partsX) {
        subSprites(x + y * partsX) = getSubSprite(x * spriteWidth, y * spriteHeight, img)
      }
    }
  }

  private def getSubSprite(x: Int, y: Int, sheet: BufferedImage): BufferedImage = {
    val subSprite: BufferedImage = sheet.getSubimage(x, y, spriteWidth, spriteHeight)
    subSprite
  }
}

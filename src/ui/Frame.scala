package ui

import java.awt.Color

import processing.Grid

import scala.swing._


class Frame extends MainFrame {
  private val cellSize: Int = 14
  private val cellSpacing: Int = 2
  private val width: Int = 800
  private val height: Int = 480
  private val columns: Int = (width - cellSize) / (cellSize + cellSpacing)
  private val rows: Int = (height - cellSize) / (cellSize + cellSpacing)
  private val maxCells: Int = width * height / (cellSize + cellSpacing)
  private val borderSize: Int = cellSize + cellSpacing

  private final val framerate: Int = 600
  private var running: Boolean = true

  private val grid: Grid = new Grid(width, height, cellSize, cellSpacing)
  private val canvas: Canvas = new Canvas(grid.getCells(), columns, rows, maxCells)

  createComponents(grid, canvas)
  startThread(grid, canvas)


  private def createComponents(grid: Grid, canvas: Canvas) = {
    title = "Visualizing Prim's"
    preferredSize = new Dimension(width + cellSize * 2 + cellSpacing * 3, height + cellSize * 4)
    contents = new BorderPanel {
      border = Swing.MatteBorder(borderSize, borderSize, borderSize, borderSize, new Color(44, 62, 80))
      background = Color.BLACK
      add(canvas, BorderPanel.Position.Center)
    }
  }

  private def startThread(grid: Grid, canvas: Canvas) = {
    // Establish thread and enter run loop for rendering
    val thread = new Thread(new Runnable {
      override def run {
        while (running) {
          val start: Long = System.currentTimeMillis()
          grid.expand()
          canvas.repaint()
          val end: Long = System.currentTimeMillis()
          // Sleep to match framerate
          val sleepTime: Long = (1000 / framerate) - (end - start)
          if (sleepTime > 0) {
            Thread.sleep(sleepTime)
          }
        }
      }
    }).start()
  }
}

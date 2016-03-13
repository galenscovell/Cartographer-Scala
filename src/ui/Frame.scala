package ui

import java.awt.Color

import processing.Grid

import scala.swing._


class Frame extends MainFrame {
  private var cellSize: Int = 9
  private val cellSpacing: Int = 1
  private val width: Int = 800
  private val height: Int = 480

  private final val framerate: Int = 60
  private var running: Boolean = true

  private val grid: Grid = new Grid(width, height, cellSize, cellSpacing)
  private val canvas: Canvas = new Canvas(grid.getCells(), (width - cellSize) / (cellSize + cellSpacing), (height - cellSize) / (cellSize + cellSpacing))

  createComponents(grid, canvas)
  startThread(grid, canvas)


  private def createComponents(grid: Grid, canvas: Canvas) = {
    title = "Visualizing Prim's"
    preferredSize = new Dimension(width + cellSize - 2, height + cellSize * 3 + cellSpacing * 2)
    contents = new BorderPanel {
      border = Swing.MatteBorder(0, 0, 0, 0, new Color(52, 73, 94))
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
          if (!grid.expand()) {
            running = false
          }
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

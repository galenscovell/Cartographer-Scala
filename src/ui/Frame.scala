package ui

import java.awt.Color

import processing.Grid

import scala.swing._

class Frame extends MainFrame {
  private val nodeSize: Int = 10
  private val width: Int = 800
  private val height: Int = 480
  private final val framerate: Int = 120
  private var running: Boolean = true

  title = "Visualizing Prim's"
  preferredSize = new Dimension(width + (2 * nodeSize), height + (2 * nodeSize))

  val grid: Grid = new Grid(width, height, nodeSize)
  val canvas: Canvas = new Canvas(grid.getGrid(), width / nodeSize, height / nodeSize)

  contents = new BorderPanel {
    border = Swing.MatteBorder(nodeSize, nodeSize, nodeSize, nodeSize, new Color(52, 73, 94))
    background = Color.BLACK
    add(canvas, BorderPanel.Position.Center)
  }

  // Establish thread and enter run loop for rendering
  val thread = new Thread(new Runnable {
    override def run {
      while (running) {
        val start: Long = System.currentTimeMillis()
        if (!grid.expandFrontier()) {
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

package ui

import java.awt.Color
import javax.swing.UIManager

import processing.Grid

import scala.swing._


class Frame extends MainFrame {
  private val cellSize: Int = 24
  private val cellSpacing: Int = 0
  private val width: Int = 960
  private val height: Int = 480
  private val columns: Int = (width - cellSize) / (cellSize + cellSpacing)
  private val rows: Int = (height - cellSize) / (cellSize + cellSpacing)
  private val maxCells: Int = width * height / (cellSize + cellSpacing)
  private val borderSize: Int = cellSize + cellSpacing

  private final val framerate: Int = 960
  private var running: Boolean = true
  private var clear: Boolean = false
  private var began: Boolean = false

  private val grid: Grid = new Grid(width, height, cellSize, cellSpacing)
  private val canvas: Canvas = new Canvas(grid.getCells(), columns, rows, maxCells, cellSize)
  private val buttonDim: Dimension = new Dimension(150, 30)

  UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel")
  private val modeButton: ToggleButton = new ToggleButton {
    borderPainted = false
    focusPainted = false
    preferredSize = buttonDim
    margin = new Insets(10, 0, 10, 0)
    action = new Action("Heatmap") {
      override def apply() = {
        canvas.switchMode()
      }
    }
  }
  private val traceButton: ToggleButton = new ToggleButton {
    borderPainted = false
    focusPainted = false
    preferredSize = buttonDim
    margin = new Insets(10, 0, 10, 0)
    action = new Action("Solve") {
      override def apply() = {
        canvas.switchTrace()
      }
    }
  }

  createComponents(grid, canvas)
  startThread(grid, canvas)


  private def createComponents(grid: Grid, canvas: Canvas) = {
    title = "Visualizing Prim's"
    preferredSize = new Dimension(width + cellSize + 16, height + cellSize * 4 + 4)
    val buttonPanel = new FlowPanel {
      background = new Color(236, 236, 236)
      contents += new Button {
        borderPainted = false
        focusPainted = false
        preferredSize = buttonDim
        margin = new swing.Insets(10, 0, 10, 0)
        action = new Action("Create") {
          override def apply() = {
             grid.start()
             began = true
          }
        }
      }
      contents += Swing.HStrut(10)
      contents += traceButton
      contents += Swing.HStrut(10)
      contents += modeButton
      contents += Swing.HStrut(10)
      contents += new Button {
        borderPainted = false
        focusPainted = false
        preferredSize = buttonDim
        margin = new swing.Insets(10, 0, 10, 0)
        action = new Action("Clear") {
          override def apply() = {
            canvas.clearTrace()
            traceButton.selected = false
            began = false
            clear = true
          }
        }
      }
      contents += Swing.HStrut(10)
      contents += new Button {
        borderPainted = false
        focusPainted = false
        preferredSize = buttonDim
        margin = new swing.Insets(10, 0, 10, 0)
        action = new Action("Quit") {
          override def apply() = {
            sys.exit(0)
          }
        }
      }
    }
    contents = new BorderPanel {
      border = Swing.MatteBorder(borderSize, borderSize, borderSize, borderSize, new Color(44, 62, 80))
      background = Color.BLACK
      add(canvas, BorderPanel.Position.Center)
      add(buttonPanel, BorderPanel.Position.South)
    }
  }

  private def startThread(grid: Grid, canvas: Canvas) = {
    // Establish thread and enter run loop for rendering
    val thread = new Thread(new Runnable {
      override def run {
        while (running) {
          if (clear) {
            grid.build()
            clear = false
          }
          val start: Long = System.currentTimeMillis()
          if (began) {
            grid.expand()
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

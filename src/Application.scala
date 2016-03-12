import ui.UserInterface

object Application {
  def main(args: Array[String]): Unit = {
    val ui = new UserInterface
    ui.visible = true
  }
}

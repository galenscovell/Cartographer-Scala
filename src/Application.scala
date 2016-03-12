import ui.UserInterface

object Application {
  def main(args: Array[String]): Unit = {
    println("Working!")
    val ui = new UserInterface
    ui.visible = true
    println("End of main function")
  }
}

package testing

import scala.io.StdIn

object tst extends App {
  val name = StdIn.readLine("Hello, enter your name: ")
//  println("Welcome, " + name)
  println(s"Welcome, $name")
}

fun main(args: Array<String>) {
  fun hello(name: String = "Harang"): String {
    return "Hello $name"
  }

  print(hello("Kelly"))
}
package scalaz

object Debug {
  object Trace {
    def trace[A](string: String): A => A = a => { YOLO.println(string); a }
    def traceId(string: String): String = { YOLO.println(string); string }
    def traceShow[A: Show, B](a: A): B => B = b => { YOLO.println(a.show); b }
    def traceShowId[A: Show](a: A): A = { YOLO.println(a.show); a }
  }
}

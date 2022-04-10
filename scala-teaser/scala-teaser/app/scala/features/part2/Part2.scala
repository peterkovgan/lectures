package scala.features.part2

object Part2 extends App {
  
  //some stylistic notes
  
  
  
  //value could be produced by anything
  def funcT1(name:String):Seq[String]={
    
      val someSideEffectUnitValue = println("Peter")
    
      val someIfValue = if(name=="Peter") "Peter" else "Not Peter"
    
      val someValue = for(x<- 0 until 100) yield (x + "-try")
      
      //this value will be returned and consumed by caller
      someValue
  }
  
  
   //you can use {..} instead of (..) when you call function
   def tellHelloToPuppy(name:String){
        println("hello "+name)
   }
   tellHelloToPuppy("Mops")  
   tellHelloToPuppy{"Chops"}  //no problem
   
   
   //you can have methods with N argument groups
   def tellHello(name:String)(times:Int, filter:(String)=>Boolean)(needZzzzz:Boolean)={
     for(i<-0 until times){
         if(filter(name)){
            println(name + (if (needZzzzz) "Zzzzz" else ""))
         }
     }
   }
   //and then execute "gradually" step by step
   val g1 = tellHello("peter") _
   //...
   val g2 = g1(10,  (i: String) => {i.startsWith("peter")} )
   //....
   val g3 = g2(true)
   
   
   
  //you can be very Java and resolve problems as Java does
  //almost java style not recursive fib (imperative style) 
  def fibo(x: Int): BigInt = {
    var a: BigInt = 0
    var b: BigInt = 1
    var c: BigInt = 0
    for (_ <- 1 to x) {
      c = a + b
      a = b
      b = c
    }
    return a
  }

  println(fibo(10))
  
  //scala style functional fib, using infinite stream
  val fibs:Stream[BigInt] = 0 #:: 1 #::(fibs zip fibs.tail).map{ case (a,b) => a+b }
  println(fibs(10))
  
  //what is ZIP:
  //def 	zip [B](that : Stream[B]) : Stream[(A, B)]
  //Returns a stream formed from this stream and the specified stream 
  //that by associating each element of the former with the element at the same 
  //position in the latter. If one of the two streams is longer than the other, 
  //its remaining elements are ignored.
  
  
  
  //a bit of explanation with simpler example
  //you see infinite recursion described here
  //right part "from(n + 1)" is repeating call to itself
  def from(n: Int): Stream[Int] = n #:: from(n + 1)
  
  //the same but with print to demonstrate growing values
  def from1(n:Int): Stream[Int] = {
       println("requesting " + n)
       n #:: from1(n + 1)
  }
  
  //10 - initial value - it will be the first value of stream
  //stream is lazy, so nothing is really in it yet
  //it is just initialized, but not yet "works"
  val nats  = from1(10)
  
  //generate 4 elements - now it begins to work
  println("nats(4)="+nats(4))
  
  //How zip works with stream and its tail
  
  //0..1        - initial stream
  //1           - tail of initial stream
  //0->1        - zip result
  //0+1=1       - map result
  
  //0..1..1     - initial stream and previous map result
  //1..1        - tail of that stream
  //(1->1)      - zip result
  //1+1=2       - map result
  
  
  //0..1..1..2  
  //1..1..2
  //(1->2)
  //1+2=3
  
  //0..1..1..2..3
  //1..1..2..3
  //(2->3)
  //2+3=5
  
  //0..1..1..2..3..5
  //1..1..2..3..5
  //(3->5)
  //3+5=8
  
  //0..1..1..2..3..5..8
  
  //you do more...
}
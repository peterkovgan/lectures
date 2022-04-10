package scala.features.part8

object Part8 extends App {
  
  
  val sum = (a: Int, b: Int, c: Int) => a + b + c

  val f = sum(1, 2, _: Int)
  
  println(f.apply(3)) //method apply used to "execute" this partially applied function
  
  //but you can also use such shorter way
  
  println(f(3))
  
  //another partially applied function
  val f1 = sum(_:Int, 2, _: Int)
  
  println(f1(1,3))
  
  //another way to declare it
  val f2:Function2[Int,Int,Int] = sum(_:Int, 2, _: Int)
  
  println(f2(1,3))
  
  
  
  
  //off topic:
  
  //apply is also reserved/used word in other situations
  
  //if you create scala object like that
  
  class Broker(val attitude:String, val salary:Int){
      override def toString = "I'm "+attitude +" broker with salary "+salary
  }
  
  object Broker{//so called "companion object"
    
     def apply(salary:Int):String={
       "I'm rich bastard with salary "+salary
     }
     
     //frequently used as a factory method
     def apply():Broker={
         new Broker("happy", 10)
     }
     //frequently used as a factory method
     def apply(attitude:String):Broker={
         new Broker(attitude, 10)
     }
    
  }
  
  //then you can use such way of calling the apply method:
  
  val salary = Broker(10)
  
  val defaultBroker = Broker()
  
  println(defaultBroker.toString)
  
  val sadBroker = Broker("sad")
  
  println(sadBroker.toString)
  
}
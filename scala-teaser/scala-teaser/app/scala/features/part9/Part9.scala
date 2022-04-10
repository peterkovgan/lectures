package scala.features.part9

object Part9 extends App {
    
    val divide = (x: Int) => 42 / x
    
    //divide(0) - such call is a problem
    
    
    val divide1 = new PartialFunction[Int, Int] {
          def apply(x: Int) = 42 / x         //here you write a definition
          def isDefinedAt(x: Int) = x != 0   //here you limit the scope
    }
    //you can do that now
    if (divide1.isDefinedAt(3)) divide1(3)
    
    //OR another form of partial function definition:
    val divide2: PartialFunction[Int, Int] = {
          case d:Int if d != 0 => 42 / d         //a block with case's is a way to define anonymous function
    }
    //Although this code doesn’t explicitly implement the isDefinedAt method, it works
    //exactly the same as the previous divide function definition:
    //divide2.isDefinedAt(0) - will produce false
    
    
    
    //more complex example
    val convert1to5 = new PartialFunction[Int, String] {
        val nums = Array("one", "two", "three", "four", "five")
        def apply(i: Int) = nums(i-1)
        def isDefinedAt(i: Int) = i > 0 && i < 6
    }
    
    val convert6to10 = new PartialFunction[Int, String] {
        val nums = Array("six", "seven", "eight", "nine", "ten")
        def apply(i: Int) = nums(i-6)
        def isDefinedAt(i: Int) = i > 5 && i < 11
    }
    
    //you can combine them!
    val handle1to10 = convert1to5 orElse convert6to10
    
    
    //or in such form:
    val convert1to5_ : PartialFunction[Int, String] = {
       case 1 =>"one"
       case 2 =>"two"
       case 3 =>"three"
       case 4 =>"four"
       case 5 =>"five"
    }
    
    val convert6to10_ : PartialFunction[Int, String] = {
        case 6 =>"six"
        case 7 =>"seven"
        case 8 =>"eight"
        case 9 =>"nine"
        case 10 =>"ten"
    }
    
    val handle1to10_ = convert1to5_ orElse convert6to10_
    
    
    //where partial functions used in scala in practice
    
    //in iterations over collections
    
    //List(41, "cat") map { case i: Int ⇒ i + 1 } // - that will fail 
    
    //but that will survive:
    
    val collected = List(41, "cat") collect { case i: Int ⇒ i + 1 }
    
    //because collect does that isDefinedAt(x) check inside
    
    println(collected)
    
    
    
    
}
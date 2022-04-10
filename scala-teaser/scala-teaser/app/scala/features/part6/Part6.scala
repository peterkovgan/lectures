package scala.features.part6

import scala.PartialFunction.AndThen

object Part6 extends App {
  
    
    //several functions with 1 argument and the same return type like an argument can be combined
    def miltiplyBy5(n:Int):Int =5*n
    def plus3(n:Int):Int       =3+n
    def module2(n:Int):Int     =n%2
    
    //executed from right to left
    val complexFunction = miltiplyBy5 _ compose  plus3 _ compose module2 _
    println(complexFunction(10))
    
    //executed from left to right
    val complexFunction2 =  miltiplyBy5 _ andThen  plus3 _  andThen module2 _
    println(complexFunction2(10))
  
}
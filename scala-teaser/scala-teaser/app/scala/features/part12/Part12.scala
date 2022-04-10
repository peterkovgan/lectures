package scala.features.part12

import java.io.IOException

import scala.io.StdIn
import scala.util.Failure
import scala.util.Success
import scala.util.Try

import javax.sound.sampled.LineUnavailableException
import javax.sound.sampled.UnsupportedAudioFileException


object Part12 extends App {
  
   //Exception handling
  
  
   //1)
   //Java Like Style is possible
   @throws(classOf[IOException])
   @throws(classOf[LineUnavailableException])
   @throws(classOf[UnsupportedAudioFileException])
   def playSoundFileWithJavaAudio {
      //....
   }
   
   try{
     
     playSoundFileWithJavaAudio
     
   }catch{
     //only catch is partial function and needs match inside
     case e: IOException => throw new Exception
     case e: LineUnavailableException => println("lalala")
     case e: UnsupportedAudioFileException => println("lalala")
   }
   
   //2)
   //you can return a value instead
   val aString = "number?"
   val result = try {
      aString.toInt
   } catch {
      case nfe:NumberFormatException => 0
   }
   
   
   //3)
   //You can use Try/Success/Failure approach
   //you define method result us Try[return type]
   def divideXByY(x: Int, y: Int): Try[Int] = {
        
        //Note, you enclose dangerous statement into Try(...) block
     
        Try(x / y)
   }
   
   //now you invoke the method and use pattern matching
   divideXByY(10,0) match{
      case Success(i) => println(s"Success, value is: $i")
      case Failure(s) => println(s"Failed, message is: $s")
   }
   
   //OR, you can just consider success
   //that prints
   divideXByY(10,1).foreach { result => println(s"Success, value is: $result")}
   //that not, because it is Failure
   divideXByY(10,0).foreach { result => println(s"Success, value is: $result")}
   
   //Or you can check isSuccess:
   val tryDivide = divideXByY(10,0)
   if(tryDivide.isSuccess){
       println(tryDivide.get)
   }
   
   //5) or you can just ask getOrElse and provide fail-result
   val resulta = divideXByY(10,0).getOrElse(0)
   
   //6)
   //This Try has ability to chain results together
   val x = "5"
   val y = "6"
   
   val z = for {
       a <- Try(x.toInt)
       b <- Try(y.toInt)
   } yield a * b
   
   val answer = z.getOrElse(0) * 2  //getOrElse will either produce Success or default value
   println(answer)
   
   import scala.util.{Try, Success, Failure}

  def divide: Try[Int] = {
    val dividend = Try(StdIn.readLine("Enter an Int that you'd like to divide:\n").toInt)
    val divisor = Try(StdIn.readLine("Enter an Int that you'd like to divide by:\n").toInt)
    val problem = dividend.flatMap(x => divisor.map(y => x/y))
    problem match {
      case Success(v) =>
        println("Result of " + dividend.get + "/"+ divisor.get +" is: " + v)
        Success(v)
      case Failure(e) =>
        println("You must've divided by zero or entered something that's not an Int. Try again!")
        println("Info from the exception: " + e.getMessage)
        divide
    }
  }
   
 
  
   
}
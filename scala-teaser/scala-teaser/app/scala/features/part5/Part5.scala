package scala.features.part5

object Part5 extends App {
  
    def inputDogsNumber(s: String): Option[Int] = {
      
        try {
            Some(Integer.parseInt(s.trim))
        } catch {
            case e: Exception => None
        }
        
    }
    
    var result  =  inputDogsNumber("5")
    result foreach {
        value => println("they registered "+value+" dogs")
    }
  
    result  =  inputDogsNumber("million")
    result foreach {
        value => println("they registered "+value+" dogs - it will never be printed")
    }
    
    result  =  inputDogsNumber("billion")
    val guaranteedResult  = result.getOrElse(0)
    val guaranteedResult2  = result getOrElse 0  //that you can do to methods with 1 parameter
    //for example
    val res = this inputDogsNumber "5" //is also OK
    println(res)
    
    
    
    
    
  
}
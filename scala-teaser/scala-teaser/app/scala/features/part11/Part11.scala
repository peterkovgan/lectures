package scala.features.part11

object Part11 extends App {
     
     case class Broker(name:String, salary:Long)
     
     val b = Broker("Peter",0)
     
     case object MostPotentBroker
     
     val champion = MostPotentBroker
     
     val greeting = b match {
       case Broker("Peter", _)=>"Hello sir"
       case _ => "Good bye sir"  
     }
     
     println(greeting)
     
     class Broker1(val name:String, val salary:Long){
     }
     val b1 = new Broker1("Peter",0)
     
     val greeting1 = b1 match {
        case b:Broker1 if b.name=="Peter" =>"Hello sir"
        case _ => "goodbye mister"
     }
     
     println(greeting1)
}
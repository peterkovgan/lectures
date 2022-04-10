package scala.features.part10

object Part10 extends App {
  
     //example 1 - implicit method
  
     class TraderRoom(val definition:String){
           def payBonus(){
                println("pay $10 to each jerk in the room")
           }
     }
  
     object TraderRoom{
           implicit def apply(definition:String): TraderRoom = {
                 new TraderRoom(definition)
           }
     }
     
     def payThemBonus(room:TraderRoom){
         room.payBonus
     }
  
     payThemBonus("A bunch of rich, ugly idiots")  //note, it works!
     
     //example 2 - implicit class
     
     implicit class Broker(val compensation:Long){
     }
     
     def fireThisBroker(broker:Broker){
          println("I fire this guy ")
     }
     
     fireThisBroker(1000000)
     
     
     
     
     //3 - implicit function parameter
     implicit val defaultAnnualBonus = (x:Int)=> x * 0.1
     
     class Company{
       
          def payBonus(implicit f:Int=>Int){
              println("paid bonus " + f.apply(100))
          }
          
     }
     
     class CEO(val name:String,val salary:Int){
     }
    
     val company = new Company
     
     val manager = new CEO("Mark", 1000000)
     
     val poorPerformance = manager match {
         case it:CEO if it.name=="Peter" => true
         case it:CEO if it.name=="Mark"  => false
     }
     
     if(poorPerformance)
        company.payBonus  //note - there is no arguments
     else
        company.payBonus(x=> x * 100)
   
        
     //implicit conversions invisibly used in SO MANY PLACES...
     val array = "I have a time to drink".split(" ") //this method absent in class String
     //String converted implicitly to StringOps - here is the method
     //100 more like this in String
     
     
     
     
     
     
        
}
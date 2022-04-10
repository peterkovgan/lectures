package scala.features.part1

import scala.collection.mutable.MutableList
import scala.collection.immutable.List

object Part1 extends App{
  
   class Car (color:String, model:String) {
     
       def this(color:String) {
            this(color, "vw")
       }
       
       def doGaz(level:Int){
            Console println s"doing gas ${level} times"
       }
       
       def checkOil():Int = {
            Console println "checking oil"
            10
       }
    }
   
   object Car{
        def drive = println("drive like s static method")
   }
   
   
  
    val car   =  new Car("red", "pegeout")
    val car1  =  new Car("red")
    car.doGaz(5)   //works
    car doGaz 5    //works
    Console println "oil is " + car.checkOil
    Car.drive
    
    
    class HeavyCar (c:String, m:String, weight: Int) extends Car(c,"heavy model"+m){
          
          override  def checkOil() = {
             Console println "checking oil in heavy car"
             super.checkOil
          }
          
          override  def toString = {
             "heavy track color "+ c + " weight "+ weight
          }
    }
    
    val carh = new HeavyCar("white", "audi", 2)
    carh.checkOil
    println(carh)
    
    //traits
    trait Nice{
      
         def washByHand() = println("washing by hands")
         
         def polishMirrors(times:Int):Long = {
             Console println s"${times} polishing movements"
             System.currentTimeMillis
         }
    }
    //mixin with class during construction
    val niceCar  = new Car("blue", "opel-adam") with Nice
    niceCar.washByHand
    niceCar.polishMirrors(100)
    
    val niceCar2 = new Car("yellow","opel-adam") with Nice
    
    
    //created immutable collection
    val myCars = niceCar :: niceCar2 :: Nil
    //another immutable collection
    val myCars3 = niceCar2 :: myCars
    
    //created mutable collection for Car
    val myCars1 = new MutableList[Car]()
    myCars1+=car
    myCars1+=car1
    myCars1+=niceCar
    
    
    //created mutable collection for Car with Nice
    val myCars2 = new MutableList[Car with Nice]()
    myCars2+=niceCar
    myCars2+=niceCar2
    
    //abstract trait
    trait Trolley{
        def hardWorker:(Int, String, Long)  //not implemented method returns a tuple
    }
    
    //mixin with class during definition
    class Lorry (color:String="black") extends Car (color) with Trolley with Nice{
      
        override def hardWorker  = {  
          
          println("brrrr...hard....work....brrrr")
          
          //suppose it reports what job it has done in what time 
          (100, "hard tasks" , 1000)
          
        }
    }
    
    //multiple inheritance problem
    //what if particular method exists in two parents ("diamond problem")
    trait CombustionEngine {
        def drive
    }
 
    trait GermanCar extends CombustionEngine {
        override def drive() { println("rrrrrrrrrrrrrr") }
    }
 
    trait KoreanCar extends CombustionEngine {
         override def drive() { println("rrr rrr rrrrrrr khhhh khhhh khhhh khhhh") }
    }
 
    // Note: the "with" keyword goes between type names for multiple inheritance
    // You can chain on multiple "with X" clauses to mix in more traits. 
    class CubanCar extends KoreanCar with GermanCar
 
    //who will drive in that mixed car
    (new CubanCar()).drive
    //GermanCar wins..because it is last mixed in
    
    
    
    //you can mixin trats not only on definition stage... 
    val cubanCarThatAlsoNice = new CubanCar with Nice
    val anotherCubanCarThatAlsoNice = new CubanCar with Nice
    
    //and you can then use any combination of mixings in your declarations 
    //you can declare that your collection must contain such mixings
    val listOfNiceCubanCars:List[CubanCar with Nice] = List(cubanCarThatAlsoNice, anotherCubanCarThatAlsoNice)
    
    //or you can demand mixing in method declaration
    def washingNiceCubanCars(car:CubanCar with Nice){
        println("washing that nice cuban car "+ car)
    }
    
    
    //tuples
    
    //think how many times you created object to pass N params in return method
    
    val t = (4,carh,2d,"BMW")
    
    //print tuple members
    t.productIterator.foreach{ i =>println("Value = " + i )}
    
    //create method that accepts tuple with 4 parameters
    def strangeMethod(s:Tuple4[Int,Lorry,Double,String])={println(s)}

    //scala sees many things like objects
    val someV1 = 1.+(2)
    val someV2 = 1+2
    
    val someL1 = 2::1::Nil
    val someL2 = (Nil.::(1)).::(2)
    
    someL1.foreach(println)
    someL2.foreach(println)
    
    val f3 :Function3[Int,Int,Int,Boolean] = ( a :Int, b :Int, c :Int ) => (a + b) < c
    println(f3.apply(1, 2, 3))
}
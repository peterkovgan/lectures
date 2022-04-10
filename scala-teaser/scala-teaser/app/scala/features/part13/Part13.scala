package scala.features.part13

object Part13 extends App {
  
  
  //1)  how to init mutable or immutable collection
  //you can init collection from any super - type
  
  //immutable collection
  
  val fruits = Traversable("apple", "banana", "orange")
  //fruits: Traversable[String] = List(apple, banana, orange)
  for (f <- fruits) println(f)
  
  
  //mutable collection
  
  val fruitsM = collection.mutable.Traversable("appleM", "bananaM", "orangeM")
  //fruitsM: scala.collection.mutable.Traversable[String] = ArrayBuffer(apple, banana, orange)
  for (f <- fruitsM) println(f)
  
  
  
  //You can not update IMmutable collection
  //you only can create a new one
  val someCars = List("ford","pegeout")
  val moreCars = "opel" +: someCars
  val yetMoreCars = moreCars :+ "jeep"
  val aLotOfCars = moreCars ++ yetMoreCars
  
  
  //You CAN update mutable one
  var someNiceCars = collection.mutable.Buffer("ford","pegeout")
  //someNiceCars: scala.collection.mutable.Buffer[String] = ArrayBuffer(ford, pegeout)
  someNiceCars += "opel"
  someNiceCars(2) = "cadillac"
  for (f <- someNiceCars) println(f)
  
  
  //2   - different methods
  
  //a) filtering
  
  val fords = someNiceCars.filter { car => car.startsWith("fo") }
  val afterFordsMakeThem = someNiceCars.foldLeft("My Cars:")((accumulated,current) => accumulated+current+"|")
  
  //b) transformation
  val niceCarsForGirls = someNiceCars.map { element => "ForGirl:"+element }
  val niceCarsForBoys  = someNiceCars.map { element => "ForBoy:"+element }
  val zippedCars = niceCarsForGirls.zip(niceCarsForBoys)
  
  //c)  grouping
  val groups = niceCarsForGirls.groupBy(car=>car.contains("ford"))
  
  //There are a lot more to say about collections...
  
  
}
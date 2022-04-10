package scala.features.part4

object Part4 extends App {
    
    
  
    class Bio{def printMe = println("I'm Bio")}
    
    class Animal   extends Bio   {override def printMe = println("I'm Animal")}
  
    class Dog      extends Animal{override def printMe = println("I'm Dog")}
    
    class SuperDog extends Dog   {override def printMe = println("I'm Superdog")}
    
    
    ////UPPER BOUND
    
    def someMethod[A <: Dog](animal:A){    //A <: Dog - is "upper bound"
        animal.printMe
    }
    
    val animal   = new Animal
    val dog      = new Dog
    val superDog = new SuperDog
    
    //someMethod(animal)  - that sucks, because Animal is not sub class of Dog
    someMethod(dog)
    someMethod(superDog)
    
    ////  LOWER BOUND (note there is always implicit upper bound - Any)
    
    def someOtherMethod[A >: Dog](any:A){
        //any.printMe  - that will not compile, because upper bound is Any and it has no printMe method
    }
    someOtherMethod(new Bio)
    someOtherMethod(animal)   //takes Animal
    someOtherMethod(dog)      //takes Dog
    someOtherMethod(superDog) //takes SuperDog - that will pass, because upper bound is Any 
    
    def someOtherMethodTwoBounds[A >: Dog <: Animal](any:A){ //that will compile
        any.printMe  //works
    }
    
    //someOtherMethodTwoBounds(new Bio)  - not compiling, because upper bound type is Animal
    someOtherMethodTwoBounds(animal)   //takes Animal
    someOtherMethodTwoBounds(dog)      //takes Dog
    someOtherMethodTwoBounds(superDog) //takes SuperDog - that will pass, because upper bound is Any 
    
    
    ////      VARIANCES
    
    
    //Co-variance
    
    //Meaning: 
    // if B <: A
    //Then Box[B] could be accepted where Box[A] required, or Box[B] <: Box[A]
    
    class LivingThing
    class FourLegsCreature extends LivingThing
    class Cat extends FourLegsCreature
    class Mice extends FourLegsCreature
    
    
    class Box[+A](list:List[A]){
        //some logic of the box, for example
       def printIt = {
           for(a<-list){
               println(a)
           }
       }
    }
    
    def accept(boxWith4LegsCreatures: Box[FourLegsCreature]): Unit = {
        //do something with box   
        boxWith4LegsCreatures.printIt
    }
    accept(new Box[Cat](Nil))
    accept(new Box[Mice](Nil))
    accept(new Box[FourLegsCreature](Nil))
    //accept(new Box[LivingThing](Nil)) - this is an error
    
    
    //Contravariance
    //Meaning: 
    // if B <: A
    //Then Box[A] could be accepted where Box[B] required, or Box[A] <: Box[B]
    
    
    class Box1[-A](list:List[A]){
        //some logic of the box
        def printIt = {
           for(a<-list){
               println(a)
           }
        }
    }
    
    def accept1(boxWith4LegsCreatures: Box1[FourLegsCreature]): Unit = {
        //do something with box
        boxWith4LegsCreatures.printIt  
        
    }
    //accept1(new Box1[Cat](Nil))   - is not working
    //accept1(new Box1[Mice](Nil))  - is not working
    accept1(new Box1[FourLegsCreature](Nil))
    accept1(new Box1[LivingThing](Nil)) 
}
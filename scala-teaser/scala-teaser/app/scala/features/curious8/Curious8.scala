package scala.features.curious8

object Curious8 extends App {
     
     
     
     
     
     //multiple arguments...
     def tellHelloToPuppy1(name:String, number : Int, doitslowly:Boolean){
         for(i <- 0 until number){
              println("hello "+name)
              if(doitslowly){
                  Thread.sleep(100)
              }
         }
     }
     
     //I can create:
     //partially applied function
     //Note, I need specify _:Type for each missed argument
     val tellHelloToPuppy1Jack = tellHelloToPuppy1("Jack", _: Int, _ : Boolean)
     //and then fully apply it
     tellHelloToPuppy1Jack(5, true)
     
     
     
     
     //if instead I create the same function, but with list of argument blocks
     //such notation called "curryied"
     def tellHelloToPuppy2(name : String)(number : Int)(doitslowly:Boolean){
          for(i <- 0 until number){
              println("hello "+name)
              if(doitslowly){
                  Thread.sleep(100)
              }
         }
     }
     //for compiler it looks like that
     //String  =>  (Int =>   (Boolean => Unit)) = <function1>
     
     
     
     //now I use curried function to do the same , as above
     //note , here type of missed arguments has been 'inferred'
     //but as of execution - result will be the same
     //the single difference - inferred types
     val currying1  = tellHelloToPuppy2("alise") _ 
     val currying2 = currying1(10)
     val currying3 = currying2(true)
     
     
     //FYI:
     //you can easily convert simple func to curryied:
     val curryiedOne = (tellHelloToPuppy1 _).curried
     
}
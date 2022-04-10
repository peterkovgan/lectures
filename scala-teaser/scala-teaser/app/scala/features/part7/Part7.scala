package scala.features.part7

package otherscope {
  class Broker {
      def exec(order:(Int) => Unit, rate:Int) {
          order(rate)
      }
  }
}



object Part7 extends App {
  
        var operation = "buy"
        
        //function declared here!!!
        def tradeOrder(rate: Int) { 
             println(s"$operation with rate $rate") 
        }
        
        val broker = new otherscope.Broker
        
        broker.exec(tradeOrder, 10)
        
        // change the local variable 'operation', then execute 'trade' from
        // the exec method of Broker, and see what happens
        operation = "sell" //function body changed here!!!
        
        broker.exec(tradeOrder, 20)
        
        println("Note, that type of operation changed")
        
}
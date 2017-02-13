import akka.actor.ActorSystem
import colossus.IOSystem
import colossus.core.{Initializer, Server, ServerContext, WorkerRef}
import colossus.protocols.http.HttpMethod._
import colossus.protocols.http.HttpService
import colossus.protocols.http.UrlParsing._
import colossus.service.Callback


class ChatService(context: ServerContext) extends HttpService(context) {
    def handle = {
        case request @ Post on Root / "message" => {

            Callback.successful({
              Postman.userMessage("Hi there")
              request.ok("Hello World!")
            })
        }
    }
}

class ChatbotInitializer(worker: WorkerRef) extends Initializer(worker) {

    def onConnect = context => new ChatService(context)

}

/**
  * Created by jack on 2017-02-12.
  */
object main extends App {



    implicit val actorSystem = ActorSystem()
    implicit val io = IOSystem()

    Server.start("chatbot", 9000){ worker => new ChatbotInitializer(worker) }

    //private val s = new Scanner(System.in)

}
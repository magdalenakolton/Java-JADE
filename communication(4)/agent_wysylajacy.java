
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.Random;

public class agent_wysylajacy extends Agent
{
    Random random;
    protected void setup()
    {
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                random = new Random();
                int number = random.nextInt(1); //losowanie 0 lub 1
                System.out.println(number);
                if(number==0)
                {
                    ACLMessage message = new ACLMessage(ACLMessage.CFP); //jesli 0 to wysyla wiad typu cfp
                    message.addReceiver(new AID("Ala", AID.ISLOCALNAME));
                    message.setOntology("my_onto");
                    message.setContent("message CFP");
                    send(message);
                }
                else
                {
                    ACLMessage message = new ACLMessage(ACLMessage.REQUEST); //jesli 1 to wysyla wiad typu request
                    message.addReceiver(new AID("Ala", AID.ISLOCALNAME));
                    message.setOntology("presence");
                    message.setContent("message REQUEST");
                    send(message);
                }
                ACLMessage message = myAgent.receive(); //odbieranie wiadomosci
                if(message != null)
                {
                    System.out.println("Message : "+message.getContent()); //wypisywanie na ekran
                    if(message.getPerformative()==7) //jesli wiad typu inform agent sie usuwa
                    {
                       //myAgent.doDelete();
                    }
                }
                else
                {
                    block();
                }
            }
        });
    }
}
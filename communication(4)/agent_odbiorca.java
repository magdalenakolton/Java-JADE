import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class agent_odbiorca extends Agent {
    private MessageTemplate template = MessageTemplate.MatchOntology("my_onto");
    protected void setup() {
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                ACLMessage message = myAgent.receive(template);
                if(message != null) {
                    ACLMessage reply = message.createReply();
                    if(message.getPerformative() == 16) {
                        System.out.println("REQUEST message : "+message.getContent());
                        reply.setPerformative(ACLMessage.INFORM);
                        reply.setContent("Done!");

                    }
                    else if(message.getPerformative()==3) {
                        System.out.println("CFP message : "+message.getContent());
                        reply.setPerformative(ACLMessage.REQUEST);
                        reply.setContent("Once again.");
                    }
                    else {
                        reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
                        reply.setContent("Unknown message type.");
                    }
                    myAgent.send(reply);
                    System.out.println("Reply from receiver: " + reply);
                }
                else {
                    block();
                }
            }
        });
    }
}
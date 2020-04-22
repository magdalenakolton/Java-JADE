import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ag_odb extends Agent {
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
                        System.out.println("Reply from receiver: " + reply);

                    }
                    else if(message.getPerformative()==3) {
                        System.out.println("CFP message : "+message.getContent());
                        reply.setPerformative(ACLMessage.REQUEST);
                        reply.setContent("Once again.");
                        System.out.println("Reply from receiver: " + reply);
                    }
                    else {
                        reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
                        reply.setContent("Unknown message type.");
                        System.out.println("Reply from receiver: " + reply);
                    }
                    myAgent.send(reply);

                }
                else {
                    block();
                }
            }
        });
    }
}
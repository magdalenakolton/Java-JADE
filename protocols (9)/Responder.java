import jade.core.Agent;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;

public class Responder extends Agent {
    protected void setup() {
        System.out.println("Agent " + getLocalName() + " is waiting...");
        MessageTemplate template = MessageTemplate.and(
                MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST),
                MessageTemplate.MatchPerformative(ACLMessage.REQUEST));

        addBehaviour(new AchieveREResponder(this, template) {
            protected ACLMessage handleRequest(ACLMessage request) throws NotUnderstoodException, RefuseException {
                double num = Double.parseDouble(request.getContent());
                System.out.println("Agent " + getLocalName() + ": received REQUEST from " + request.getSender().getName() + ". Response: " + request.getContent());
                if (checkAction(num)) {
                    System.out.println("Agent " + getLocalName() + ": Agree");
                    ACLMessage agree = request.createReply();
                    agree.setPerformative(ACLMessage.AGREE);
                    return agree;
                } else {
                    System.out.println("Agent " + getLocalName() + ": refused");
                    throw new RefuseException("failure");
                }
            }

            protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response) throws FailureException {
                double num = Double.parseDouble(request.getContent());
                if (performAction(num)) {
                    double result = Math.sqrt(num);
                    System.out.println("Agent " + getLocalName() + ": succesfully performed");
                    ACLMessage inform = request.createReply();
                    inform.setPerformative(ACLMessage.INFORM);
                    inform.setContent(String.valueOf(result));
                    return inform;
                } else {
                    System.out.println("Agent " + getLocalName() + ": ");
                    throw new FailureException("failure");
                }
            }
        });
    }

    private boolean checkAction(double num) throws RefuseException {
        if (num < 0) throw new RefuseException("incorrect argument");
        return true;
    }

    private boolean performAction(double num) throws FailureException {
        if (num < 0) throw new FailureException("incorrect argument");
        return true;
    }
}




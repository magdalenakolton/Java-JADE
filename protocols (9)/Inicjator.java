import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;
import jade.proto.AchieveREResponder;

import java.util.Date;
import java.util.Random;
import java.util.Vector;

public class Inicjator extends Agent {
    private int nResponders;

    protected void setup() {

        //losuje liczbe

        Random random = null;
        int number = (int) (Math.round(random.nextDouble() * 100));
        System.out.println("Wylosowano liczbe: "+number);

        //przygotowuje wiadomosc:

        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(new AID("John", AID.ISLOCALNAME));
        msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
        msg.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
        msg.setContent(String.valueOf(number));
        nResponders = 1;

        //tworze agenta inicjatora:

        addBehaviour(new AchieveREInitiator(this, msg) {
            protected void handleInform(ACLMessage inform) {
                System.out.println("Succesfully performed");
            }

            protected void handleRefuse(ACLMessage refuse) {
                System.out.println("refused");
                nResponders--;
            }

            protected void handleFailure(ACLMessage failure) {
                System.out.println("failed");
            }

            protected void handleAllResultNotifications(Vector notifications) {
                if (notifications.size() < nResponders) {
                    System.out.println("Missing: " + (nResponders - notifications.size()) + " responses");
                }
            }
        });


    }
}
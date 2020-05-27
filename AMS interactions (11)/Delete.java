
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;
import jade.domain.*;
import jade.domain.JADEAgentManagement.*;
import jade.content.onto.basic.*;



public class Delete extends Agent {

    public void setup() {

        getContentManager().registerLanguage(new jade.content.lang.sl.SLCodec(0));
        getContentManager().registerOntology(JADEManagementOntology.getInstance());

        KillAgent kill = new KillAgent(); //utworzenie akcji KillAgent
        kill.setAgent(getAID("John")); //wskazanie agenta, ktorego akcja dotyczy

        Action actExpr = new Action(getAMS(), kill); //przypisanie akcji KillAgent

        ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
        request.addReceiver(getAMS());
        request.setOntology(JADEManagementOntology.getInstance().getName());
        request.setLanguage(FIPANames.ContentLanguage.FIPA_SL0);
        request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
        try {
            getContentManager().fillContent(request, actExpr);
            addBehaviour(new AchieveREInitiator(this, request) {
                protected void handleInform(ACLMessage inform) {
                    System.out.println("Usunieto agenta.");
                } //powodzenie akcji KillAgent

                protected void handleFailure(ACLMessage failure) {
                    System.out.println("Blad!");
                } //gdy wystapi blad
            } );
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}

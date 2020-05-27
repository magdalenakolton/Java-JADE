
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;
import jade.domain.*;
import jade.domain.JADEAgentManagement.*;
import jade.content.onto.basic.*;



public class ShutDown extends Agent {

    public void setup() {

        getContentManager().registerLanguage(new jade.content.lang.sl.SLCodec(0));
        getContentManager().registerOntology(JADEManagementOntology.getInstance());

        ShutdownPlatform shutdown = new ShutdownPlatform(); //utworzenie nowej akcji zamykajacej platforme

        Action actExpr = new Action(getAMS(), shutdown); //przypisanie akcji zamykania
        ACLMessage request = new ACLMessage(ACLMessage.REQUEST); //utworzenie wiadomosci typu REQUEST
        request.addReceiver(getAMS()); //dodanie odbiorcy
        request.setOntology(JADEManagementOntology.getInstance().getName());
        request.setLanguage(FIPANames.ContentLanguage.FIPA_SL0);
        request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
        try {
            getContentManager().fillContent(request, actExpr);
            addBehaviour(new AchieveREInitiator(this, request) { //inicjator wysyla wiadomosc
                protected void handleInform(ACLMessage inform) {
                    System.out.println("Zamknieto");
                } //wiadomosc inform jestli sie powiodlo

                protected void handleFailure(ACLMessage failure) {
                    System.out.println("Blad!");
                } //wyswietlenie o bledzie gdy wystapi
            } );
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}

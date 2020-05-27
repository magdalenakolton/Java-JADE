import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;
import jade.domain.*;
import jade.domain.JADEAgentManagement.*;
import jade.content.onto.basic.*;

import jade.util.leap.List;

public class CreatingAgent extends Agent {

    public void setup() {

        getContentManager().registerLanguage(new jade.content.lang.sl.SLCodec(0));
        getContentManager().registerOntology(JADEManagementOntology.getInstance());

        CreateAgent createAgent = new CreateAgent();
        createAgent.setAgentName("john");
        createAgent.setClassName("HelloWorldAgent");
        createAgent.setContainer(new ContainerID("Main-Container", null));

        QueryAgentsOnLocation  query=new QueryAgentsOnLocation(); //utworzenie akcji
        query.setLocation(new ContainerID("Container", null)); //ustawienie location
        Action actExpr = new Action(getAMS(), query); //przypisanie akcji

        ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
        request.addReceiver(getAMS());
        request.setOntology(JADEManagementOntology.getInstance().getName());
        request.setLanguage(FIPANames.ContentLanguage.FIPA_SL0);
        request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);

        try {
            getContentManager().fillContent(request, actExpr);
            addBehaviour(new AchieveREInitiator(this, request) {
                protected void handleInform(ACLMessage inform) {
                    Result lista= null;
                    try {
                        lista = (Result) myAgent.getContentManager().extractContent(inform);
                        List result=(List)lista.getValue();
                        for(int i=0;i<result.size();i++){
                            System.out.println(result.get(i));
                        }
                    } catch (Codec.CodecException e) {
                        e.printStackTrace();
                    } catch (OntologyException e) {
                        e.printStackTrace();
                    }


                }
                protected void handleFailure(ACLMessage failure) {
                    System.out.println("Error .\n"+failure);
                }
            } );
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }


}
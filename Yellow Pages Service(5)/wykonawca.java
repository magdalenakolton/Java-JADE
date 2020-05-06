import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.domain.FIPAException;

public class wykonawca extends Agent{

    protected void setup() {

        super.setup();

        DFAgentDescription dfAgentDescription = new DFAgentDescription();
        dfAgentDescription.setName( getAID() );
        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setType( "WYPISUJE" );
        serviceDescription.setName( getLocalName() );
        dfAgentDescription.addServices(serviceDescription);

        try {
            DFService.register(this, dfAgentDescription);
        }
        catch (FIPAException e) { e.printStackTrace(); }
    }

    protected void takeDown() {
        try {
            DFService.deregister(this);
        } catch (FIPAException e) {e.printStackTrace();}

    }
}
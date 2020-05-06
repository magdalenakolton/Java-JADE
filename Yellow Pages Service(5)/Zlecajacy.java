import java.util.Random;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class Zlecajacy extends Agent{
    private Zlecajacy myAgent;
    private Integer number;

    protected void setup() {
        super.setup();
        myAgent = this;
        number = null;

        FSMBehaviour fsm = new FSMBehaviour(this) {
            public int onEnd() {
                myAgent.doDelete();
                return super.onEnd();
            }
        };

        addBehaviour(new TickerBehaviour(this, 2000) {
            protected void onTick() {
                System.out.println("Zlecajacy: sprawdzam uslugi...");

                DFAgentDescription dfd = new DFAgentDescription();
                ServiceDescription sd = new ServiceDescription();
                sd.setType( "WYPISUJE");
                dfd.addServices(sd);

                SearchConstraints ALL = new SearchConstraints();
                ALL.setMaxResults((long) -1);
                try
                {
                    DFAgentDescription[] result = DFService.search(myAgent, dfd, ALL);
                    System.out.println("Ilosc wyszukanych uslug w serwisie WYPISUJE: " + result.length);
                    for(int i = 0; i < result.length; i++) send(result[i].getName());

                }
                catch (FIPAException fe) { System.out.println("Brak uslug!"); }
            }
        });
    }
    private void send(AID aid) {
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(aid);
        msg.setContent("Wiadomosc");
        send(msg);
        System.out.println("Zlecajacy: Wysylam wiadomosc REQUEST do agenta " + aid);
        myAgent.doDelete();
    }

    protected void takeDown() {
        System.out.println("Zlecajacy konczy dzialanie.");
    }
}

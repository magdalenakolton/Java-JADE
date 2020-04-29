import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.FSMBehaviour;

public class Maszyna extends Agent {
    private static final String STATE_A = "A";
    private static final String STATE_B = "B";
    private static final String STATE_C = "C";
    private static final String STATE_D = "D";
    private static final String STATE_E = "E";

    protected void setup() {
        FSMBehaviour fsm = new FSMBehaviour(this) {
            public int onEnd() {
                System.out.println("FSM behaviour completed.");
                myAgent.doDelete();
                return super.onEnd();
            }
        };

        fsm.registerFirstState(new NamePrinter(), STATE_A);
        fsm.registerState(new RandomGenerator(2), STATE_B);
        fsm.registerState(new NamePrinter(), STATE_C );
        fsm.registerState(new RandomGenerator(2), STATE_D);
        fsm.registerLastState(new NamePrinter(), STATE_E);

        fsm.registerDefaultTransition(STATE_A, STATE_B);
        fsm.registerTransition(STATE_B, STATE_D, 0);
        fsm.registerTransition(STATE_B, STATE_C, 1);
        fsm.registerDefaultTransition(STATE_C, STATE_D);
        fsm.registerTransition(STATE_D, STATE_A, 1);
        fsm.registerTransition(STATE_D, STATE_E, 0);

        addBehaviour(fsm);
    }

    private class NamePrinter extends OneShotBehaviour {
        public void action() {
            System.out.println("Executing behaviour "+getBehaviourName());
        }
    }

    private class RandomGenerator extends NamePrinter {
        private int maxExitValue;
        private int exitValue;

        private RandomGenerator(int max) {
            super();
            maxExitValue = max;
        }

        public void action() {
            System.out.println("Executing behaviour "+getBehaviourName());
            exitValue = (int) (Math.random() * maxExitValue)%2;
            System.out.println("Exit value is "+exitValue);
        }

        public int onEnd() {
            return exitValue;
        }
    }
}

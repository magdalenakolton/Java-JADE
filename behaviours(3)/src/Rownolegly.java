import jade.core.Agent;
import jade.core.behaviours.*;

public class Rownolegly extends Agent {

    private ParallelBehaviour tbf = new ParallelBehaviour();

    protected void setup() {
        Behaviour B1=new ThreeClassBehaviour();
        Behaviour B2=new ThreeClassBehaviour();
        Behaviour B3=new ThreeClassBehaviour();

        tbf.addSubBehaviour(B1);
        tbf.addSubBehaviour(B2);
        tbf.addSubBehaviour(B3);

        addBehaviour(tbf);
    }

    public class ThreeClassBehaviour extends Behaviour{
        private int step = 0;

        public void action( ) {
            switch (step) {
                case 0:
                    System.out.println("Dzialam 1...");
                    step++;
                    break;
                case 1:
                    System.out.println("Dzialam 2...");
                    step++;
                    break;
                case 2:
                    System.out.println("Dzialam 3...");
                    step++;
                    break;
            }
        }
        public boolean done( ) {
            return step == 3;
        }
    }
}




import jade.content.onto.*;
import jade.content.schema.*;
import java.util.*;

public class ApartamentOntology extends Ontology {

    public static final String NAME = "ApartamentOntology"; //nazwa ontologii

    public static final String APARTAMENT = "Apartament"; //koncepty
    public static final String PERSON = "Person";

    public static final String ADRESS = "Adress"; //sloty w ontologii "Apartament"
    public static final String MEASUREMENT = "Measurement";

    public static final String NAME2= "Name"; //sloty w ontologii "Person"
    public static final String ID = "ID";

    public static final String OWNS = "Owns"; //predykat

    private static Ontology theInstance = new ApartamentOntology();

    public static Ontology getInstance() {
        return theInstance;
    }

    private ApartamentOntology() {

        super(NAME, BasicOntology.getInstance()); //wywołanie konstruktora

        try {
            add(new ConceptSchema(APARTAMENT), Apartament.class); //dodanie konceptu, ktorym jest Apartament - klasa Apartament napisana w Javie
            add(new ConceptSchema(PERSON), Person.class); //dodanie kolejnego konceptu do ontologii
            add(new PredicateSchema(OWNS), Owns.class); //predykat, ktoremu bedzie odpowiadal obiekt w Javie, bedzie on klasy Owns.class

            //musimy powiedziec, na czym ten koncept polega, czyli jakie ma pola (sloty):
            //kazdy Apartament będzie mial wymienionej nizej rzeczy czyli adres i wymiar:
            ConceptSchema cs = (ConceptSchema)getSchema(APARTAMENT);
            cs.add(ADRESS, (PrimitiveSchema)getSchema(BasicOntology.STRING));
            cs.add(MEASUREMENT, (PrimitiveSchema)getSchema(BasicOntology.FLOAT), ObjectSchema.OPTIONAL);

            //to samo musimy zrobic dla konceptu Person, czyli musi on miec stringa zawierajacego imie
            //oraz informacje o ID (integer):
            cs = (ConceptSchema)getSchema(PERSON);
            cs.add(NAME2, (PrimitiveSchema)getSchema(BasicOntology.STRING));
            cs.add(ID, (PrimitiveSchema)getSchema(BasicOntology.INTEGER));

            //to samo rowniez robimy dla predykatu:
            PredicateSchema ps = (PredicateSchema)getSchema(OWNS);
            ps.add(PERSON, (ConceptSchema)getSchema(APARTAMENT));

        }
        catch(OntologyException oe) {
            oe.printStackTrace();
        }
    }
}


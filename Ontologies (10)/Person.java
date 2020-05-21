import jade.content.Predicate;

public class Person implements Predicate {

    private String 	_name;
    private Integer _id;

    public void setName(String name) {
        _name=name;
    }
    public String getName() {
        return _name;
    }

    public void setID(Integer ID) {
        _id=ID;
    }
    public Integer getID() {
        return _id;
    }


}

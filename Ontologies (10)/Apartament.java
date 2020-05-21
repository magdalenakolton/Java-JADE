import jade.content.Predicate;

public class Apartament implements Predicate {

    private String _adress;
    private float _measurement;

    public void setAdress(String adress) {
        _adress=adress;
    }
    public String getAdress() {
        return _adress;
    }

    public void setMeasurement(float measurement) {
        _measurement=measurement;
    }
    public float getMeasurement() {
        return _measurement;
    }
}

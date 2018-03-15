package a238443.bmi;

public abstract class BMI {
    private double d_weight;
    private double d_height;

    BMI(double dWeight, double dHeight) {
        this.d_weight = dWeight;
        this.d_height = dHeight;
    }

    double d_getWeight() {
        return d_weight;
    }

    double d_getHeight() {
        return d_height;
    }

    abstract public double d_calculateBMI();

    abstract protected boolean b_dataIsValid();

    abstract protected int[] it_validatingMessage();
}

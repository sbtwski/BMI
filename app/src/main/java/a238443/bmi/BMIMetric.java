package a238443.bmi;

public class BMIMetric extends BMI{
    BMIMetric(double dWeight, double dHeight) {
        super(dWeight, dHeight);
    }

    @Override
    public double d_calculateBMI() {
        if(!b_dataIsValid())
            throw new IllegalArgumentException("Invalid data");
        return d_getWeight()/(d_getHeight()*d_getHeight());
    }

    @Override
    protected boolean b_dataIsValid() {
        return d_getWeight()>=5 && d_getHeight()>=0.75 && d_getHeight()<=2.75;
    }

    @Override
    protected int[] it_validatingMessage() {
        int[] it_message = {R.string.data_valid, R.string.data_valid, R.string.data_valid};

        if(!b_dataIsValid()) {
            it_message[0] = R.string.data_not_valid;
            if(d_getWeight()<5)
                it_message[1] = R.string.weight_input_low;
            if(d_getHeight()<0.75)
                it_message[2] = R.string.height_input_low;
            else{
                if(d_getHeight()>2.75)
                    it_message[2] = R.string.height_input_big;
            }
        }

        return it_message;
    }
}

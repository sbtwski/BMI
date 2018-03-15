package a238443.bmi;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    /*
        METRIC
        Testing basic validation
    */
    @Test
    public void for_valid_metric_weight_and_height_should_return_correct_value() {
        BMI bmi_counter = new BMIMetric(60,1.70);
        double d_bmi = bmi_counter.d_calculateBMI();
        assertEquals(20.761,d_bmi,0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void for_zero_metric_weight_and_height_should_throw_exception() {
        BMI bmi_counter = new BMIMetric(0,0);
        bmi_counter.d_calculateBMI();
    }

    @Test(expected = IllegalArgumentException.class)
    public void for_negative_metric_weight_and_height_should_throw_exception() {
        BMI bmi_counter = new BMIMetric(-10,-14);
        bmi_counter.d_calculateBMI();
    }

    @Test(expected = IllegalArgumentException.class)
    public void for_too_low_positive_metric_weight_should_throw_exception() {
        BMI bmi_counter = new BMIMetric(2,50);
        bmi_counter.d_calculateBMI();
    }

    @Test(expected = IllegalArgumentException.class)
    public void for_too_low_positive_metric_height_should_throw_exception() {
        BMI bmi_counter = new BMIMetric(30,0.5);
        bmi_counter.d_calculateBMI();
    }

    @Test(expected = IllegalArgumentException.class)
    public void for_too_big_metric_height_should_throw_exception() {
        BMI bmi_counter = new BMIMetric(30, 12);
        bmi_counter.d_calculateBMI();
    }

    /*
        METRIC
        Testing validation method giving id of prompt to show
    */

    @Test
    public void for_valid_metric_weight_and_height_should_return_valid_message_id() {
        BMI bmi_counter = new BMIMetric(60,1.70);
        assertEquals(bmi_counter.it_validatingMessage(), R.string.data_valid);
    }

    @Test
    public void for_too_low_metric_weight_should_return_according_message_id() {
        BMI bmi_counter = new BMIMetric(3,1.70);
        assertEquals(bmi_counter.it_validatingMessage(), R.string.weight_input_low);
    }

    @Test
    public void for_too_low_metric_height_should_return_according_message_id() {
        BMI bmi_counter = new BMIMetric(60,0.5);
        assertEquals(bmi_counter.it_validatingMessage(), R.string.height_input_low);
    }

    @Test
    public void for_too_big_metric_height_should_return_according_message_id() {
        BMI bmi_counter = new BMIMetric(60,5);
        assertEquals(bmi_counter.it_validatingMessage(), R.string.height_input_big);
    }

    /*
        IMPERIAL
        Testing basic validation
    */

    @Test
    public void for_valid_imperial_weight_and_height_should_return_correct_value() {
        BMI bmi_counter = new BMIImperial(132,66.93);
        double d_bmi = bmi_counter.d_calculateBMI();
        assertEquals(20.715,d_bmi,0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void for_zero_imperial_weight_and_height_should_throw_exception() {
        BMI bmi_counter = new BMIImperial(0,0);
        bmi_counter.d_calculateBMI();
    }

    @Test(expected = IllegalArgumentException.class)
    public void for_negative_imperial_weight_and_height_should_throw_exception() {
        BMI bmi_counter = new BMIImperial(-10,-14);
        bmi_counter.d_calculateBMI();
    }

    @Test(expected = IllegalArgumentException.class)
    public void for_too_low_positive_imperial_weight_should_throw_exception() {
        BMI bmi_counter = new BMIImperial(10,50);
        bmi_counter.d_calculateBMI();
    }

    @Test(expected = IllegalArgumentException.class)
    public void for_too_low_positive_imperial_height_should_throw_exception() {
        BMI bmi_counter = new BMIImperial(30,20);
        bmi_counter.d_calculateBMI();
    }

    @Test(expected = IllegalArgumentException.class)
    public void for_too_big_imperial_height_should_throw_exception() {
        BMI bmi_counter = new BMIImperial(30,120);
        bmi_counter.d_calculateBMI();
    }

    /*
        IMPERIAL
        Testing validation method giving id of prompt to show
    */

    @Test
    public void for_valid_imperial_weight_and_height_should_return_valid_message_id() {
        BMI bmi_counter = new BMIImperial(50,60);
        assertEquals(bmi_counter.it_validatingMessage(), R.string.data_valid);
    }

    @Test
    public void for_too_low_imperial_weight_should_return_according_message_id() {
        BMI bmi_counter = new BMIImperial(3,50);
        assertEquals(bmi_counter.it_validatingMessage(), R.string.weight_input_low);
    }

    @Test
    public void for_too_low_imperial_height_should_return_according_message_id() {
        BMI bmi_counter = new BMIImperial(50,10);
        assertEquals(bmi_counter.it_validatingMessage(), R.string.height_input_low);
    }

    @Test
    public void for_too_big_imperial_height_should_return_according_message_id() {
        BMI bmi_counter = new BMIImperial(50,150);
        assertEquals(bmi_counter.it_validatingMessage(), R.string.height_input_big);
    }
}
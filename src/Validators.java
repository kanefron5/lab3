import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Validators {
    @FacesValidator("ValidatorY")
    public static class ValidatorY implements Validator {
        @Override
        public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
            ValidatorException validatorException = new ValidatorException(new FacesMessage("Число должно быть в диапозоне от -3.0 до 5.0"));
            if (value == null || String.valueOf(value).isEmpty()) throw validatorException;
            System.out.printf("%s=%s\n", value.getClass(), value);
            double value1 = Double.parseDouble(String.valueOf(value).replace(",", "."));
            if (value1 <= -3 || value1 >= 5) throw validatorException;
        }
    }

    @FacesValidator("ValidatorX")
    public static class ValidatorX implements Validator {
        static int counterChecked = 0;
        static int counterUnchecked = 0;

        @Override
        public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
            if (value.equals(true)) counterChecked++;
            if (value.equals(false)) counterUnchecked++;
            System.out.printf("counterUnchecked=%d; counterChecked=%d\n", counterUnchecked, counterChecked);

            if ((counterUnchecked + counterChecked) < 7) return;
            if ((counterUnchecked + counterChecked) > 7) throw new ValidatorException(new FacesMessage("Смэрть."));

            if (counterUnchecked != 1 && counterChecked != 1) {
                counterUnchecked = 0;
                counterChecked = 0;
                throw new ValidatorException(new FacesMessage("Выберите значение X. X может быть только один."));
            }
            counterUnchecked = 0;
            counterChecked = 0;
        }
    }

    @FacesValidator("ValidatorR")
    public static class ValidatorR implements Validator {

        @Override
        public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
            System.out.printf("%s=%s\n", value.getClass(), value);
            List<String> correct = new ArrayList<>(Arrays.asList("1", "1.5", "2", "2.5", "3"));
            if (!correct.contains(value.toString().replace(",", ".")))
                throw new ValidatorException(new FacesMessage("И зачем?"));
        }
    }
}

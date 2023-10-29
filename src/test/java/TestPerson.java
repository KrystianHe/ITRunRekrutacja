import com.app.utils.DataValidator;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestPerson {
    @Test
    public void testIsValidEmail() {
        var validator = new DataValidator();

        assertTrue(validator.isValidEmail("jan@gmail.com"));
        assertTrue(validator.isValidEmail("anna@777.com"));
        assertFalse(validator.isValidEmail("invalid-email"));
        assertFalse(validator.isValidEmail("test@.com"));
    }

    @Test
    public void testIsValidPesel() {
        DataValidator validator = new DataValidator();

        assertTrue(validator.isValidPesel("80010112345"));
        assertFalse(validator.isValidPesel("12345"));
        assertFalse(validator.isValidPesel("abcdefghijk"));
    }

    @Test
    public void testIsValidName() {
        DataValidator validator = new DataValidator();

        assertTrue(validator.isValidName("Jan Kowalski"));
        assertTrue(validator.isValidName("Anna Nowak"));
        assertFalse(validator.isValidName("12345"));
        assertFalse(validator.isValidName("A"));
    }

    @Test
    public void testIsValidPhoneNumber() {
        DataValidator validator = new DataValidator();

        assertTrue(validator.isValidPhoneNumber("+481234567890"));
        assertFalse(validator.isValidPhoneNumber("1234567890"));
        assertFalse(validator.isValidPhoneNumber("+123"));
    }
}


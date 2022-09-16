package az.babazade.pharmacy.exception;

public class PharmacyException extends RuntimeException {

    private Integer code;

    public PharmacyException() {

    }

    public PharmacyException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }


}

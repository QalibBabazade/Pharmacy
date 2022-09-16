package az.babazade.pharmacy.enums;

public enum EnumAvailableRole {

    ADMIN("ROLE_ADMIN") , USER("ROLE_USER");

    private String value;

    private EnumAvailableRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}

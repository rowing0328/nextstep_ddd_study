package racingcar.domain;

public record ModelName(String name) {

    private static final String ERROR_NAME_TOO_LONG = "이름은 %d글자를 넘을 수 없습니다.";
    private static final int MAX_NAME_LENGTH = 5;

    public ModelName {
        validateLength(name);
    }

    private static void validateLength(final String name) {
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException(String.format(ERROR_NAME_TOO_LONG, MAX_NAME_LENGTH));
        }
    }

    public static ModelName from(final String name) {
        return new ModelName(name);
    }

    @Override
    public String toString() {
        return "ModelName{" +
                "name='" + name + '\'' +
                '}';
    }

}

package racingcar.domain;

import java.util.Objects;

public class ModelName {

    private static final String ERROR_NAME_TOO_LONG = "이름은 %d글자를 넘을 수 없습니다.";

    private static final int MAX_NAME_LENGTH = 5;

    private final String name;

    private ModelName(final String name) {
        this.name = name;
        validateLength(name);
    }

    private void validateLength(final String name) {
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException(String.format(ERROR_NAME_TOO_LONG, MAX_NAME_LENGTH));
        }
    }

    public static ModelName from(final String name) {
        return new ModelName(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ModelName modelName = (ModelName) o;
        return Objects.equals(name, modelName.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return "ModelName{" +
                "name='" + name + '\'' +
                '}';
    }

}

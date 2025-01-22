package racingcar.domain;

import java.util.Objects;

public class Position {

    private static final String ERROR_NEGATIVE_POSITION = "위치는 %d보다 큰 숫자를 입력해야합니다.";

    private static final int MIN_POSITION = 0;
    private static final int MOVE_INCREMENT = 1;

    private final int position;

    private Position(final int position) {
        this.position = position;
        checkPositive(position);
    }

    private void checkPositive(final int position) {
        if (position < 0) {
            throw new IllegalArgumentException(String.format(ERROR_NEGATIVE_POSITION, MIN_POSITION));
        }
    }

    public static Position from(final int initialPosition) {
        return new Position(initialPosition);
    }

    public Position move(final boolean moveable) {
        if (moveable) {
            return new Position(this.position + MOVE_INCREMENT);
        }
        return this;
    }

    public int getPosition() {
        return this.position;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Position position1 = (Position) o;
        return position == position1.position;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(position);
    }

    @Override
    public String toString() {
        return "Position{" +
                "position=" + position +
                '}';
    }

}

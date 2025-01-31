package racingcar.domain;

public record Position(int position) {

    private static final String ERROR_NEGATIVE_POSITION = "위치는 %d보다 큰 숫자를 입력해야합니다.";
    private static final int MIN_POSITION = 0;
    private static final int MOVE_INCREMENT = 1;

    public Position {
        checkPositive(position);
    }

    private static void checkPositive(final int position) {
        if (position < MIN_POSITION) {
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

    @Override
    public String toString() {
        return "Position{" +
                "position=" + position +
                '}';
    }

}

package racingcar.strategy;

public class ThresholdStrategy implements MoveStrategy {

    private static final int CONDITION = 4;

    @Override
    public boolean moveable(final int inputValue) {
        return inputValue >= CONDITION;
    }

}

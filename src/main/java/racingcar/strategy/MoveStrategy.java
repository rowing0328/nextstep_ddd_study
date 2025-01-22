package racingcar.strategy;

@FunctionalInterface
public interface MoveStrategy {

    boolean moveable(int inputValue);

}

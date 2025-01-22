package racingcar.domain;

import racingcar.strategy.MoveStrategy;
import java.util.Objects;

public class Car {

    private final ModelName modelName;
    private final Position position;

    private Car(ModelName modelName, Position position) {
        this.modelName = modelName;
        this.position = position;
    }

    public static Car of(final String name, final int position) {
        return new Car(ModelName.from(name), Position.from(position));
    }

    public Car move(final MoveStrategy moveStrategy, final int inputValue) {
        return new Car(this.modelName, position.move(moveStrategy.moveable(inputValue)));
    }

    public String getModelName() {
        return this.modelName.getName();
    }

    public int getPosition() {
        return this.position.getPosition();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(modelName, car.modelName) && Objects.equals(position, car.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(modelName, position);
    }

    @Override
    public String toString() {
        return "Car{" +
                "modelName=" + modelName +
                ", position=" + position +
                '}';
    }

}

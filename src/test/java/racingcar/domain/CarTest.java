package racingcar.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import racingcar.strategy.MoveStrategy;
import racingcar.strategy.ThresholdStrategy;

import static org.assertj.core.api.Assertions.assertThat;

class CarTest {

    @Test
    void Car_객체를_정상적으로_생성한다() {
        final Car car = new Car("Car", 0);

        assertThat(car.getModelName()).isEqualTo("Car");
        assertThat(car.getPosition()).isEqualTo(0);
    }

    @ValueSource(ints = {4, 5, 6, 7, 8, 9})
    @ParameterizedTest
    void 입력_값이_4이상인_경우_자동차_위치가_1만큼_이동한다(int inputValue) {
        MoveStrategy moveStrategy = new ThresholdStrategy();
        final var car = new Car("Car", 0);

        final var movedCar = car.move(moveStrategy, inputValue);

        assertThat(movedCar.getPosition()).isEqualTo(1);
    }

    @ValueSource(ints = {1, 2, 3})
    @ParameterizedTest
    void 입력_값이_3이하인_경우_자동차는_이동하지_않는다(int inputValue) {
        MoveStrategy moveStrategy = new ThresholdStrategy();
        final var car = new Car("Car", 0);

        final var movedCar = car.move(moveStrategy, inputValue);

        assertThat(movedCar.getPosition()).isEqualTo(0);
    }

}

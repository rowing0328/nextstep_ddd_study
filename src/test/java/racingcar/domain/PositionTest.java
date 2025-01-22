package racingcar.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class PositionTest {

    @Test
    void 초기_위치를_생성한다() {
        final var position = Position.from(0);
        assertThat(position.getPosition()).isEqualTo(0);
    }

    @Test
    void 위치_값이_음수일_경우_예외를_발생시킨다() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Position.from(-1))
                .withMessage("위치는 0보다 큰 숫자를 입력해야합니다.");
    }

    @Test
    void 이동이_가능하면_위치를_증가시킨다() {
        final var position = Position.from(0);
        final var newPosition = position.move(true);

        assertThat(newPosition.getPosition()).isEqualTo(1);
    }

    @Test
    void 이동이_불가능하면_위치를_유지한다() {
        final var position = Position.from(0);
        final var newPosition = position.move(false);

        assertThat(newPosition.getPosition()).isEqualTo(0);
    }

}
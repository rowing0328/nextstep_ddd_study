package racingcar.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class ModelNameTest {

    @Test
    void 모델_이름이_유효한_경우_정상_생성된다() {
        final var modelName = ModelName.from("Car");
        assertThat(modelName.name()).isEqualTo("Car");
    }

    @Test
    void 모델_이름이_5글자를_초과하면_예외를_발생시킨다() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> ModelName.from("rowing0328"))
                .withMessage("이름은 5글자를 넘을 수 없습니다.");
    }

}
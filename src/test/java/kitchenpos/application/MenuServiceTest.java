package kitchenpos.application;

import kitchenpos.domain.*;
import kitchenpos.fixture.MenuFixture;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static kitchenpos.fixture.MenuFixture.*;
import static kitchenpos.fixture.MenuGroupFixture.menuGroup;
import static kitchenpos.fixture.MenuProductFixture.*;
import static kitchenpos.fixture.ProductFixture.product;
import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class MenuServiceTest {

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuGroupRepository menuGroupRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Nested
    class 메뉴_생성_경우 {
        @Test
        void 유효한_메뉴_요청이면_정상적으로_생성된다() {
            // given
            MenuGroup menuGroup = menuGroup();
            menuGroupRepository.save(menuGroup);

            Product product = product();
            productRepository.save(product);

            MenuProduct menuProduct = menuProduct(seq(), DEFALUT_QUANTITY, product);

            Menu request = menu(MenuFixture.DEFAULT_MENU_NAME, DEFAULT_MENU_PRICE, menuGroup, List.of(menuProduct), DEFAULT_DISPLAYED);

            // when
            Menu expected = menuService.create(request);

            // then
            assertThat(expected.getName()).isEqualTo(request.getName());
            assertThat(expected.getPrice()).isEqualTo(request.getPrice());
            assertThat(expected.getMenuGroup().getId()).isEqualTo(request.getMenuGroupId());
            assertThat(expected.getMenuProducts()).hasSize(1);
        }

        @Test
        void 메뉴_가격이_구성_상품_총_합보다_높으면_예외가_발생한다() {
            // given
            MenuGroup menuGroup = menuGroup();
            menuGroupRepository.save(menuGroup);

            Product product = product();
            productRepository.save(product);

            MenuProduct menuProduct = menuProduct(seq(), DEFALUT_QUANTITY, product);

            Menu request = menu(MenuFixture.DEFAULT_MENU_NAME, 12_000, menuGroup, List.of(menuProduct), DEFAULT_DISPLAYED);

            // when & then
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> menuService.create(request));
        }

        @Test
        void 이름이_NULL이면_예외가_발생한다() {
            // given
            MenuGroup menuGroup = menuGroup();
            menuGroupRepository.save(menuGroup);

            Product product = product();
            productRepository.save(product);

            MenuProduct menuProduct = menuProduct(seq(), DEFALUT_QUANTITY, product);

            Menu request = menu(null, DEFAULT_MENU_PRICE, menuGroup, List.of(menuProduct), DEFAULT_DISPLAYED);

            // when & then
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> menuService.create(request));
        }

        @ParameterizedTest(name = "입력 값 `{0}`")
        @ValueSource(strings = {"bitch", "shit"})
        void 이름의_욕설이_포함되면_예외가_발생한다(final String name) {
            // given
            MenuGroup menuGroup = menuGroup();
            menuGroupRepository.save(menuGroup);

            Product product = product();
            productRepository.save(product);

            MenuProduct menuProduct = menuProduct(seq(), DEFALUT_QUANTITY, product);

            Menu request = menu(name, DEFAULT_MENU_PRICE, menuGroup, List.of(menuProduct), DEFAULT_DISPLAYED);

            // when & then
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> menuService.create(request));
        }

        @Test
        void 메뉴_상품이_NULL이면_예외가_발생한다() {
            // given
            MenuGroup menuGroup = menuGroup();
            menuGroupRepository.save(menuGroup);

            Product product = product();
            productRepository.save(product);

            Menu request = menu(MenuFixture.DEFAULT_MENU_NAME, DEFAULT_MENU_PRICE, menuGroup, null, DEFAULT_DISPLAYED);

            // when & then
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> menuService.create(request));
        }

        @Test
        void 메뉴_상품이_비어있다면_예외가_발생한다() {
            // given
            MenuGroup menuGroup = menuGroup();
            menuGroupRepository.save(menuGroup);

            Product product = product();
            productRepository.save(product);

            Menu request = menu(MenuFixture.DEFAULT_MENU_NAME, DEFAULT_MENU_PRICE, menuGroup, Collections.emptyList(), DEFAULT_DISPLAYED);

            // when & then
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> menuService.create(request));
        }

        @Test
        void 구성_상품의_수량이_음수이면_예외가_발생한다() {
            // given
            MenuGroup menuGroup = menuGroup();
            menuGroupRepository.save(menuGroup);

            Product product = product();
            productRepository.save(product);

            MenuProduct menuProduct = menuProduct(seq(), -1L, product);

            Menu request = menu(MenuFixture.DEFAULT_MENU_NAME, DEFAULT_MENU_PRICE, menuGroup, List.of(menuProduct), DEFAULT_DISPLAYED);

            // when & then
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> menuService.create(request));
        }
    }

    @Nested
    class 메뉴_표시_경우 {
        @Test
        void 구성_상품_총_합이_메뉴_가격_이상이면_메뉴를_표시할_수_있다() {
            // given
            MenuGroup menuGroup = menuGroup();
            menuGroupRepository.save(menuGroup);

            Product product = product();
            productRepository.save(product);

            MenuProduct menuProduct = menuProduct(seq(), DEFALUT_QUANTITY, product);

            Menu request = menu(MenuFixture.DEFAULT_MENU_NAME, DEFAULT_MENU_PRICE, menuGroup, List.of(menuProduct), false);
            Menu menu = menuService.create(request);

            // when
            Menu excepted = menuService.display(menu.getId());

            // then
            assertThat(excepted.isDisplayed()).isTrue();
        }

        @Test
        void 구성_상품_총_합이_메뉴_가격보다_낮으면_메뉴_표시_시_예외가_발생한다() {
            // given
            MenuGroup menuGroup = menuGroup();
            menuGroupRepository.save(menuGroup);

            Product product = product();
            productRepository.save(product);

            MenuProduct menuProduct = menuProduct(seq(), DEFALUT_QUANTITY, product);

            Menu menu = menu(MenuFixture.DEFAULT_MENU_NAME, 12_000, menuGroup, List.of(menuProduct), false);
            menu.setId(createMenuId());
            Menu excepted = menuRepository.save(menu);

            // when & then
            assertThatIllegalStateException()
                    .isThrownBy(() -> menuService.display(excepted.getId()));
        }
    }

    @Nested
    class 메뉴_숨김_경우 {
        @Test
        void 메뉴_숨김_처리는_정상적으로_수행된다() {
            // given
            MenuGroup menuGroup = menuGroup();
            menuGroupRepository.save(menuGroup);

            Product product = product();
            productRepository.save(product);

            MenuProduct menuProduct = menuProduct(seq(), DEFALUT_QUANTITY, product);

            Menu request = menu(MenuFixture.DEFAULT_MENU_NAME, DEFAULT_MENU_PRICE, menuGroup, List.of(menuProduct), DEFAULT_DISPLAYED);
            Menu created = menuService.create(request);

            // when
            Menu expected = menuService.hide(created.getId());

            // then
            assertThat(expected.isDisplayed()).isFalse();
        }
    }

    @Nested
    class 메뉴_조회_경우 {
        @Test
        void 전체_메뉴를_조회하면_생성된_모든_메뉴가_반환된다() {
            // given
            MenuGroup menuGroup = menuGroup();
            menuGroupRepository.save(menuGroup);

            Product product1 = product();
            Product product2 = product("후라이드 치킨", 14_000);
            Product product3 = product("양념 치킨", 14_000);
            productRepository.saveAll(List.of(product1, product2, product3));

            MenuProduct menuProduct1 = menuProduct(seq(), DEFALUT_QUANTITY, product1);
            MenuProduct menuProduct2 = menuProduct(seq(), DEFALUT_QUANTITY, product2);
            MenuProduct menuProduct3 = menuProduct(seq(), DEFALUT_QUANTITY, product3);

            Menu request1 = menu(DEFAULT_MENU_NAME, DEFAULT_MENU_PRICE, menuGroup, List.of(menuProduct1), DEFAULT_DISPLAYED);
            Menu request2 = menu(SECONDARY_MENU_NAME, SECONDARY_MENU_PRICE, menuGroup, List.of(menuProduct2, menuProduct3), DEFAULT_DISPLAYED);
            menuService.create(request1);
            menuService.create(request2);

            // when
            List<Menu> excepted = menuService.findAll();

            // then
            assertThat(excepted).hasSize(2);
        }
    }

}
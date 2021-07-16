package asd.group2.bms.repositoryImpl;

import asd.group2.bms.model.cards.credit.CreditCard;
import asd.group2.bms.model.cards.credit.CreditCardStatus;
import asd.group2.bms.repositoryMapper.CreditCardRowMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.util.ReflectionTestUtils;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreditCardRepositoryImplTest {

  @Mock
  private JdbcTemplate jdbcTemplate;

  @InjectMocks
  private CreditCardRepositoryImpl creditCardRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    ReflectionTestUtils.setField(creditCardRepository, "jdbcTemplate", jdbcTemplate);
  }

  @Test
  public void findByCreditCardStatusEqualsTest() {
    Long creditCardNumber = 1L;
    Integer totalPage = 1;

    when(jdbcTemplate.queryForObject(ArgumentMatchers.anyString(),
        ArgumentMatchers.any(),
        ArgumentMatchers.any(RowMapper.class)))
        .thenAnswer((invocation) -> {
          ResultSet rs = Mockito.mock(ResultSet.class);
          return totalPage;
        });

    CreditCard creditCard = new CreditCard();
    creditCard.setCreditCardNumber(creditCardNumber);

    List<CreditCard> cards = new ArrayList<>();
    cards.add(creditCard);

    when(jdbcTemplate.query(
        ArgumentMatchers.anyString(),
        (Object[]) ArgumentMatchers.any(),
        ArgumentMatchers.any(CreditCardRowMapper.class)))
        .thenReturn(cards);

    Pageable pageable = PageRequest.of(0, 1, Sort.Direction.ASC,
        "createdAt");

    Page<CreditCard> cardPage =
        creditCardRepository.findByCreditCardStatusEquals(CreditCardStatus.PENDING,
            pageable);

    assertEquals(totalPage, cardPage.getTotalPages());
  }

}

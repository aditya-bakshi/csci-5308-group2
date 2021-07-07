package asd.group2.bms.service;

import asd.group2.bms.exception.ResourceNotFoundException;
import asd.group2.bms.model.cards.credit.CreditCard;
import asd.group2.bms.model.cards.credit.CreditCardStatus;
import asd.group2.bms.model.resign.RequestStatus;
import asd.group2.bms.model.resign.ResignRequest;
import asd.group2.bms.payload.response.CreditCardListResponse;
import asd.group2.bms.payload.response.PagedResponse;
import asd.group2.bms.repository.CreditCardRepository;
import asd.group2.bms.util.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CreditCardService {
    @Autowired
    CreditCardRepository creditCardRepository;

    /**
     * @param creditCardStatus: Credit Card Status (PENDING, APPROVED, REJECTED)
     * @param page:          Page Number
     * @param size:          Size of the response data
     * @description: This will return all the credit cards having status resignStatus
     */
    public PagedResponse<CreditCardListResponse> getCreditCardListByStatus(CreditCardStatus creditCardStatus, int page, int size) {
        /** Making list in ascending order */
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "createdAt");
        Page<CreditCard> cards = creditCardRepository.findByCreditCardStatusEquals(creditCardStatus, pageable);

        if (cards.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), cards.getNumber(),
                    cards.getSize(), cards.getTotalElements(), cards.getTotalPages(), cards.isLast());
        }

        List<CreditCardListResponse> creditCardListResponses = cards.map(ModelMapper::mapCreditCardToCreditCardListResponse).getContent();

        return new PagedResponse<>(creditCardListResponses, cards.getNumber(),
                cards.getSize(), cards.getTotalElements(), cards.getTotalPages(), cards.isLast());
    }


    /**
     * @param creditCardNumber: credit card number
     * @return credit card based on credit card number
     */
    public CreditCard getCreditCardByCreditCardNumber(Long creditCardNumber) {
        return creditCardRepository.findById(creditCardNumber).orElseThrow(() -> new ResourceNotFoundException("Credit Card Number", "creditCardNumber", creditCardNumber));
    }

    /**
     * @param creditCardNumber: credit card number
     * @param creditCardStatus: Status of the credit card (APPROVED, REJECTED, PENDING)
     * @return the updated status of the credit card having credit card number - creditCardNumber
     */
    public CreditCard setCreditCardRequestStatus(Long creditCardNumber, CreditCardStatus creditCardStatus) {
        CreditCard creditCard = getCreditCardByCreditCardNumber(creditCardNumber);
        creditCard.setCreditCardStatus(creditCardStatus);
        return creditCardRepository.save(creditCard);
    }
}

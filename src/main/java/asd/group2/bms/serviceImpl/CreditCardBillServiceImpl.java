package asd.group2.bms.serviceImpl;

import asd.group2.bms.model.account.Account;
import asd.group2.bms.model.cards.credit.CreditCardBill;
import asd.group2.bms.repository.CreditCardBillRepository;
import asd.group2.bms.service.IAccountService;
import asd.group2.bms.service.ICreditCardBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditCardBillServiceImpl implements ICreditCardBillService {

    @Autowired
    CreditCardBillRepository CreditCard;

    @Autowired
    IAccountService accountService;


    public Boolean payCreditCardBill(Long accountNumber,Long billId) {
        Long creditCardNumber = CreditCard.getCreditCardNumber(accountNumber);
        Account account = accountService.getAccountByAccountNumber(accountNumber);
        Double balance = account.getBalance();
        Double billAmount = CreditCard.getBillAmount(creditCardNumber);
        if (balance >= billAmount)
        {
            return CreditCard.payCreditCardBill(billId);
        }
        return false;
    }

    public CreditCardBill getBills(Long creditCardNo)
    {
        return CreditCard.showBills(creditCardNo);
    }
}
package asd.group2.bms.serviceImpl;

import asd.group2.bms.model.account.Account;
import asd.group2.bms.model.cards.credit.CreditCardBill;
import asd.group2.bms.repository.ICreditCardBillRepository;
import asd.group2.bms.service.IAccountService;
import asd.group2.bms.service.ICreditCardBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreditCardBillServiceImpl implements ICreditCardBillService {

    @Autowired
    ICreditCardBillRepository CreditCard;

    @Autowired
    IAccountService accountService;

    /**
     * @param accountNumber: accountNumber of User
     * @param billId: billId of the CreditCardBill
     * @return the updated status of the credit card bill having bill no BillId
     */
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

    /**
     * @parm creditCardNo: CreditCardNo of User
     * @return the CreditCardBills associated with the Card
     */
    public Optional<CreditCardBill> getBills(Long creditCardNo)
    {
        return CreditCard.showBills(creditCardNo);
    }
}

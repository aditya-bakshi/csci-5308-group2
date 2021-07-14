package asd.group2.bms.controller;

import asd.group2.bms.model.account.Account;
import asd.group2.bms.model.cards.credit.CreditCard;
import asd.group2.bms.model.cards.credit.CreditCardStatus;
import asd.group2.bms.payload.request.CreditCardRequest;
import asd.group2.bms.payload.request.CreditCardSetPinRequest;
import asd.group2.bms.payload.request.UpdateCreditCardStatusRequest;
import asd.group2.bms.payload.response.ApiResponse;
import asd.group2.bms.payload.response.CreditCardListResponse;
import asd.group2.bms.payload.response.PagedResponse;
import asd.group2.bms.security.CurrentLoggedInUser;
import asd.group2.bms.security.UserPrincipal;
import asd.group2.bms.service.IAccountService;
import asd.group2.bms.service.ICreditCardService;
import asd.group2.bms.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api")
public class CreditCardController {

  @Autowired
  ICreditCardService creditCardService;

  @Autowired
  IAccountService accountService;

  /**
   * @param creditCardStatus: Credit card status
   * @description: Return all the credit cards having status creditCardStatus
   */
  @GetMapping("/services/creditcards")
  @RolesAllowed({"ROLE_MANAGER", "ROLE_EMPLOYEE"})
  public PagedResponse<CreditCardListResponse> getCreditCardByStatus(
      @RequestParam(value = "creditCardStatus") CreditCardStatus creditCardStatus,
      @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
      @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
    return creditCardService.getCreditCardListByStatus(creditCardStatus, page, size);
  }

  /**
   * @param updateCreditCardStatusRequest: credit card number and credit card status
   * @return success or failure response with appropriate message
   */
  @PutMapping("/services/creditcards")
  @RolesAllowed({"ROLE_MANAGER", "ROLE_EMPLOYEE"})
  public ResponseEntity<?> updateCreditCardRequestStatus(
      @Valid @RequestBody UpdateCreditCardStatusRequest updateCreditCardStatusRequest) throws MessagingException, UnsupportedEncodingException {
    Boolean isUpdated =
        creditCardService.setCreditCardRequestStatus(updateCreditCardStatusRequest.getCreditCardNumber(), updateCreditCardStatusRequest.getCreditCardStatus());
    if (isUpdated) {
      return ResponseEntity.ok(new ApiResponse(true, "Credit Card request status changed successfully!"));
    } else {
      return new ResponseEntity<>(new ApiResponse(false, "Something went wrong while changing Credit Card request status!"),
          HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * @param currentUser: current logged in user
   * @return it will create and return credit card information
   */
  @PostMapping("/services/creditcards")
  @RolesAllowed({"ROLE_USER"})
  public CreditCard createCreditCard(
      @CurrentLoggedInUser UserPrincipal currentUser,
      @Valid @RequestBody CreditCardRequest creditCardRequest) {
    Integer requestedTransactionLimit =
        creditCardRequest.getExpectedTransactionLimit();
    Long userId = currentUser.getId();

    Account account = accountService.getAccountByUserId(userId);

    return creditCardService.createCreditCard(account, requestedTransactionLimit);
  }

  /**
   * @param creditCardSetPinRequest: Request to set pin for credit card
   * @return True or false for updation
   */
  @PutMapping("/services/creditcards/pin")
  @RolesAllowed({"ROLE_USER"})
  public ResponseEntity<?> creditCardSetPin(
      @Valid @RequestBody CreditCardSetPinRequest creditCardSetPinRequest) {
    Boolean isUpdated =
        creditCardService.setCreditCardPin(creditCardSetPinRequest.getCreditCardNumber(),
            creditCardSetPinRequest.getPin());
    if (isUpdated) {
      return ResponseEntity.ok(new ApiResponse(true, "Pin updated successfully!"));
    } else {
      return new ResponseEntity<>(new ApiResponse(false, "Something went wrong while changing pin"),
          HttpStatus.BAD_REQUEST);
    }
  }
}

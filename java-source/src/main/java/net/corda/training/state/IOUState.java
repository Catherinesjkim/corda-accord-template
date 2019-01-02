package net.corda.training.state;

import net.corda.core.contracts.Amount;
import net.corda.core.contracts.ContractState;
import net.corda.core.contracts.LinearState;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.identity.Party;
import net.corda.core.identity.AbstractParty;
import java.util.*;
import com.google.common.collect.ImmutableList;
import net.corda.core.serialization.ConstructorForDeserialization;
import net.corda.finance.Currencies;
import org.accordproject.money.MonetaryAmount;
import org.accordproject.promissorynote.PromissoryNoteContract;
import org.accordproject.usa.business.BusinessEntity;

/**
 * This is where you'll add the definition of your state object. Look at the unit tests in [IOUStateTests] for
 * instructions on how to complete the [IOUState] class.
 *
 */
public class IOUState implements ContractState, LinearState {

    // TODO: `amount` and `principal` are both included in parsedJSON as a accord-project monetary amount.
    // They must be converted to the Corda Amount<Currency> class.
    public Amount<Currency> amount;
    public Date date;
    public String maker;
    public double interestRate;
    public boolean individual;
    public String makerAddress;
    public String lender;
    public BusinessEntity legalEntity;
    public String lenderAddress;
    public Amount<Currency> principal;
    public Date maturityDate;
    public int defaultDays;
    public int insolvencyDays;
    public String jurisdiction;
    public UniqueIdentifier linearId;
    public Amount<Currency> paid;
    public Party makerCordaParty;
    public Party lenderCordaParty;

    // Private constructor used only for copying a State object
    @ConstructorForDeserialization
    private IOUState(Amount<Currency> amount,
                     Date date,
                     String maker,
                     double interestRate,
                     boolean individual,
                     String makerAddress,
                     String lender,
                     BusinessEntity legalEntity,
                     String lenderAddress,
                     Amount<Currency> principal,
                     Date maturityDate,
                     int defaultDays,
                     int insolvencyDays,
                     String jurisdiction,
                     UniqueIdentifier linearId,
                     Amount<Currency> paid,
                     Party makerCordaParty,
                     Party lenderCordaParty
    ){
       this.amount = amount;
       this.date = date;
       this.maker = maker;
       this.interestRate = interestRate;
       this.individual = individual;
       this.makerAddress = makerAddress;
       this.lender = lender;
       this.legalEntity = legalEntity;
       this.lenderAddress = lenderAddress;
       this.principal = principal;
       this.maturityDate = maturityDate;
       this.defaultDays = defaultDays;
       this.insolvencyDays = insolvencyDays;
       this.jurisdiction = jurisdiction;
       this.linearId = linearId;
       this.paid = paid;
       this.makerCordaParty = makerCordaParty;
       this.lenderCordaParty = lenderCordaParty;
	}

	public IOUState(PromissoryNoteContract promissoryNoteContract, Party makerCordaParty, Party lenderCordaParty) {
        this(
                new Amount<Currency>((long) promissoryNoteContract.amount.doubleValue, Currencies.DOLLARS(0).getToken()),
                promissoryNoteContract.date,
                promissoryNoteContract.maker,
                promissoryNoteContract.interestRate,
                promissoryNoteContract.individual,
                promissoryNoteContract.makerAddress,
                promissoryNoteContract.lender,
                promissoryNoteContract.legalEntity,
                promissoryNoteContract.lenderAddress,
                new Amount<Currency>((long) promissoryNoteContract.principal.doubleValue, Currencies.DOLLARS(0).getToken()),
                promissoryNoteContract.maturityDate,
                promissoryNoteContract.defaultDays,
                promissoryNoteContract.insolvencyDays,
                promissoryNoteContract.jurisdiction,
                new UniqueIdentifier(),
                new Amount<Currency>((long) promissoryNoteContract.amount.doubleValue, Currencies.DOLLARS(0).getToken()),
                lenderCordaParty,
                makerCordaParty
        );
    }

    public Amount<Currency> getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public String getMaker() {
        return maker;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public boolean isIndividual() {
        return individual;
    }

    public String getMakerAddress() {
        return makerAddress;
    }

    public int getInsolvencyDays() {
        return insolvencyDays;
    }

    public String getLender() {
        return lender;
    }

    public String getLenderAddress() {
        return lenderAddress;
    }

    public Amount<Currency> getPrincipal() {
        return principal;
    }

    public Amount<Currency> getPaid() {
        return paid;
    }

    @Override
    public UniqueIdentifier getLinearId() {
	    return linearId;
    }

    // Participants included in the contract must also be CordaNodes.
   	@Override
    public List<AbstractParty> getParticipants() {
        return ImmutableList.of(makerCordaParty, lenderCordaParty);
    }

    public IOUState pay(Amount paidAmount) {
        Amount<Currency> newAmountPaid = this.paid.plus(paidAmount);
        return new IOUState(amount, date, maker, interestRate, individual, makerAddress, lender, legalEntity, lenderAddress, principal, maturityDate, defaultDays, insolvencyDays, jurisdiction, linearId, newAmountPaid, lenderCordaParty, makerCordaParty);
    }

    public IOUState copy(Amount<Currency> amount,
                         Date date,
                         String maker,
                         double interestRate,
                         boolean individual,
                         String makerAddress,
                         String lender,
                         BusinessEntity legalEntity,
                         String lenderAddress,
                         Amount<Currency> principal,
                         Date maturityDate,
                         int defaultDays,
                         int insolvencyDays,
                         String jurisdiction,
                         Amount<Currency> paid) {
        return new IOUState(amount, date, maker, interestRate, individual, makerAddress, lender, legalEntity, lenderAddress, principal, maturityDate, defaultDays, insolvencyDays, jurisdiction, this.getLinearId(), paid, lenderCordaParty, makerCordaParty);
    }

}
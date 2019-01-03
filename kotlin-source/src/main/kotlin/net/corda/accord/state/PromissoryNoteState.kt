package net.corda.accord.state

import net.corda.core.contracts.Amount
import net.corda.core.contracts.ContractState
import net.corda.core.contracts.LinearState
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.Party
import java.util.*

/**
 * This is where you'll add the definition of your state object. Look at the unit tests in [IOUStateTests] for
 * instructions on how to complete the [PromissoryNoteState] class.
 *
 * Remove the "val data: String = "data" property before starting the [PromissoryNoteState] tasks.
 */
data class PromissoryNoteState(
        val amount: Amount<Currency>,
        val lender: Party,
        val borrower: Party,
        val paid: Amount<Currency> = Amount(0, amount.token),
        override val linearId: UniqueIdentifier = UniqueIdentifier()
): ContractState, LinearState {
    override val participants: List<Party> get() = listOf(borrower, lender)
    fun pay(paidAmount: Amount<Currency>):
            PromissoryNoteState {
        return this.copy(paid = this.paid + paidAmount)
    }
    fun withNewLender(newLender: Party): PromissoryNoteState {
        return this.copy(lender = newLender)
    }
}
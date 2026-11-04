using Ewallet.Console.Models;

namespace Ewallet.Console.Interfaces;

public interface ITransactionService
{
    void TopUp(Wallet wallet, decimal amount, string description);
    void Transfer(string senderWalletId, string receiverWalletId, decimal amount);
    List<Mutation> GetMutationHistory(string walletId, TransactionTypeEnum? type = null);
}
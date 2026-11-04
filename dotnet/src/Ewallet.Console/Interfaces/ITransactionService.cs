using Ewallet.Console.Models;

namespace Ewallet.Console.Interfaces;

public interface ITransactionService
{
    void TopUp(string userId, decimal amount, string description);
    void Transfer(string senderId, string receiverId, decimal transferAmount);
    List<Mutation> GetMutationHistory(string walletId, TransactionTypeEnum? type = null);
}
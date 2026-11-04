using System;

using Ewallet.Console.Data;
using Ewallet.Console.Interfaces;
using Ewallet.Console.Models;

namespace Ewallet.Console.Services;

public class TransactionService : ITransactionService
{
    private readonly InMemoryDbContext _context;

    public TransactionService(InMemoryDbContext context)
    {
        _context=context;
    }

    public List<Mutation> GetMutationHistory(string walletId, TransactionTypeEnum? type = null)
    {
        return _context.Mutations.Where(w => w.Id == walletId).ToList();
    }

    public void TopUp(Wallet wallet, decimal amount, string description)
    {
        Transaction newTransaction = new Transaction();
        _context.Transactions.Add(newTransaction);

        Mutation newMutation = new Mutation(description, amount, TransactionTypeEnum.Income,wallet.Id,newTransaction.Id);
        _context.Mutations.Add(newMutation);

        System.Console.WriteLine($"\nWallet id: {wallet.Id}\nSaldo Awal: {wallet.Balance.ToString("C0")}\n");

        wallet.Debit(amount);

        System.Console.WriteLine($"--- Menjalankan Top Up {amount.ToString("C0")} ---");
        Thread.Sleep(2000);

        System.Console.WriteLine($"Top up berhasil! Saldo sekarang: {wallet.Balance.ToString("C0")}\n");
    }

    public void Transfer(string senderWalletId, string receiverWalletId, decimal amount)
    {
        throw new NotImplementedException();
    }
}
using System.Globalization;
using Ewallet.Console.Data;
using Ewallet.Console.Interfaces;
using Ewallet.Console.Models;
using System;

namespace Ewallet.Console.Services;

public class WalletService:IWalletService
{
    private readonly InMemoryDbContext _context;

    public WalletService(InMemoryDbContext context)
    {
        _context = context;
    }

    public Wallet? GetWalletByUserId(string userId)
    {
        return _context.Wallets.FirstOrDefault(w => w.UserId == userId);
    }

    public User? GetUserByUserId(string userId)
    {
        return _context.Users.FirstOrDefault(u => u.Id == userId);
    }

    public decimal CheckBalance(string userId)
    {
        var wallet = GetWalletByUserId(userId);

        return wallet is null ?  wallet.Balance : 0m ;
    }

    public void GetInformation(string userId)
    {
        System.Console.WriteLine("\n\n----- LIHAT SALDO -----");
        var formatToRupiah = CultureInfo.GetCultureInfo("id-ID");

        User? user = GetUserByUserId(userId);
        Wallet? wallet = GetWalletByUserId(userId);

        System.Console.WriteLine($"\nNomor Wallet: {wallet?.Id}");
        System.Console.WriteLine($"Nama Pemilik: {user?.FullName}");
        System.Console.WriteLine($"Saldo: {wallet?.Balance.ToString("C0", formatToRupiah)}\n");
    }

    public User? SelectUser(string userId)
    {
        User? user = GetUserByUserId(userId);
        
        if(user is null)
        {
            throw new ArgumentException("user not found");
        }
        // TODO: userId > users.count()
        // TODO: userId < 0

        return user;
    }

    public void TopUpExecute(string userId, decimal amount)
    {

        Wallet? wallet = GetWalletByUserId(userId) ?? throw new ArgumentException($"Sorry, wallet not found by user id: {userId}");

        if(wallet != null)
        {
            Transaction newTransaction = new Transaction();
            _context.Transactions.Add(newTransaction);

            Mutation newMutation = new Mutation("desc", amount, TransactionTypeEnum.Income,wallet.Id,newTransaction.Id);
            _context.Mutations.Add(newMutation);

            TransactionService transactionService = new TransactionService(_context);
            transactionService.TopUp(wallet, amount);

        }
    }
}
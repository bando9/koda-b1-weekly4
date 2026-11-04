using System;
using System.Globalization;
using Ewallet.Console.Data;
using Ewallet.Console.Interfaces;
using Ewallet.Console.Models;

namespace Ewallet.Console.Services;

public class TransactionService : ITransactionService
{
    private readonly InMemoryDbContext _context;
    private readonly WalletService _walletService;
    

    public TransactionService(InMemoryDbContext context)
    {
        _context=context;
        _walletService= new WalletService(_context);
    }

    public List<Mutation> GetMutationHistory(string walletId, TransactionTypeEnum? type = null)
    {
        return _context.Mutations.Where(w => w.Id == walletId).ToList();
    }

    public void TopUp(string userId, decimal amount, string description)
    {
        Wallet? wallet = _walletService.GetWalletByUserId(userId);
        System.Console.WriteLine(wallet.UserId);

        if(wallet != null)
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
    }

    public void Transfer(string senderId, string receiverId, decimal transferAmount)
    {
        Wallet? senderWallet = _walletService.GetWalletByUserId(senderId);
        Wallet? receiverWallet = _walletService.GetWalletByUserId(receiverId);
        User? receiver = _walletService.GetUserByUserId(receiverId);
        User? sender = _walletService.GetUserByUserId(senderId);

        System.Console.WriteLine("\n\n-------- TRANSFER --------");
        System.Console.WriteLine($"Nomor Wallet Tujuan: {receiverWallet.Id}\nPenerima: {receiver?.FullName}\nNominal Transfer: {transferAmount.ToString("C0")}");

        Transaction newTransaction = new();
        _context.Transactions.Add(newTransaction);

        Mutation senderMutation = new("deskripsi", transferAmount,TransactionTypeEnum.Expense,senderWallet.Id,newTransaction.Id); 
        
        Mutation receiverMutation = new("deskripsi", transferAmount,TransactionTypeEnum.Income,receiverWallet.Id,newTransaction.Id); 
        
        _context.Mutations.Add(senderMutation);
        _context.Mutations.Add(receiverMutation);

        System.Console.WriteLine("Konfirmasi (Y/N): ");
        string input = System.Console.ReadLine() ?? "";

        if(!input.Equals("Y", StringComparison.OrdinalIgnoreCase))
        {
            System.Console.WriteLine("Transaksi dibatalkan oleh pengguna.");
            return;
        }


        System.Console.WriteLine("Sedang melakukan transaksi...");
        
        Thread.Sleep(3000);

        System.Console.WriteLine("Transaksi Berhasil!\n");

        senderWallet.Credit(transferAmount);
        receiverWallet.Debit(transferAmount);

        System.Console.WriteLine($"Pengirim: {receiver.FullName}\nPenerima: {sender.FullName}\nNominal: {transferAmount.ToString("C0")}\nSisa Saldo: {senderWallet.Balance.ToString("C0")}");   
    }

    public decimal TotalTransactionIncome(string walletId)
    {
        List<Mutation> mutationsIncome = GetMutationByWalletId(walletId).Where(m => m.TransactionType == TransactionTypeEnum.Income).ToList();
        var totalIncome = mutationsIncome.Sum(m => m.Amount);

        return totalIncome;
    }

    public decimal TotalTransactionExpense(string walletId)
    {
        List<Mutation> mutationsExpense = GetMutationByWalletId(walletId).Where(m => m.TransactionType == TransactionTypeEnum.Expense).ToList();

        var totalExpense = mutationsExpense.Sum(m => m.Amount);

        return totalExpense;
    }

    public List<Mutation> GetMutationByWalletId(string walletId)
    {         
        return _context.Mutations.Where(m => m.WalletId == walletId).ToList();
    }

        public void GetMutationHistory(string userId)
    {
        TransactionService transactionService = new(_context);

        var budayaIndo = new CultureInfo("id-ID");

        System.Console.WriteLine("\n\n-------- RIWAYAT --------");
        Wallet? wallet = _walletService.GetWalletByUserId(userId);

        List<Mutation> mutationUser = GetMutationByWalletId(wallet.Id).OrderByDescending(m => m.CreatedAt).ToList();

        if(mutationUser.Count <= 0)
        {
            System.Console.WriteLine("Belum ada transaksi");
            return;
        }

        var i = 0;
        foreach(Mutation mutation in mutationUser)
        {
            i++;
            System.Console.WriteLine($"{i}. {mutation.Description} ({mutation.TransactionType}) = {mutation.Amount.ToString("C0")} ({mutation.CreatedAt.ToString("dd MMMM yyyy, HH:mm:tt", budayaIndo)})");
        }


        System.Console.WriteLine($"\nTotal Pemasukan: {transactionService.TotalTransactionIncome(wallet.Id).ToString("C0")}");
        System.Console.WriteLine($"Total Pengeluaran: {transactionService.TotalTransactionExpense(wallet.Id).ToString("C0")}");
    }
    
}
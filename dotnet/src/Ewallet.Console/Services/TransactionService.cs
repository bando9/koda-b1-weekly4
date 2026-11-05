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
    private readonly UserService _userService;
    

    public TransactionService(InMemoryDbContext context)
    {
        _context=context;
        _walletService= new WalletService(_context);
        _userService= new(_context);
    }

    public List<Mutation> GetMutationHistory(string walletId, TransactionTypeEnum? type = null)
    {
        return _context.Mutations.Where(w => w.Id == walletId).ToList();
    }

    public void TopUp(string userId, decimal amount, string description)
    {

        Wallet? wallet = _walletService.GetWalletByUserId(userId);

        if(wallet != null)
        {
            System.Console.WriteLine("\n\n-------- TOP UP --------");
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

    public void Transfer(string senderId, string receiverId, decimal transferAmount, string? description)
    {
        Wallet? senderWallet = _walletService.GetWalletByUserId(senderId) ?? throw new ArgumentException("Wallet not registered");
        Wallet? receiverWallet = _walletService.GetWalletByUserId(receiverId) ?? throw new ArgumentException("Wallet not registered");
        User? receiver = _userService.GetUserByUserId(receiverId) ?? throw new ArgumentException("Wallet not registered"); 
        User? sender = _userService.GetUserByUserId(senderId)?? throw new ArgumentException("Wallet not registered");

        if(senderWallet == receiverWallet)
        {
            throw new ArgumentException("Kmu gabisa ngirim diri sendiri.");
        }

        if(senderWallet.Balance < transferAmount)
        {
            throw new ArgumentException("Maaf saldo dompetmu kurang.\nTransaksi dibatalkan.");
        }

        System.Console.WriteLine("\n\n-------- TRANSFER --------");
        System.Console.WriteLine($"Nomor Wallet Tujuan: {receiverWallet.Id}\nPenerima: {receiver?.FullName}\nNominal Transfer: {transferAmount.ToString("C0")}");

        Transaction newTransaction = new();
        _context.Transactions.Add(newTransaction);

        Mutation senderMutation = new(description, transferAmount,TransactionTypeEnum.Expense,senderWallet.Id,newTransaction.Id); 
        
        Mutation receiverMutation = new(description, transferAmount,TransactionTypeEnum.Income,receiverWallet.Id,newTransaction.Id); 
        
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

        System.Console.WriteLine($"Pengirim: {sender?.FullName}\nPenerima: {receiver?.FullName}\nNominal: {transferAmount.ToString("C0")}\nSisa Saldo: {senderWallet.Balance.ToString("C0")}");   
    }

    public decimal TotalTransactionIncome(string walletId)
    {
        List<Mutation> mutationsIncome = GetMutationByWalletId(walletId).Where(m => m.TransactionType == TransactionTypeEnum.Income).ToList();
        decimal totalIncome = mutationsIncome.Sum(m => m.Amount);

        return totalIncome;
    }

    public decimal TotalTransactionExpense(string walletId)
    {
        List<Mutation> mutationsExpense = GetMutationByWalletId(walletId).Where(m => m.TransactionType == TransactionTypeEnum.Expense).ToList();

        decimal totalExpense = mutationsExpense.Sum(m => m.Amount);

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

        Wallet? wallet = _walletService.GetWalletByUserId(userId);

        if(wallet != null)
        {
            System.Console.WriteLine("\n(1) Income\n(2) Expense\n(3) Semua\nFilter mutasi (id):");
            string? filterMutations = System.Console.ReadLine();
            
            List<Mutation> mutations = filterMutations switch
            {
                "1"=> GetMutationIncome(wallet.Id),
                "2"=> GetMutationExpense(wallet.Id),
                _  => GetMutationAll(wallet.Id),

            };

        System.Console.WriteLine("\n\n-------- RIWAYAT --------");

        if(mutations.Count <= 0)
        {
            System.Console.WriteLine("Belum ada transaksi");
            return;
        }

        var i = 0;
        foreach(Mutation mutation in mutations)
        {
            i++;
            System.Console.WriteLine($"{i}. {mutation.Description} ({mutation.TransactionType}) = {mutation.Amount.ToString("C0")} ({mutation.CreatedAt.ToString("dd MMMM yyyy, HH:mm:tt", budayaIndo)})");
        }


        System.Console.WriteLine($"\nTotal Pemasukan: {transactionService.TotalTransactionIncome(wallet.Id).ToString("C0")}");
        System.Console.WriteLine($"Total Pengeluaran: {transactionService.TotalTransactionExpense(wallet.Id).ToString("C0")}");
        }
    }

    public List<Mutation> GetMutationIncome(string walletId)
    {   
        List<Mutation> mutationsIncome = GetMutationByWalletId(walletId).Where(m => m.TransactionType == TransactionTypeEnum.Income).OrderByDescending(m => m.CreatedAt).ToList();

        return mutationsIncome;
    }
    
    public List<Mutation> GetMutationExpense(string walletId)
    {   
        List<Mutation> mutationsExpense = GetMutationByWalletId(walletId).Where(m => m.TransactionType == TransactionTypeEnum.Expense).OrderByDescending(m => m.CreatedAt).ToList();

        return mutationsExpense;
    }

    public List<Mutation> GetMutationAll(string walletId)
    {
        List<Mutation> mutations = GetMutationByWalletId(walletId).OrderByDescending(m => m.CreatedAt).ToList();

        return mutations;
    }

}
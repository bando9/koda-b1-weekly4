using System.Globalization;
using Ewallet.Console.Data;
using Ewallet.Console.Interfaces;
using Ewallet.Console.Models;
using System;

namespace Ewallet.Console.Services;

public class WalletService:IWalletService
{
    private readonly InMemoryDbContext _context;
    private readonly UserService _userservice;

    public WalletService(InMemoryDbContext context)
    {
        _context = context;
        _userservice = new(_context);
    }

    public Wallet? GetWalletByUserId(string userId)
    {
        return _context.Wallets.FirstOrDefault(w => w.UserId == userId);
    }

    public decimal CheckBalance(string userId)
    {
        var wallet = GetWalletByUserId(userId) ?? throw new ArgumentException("wallet blm didaftarin");
        
        return wallet.Balance; 
    }

    public void GetInformation(string userId)
    {
        System.Console.WriteLine("\n\n-------- LIHAT SALDO --------");
        var formatToRupiah = CultureInfo.GetCultureInfo("id-ID");

        User? user = _userservice.GetUserByUserId(userId);
        Wallet? wallet = GetWalletByUserId(userId);

        System.Console.WriteLine($"\nNomor Wallet: {wallet?.Id}");
        System.Console.WriteLine($"Nama Pemilik: {user?.FullName}");
        System.Console.WriteLine($"Saldo: {wallet?.Balance.ToString("C0", formatToRupiah)}\n");
    }

    
}
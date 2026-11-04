using System.Globalization;

namespace Ewallet.Console.Models;

public class Wallet
{
    public string Id {get;private set;}
    public string AccountNumber {get;private set;}
    public string PINHash {get;private set;}
    public decimal Balance {get;private set;}
    public string UserId {get; private set;}
    public DateTime CreatedAt {get;set;} = DateTime.UtcNow;
    public DateTime UpdatedAt {get;set;} = DateTime.UtcNow;
    

    public Wallet(string id, string accountNumber, string pinHash, string userId, decimal balance = 0)
    {        
        Id=id;
        AccountNumber=accountNumber;
        PINHash=pinHash;
        Balance = balance >= 0 ? balance : throw new ArgumentException("Saldo gaboleh kurang dari 0");
        UserId=userId;
    }

    public string GetInformation()
    {
        var formatToRuipah = CultureInfo.GetCultureInfo("id-ID");
        return $"Nomor Akun: {AccountNumber}\nPemilik: {UserId}\nSaldo: Rp.{Balance.ToString("C0", formatToRuipah)} ";
    }

    public decimal Debit(decimal amount)
    {
        if(amount <= 0)
        {
            throw new ArgumentException("tambah saldo harus lebih dari 0");
        }
        
        return Balance+=amount;
    }
    public decimal Credit(decimal amount)
    {
        if(amount <= 0)
        {
            throw new ArgumentException("tambah saldo harus lebih dari 0");
        } 
        else if(amount > Balance)
        {
            throw new ArgumentException("maaf, saldo akunmu kurang");
        }
        UpdatedAt = DateTime.UtcNow;
        return Balance-=amount;
    }

    
}
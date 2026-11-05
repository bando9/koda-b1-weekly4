using Ewallet.Console.Models;

namespace Ewallet.Console.Interfaces;

public interface IWalletService
{
    void GetInformation (string userId);
    decimal CheckBalance(string userId);

}
namespace Ewallet.Console.Models;

public enum TransactionTypeEnum
{
    Income, 
    Expense
}


public class Mutation
{
    public string Id {get; private set;}
    public string? Description {get; private set;}
    public decimal Amount {get; private set;}
    public TransactionTypeEnum TransactionType {get; private set;}
    public string WalletId {get; private set;}
    public string TransactionId {get; private set;}
    public DateTime CreatedAt {get;set;} = DateTime.Now;
    public DateTime UpdatedAt {get;set;} = DateTime.Now;

    public Mutation(string? description, decimal amount, TransactionTypeEnum transactionType, string walletId, string transactionId)
    {
        Id = Guid.NewGuid().ToString();
        Description=description;
        Amount=amount;
        TransactionType=transactionType;
        WalletId = walletId;
        TransactionId=transactionId;
    }


}
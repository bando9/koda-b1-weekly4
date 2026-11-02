namespace Ewallet.Console.Models;

public sealed class Transaction
{
    public string Id {get;}
    public string ReferenceId {get;}
    public DateTime CreatedAt {get;set;} = DateTime.UtcNow;
    public DateTime UpdatedAt {get;set;} = DateTime.UtcNow;

    public Transaction(string id, string referenceId, DateTime createdAt, DateTime updatedAt)
    {
        Id = id;
        ReferenceId=referenceId;
        CreatedAt=createdAt;
        UpdatedAt=updatedAt;
    }

}
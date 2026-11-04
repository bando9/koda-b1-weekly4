namespace Ewallet.Console.Models;

public sealed class Transaction
{
    public string Id {get;}
    public string ReferenceId {get;}
    public DateTime CreatedAt {get;set;} = DateTime.UtcNow;
    public DateTime UpdatedAt {get;set;} = DateTime.UtcNow;

    public Transaction()
    {
        Id = Guid.NewGuid().ToString();
        ReferenceId= "REF-" +  DateTime.UtcNow.Ticks;
    }

    public string GetInformation()
    {
        return $"No Referensi: {ReferenceId}";
    }

}
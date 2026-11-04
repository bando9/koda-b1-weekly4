using Ewallet.Console.Models;

namespace Ewallet.Console.Data;

public class InMemoryDbContext
{
    public List<User> Users {get;set;}
    public List<Wallet> Wallets {get;set;}
    public List<Mutation> Mutations {get;set;} = new List<Mutation>();
    public List<Transaction> Transactions {get;set;} = new List<Transaction>();

    public InMemoryDbContext()
    {
        Users = new List<User>
        {
            new User("1","bandomega", "password_hash", "bandomega@gmail.com", "url_link_photo", "Bando Mega", "89530950881"),
            new User("2","keysa", "hash_pw_2", "keysa@email.com", "url_photo_2", "Keysa Maharani", "081200000002"),
            new User("3","dika", "hash_pw_3", "dika@email.com", "url_photo_3", "Dika Pratama", "081200000003"),
            new User("4","nayati", "hash_pw_4", "nayati@email.com", "url_photo_4", "Nayati Ayu", "081200000004"),
            new User("5","dany", "hash_pw_5", "dany@email.com", "url_photo_5", "Dany Setiawan", "081200000005")
        };

        Wallets = new List<Wallet>
        {
            new Wallet("W-001", "88990001", "hash_pin_1", "1", 1500000m),
            new Wallet("W-002", "88990002", "hash_pin_2", "2", 2500000m),
            new Wallet("W-003", "88990003", "hash_pin_3", "3", 500000m),
            new Wallet("W-004", "88990004", "hash_pin_4", "4", 3000000m),
            new Wallet("W-005", "88990005", "hash_pin_5", "5", 100000m),
        };

    }
}
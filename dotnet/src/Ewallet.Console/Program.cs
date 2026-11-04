using Ewallet.Console.Data;
using Ewallet.Console.Models;
using Ewallet.Console.Services;

System.Console.WriteLine("\n================================");
System.Console.WriteLine("          KODA E-WALLET         ");
System.Console.WriteLine("================================\n");


InMemoryDbContext context = new InMemoryDbContext();

WalletService walletService = new WalletService(context);
TransactionService transactionService = new(context);

List<string> menu = new List<string>
{
    "1. Lihat Saldo", "2. Top Up", "3. Transfer", "4. Riwayat Transaksi", "5. Logout"
};

foreach(User u in context.Users)
{
    System.Console.WriteLine($"{u.Id}. {u.FullName}");
}

System.Console.WriteLine("Pilih Pengguna (id): ");
String? selectedUserId = Console.ReadLine();


bool isRunningApp = true;

while (isRunningApp)
{

    User? user = walletService.SelectUser(selectedUserId);

    if(user is null)
    {
        System.Console.WriteLine("user tidak ditemukan\nExit...");
        return;
    }

    System.Console.WriteLine($"\n======== WALLET {user?.FullName} ========\n");
    
    // Show Menu Choises
    foreach(string m in menu)
    {
        System.Console.WriteLine(m);
    }


    System.Console.WriteLine("Silahkan pilih menu: ");
    string? selectedMenu = Console.ReadLine();

    switch (selectedMenu)
    {
        case "1":
            walletService.GetInformation(user.Id);
            break;
        case "2":
            System.Console.WriteLine("Mau Top up berapa: ");
            decimal topUpAmount = Convert.ToDecimal(Console.ReadLine());

            System.Console.WriteLine("Deskripsi tujuan top up: ");
            string? description = Console.ReadLine();

            transactionService.TopUp(user.Id, topUpAmount, description);
            break;
        case "3":
            System.Console.WriteLine();
            List<User> usersToTransfer = context.Users.Where(u => u.Id != user.Id).ToList();

            foreach(User u in usersToTransfer)
            {
                System.Console.WriteLine($"{u.Id}. {u.FullName}");
            }

            System.Console.WriteLine("Pilih siapa yg mau kmu kirim (id): ");
            string? receiverId = Console.ReadLine();
           
            System.Console.WriteLine("Mau kirim berapa: ");
            decimal transferAmount = Convert.ToDecimal(Console.ReadLine());

            transactionService.Transfer(user.Id, receiverId, transferAmount);
            
            break;
        case "4":
            transactionService.GetMutationHistory(user.Id);
            break;
        case "5":
            System.Console.WriteLine("Logout");
            isRunningApp=false;
            break;
        default:
            System.Console.WriteLine("Masukkan menu id (1-4)");
            break;
    }
}
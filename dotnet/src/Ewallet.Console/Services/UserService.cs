using Ewallet.Console.Data;
using Ewallet.Console.Models;

namespace Ewallet.Console.Services;

public class UserService
{
    private readonly InMemoryDbContext _context;

    public UserService(InMemoryDbContext context)
    {
        _context=context;
    }

    public User? SelectUser(string userId)
    {
        User? user = GetUserByUserId(userId);
        
        if(user is null)
        {
            System.Console.WriteLine("user not found");
        }

        return user;
    }

    public User? GetUserByUserId(string userId)
    {
        return _context.Users.FirstOrDefault(u => u.Id == userId);
    }

    public void ShowUser(string? userId)
    {
        List<User> users = _context.Users.Where(u => u.Id != userId).ToList();
        
        foreach(User u in _context.Users)
        {
            System.Console.WriteLine($"{u.Id}. {u.FullName}");
        }
        int nextMenuId = _context.Users.Count()+1;

        System.Console.WriteLine($"{nextMenuId}. Exit");
    }

    
}
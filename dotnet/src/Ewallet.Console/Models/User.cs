using System.ComponentModel.DataAnnotations;

namespace Ewallet.Console.Models;

public class User
{
    [Required]
    [StringLength(100)]
    public string Id { get; private set; }
    public string Username { get; private set; }
    [Required]
    [StringLength(50)]
    public string PasswordHash { get; private set; } 
    public string Email { get; private set; } 
    public string Photo { get; private set; } 
    public string FullName { get; private set; }
    public string Phone { get; private set; }
    public DateTime CreatedAt {get;set;} = DateTime.UtcNow;
    public DateTime UpdatedAt {get;set;} = DateTime.UtcNow;

    public User(string id,string username, string passwordHash, string email, string photo, string fullName, string phone)
    {
        Id=id;
        Username = username;
        PasswordHash=passwordHash;
        Email = email;
        Photo=photo;
        FullName=fullName;
        Phone=phone;
    }

    public string GetInformation()
    {
        return $"Full Name: {FullName}\nEmail: {Email}\nPhone: {Phone}\nPhoto: {Photo}\nUsername: {Username}";
    }


}



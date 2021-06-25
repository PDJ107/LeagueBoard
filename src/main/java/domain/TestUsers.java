package domain;

public class TestUsers {
    protected long id;
    protected String account;
    protected String password;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return "id : " + Long.toString(id) + "\naccount : " + account + "\npassword : " + password;
    }
}

package common.users;

import api.configs.Config;

public enum BankUsers {
    NAZAR_K(String.valueOf(Config.getProperty("user.login")), String.valueOf(Config.getProperty("user.password")));

    public String login;
    public String password;

    BankUsers(String login, String password){
        this.login = login;
        this.password = password;
    }
}

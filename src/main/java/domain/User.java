package domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class User {
    protected Long id;

    @NotNull(message = "acccount가 Null 입니다.")
    @Size(min = 1, max = 20, message = "account는 최소값 1과(와) 최대값 20 사이의 크기이어야 합니다.")
    protected String account; // 1 ~ 20

    @NotNull(message = "password가 Null 입니다.")
    @Size(min = 4, max = 20, message = "password는 최소값 4과(와) 최대값 20 사이의 크기이어야 합니다.")
    protected String password; // 4 ~ 20

    protected String summoner_name; // 3 ~ 16
    protected Long report_num;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getSummoner_name() {
        return summoner_name;
    }

    public void setSummoner_name(String summoner_name) {
        this.summoner_name = summoner_name;
    }

    public Long getReport_num() {
        return report_num;
    }

    public void setReport_num(Long report_num) {
        this.report_num = report_num;
    }
}

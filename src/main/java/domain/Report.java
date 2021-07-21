package domain;

import javax.validation.constraints.NotNull;

public class Report {
    protected Long id;
    protected Long victim_id;

    @NotNull(message = "perpetrator_id가 Null입니다.")
    protected Long perpetrator_id;

    @NotNull(message = "code가 Null입니다.")
    protected Long code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVictim_id() {
        return victim_id;
    }

    public void setVictim_id(Long victim_id) {
        this.victim_id = victim_id;
    }

    public Long getPerpetrator_id() {
        return perpetrator_id;
    }

    public void setPerpetrator_id(Long perpetrator_id) {
        this.perpetrator_id = perpetrator_id;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }
}

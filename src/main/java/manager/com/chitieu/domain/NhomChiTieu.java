package manager.com.chitieu.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A NhomChiTieu.
 */
@Entity
@Table(name = "nhom_chi_tieu")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NhomChiTieu implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "status")
    private String status;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "update_time")
    private String updateTime;

    @Column(name = "update_program")
    private String updateProgram;

    @ManyToOne
    @JsonIgnoreProperties("nhomchitieus")
    private KiCongBo kicongbo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public NhomChiTieu code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public NhomChiTieu name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public NhomChiTieu status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public NhomChiTieu userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public NhomChiTieu updateTime(String updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateProgram() {
        return updateProgram;
    }

    public NhomChiTieu updateProgram(String updateProgram) {
        this.updateProgram = updateProgram;
        return this;
    }

    public void setUpdateProgram(String updateProgram) {
        this.updateProgram = updateProgram;
    }

    public KiCongBo getKicongbo() {
        return kicongbo;
    }

    public NhomChiTieu kicongbo(KiCongBo kiCongBo) {
        this.kicongbo = kiCongBo;
        return this;
    }

    public void setKicongbo(KiCongBo kiCongBo) {
        this.kicongbo = kiCongBo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NhomChiTieu nhomChiTieu = (NhomChiTieu) o;
        if (nhomChiTieu.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nhomChiTieu.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NhomChiTieu{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            ", userName='" + getUserName() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", updateProgram='" + getUpdateProgram() + "'" +
            "}";
    }
}

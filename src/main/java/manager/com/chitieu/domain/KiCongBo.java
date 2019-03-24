package manager.com.chitieu.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A KiCongBo.
 */
@Entity
@Table(name = "ki_cong_bo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class KiCongBo implements Serializable {

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

    @OneToMany(mappedBy = "kicongbo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<NhomChiTieu> nhomchitieus = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("kicongbos")
    private ChiTieu chitieu;

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

    public KiCongBo code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public KiCongBo name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public KiCongBo status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public KiCongBo userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public KiCongBo updateTime(String updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateProgram() {
        return updateProgram;
    }

    public KiCongBo updateProgram(String updateProgram) {
        this.updateProgram = updateProgram;
        return this;
    }

    public void setUpdateProgram(String updateProgram) {
        this.updateProgram = updateProgram;
    }

    public Set<NhomChiTieu> getNhomchitieus() {
        return nhomchitieus;
    }

    public KiCongBo nhomchitieus(Set<NhomChiTieu> nhomChiTieus) {
        this.nhomchitieus = nhomChiTieus;
        return this;
    }

    public KiCongBo addNhomchitieu(NhomChiTieu nhomChiTieu) {
        this.nhomchitieus.add(nhomChiTieu);
        nhomChiTieu.setKicongbo(this);
        return this;
    }

    public KiCongBo removeNhomchitieu(NhomChiTieu nhomChiTieu) {
        this.nhomchitieus.remove(nhomChiTieu);
        nhomChiTieu.setKicongbo(null);
        return this;
    }

    public void setNhomchitieus(Set<NhomChiTieu> nhomChiTieus) {
        this.nhomchitieus = nhomChiTieus;
    }

    public ChiTieu getChitieu() {
        return chitieu;
    }

    public KiCongBo chitieu(ChiTieu chiTieu) {
        this.chitieu = chiTieu;
        return this;
    }

    public void setChitieu(ChiTieu chiTieu) {
        this.chitieu = chiTieu;
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
        KiCongBo kiCongBo = (KiCongBo) o;
        if (kiCongBo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), kiCongBo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "KiCongBo{" +
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

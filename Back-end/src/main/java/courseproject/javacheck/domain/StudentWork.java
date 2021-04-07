package courseproject.javacheck.domain;

import javax.persistence.*;

@Entity
@Table(name = "studentWork")
public class StudentWork {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String path;
    private Double mark;
    private Double originality;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Double getMark() {
        return mark;
    }

    public void setMark(Double mark) {
        this.mark = mark;
    }

    public Double getOriginality() {
        return originality;
    }

    public void setOriginality(Double originality) {
        this.originality = originality;
    }
}

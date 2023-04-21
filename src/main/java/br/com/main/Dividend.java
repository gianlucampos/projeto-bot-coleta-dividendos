package br.com.main;

import lombok.*;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Dividend {

    private String dtActive;
    private String nameActive;
    private String value;
    private String taxes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dividend dividend = (Dividend) o;
        return Objects.equals(dtActive, dividend.dtActive) && Objects.equals(nameActive, dividend.nameActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dtActive, nameActive);
    }

    @Override
    public String toString() {
        return "Dividend{" +
                "dtActive='" + dtActive + '\'' +
                ", nameActive='" + nameActive + '\'' +
                ", value='" + value + '\'' +
                ", taxes='" + taxes + '\'' +
                '}' + "\n";
    }
}

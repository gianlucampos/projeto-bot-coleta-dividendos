package br.com.main;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Dividend {

    private String dtActive;
    private String codeActive;
    private String value;
    private String taxes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dividend dividend = (Dividend) o;
        return Objects.equals(dtActive, dividend.dtActive) && Objects.equals(codeActive, dividend.codeActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dtActive, codeActive);
    }

    @Override
    public String toString() {
        return "Dividend{" +
                "dtActive='" + dtActive + '\'' +
                ", nameActive='" + codeActive + '\'' +
                ", value='" + value + '\'' +
                ", taxes='" + taxes + '\'' +
                '}' + "\n";
    }
}

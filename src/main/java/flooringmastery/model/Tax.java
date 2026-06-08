package flooringmastery.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Tax {
    private String stateAbbr;
    private String stateName;
    private BigDecimal taxRate;

    public Tax() {
    }

    public Tax(String stateAbbr) {
        this.stateAbbr = stateAbbr;
    }

    public String getStateAbbr() {
        return stateAbbr;
    }

    public void setStateAbbr(String stateAbbr) {
        this.stateAbbr = stateAbbr;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tax tax = (Tax) o;
        return Objects.equals(stateAbbr, tax.stateAbbr) && Objects.equals(stateName, tax.stateName)
                && Objects.equals(taxRate, tax.taxRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stateAbbr, stateName, taxRate);
    }

    @Override
    public String toString() {
        return "Tax{" +
                "stateAbbr='" + stateAbbr + '\'' +
                ", stateName='" + stateName + '\'' +
                ", taxRate='" + taxRate + '\'' +
                '}';
    }
}

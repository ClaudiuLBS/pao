import java.util.Enumeration;
import java.util.function.DoubleBinaryOperator;

public class CryptoCurrency extends Asset {
    protected Double stakingReturn;

    public CryptoCurrency() {

    }

    public CryptoCurrency(String name, String abb, Double value, Double stakingReturn) {
        super(name, abb, value);
        this.stakingReturn = stakingReturn;
    }

    public Double getStakingReturn() {
        return stakingReturn;
    }

    public void setStakingReturn(Double stakingReturn) {
        this.stakingReturn = stakingReturn;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nStakingReturn=" + stakingReturn;
    }
}

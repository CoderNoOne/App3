package model.statistics;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;


public class PriceCollector implements Collector<BigDecimal, PriceCollector.PriceSummary, /*BigDecimal*/ PriceCollector.PriceSummary> {

  public static class PriceSummary {

    private BigDecimal sum = BigDecimal.ZERO;
    private int n;
    private BigDecimal min;
    private BigDecimal max;

    public BigDecimal getSum() {
      return sum;
    }

    public void setSum(BigDecimal sum) {
      this.sum = sum;
    }

    public int getN() {
      return n;
    }

    public void setN(int n) {
      this.n = n;
    }

    public BigDecimal getMin() {
      return min;
    }

    public void setMin(BigDecimal min) {
      this.min = min;
    }

    public BigDecimal getMax() {
      return max;
    }

    public void setMax(BigDecimal max) {
      this.max = max;
    }
  }

  @Override
  public Supplier<PriceSummary> supplier() {
    return PriceSummary::new;
  }

  @Override
  public BiConsumer<PriceSummary, BigDecimal> accumulator() {
    return (a, p) -> {
      a.sum = a.sum.add(p);
      a.n += 1;
      a.setMin(a.getMin() == null ? p : a.getMin().compareTo(p) <= 0 ? a.getMin() : p);
      a.setMax(a.getMax() == null ? p : a.getMax().compareTo(p) >= 0 ? a.getMax() : p);
    };
  }

  @Override
  public BinaryOperator<PriceSummary> combiner() {
    return (a, b) -> {
      PriceSummary s = new PriceSummary();
      s.sum = a.sum.add(b.sum);
      s.n = a.n + b.n;

      s.max = a.getMax().compareTo(b.getMax()) >=0? a.getMax(): b.getMax();
      s.min = a.getMin().compareTo(b.getMin()) >=0? a.getMin(): b.getMin();

      return s;
    };
  }

  @Override
  public Function<PriceSummary, PriceSummary> finisher() {
    /*throw new UnsupportedOperationException();*/
    return s->s;

  }

  @Override
  public Set<Characteristics> characteristics() {
    return new HashSet<>((Arrays.asList(Characteristics.IDENTITY_FINISH)));
  }

}

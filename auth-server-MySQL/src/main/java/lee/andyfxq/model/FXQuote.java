package lee.andyfxq.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
public class FXQuote implements Comparable<FXQuote> {
	static final DecimalFormat f = new DecimalFormat("##.00000000");
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@NotBlank
	private String symbol;
	@NotNull
	@NotBlank
	private String tenor;
	private String px_str;
	private BigDecimal price;
	private long quotetime;

	public FXQuote() {
		this.quotetime = System.nanoTime();
	}
	
	public FXQuote(String symbol,String tenor, BigDecimal price) {
		this.symbol = symbol;
		this.tenor = tenor;
		this.px_str = f.format(price);
		this.price = price;
		this.quotetime = System.nanoTime();
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getPxStr() {
		return px_str;
	}
	
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.px_str = f.format(price);
		this.price = price;
	}

	public String getTenor() {
		return tenor;
	}

	public void setTenor(String tenor) {
		this.tenor = tenor;
	}

	public long getQuoteTime() {
		return quotetime;
	}

	public void setQuoteTime(long quoteTime) {
		this.quotetime = quoteTime;
	}

	public String toString() {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("symbol="+symbol+"|");
		strBuf.append("tenor="+tenor+"|");
		strBuf.append("price="+px_str+"|");
		strBuf.append("time="+quotetime+"||");
		
		return strBuf.toString();
	}

	@Override
	public int compareTo(FXQuote o) {
		return this.symbol.compareTo(((FXQuote)o).getSymbol());
	}
}

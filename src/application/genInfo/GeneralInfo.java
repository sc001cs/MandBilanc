package application.genInfo;


import java.math.BigDecimal;
import java.util.Date;

public class GeneralInfo {

	private String firma;
	private String idCard;
	private String nipt;
	private String perfaqesuesi;
	private BigDecimal totaliPlote;
	
	public GeneralInfo(String firma, String idCard, String nipt,
			String perfaqesuesi, BigDecimal totaliPlote) {
		super();
		this.firma = firma;
		this.idCard = idCard;
		this.nipt = nipt;
		this.perfaqesuesi = perfaqesuesi;
		this.totaliPlote = totaliPlote;
	}
	public String getFirma() {
		return firma;
	}
	public void setFirma(String firma) {
		this.firma = firma;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getNipt() {
		return nipt;
	}
	public void setNipt(String nipt) {
		this.nipt = nipt;
	}
	public String getPerfaqesuesi() {
		return perfaqesuesi;
	}
	public void setPerfaqesuesi(String perfaqesuesi) {
		this.perfaqesuesi = perfaqesuesi;
	}
	public BigDecimal getTotaliPlote() {
		return totaliPlote;
	}
	public void setTotaliPlote(BigDecimal totaliPlote) {
		this.totaliPlote = totaliPlote;
	}
	
	
	
	
}

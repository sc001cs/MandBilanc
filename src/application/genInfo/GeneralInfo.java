package application.genInfo;


import java.math.BigDecimal;

public class GeneralInfo {

	private String dataFatures;
	private String dataAktuale;
	private String kohevonesa;
	private String firma;
	private String idCard;
	private String nipt;
	private String perfaqesuesi;
	private BigDecimal totaliPlote;
	
	public GeneralInfo(String dataFatures, String dataAktuale, String kohevonesa, String firma, String idCard,
			String nipt, String perfaqesuesi, BigDecimal totaliPlote) {
		super();
		this.dataFatures = dataFatures;
		this.dataAktuale = dataAktuale;
		this.kohevonesa = kohevonesa;
		this.firma = firma;
		this.idCard = idCard;
		this.nipt = nipt;
		this.perfaqesuesi = perfaqesuesi;
		this.totaliPlote = totaliPlote;
	}
	public String getDataFatures() {
		return dataFatures;
	}
	public void setDataFatures(String dataFatures) {
		this.dataFatures = dataFatures;
	}
	public String getDataAktuale() {
		return dataAktuale;
	}
	public void setDataAktuale(String dataAktuale) {
		this.dataAktuale = dataAktuale;
	}
	public String getKohevonesa() {
		return kohevonesa;
	}
	public void setKohevonesa(String kohevonesa) {
		this.kohevonesa = kohevonesa;
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

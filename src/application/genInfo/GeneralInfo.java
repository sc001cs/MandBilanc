package application.genInfo;


import java.math.BigDecimal;
import java.util.Date;

public class GeneralInfo {

	private Date dataFatures;
	private Date dataAktuale;
	private Integer kohevonesa;
	private String firma;
	private String idCard;
	private String nipt;
	private String perfaqesuesi;
	private BigDecimal totaliPlote;
	
	public GeneralInfo(Date dataFatures, Date dataAktuale, Integer kohevonesa, String firma, String idCard, String nipt,
			String perfaqesuesi, BigDecimal totaliPlote) {
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
	public Date getDataFatures() {
		return dataFatures;
	}
	public void setDataFatures(Date dataFatures) {
		this.dataFatures = dataFatures;
	}
	public Date getDataAktuale() {
		return dataAktuale;
	}
	public void setDataAktuale(Date dataAktuale) {
		this.dataAktuale = dataAktuale;
	}
	public Integer getKohevonesa() {
		return kohevonesa;
	}
	public void setKohevonesa(Integer kohevonesa) {
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

/**
*
* Zehra Bak zehra.bak@sakarya.edu.tr
* @since 15.04.2023
* <p>
* parametre olarak girilen java dosyasını satır satır okuyan sınıf
* 
* </p>
*/
package pdp_odev_1;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;




public class DosyaOkuma  {
	    private String dosyaAdi;
	  
	    
	public DosyaOkuma(String dosyaAdi) {
		this.dosyaAdi=dosyaAdi;
		
	}
	
	public String getDosyaAdi() {
		return dosyaAdi;
	}
	String sonOkunanFonksiyonAdi = null;
	// Satır satır dosyayı okuyor yorum satırları ve dosya yazma sınıfından nesneleri parametre olarak alıyor.
	public void dosyaOku(YorumSatirlari yorumSatiriNesnesi,DosyaYazma tekliDosyayaYaz,DosyaYazma cokluDosyayaYaz,DosyaYazma javadocDosyayaYaz) {
		
		try (FileReader fReader=new FileReader(dosyaAdi);
			 BufferedReader bReader=new BufferedReader(fReader)){
			//her bir satır için yorum satırı nesnesinin satır oku fonksiyonunu çağırıyor.
			while((bReader.readLine())!=null) {
			 yorumSatiriNesnesi.satirOku(bReader,sonOkunanFonksiyonAdi,tekliDosyayaYaz,cokluDosyayaYaz,javadocDosyayaYaz);
			}
		} catch(IOException e) {
			System.err.format("Dosya okunamadı: %s%n",e);
		}
		
		
	}
	
	
}



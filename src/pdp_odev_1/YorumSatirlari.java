/**
*
* Zehra Bak zehra.bak@sakarya.edu.tr
* @since 15.04.2023
* <p>
* Yorum satırlarını bulan,ayıran ve dosya yazma sınıfının nesnesini
* parametre olarak alıp dosyaya yazdıran sınıf.
* </p>
*/
package pdp_odev_1;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;
import java.io.BufferedReader;
public class YorumSatirlari {
    private int tekliYorumSatiriSayisi=0 ;
    private int cokluYorumSatiriSayisi=0 ;
    private int javadocYorumSatiriSayisi=0;
    private String sonOkunanFonksiyonAdi = "";
    private boolean cokluYorumModu=false;
    private boolean javadocYorumModu=false;
    private boolean fonksiyonModu = false;
    // dosyayaya yazmadan önce yorumları string bir değişkende tutuyorum.
    String tekliYorumSatirlari = "";
    String cokluYorumSatirlari = "";
    String javadocYorumSatirlari = "";
    public int getTekliYorumSatiriSayisi() {
    	return tekliYorumSatiriSayisi;
    }
    
    public void setTekliYorumSatiriSayisi(int tekliYorumSatiriSayisi) {
    	this.tekliYorumSatiriSayisi = tekliYorumSatiriSayisi; 
    }
    
    public int getCokluYorumSatiriSayisi() {
    	return cokluYorumSatiriSayisi;
    }
    
    public void setCokluYorumSatiriSayisi(int cokluYorumSatiriSayisi) {
    	this.cokluYorumSatiriSayisi=cokluYorumSatiriSayisi;
    }
    
    public int getJavadocYorumSatiriSayisi() {
    	return javadocYorumSatiriSayisi;
    }
    
    public void setJavadocYorumSatiriSayisi(int javadocYorumSatiriSayisi) {
    	this.javadocYorumSatiriSayisi=javadocYorumSatiriSayisi;
    }
    
    public String getSonOkunanFonksiyonAdi() {
        return sonOkunanFonksiyonAdi;
    }
    public void setSonOkunanFonksiyonAdi(String sonOkunanFonksiyonAdi) {
    	this.sonOkunanFonksiyonAdi=sonOkunanFonksiyonAdi;
    }
     public void satirOku( BufferedReader bReader,String sonOkunanFonksiyonAdi,DosyaYazma tekliDosyayaYaz,DosyaYazma cokluDosyayaYaz,DosyaYazma javadocDosyayaYaz) {
       
       // regex ifadeleri tanımlıyorum.
     Pattern tekliYorumSatiri= Pattern.compile("//.*");
     Pattern cokluYorumSatiri=Pattern.compile("/\\*(.|\\s)*?\\*/", Pattern.DOTALL);
     Pattern javadocYorumSatiri= Pattern.compile("/\\*\\*(.|\\s)*?\\*/", Pattern.DOTALL);
     Pattern fonksiyonBaslangic = Pattern.compile("(public|private|protected)?\\s+\\w+\\s+\\w+\\s*\\([^)]*\\)");
     Pattern fonksiyonSonu=Pattern.compile("}\\s*$");
          
      String okunanSatir = null;

     do {
    	  try {
              okunanSatir = bReader.readLine();  
     
         
       if (okunanSatir == null) {
    	    break; 
    	                }  
         //okunan satırda bu regex ifadeler var mı diye bakıyorum.
         Matcher tekliEslesme=tekliYorumSatiri.matcher(okunanSatir);
         Matcher cokluEslesme=cokluYorumSatiri.matcher(okunanSatir);
         Matcher javadocEslesme=javadocYorumSatiri.matcher(okunanSatir);
         Matcher fonksiyonBaslangicEslesme = fonksiyonBaslangic.matcher(okunanSatir);
         Matcher fonksiyonSonuEslesme=fonksiyonSonu.matcher(okunanSatir);
         
         // javadoc esleşme true olduğunda javadoc yorum sayısı bir artıyor ve javadoc yorum modu true oluyor.
         if (javadocEslesme.find() || okunanSatir.contains("/**")) {
         	 setJavadocYorumSatiriSayisi(getJavadocYorumSatiriSayisi()+1);
         	 javadocYorumModu=true;
         	 javadocYorumSatirlari += okunanSatir + "\r\n";
        	 
         	 continue;
         }  
         else if(javadocYorumModu) {
        	 // javadoc yorum modu true iken satır * ile başlıyorsa javadocYorumSatirlarina okunan satırı ekliyorum
              if(okunanSatir.startsWith("* ") ) {	
            	  javadocYorumSatirlari += okunanSatir + "\r\n";
            
            	           		
         	  } 
              //javadoc yorum modu true iken okunan satır */ ile bitiyorsa javadoc yorumun son satırı demektir ve okunanSatirını javadocYorumSatirlarina ekliyorum.
              //en sonda ise dosya yazma sınıfının yaz metodunu çağırarak javadoc.txt dosyasına yazıyorum.
              else if(okunanSatir.endsWith("*/")) {
            	  javadocYorumSatirlari += okunanSatir + "\r\n";
         	      javadocYorumModu=false;
            	  javadocDosyayaYaz.yaz(javadocYorumSatirlari); 
         	      
          }
         }
         //fonksiyon baslangıcını regex ifadesiyle buluyorum ve fonksiyon baslangıcı bulunduğunda fonksiyonun adını alıyorum.
         // fonksiyon modunu ise true yapıyorum
         if (fonksiyonBaslangicEslesme.find()) {
        	 sonOkunanFonksiyonAdi=okunanSatir.trim().replace("{", "")+ "fonksiyonu";
        	 fonksiyonModu=true;
        	}
         
         else if(fonksiyonModu) {
        	//fonksiyonModu true iken tekli eslesme bulunursa veya tekli yorum satırı sayısını bir arttırıyorum.
        	 //tekliYorumSatirlarina okunan satırı ekliyorum ve yaz metoduyla yazdırıyorum dosyaya.
    	    if(tekliEslesme.find() || okunanSatir.contains("//")) {
    		      setTekliYorumSatiriSayisi(getTekliYorumSatiriSayisi()+1);
    		      tekliYorumSatirlari += okunanSatir + "\r\n";
    		      tekliDosyayaYaz.yaz(tekliYorumSatirlari);
    		      
    		      
    	    }
    	    //fonksiyonModu true iken coklu eslesme bulunursa coklu yorum satırı sayısını bir arttırıyorum ve çoklu yorum modunu true yapıyorum.
    	    // cokluYorumSatirlari na okunan satırı ekliyorum.
    	    
    	    else if(cokluEslesme.find() || okunanSatir.contains("/*")) {
    	    	   setCokluYorumSatiriSayisi(getCokluYorumSatiriSayisi()+1);
    	    	   cokluYorumModu=true;
    	    	   cokluYorumSatirlari += okunanSatir + "\r\n";
    	    	   
    	    	   
    	    }
    	    //çoklu yorum modunu true iken satır * içeriyorsa cokluYorumSatirlari na okunan satırı ekliyorum.
    	    
    	    else if (cokluYorumModu) {

    	     if(okunanSatir.startsWith("*") ) {
    	    	 cokluYorumSatirlari += okunanSatir + "\r\n";
    	    	
    	     }
    	   //çoklu yorum modunu true iken satır */ içeriyorsa cokluYorumSatirlari na okunan satırı ekliyorum ve coklu yorumun son satırındayım. Bu yüzdençoklu yorum modunu false yapıyorum.
    	     //en son coksatir.txt ye yazdırıyorum.
    	     else if((okunanSatir.endsWith("*/")) ) {
    	    	 cokluYorumSatirlari += okunanSatir + "\r\n";
    	    	   cokluYorumModu = false;
    	    	   cokluDosyayaYaz.yaz(cokluYorumSatirlari);
    	    	
    	     }    	    	
    	   }
    	    
         }
         //fonksiyon Sonu Eslesme bulunduğunda ve fonksiyonModu true iken fonksiyonun sonuna geldiği anlaşılıyor.
         //tüm yorum satırı sayılarını sıfırlıyorum.metinleri de sıfırlıyorum ki tekrar tekrar aynı şeyi dosyaya yazmasın.
          if(fonksiyonSonuEslesme.find() && fonksiyonModu) {
    	  //verileri yazdır fonksiyonunu çağırarak verileri ekrana yazdırıyorum.
        	  verileriYazdir(sonOkunanFonksiyonAdi);
        	  fonksiyonModu=false;
        	  setTekliYorumSatiriSayisi(0);
        	  setCokluYorumSatiriSayisi(0);
        	  setJavadocYorumSatiriSayisi(0); 
        	  setSonOkunanFonksiyonAdi("");
        	  tekliYorumSatirlari="";
          	  cokluYorumSatirlari="";
        	  javadocYorumSatirlari="";
         }
    	  } catch (IOException e) {
    	         System.err.format("Dosya okunamadı: %s%n", e);
    	         break;
    	      }   
    	      
          
      } while (okunanSatir != null); 
     
     // tüm satırlar okunup gerekli satırlar dosyaya yazdırıldıktan sonra dosya yazmayı kapatıyorum.
     tekliDosyayaYaz.kapat();
     cokluDosyayaYaz.kapat();
     javadocDosyayaYaz.kapat();
    
    }
    
    public void verileriYazdir(String sonOkunanFonksiyonAdi) {
    	System.out.println(sonOkunanFonksiyonAdi);
    	System.out.println("Tekli yorum satırı sayısı: " + getTekliYorumSatiriSayisi());
        System.out.println("Çoklu yorum satırı sayısı: " + getCokluYorumSatiriSayisi());
    	System.out.println("Javadoc yorum satırı sayısı: " + getJavadocYorumSatiriSayisi());
    	System.out.println("-----------------------------------------------");
    }
     
     
 }
    



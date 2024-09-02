/**
*
* Zehra Bak zehra.bak@sakarya.edu.tr
* @since 15.04.2023
* <p>
* Yorum satırlarını dosyaya yazan sınıf
* </p>
*/

package pdp_odev_1;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DosyaYazma {
	public String yazilacakdosyaAdi;
    private BufferedWriter yazici;

    public DosyaYazma(String yazilacakdosyaAdi) {
        this.yazilacakdosyaAdi = yazilacakdosyaAdi;

        try {
            yazici = new BufferedWriter(new FileWriter(yazilacakdosyaAdi));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     //yazma metodu
    public void yaz(String yazi) {
        try {
            if (yazici != null) {
                yazici.write(yazi);
                yazici.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //dosya kapatma metodu
    public void kapat() {
        try {
            yazici.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

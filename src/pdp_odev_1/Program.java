/**
*
* Zehra Bak zehra.bak@sakarya.edu.tr
* @since 15.04.2023
* <p>
* Gerekli nesneleri oluşturup metotları çağıran main metodunun bulunduğu sınıf
* </p>
*/
package pdp_odev_1;
import java.io.*;
import java.util.regex.*;
public class Program {

	public static void main(String[] args) {
		String dosyaAdi=args[0];
		
	    DosyaYazma tekliDosyayaYaz=new DosyaYazma("C:\\Users\\pc\\Desktop\\pdp_odev_1\\teksatir.txt");
	    DosyaYazma cokluDosyayaYaz=new DosyaYazma("C:\\Users\\pc\\Desktop\\pdp_odev_1\\coksatir.txt");
	    DosyaYazma javadocDosyayaYaz=new DosyaYazma("C:\\Users\\pc\\Desktop\\pdp_odev_1\\javadoc.txt");
	    YorumSatirlari yorumSatiriNesnesi=new YorumSatirlari();
		DosyaOkuma dosyaoku = new DosyaOkuma(dosyaAdi);
		dosyaoku.dosyaOku(yorumSatiriNesnesi,tekliDosyayaYaz,cokluDosyayaYaz,javadocDosyayaYaz);
		
		File file = new File(dosyaAdi);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            StringBuilder sb = new StringBuilder();
            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = reader.readLine();
            }
            String fileAsString = sb.toString();
            Pattern pattern = Pattern.compile("(\\/\\/.*$)|\\/\\*(?:.|[\\n\\r])*?\\*\\/", Pattern.MULTILINE);
            Matcher matcher = pattern.matcher(fileAsString);
            BufferedWriter teksatir = new BufferedWriter(new FileWriter("C:\\Users\\pc\\Desktop\\pdp_odev_1\\teksatir.txt"));
            BufferedWriter coksatir = new BufferedWriter(new FileWriter("C:\\Users\\pc\\Desktop\\pdp_odev_1\\coksatir.txt"));
            BufferedWriter javadoc = new BufferedWriter(new FileWriter("C:\\Users\\pc\\Desktop\\pdp_odev_1\\javadoc.txt"));
            while (matcher.find()) {
                String match = matcher.group();
                if (match.startsWith("//")) {
                    teksatir.write(match.trim() + "\n");
                } else if (match.startsWith("/*")) {
                    if (match.contains("/**")) {
                        javadoc.write(match.trim() + "\n");
                    } else {
                        coksatir.write(match.trim() + "\n");
                    }
                }
            }
            reader.close();
            teksatir.close();
            coksatir.close();
            javadoc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
     
	}

}

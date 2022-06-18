import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

public class ZipFileReadingTest {
  ClassLoader classLoader = ZipFileReadingTest.class.getClassLoader();

  @Test
  @DisplayName("Reading files from ZIP archive")
  void zipTest() throws Exception {
    try (InputStream inStream = classLoader.getResourceAsStream("zip-example.zip")) {
      assert inStream != null;
      try (ZipInputStream zipStream = new ZipInputStream(inStream)) {
        ZipEntry entry;
        while ((entry = zipStream.getNextEntry()) != null) {

          if (entry.getName().contains("csv")) {
            CSVReader csvFileReader = new CSVReader(new InputStreamReader(zipStream, UTF_8));
            List<String[]> csvList = csvFileReader.readAll();
            assertThat(csvList).contains(
                    new String[]{"mary@example.com;9346;Mary;Jenkins"});
            assertThat(csvList).contains(
                    new String[]{"jamie@example.com;5079;Jamie;Smith"});

          } else if (entry.getName().contains("pdf")) {
            PDF pdfFile = new PDF(zipStream);
            assertThat(pdfFile.text).contains("Dummy PDF file");

          } else if (entry.getName().contains("xls")) {
            XLS xls = new XLS(zipStream);
            assertThat(xls.excel.getSheetAt(0).getRow(1)
                    .getCell(3).getStringCellValue()).contains("Female");
            assertThat(xls.excel.getSheetAt(0).getRow(1)
                    .getCell(6).getStringCellValue()).contains("15/10/2017");

          }
        }
      }
    }
  }
}